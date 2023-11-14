package jiraiyah.bucketfiller;

import jiraiyah.bucketfiller.gui.ModScreenHandlers;
import jiraiyah.bucketfiller.gui.description.FillerDescription;
import jiraiyah.bucketfiller.gui.screen.FillerScreen;
import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screen.ingame.HandledScreens;

public class ModClient implements ClientModInitializer
{
    @Override
    public void onInitializeClient()
    {
        //region SCREEN REGISTRATION
        //noinspection RedundantTypeArguments
        HandledScreens.<FillerDescription, FillerScreen>register(ModScreenHandlers.FILLER,
                (gui, inventory, title) -> new FillerScreen(gui, inventory.player, title));
        //endregion
    }
}