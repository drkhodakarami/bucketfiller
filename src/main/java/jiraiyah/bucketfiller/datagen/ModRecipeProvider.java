package jiraiyah.bucketfiller.datagen;

import jiraiyah.bucketfiller.Reference;
import jiraiyah.bucketfiller.block.ModBlocks;
import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.util.Identifier;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends FabricRecipeProvider
{

    public ModRecipeProvider(FabricDataOutput output, CompletableFuture<RegistryWrapper.WrapperLookup> registriesFuture)
    {
        super(output, registriesFuture);
    }

    @Override
    public void generate(RecipeExporter exporter)
    {
        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModBlocks.FILLER, 1)
                .pattern("OBD")
                .input('O', Items.OBSERVER)
                .input('B', Items.BUCKET)
                .input('D', Items.DISPENSER)
                .criterion(hasItem(Items.OBSERVER), conditionsFromItem(Items.OBSERVER))
                .criterion(hasItem(Items.BUCKET), conditionsFromItem(Items.BUCKET))
                .criterion(hasItem(Items.DISPENSER), conditionsFromItem(Items.DISPENSER))
                .offerTo(exporter, Reference.identifier(getRecipeName(ModBlocks.FILLER)));
    }
}