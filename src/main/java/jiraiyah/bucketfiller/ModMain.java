package jiraiyah.bucketfiller;

import jiraiyah.bucketfiller.block.ModBlocks;
import jiraiyah.bucketfiller.block.ModBlockEntities;
import jiraiyah.bucketfiller.gui.ModScreenHandlers;
import net.fabricmc.api.ModInitializer;

// This is the flat world gen custom preset I always use
// 5*minecraft:bedrock,30*minecraft:light_blue_wool,minecraft:light_blue_carpet;minecraft:plains;village

public class ModMain implements ModInitializer
{
    @Override
    public void onInitialize()
    {
        Reference.LOGGER.info(">>> Initializing");

        ModBlocks.register();
        ModBlockEntities.register();
        ModScreenHandlers.register();
    }
}