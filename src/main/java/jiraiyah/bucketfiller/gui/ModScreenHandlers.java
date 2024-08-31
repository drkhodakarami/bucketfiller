package jiraiyah.bucketfiller.gui;

import io.netty.buffer.ByteBuf;
import jiraiyah.bucketfiller.Reference;
import jiraiyah.bucketfiller.data.FillerData;
import jiraiyah.bucketfiller.gui.description.FillerDescription;
import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.ScreenHandlerType;

public class ModScreenHandlers
{
    public static ScreenHandlerType FILLER = Registry.register(Registries.SCREEN_HANDLER, Reference.identifier("filler_screen"),
            new ExtendedScreenHandlerType(
                    (syncId, inventory, data) -> new FillerDescription(syncId, inventory,
                                                ScreenHandlerContext.create(inventory.player.getWorld(), ((FillerData)data).pos())),
               FillerData.PACKET_CODEC));

    /*public static ScreenHandlerType FILLER = Registry.register(Registries.SCREEN_HANDLER, Reference.identifier("filler_screen"),
                                                               new ExtendedScreenHandlerType(
                                                                       (syncId, inventory, buf) -> new FillerDescription(syncId, inventory,ScreenHandlerContext.EMPTY),
                                                                       FillerData.PACKET_CODEC));*/

    public static void register()
    {
        Reference.LOGGER.info(">>> Registering Screen Handlers");
    }
}