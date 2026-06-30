package seifres.seifchocoboriders.datagen;


import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.CropBlock;
import net.minecraft.world.level.block.state.properties.IntegerProperty;
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

        makeCrop(blockModels, ModBlocks.GYSAHL_GREENS_CROP.block().get(), GysahlGreensCrop.AGE, "gysahl_greens_crop");



    }

    private void makeCrop(BlockModelGenerators blockModels, GysahlGreensCrop block, IntegerProperty ageProperty, String textureName) {
        int maxAge = ageProperty.getPossibleValues().stream().mapToInt(i -> i).max().orElse(7);

        var dispatch = PropertyDispatch.initial(ageProperty);
        for (int age = 0; age <= maxAge; age++) {
            Identifier texture = Identifier.fromNamespaceAndPath(Constants.MOD_ID,
                    "block/" + textureName + "_stage" + age);
            Identifier modelId = Identifier.fromNamespaceAndPath(Constants.MOD_ID,
                    "block/" + textureName + "_stage" + age);

            ModelTemplate cropTemplate = new ModelTemplate(
                    Optional.of(Identifier.withDefaultNamespace("block/crop")),
                    Optional.empty(),
                    TextureSlot.CROP
            );

            cropTemplate.create(
                    modelId,
                    new TextureMapping().put(TextureSlot.CROP, new Material(texture)),
                    blockModels.modelOutput
            );

            dispatch = dispatch.select(age, BlockModelGenerators.plainVariant(modelId));
        }

        blockModels.blockStateOutput.accept(
                MultiVariantGenerator.dispatch(block).with(dispatch)
        );
    }

}

