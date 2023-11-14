package jiraiyah.bucketfiller.gui;

import jiraiyah.bucketfiller.Reference;
import jiraiyah.bucketfiller.gui.description.FillerDescription;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;

public class ModScreenHandlers
{
    public static ScreenHandlerType FILLER = Registry.register(Registries.SCREEN_HANDLER, Reference.id("filler_screen"),
            new ExtendedScreenHandlerType((syncId, inventory, buf) -> new FillerDescription(syncId, inventory,
                ScreenHandlerContext.create(inventory.player.getWorld(), buf.readBlockPos()))));

    public static void register()
    {
        Reference.LOGGER.info(">>> Registering Screen Handlers");
    }
}