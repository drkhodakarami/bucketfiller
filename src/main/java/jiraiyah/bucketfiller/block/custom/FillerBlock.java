package jiraiyah.bucketfiller.block.custom;

import com.mojang.serialization.MapCodec;
import jiraiyah.bucketfiller.Reference;
import jiraiyah.bucketfiller.block.ModBlockEntities;
import jiraiyah.bucketfiller.block.entity.FillerBE;
import net.minecraft.block.*;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityTicker;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemPlacementContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.BooleanProperty;
import net.minecraft.state.property.DirectionProperty;
import net.minecraft.state.property.Properties;
import net.minecraft.util.*;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.random.Random;
import net.minecraft.util.shape.VoxelShape;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class FillerBlock extends BlockWithEntity
{
    public static final MapCodec<FillerBlock> CODEC = createCodec(FillerBlock::new);

    public static final Identifier ID = Reference.identifier("filler");
    public static final DirectionProperty FACING = Properties.FACING;
    public static final BooleanProperty POWERED = Properties.POWERED;

    private static final VoxelShape SHAPE = Block.createCuboidShape(0, 0, 0, 16, 12, 16);

    public FillerBlock(Settings settings)
    {
        super(settings);
        this.setDefaultState(this.stateManager.getDefaultState().with(FACING, Direction.SOUTH));
        this.setDefaultState(this.stateManager.getDefaultState().with(POWERED, false));
    }

    @Override
    protected MapCodec<? extends BlockWithEntity> getCodec()
    {
        return CODEC;
    }

    @Nullable
    @Override
    public BlockEntity createBlockEntity(BlockPos pos, BlockState state)
    {
        return new FillerBE(pos, state);
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit)
    {
        player.openHandledScreen(state.createScreenHandlerFactory(world, pos));
        return ActionResult.SUCCESS;
    }

    @Nullable
    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(World world, BlockState state, BlockEntityType<T> type)
    {
        return validateTicker(type, ModBlockEntities.FILLER,
                (world1, pos, state1, blockEntity) -> blockEntity.tick(world1, pos, state1));
    }

    @Override
    public void onStateReplaced(BlockState state, World world, BlockPos pos, BlockState newState, boolean moved)
    {
        if (state.getBlock() != newState.getBlock())
        {
            BlockEntity blockEntity = world.getBlockEntity(pos);
            if (blockEntity instanceof FillerBE)
            {
                ItemScatterer.spawn(world, pos, (FillerBE) blockEntity);
                world.updateComparators(pos, this);
            }
            super.onStateReplaced(state, world, pos, newState, moved);
        }
    }

    @Override
    public BlockRenderType getRenderType(BlockState state)
    {
        return BlockRenderType.MODEL;
    }

    @Nullable
    @Override
    public BlockState getPlacementState(ItemPlacementContext ctx)
    {
        return this.getDefaultState().with(FACING, ctx.getPlayerLookDirection().getOpposite().getOpposite());
    }

    @Override
    public BlockState rotate(BlockState state, BlockRotation rotation)
    {
        return state.with(FACING, rotation.rotate(state.get(FACING)));
    }

    @Override
    public BlockState mirror(BlockState state, BlockMirror mirror)
    {
        return state.rotate(mirror.getRotation(state.get(FACING)));
    }

    @Override
    protected void appendProperties(StateManager.Builder<Block, BlockState> builder)
    {
        builder.add(FACING);
        builder.add(POWERED);
    }

    @Override
    public void scheduledTick(BlockState state, ServerWorld world, BlockPos pos, Random random)
    {
        world.setBlockState(pos, state, Block.NOTIFY_LISTENERS);
        world.scheduleBlockTick(pos, this, 2);
        this.updateNeighbors(world, pos, state);
    }

    @Override
    public boolean emitsRedstonePower(BlockState state)
    {
        return true;
    }

    @Override
    public int getStrongRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction)
    {
        return state.getWeakRedstonePower(world, pos, direction);
    }

    @Override
    public int getWeakRedstonePower(BlockState state, BlockView world, BlockPos pos, Direction direction)
    {
        if(state.get(POWERED) && direction != Direction.DOWN && direction != Direction.UP)
            return 15;
        return 0;
    }

    protected void updateNeighbors(World world, BlockPos pos, BlockState state)
    {
        Direction direction = state.get(FACING);
        BlockPos blockPos = pos.offset(direction.getOpposite());
        world.updateNeighbor(blockPos, this, pos);
        world.updateNeighbors(pos, this);
        world.updateNeighborsExcept(blockPos, this, direction);
    }
}