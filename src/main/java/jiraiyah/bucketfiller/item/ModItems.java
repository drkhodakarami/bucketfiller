package jiraiyah.bucketfiller.item;

import jiraiyah.bucketfiller.Reference;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModItems
{
    //region CUSTOM ITEMS
    //public static final Item RUBY = registerItem("gem_ruby", new Item(new FabricItemSettings()));
    //endregion

    //region CUSTOM TOOLS
    /*public static final Item TOOL_COPPER_AXE = registerItem("tool_copper_axe",
            new AxeItem(ModToolMaterial.COPPER, 3, 1, new FabricItemSettings()));
    public static final Item TOOL_COPPER_HOE = registerItem("tool_copper_hoe",
            new HoeItem(ModToolMaterial.COPPER, 0, 0, new FabricItemSettings()));
    public static final Item TOOL_COPPER_PICKAXE = registerItem("tool_copper_pickaxe",
            new PickaxeItem(ModToolMaterial.COPPER, 1, 1f, new FabricItemSettings()));
    public static final Item TOOL_COPPER_SHOVEL = registerItem("tool_copper_shovel",
            new ShovelItem(ModToolMaterial.COPPER, 0, 0, new FabricItemSettings()));
    public static final Item TOOL_COPPER_SWORD = registerItem("tool_copper_sword",
            new SwordItem(ModToolMaterial.COPPER, 5, 3, new FabricItemSettings()));*/
    //endregion

    //region CUSTOM ARMOR
    /*public static final Item ARMOR_AMETHYST_HELMET = registerItem("armor_amethyst_helmet",
            new ModArmorItem(ModArmorMaterial.AMETHYST, ArmorItem.Type.HELMET, new FabricItemSettings()));
    public static final Item ARMOR_AMETHYST_CHESTPLATE = registerItem("armor_amethyst_chestplate",
            new ArmorItem(ModArmorMaterial.AMETHYST, ArmorItem.Type.CHESTPLATE, new FabricItemSettings()));
    public static final Item ARMOR_AMETHYST_LEGGINGS = registerItem("armor_amethyst_leggings",
            new ArmorItem(ModArmorMaterial.AMETHYST, ArmorItem.Type.LEGGINGS, new FabricItemSettings()));
    public static final Item ARMOR_AMETHYST_BOOTS = registerItem("armor_amethyst_boots",
            new ArmorItem(ModArmorMaterial.AMETHYST, ArmorItem.Type.BOOTS, new FabricItemSettings()));*/
    //endregion

    private ModItems()
    {
        throw new AssertionError();
    }

    private static Item registerItem(String name, Item item)
    {
        return Registry.register(Registries.ITEM, Reference.id(name), item);
    }

    public static void register()
    {
        Reference.LOGGER.info(">>> Registering Items");
    }
}