package jiraiyah.bucketfiller.block.entity;

import jiraiyah.bucketfiller.block.ModBlockEntities;
import jiraiyah.bucketfiller.block.ModBlocks;
import jiraiyah.bucketfiller.block.custom.FillerBlock;
import jiraiyah.bucketfiller.data.FillerData;
import jiraiyah.bucketfiller.gui.description.FillerDescription;
import jiraiyah.bucketfiller.utils.block.entity.BEWithInventory;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventories;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.state.property.Properties;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

public class FillerBE extends BEWithInventory
{
    public static final int TOTAL_SLOTS = 2;

    public static final int BASE_INPUT_SLOT = 0;
    public static final int BASE_OUTPUT_SLOT = 1;

    public FillerBE(BlockPos pos, BlockState state)
    {
        super(ModBlockEntities.FILLER, pos, state);
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player)
    {
        return new FillerDescription(syncId, playerInventory, ScreenHandlerContext.create(world, pos));
    }

    @Override
    public boolean canInsert(int slot, ItemStack stack, @Nullable Direction side)
    {
        Direction localDir = this.getWorld().getBlockState(this.pos).get(FillerBlock.FACING);

        // TOP --> RAW SLOT

        if (side == Direction.UP)
            return slot == BASE_INPUT_SLOT && StackAcceptableInSlot(stack, slot);
        if (side == Direction.DOWN)
            return false;

        // RIGHT --> TOOL SLOTS
        // LEFT --> LIQUIDATION SLOT

        return switch (localDir)
        {
            default -> side.getOpposite() == Direction.NORTH && slot == BASE_INPUT_SLOT && StackAcceptableInSlot(stack, slot);
            case EAST -> side.rotateYClockwise() == Direction.NORTH && slot == BASE_INPUT_SLOT && StackAcceptableInSlot(stack, slot);
            case SOUTH -> side == Direction.NORTH && slot == BASE_INPUT_SLOT && StackAcceptableInSlot(stack, slot);
            case WEST -> side.rotateYCounterclockwise() == Direction.NORTH && slot == BASE_INPUT_SLOT && StackAcceptableInSlot(stack, slot);
        };

    }

    @Override
    public boolean canExtract(int slot, ItemStack stack, Direction side)
    {
        Direction localDir = this.getWorld().getBlockState(this.pos).get(FillerBlock.FACING);
        if (side == Direction.DOWN)
            return slot == BASE_OUTPUT_SLOT;

        return switch (localDir)
        {
            default -> side.getOpposite() == Direction.SOUTH && slot == BASE_OUTPUT_SLOT;
            case EAST -> side.rotateYClockwise() == Direction.SOUTH && slot == BASE_OUTPUT_SLOT;
            case SOUTH -> side == Direction.SOUTH && slot == BASE_OUTPUT_SLOT;
            case WEST -> side.rotateYCounterclockwise() == Direction.SOUTH && slot == BASE_OUTPUT_SLOT;
        };
    }

    @Override
    public FillerData getScreenOpeningData(ServerPlayerEntity player)
    {
        return new FillerData(pos);
    }

    @Override
    protected void writeNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup)
    {
        super.writeNbt(nbt, registryLookup);
        Inventories.writeNbt(nbt, inventory, registryLookup);
    }

    @Override
    public void readNbt(NbtCompound nbt, RegistryWrapper.WrapperLookup registryLookup)
    {
        super.readNbt(nbt, registryLookup);
        Inventories.readNbt(nbt, inventory, registryLookup);
    }

    @Override
    protected int totalSlots()
    {
        return TOTAL_SLOTS;
    }

    @Override
    public void tick(World world, BlockPos pos, BlockState state)
    {
        if (world.isClient())
            return;

        if(state.get(FillerBlock.POWERED))
        {
            world.setBlockState(pos, state.with(FillerBlock.POWERED, false), Block.NOTIFY_ALL);
            return;
        }

        var offset = pos.offset(state.get(Properties.FACING), 1);
        var blockState = world.getBlockState(offset);
        var block = blockState.getBlock();
        if(((block == Blocks.WATER_CAULDRON || block == Blocks.POWDER_SNOW_CAULDRON) && blockState.get(Properties.LEVEL_3) == 3) ||
                block == Blocks.LAVA_CAULDRON)
        {
            if(isItemStackEmptyBucket(BASE_INPUT_SLOT) && isOutputSlotEmptyOrReceivable(BASE_OUTPUT_SLOT))
                tickInventory(world, offset, block, pos, state);
            world.updateNeighbors(pos, state.getBlock());
        }
        else if(block == Blocks.CAULDRON)
            world.updateNeighbors(pos, state.getBlock());
    }

    private void tickInventory(World world, BlockPos pos, Block block, BlockPos origin, BlockState state)
    {
        if(block == Blocks.WATER_CAULDRON)
            this.setStack(BASE_OUTPUT_SLOT, new ItemStack(Items.WATER_BUCKET, getStack(BASE_OUTPUT_SLOT).getCount() + 1));
        else if(block == Blocks.LAVA_CAULDRON)
            this.setStack(BASE_OUTPUT_SLOT, new ItemStack(Items.LAVA_BUCKET, getStack(BASE_OUTPUT_SLOT).getCount() + 1));
        else
            this.setStack(BASE_OUTPUT_SLOT, new ItemStack(Items.POWDER_SNOW_BUCKET, getStack(BASE_OUTPUT_SLOT).getCount() + 1));
        this.removeStack(BASE_INPUT_SLOT, 1);
        world.setBlockState(pos, Blocks.CAULDRON.getDefaultState(), Block.NOTIFY_ALL);
        world.setBlockState(origin, state.with(FillerBlock.POWERED, true), Block.NOTIFY_ALL);
        markDirty();
    }

    //region PRIVATE METHODS
    private boolean StackAcceptableInSlot(ItemStack stack, int slot)
    {
        if (slot == BASE_INPUT_SLOT)
            return StackIsLiquidable(stack);
        return false;
    }

    private boolean StackIsLiquidable(ItemStack stack)
    {
        return stack.isOf(Items.BUCKET);
    }
    //endregion
}