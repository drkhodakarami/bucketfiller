package jiraiyah.bucketfiller.utils.block.entity;

import jiraiyah.bucketfiller.utils.interfaces.ImplementedInventory;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidConstants;
import net.fabricmc.fabric.api.transfer.v1.fluid.FluidVariant;
import net.fabricmc.fabric.api.transfer.v1.storage.base.SingleVariantStorage;
import net.fabricmc.fabric.api.transfer.v1.transaction.Transaction;
import net.minecraft.block.BlockState;
import net.minecraft.block.InventoryProvider;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluids;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.SidedInventory;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.Packet;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.RecipeType;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;

public abstract class BEWithInventory extends BlockEntity implements NamedScreenHandlerFactory, ExtendedScreenHandlerFactory, ImplementedInventory, InventoryProvider
{
    protected final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(totalSlots(), ItemStack.EMPTY);

    public BEWithInventory(BlockEntityType<?> type, BlockPos pos, BlockState state)
    {
        super(type, pos, state);
    }

    protected abstract int totalSlots();

    @Override
    public DefaultedList<ItemStack> getItems()
    {
        return inventory;
    }

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf)
    {
        buf.writeBlockPos(pos);
    }

    @Override
    public Text getDisplayName()
    {
        return Text.translatable(getCachedState().getBlock().getTranslationKey());
    }

    @Nullable
    @Override
    public abstract ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player);

    @Override
    public void markDirty()
    {
        world.updateListeners(pos, getCachedState(), getCachedState(), 3);
        super.markDirty();
    }

    @Nullable
    @Override
    public Packet<ClientPlayPacketListener> toUpdatePacket()
    {
        return BlockEntityUpdateS2CPacket.create(this);
    }

    @Override
    public NbtCompound toInitialChunkDataNbt()
    {
        return createNbt();
    }

    @Override
    public SidedInventory getInventory(BlockState state, WorldAccess world, BlockPos pos)
    {
        return this;
    }

    public abstract void tick(World world, BlockPos pos, BlockState state);

    protected boolean isItemStackEmptyBucket(int slotIndex)
    {
        return getStack(slotIndex).isOf(Items.BUCKET);
    }

    protected boolean isOutputSlotEmptyOrReceivable(int slotIndex)
    {
        return this.getStack(slotIndex).isEmpty() ||
                this.getStack(slotIndex).getCount() < this.getStack(slotIndex).getMaxCount();
    }

    private boolean canInsertItemIntoOutputSlot(Item item, int slotIndex)
    {
        return this.getStack(slotIndex).getItem() == item || this.getStack(slotIndex).isEmpty();
    }

    private boolean canInsertAmountIntoOutputSlot(ItemStack result, int slotIndex)
    {
        return this.getStack(slotIndex).getCount() + result.getCount() <= getStack(slotIndex).getMaxCount();
    }
}