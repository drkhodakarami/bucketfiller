package jiraiyah.bucketfiller.item;

import jiraiyah.bucketfiller.Reference;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;

public class ModItemGroups
{
    public static final ItemGroup ATM_GROUP = Registry.register(Registries.ITEM_GROUP,
            Reference.id("modmdkgroup"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.modmdkgroup"))
                    .icon(() -> new ItemStack(Items.DIAMOND)).entries((displayContext, entries) ->
                    {
                        //entries.add(ModBlocks.CHUNK_LOADER);
                    }).build());

    public static void register()
    {
        Reference.LOGGER.info(">>> Registering Item Groups");
    }
}