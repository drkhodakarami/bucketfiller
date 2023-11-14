package jiraiyah.bucketfiller.block;

import jiraiyah.bucketfiller.Reference;
import jiraiyah.bucketfiller.block.custom.*;
import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModBlocks
{
    public static final FillerBlock FILLER = (FillerBlock) registerBlock("filler",
            new FillerBlock(FabricBlockSettings.copyOf(Blocks.STONE).requiresTool()));

    private ModBlocks()
    {
        throw new AssertionError();
    }

    //region Helper Methods
    private static void addItemsToFunctionalItemGroup(FabricItemGroupEntries entries)
    {
        entries.add(FILLER);
    }

    private static Block registerBlock(String name, Block block)
    {
        registerBlockItem(name, block);
        return Registry.register(Registries.BLOCK, Reference.id(name), block);
    }

    private static Item registerBlockItem(String name, Block block)
    {
        return Registry.register(Registries.ITEM, Reference.id(name),
                new BlockItem(block, new FabricItemSettings()));
    }

    public static void register()
    {
        Reference.LOGGER.info(">>> Registering Blocks");

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.FUNCTIONAL).register(ModBlocks::addItemsToFunctionalItemGroup);
    }
    //endregion
}