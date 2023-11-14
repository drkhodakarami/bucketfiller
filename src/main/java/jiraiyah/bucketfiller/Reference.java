package jiraiyah.bucketfiller;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.tag.TagKey;
import net.minecraft.util.Identifier;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Reference
{
    public static final String MOD_ID = "bucketfiller";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

    @NotNull
    public static Identifier id(@NotNull String path)
    {
        return new Identifier(MOD_ID, path);
    }

    public static class Tags
    {
        public static class Blocks
        {
            //public static final TagKey<Block> GEM_BLOCKS = createCommonTag("gem_blocks");
            //public static final TagKey<Block> HAMMER_NO_SMASHY = createTag("hammer_no_smashy");

            private static TagKey<Block> createTag(String name)
            {
                return TagKey.of(RegistryKeys.BLOCK, Reference.id(name));
            }

            private static TagKey<Block> createCommonTag(String name)
            {
                return TagKey.of(RegistryKeys.BLOCK, new Identifier("c", name));
            }
        }

        public static class Items
        {
            //public static final TagKey<Item> FLUID_BUCKETS = createCommonTag("fluid_buckets");

            private static TagKey<Item> createTag(String name)
            {
                return TagKey.of(RegistryKeys.ITEM, Reference.id(name));
            }

            private static TagKey<Item> createCommonTag(String name)
            {
                return TagKey.of(RegistryKeys.ITEM, new Identifier("c", name));
            }
        }
    }
}