package jiraiyah.bucketfiller.block;

import jiraiyah.bucketfiller.Reference;
import jiraiyah.bucketfiller.block.custom.FillerBlock;
import jiraiyah.bucketfiller.block.entity.FillerBE;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;

public class ModBlockEntities
{
    public static BlockEntityType<FillerBE> FILLER =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, Reference.ModID + ":" + FillerBlock.ID.getPath(),
                              FabricBlockEntityTypeBuilder.create(FillerBE::new, ModBlocks.FILLER).build());

    public ModBlockEntities()
    {
        throw new AssertionError();
    }

    public static void register()
    {
        Reference.LOGGER.info(">>> Registering Block Entities");
    }
}