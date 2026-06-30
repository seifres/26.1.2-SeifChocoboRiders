package seifres.seifchocoboriders.datagen;


import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.data.PackOutput;
import seifres.seifchocoboriders.Constants;
import seifres.seifchocoboriders.block.GysahlGreensCrop;
import seifres.seifchocoboriders.init.ModBlocks;
import seifres.seifchocoboriders.init.ModItems;
import net.minecraft.resources.Identifier;

import java.util.Optional;

import static seifres.seifchocoboriders.Constants.*;

public class SeifChocoboRidersModelProvider extends ModelProvider {
    public SeifChocoboRidersModelProvider(PackOutput output) {
        super(output, MOD_ID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        itemModels.generateFlatItem(ModItems.CHOCOBO_WHISTLE_BLACK.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.CHOCOBO_WHISTLE_BLUE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.CHOCOBO_WHISTLE_BROWN.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.CHOCOBO_WHISTLE_CYAN.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.CHOCOBO_WHISTLE_GRAY.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.CHOCOBO_WHISTLE_GREEN.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.CHOCOBO_WHISTLE_LIGHT_BLUE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.CHOCOBO_WHISTLE_LIGHT_GRAY.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.CHOCOBO_WHISTLE_LIME.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.CHOCOBO_WHISTLE_MAGENTA.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.CHOCOBO_WHISTLE_ORANGE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.CHOCOBO_WHISTLE_PINK.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.CHOCOBO_WHISTLE_PURPLE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.CHOCOBO_WHISTLE_RED.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.CHOCOBO_WHISTLE_WHITE.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.CHOCOBO_WHISTLE_YELLOW.get(), ModelTemplates.FLAT_ITEM);

        itemModels.generateFlatItem(ModItems.GYSAHL_GREENS.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.GYSAHL_GREENS_SEED.get(), ModelTemplates.FLAT_ITEM);


        blockModels.createTrivialBlock(ModBlocks.GYSAHL_GREENS_CRATE.block().get(), TexturedModel.CUBE_TOP);

        makeCrop(blockModels, ModBlocks.GYSAHL_GREENS_CROP.block().get(),"gysahl_greens_crop");



    }

    private static final TextureSlot TEXTURE_A = TextureSlot.create("gysahl_greens_crop");

    private void makeCrop(BlockModelGenerators blockModels, GysahlGreensCrop block, String textureNameA) {
        int maxAge = GysahlGreensCrop.MAX_AGE;

        var dispatch = PropertyDispatch.initial(GysahlGreensCrop.AGE);

        for (int age = 0; age <= maxAge; age++) {
            Identifier texture_A = Identifier.fromNamespaceAndPath(Constants.MOD_ID,
                    "block/" + textureNameA + "_stage" + age);
            Identifier modelId = Identifier.fromNamespaceAndPath(Constants.MOD_ID,
                    "block/" + textureNameA + "_stage" + age);

            ModelTemplate cropTemplate = new ModelTemplate(
                    Optional.of(Identifier.fromNamespaceAndPath(MOD_ID, "block/gysahlgreenscropcrosstemplate")),
                    Optional.empty(),
                    TEXTURE_A
            );

            cropTemplate.create(modelId,
                    new TextureMapping().put(TEXTURE_A, new Material(texture_A)),
                    blockModels.modelOutput
            );

            dispatch = dispatch.select(age, BlockModelGenerators.plainVariant(modelId));
        }

        blockModels.blockStateOutput.accept(
                MultiVariantGenerator.dispatch(block).with(dispatch)
        );
    }

}

