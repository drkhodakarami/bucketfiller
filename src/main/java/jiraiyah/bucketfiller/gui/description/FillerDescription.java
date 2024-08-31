package jiraiyah.bucketfiller.gui.description;

import io.github.cottonmc.cotton.gui.SyncedGuiDescription;
import io.github.cottonmc.cotton.gui.widget.WItemSlot;
import io.github.cottonmc.cotton.gui.widget.WPlainPanel;
import io.github.cottonmc.cotton.gui.widget.WSprite;
import io.github.cottonmc.cotton.gui.widget.data.Insets;
import jiraiyah.bucketfiller.Reference;
import jiraiyah.bucketfiller.block.ModBlocks;
import jiraiyah.bucketfiller.block.entity.FillerBE;
import jiraiyah.bucketfiller.gui.ModScreenHandlers;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Items;
import net.minecraft.screen.ScreenHandlerContext;

public class FillerDescription extends SyncedGuiDescription
{
    private final ScreenHandlerContext context;

    public FillerBE loaderEntity;

    public FillerDescription(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context)
    {
        super(ModScreenHandlers.FILLER, syncId, playerInventory, getBlockInventory(context, FillerBE.TOTAL_SLOTS), null);

        this.context = context;

        this.context.run((world, pos) -> loaderEntity = (FillerBE) world.getBlockEntity(pos));

        this.setTitleVisible(false);

        WPlainPanel root = new WPlainPanel ();
        setRootPanel(root);

        root.setSize(190, 157);
        root.setInsets(Insets.ROOT_PANEL);

        WItemSlot base_input_slot = WItemSlot.of(blockInventory, FillerBE.BASE_INPUT_SLOT);
        WItemSlot base_output_slot = WItemSlot.of(blockInventory, FillerBE.BASE_OUTPUT_SLOT);

        base_input_slot.setInputFilter(itemStack -> itemStack.isOf(Items.BUCKET));
        base_output_slot.setInsertingAllowed(false);

        WSprite bucketInSlot = new WSprite(Reference.identifier("textures/gui/slot_bucket.png"));
        WSprite littleArrow = new WSprite(Reference.identifier("textures/gui/little_arrow_down.png"));

        root.add(base_input_slot, 79, 13);
        root.add(base_output_slot, 79, 59);

        root.add(bucketInSlot, 82, 15, 12, 14);
        root.add(littleArrow, 83, 40, 9, 8);

        root.add(this.createPlayerInventoryPanel(), 7, 85);

        root.validate(this);
    }

    @Override
    public boolean canUse(PlayerEntity entity)
    {
        return canUse(this.context, entity, ModBlocks.FILLER);
    }
}