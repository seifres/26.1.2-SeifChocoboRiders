package seifres.seifchocoboriders.datagen;


import net.minecraft.client.data.models.BlockModelGenerators;
import net.minecraft.client.data.models.ItemModelGenerators;
import net.minecraft.client.data.models.ModelProvider;
import net.minecraft.client.data.models.blockstates.MultiVariantGenerator;
import net.minecraft.client.data.models.blockstates.PropertyDispatch;
import net.minecraft.client.data.models.model.*;
import net.minecraft.client.renderer.item.ItemModel;
import net.minecraft.client.renderer.item.SelectItemModel;
import net.minecraft.client.renderer.item.properties.select.ComponentContents;
import net.minecraft.client.resources.model.sprite.Material;
import net.minecraft.data.PackOutput;
import net.minecraft.world.level.block.SlabBlock;
import seifres.seifchocoboriders.Constants;
import seifres.seifchocoboriders.block.GysahlGreensCrop;
import seifres.seifchocoboriders.entities.ChocoboVariant;
import seifres.seifchocoboriders.init.ModBlocks;
import seifres.seifchocoboriders.init.ModDataComponents;
import seifres.seifchocoboriders.init.ModItems;
import net.minecraft.resources.Identifier;

import java.util.*;

import static seifres.seifchocoboriders.Constants.*;

public class SeifChocoboRidersModelProvider extends ModelProvider {
    public SeifChocoboRidersModelProvider(PackOutput output) {
        super(output, MOD_ID);
    }

    @Override
    protected void registerModels(BlockModelGenerators blockModels, ItemModelGenerators itemModels) {
        Map<ChocoboVariant, String> textureSuffixes = new EnumMap<>(ChocoboVariant.class);
        textureSuffixes.put(ChocoboVariant.BLACK, "black");
        textureSuffixes.put(ChocoboVariant.BLUE, "blue");
        textureSuffixes.put(ChocoboVariant.BROWN, "brown");
        textureSuffixes.put(ChocoboVariant.CYAN, "cyan");
        textureSuffixes.put(ChocoboVariant.GRAY, "gray");
        textureSuffixes.put(ChocoboVariant.GREEN, "green");
        textureSuffixes.put(ChocoboVariant.LIGHTBLUE, "light_blue");
        textureSuffixes.put(ChocoboVariant.LIGHTGRAY, "light_gray");
        textureSuffixes.put(ChocoboVariant.LIME, "lime");
        textureSuffixes.put(ChocoboVariant.MAGENTA, "magenta");
        textureSuffixes.put(ChocoboVariant.ORANGE, "orange");
        textureSuffixes.put(ChocoboVariant.PINK, "pink");
        textureSuffixes.put(ChocoboVariant.PURPLE, "purple");
        textureSuffixes.put(ChocoboVariant.RED, "red");
        textureSuffixes.put(ChocoboVariant.WHITE, "white");
        textureSuffixes.put(ChocoboVariant.YELLOW, "yellow");

        ComponentContents<ChocoboVariant> whistleColorProperty =
                new ComponentContents<>(ModDataComponents.WHISTLE_COLOR.get());

        List<SelectItemModel.SwitchCase<ChocoboVariant>> cases = new ArrayList<>();
        ItemModel.Unbaked fallback = null;

        for (Map.Entry<ChocoboVariant, String> entry : textureSuffixes.entrySet()) {
            Identifier modelId = itemModels.createFlatItemModel(
                    ModItems.CHOCOBO_WHISTLE.get(), "_" + entry.getValue(), ModelTemplates.FLAT_ITEM);
            ItemModel.Unbaked model = ItemModelUtils.plainModel(modelId);
            cases.add(ItemModelUtils.when(entry.getKey(), model));
            if (entry.getKey() == ChocoboVariant.YELLOW) {
                fallback = model; // matches the item's default component value
            }
        }

        itemModels.itemModelOutput.accept(
                ModItems.CHOCOBO_WHISTLE.get(),
                ItemModelUtils.select(whistleColorProperty, fallback, cases)
        );

        itemModels.generateFlatItem(ModItems.CHOCOBO_TRAINING_WHIP.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.CHOCOBO_SCROLL_TRAINING_LEVEL_1.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.CHOCOBO_SCROLL_TRAINING_LEVEL_2.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.CHOCOBO_SCROLL_TRAINING_LEVEL_3.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.CHOCOBO_SCROLL_TRAINING_LEVEL_4.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.CHOCOBO_SCROLL_TRAINING_LEVEL_5.get(), ModelTemplates.FLAT_ITEM);

        itemModels.generateFlatItem(ModItems.GYSAHL_GREENS.get(), ModelTemplates.FLAT_ITEM);
        itemModels.generateFlatItem(ModItems.GYSAHL_GREENS_SEED.get(), ModelTemplates.FLAT_ITEM);

        createGysahlGreensBundleSlab(blockModels, ModBlocks.GYSAHL_GREENS_BUNDLE.block().get(), "gysahl_greens_bundle");
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

    public static final ModelTemplate COLUMN_SLAB_BOTTOM = new ModelTemplate(
            Optional.of(Identifier.fromNamespaceAndPath(MOD_ID, "block/template_column_slab_bottom")),
            Optional.empty(),
            TextureSlot.END, TextureSlot.SIDE);

    public static final ModelTemplate COLUMN_SLAB_TOP = new ModelTemplate(
            Optional.of(Identifier.fromNamespaceAndPath(MOD_ID, "block/template_column_slab_top")),
            Optional.empty(),
            TextureSlot.END, TextureSlot.SIDE);


    private void createGysahlGreensBundleSlab(BlockModelGenerators blockModels, SlabBlock block, String name) {
        TextureMapping columnMapping = TextureMapping.column(block);

        Identifier bottomId = Identifier.fromNamespaceAndPath(MOD_ID, "block/" + name + "_bottom");
        Identifier topId    = Identifier.fromNamespaceAndPath(MOD_ID, "block/" + name + "_top");

        COLUMN_SLAB_BOTTOM.create(bottomId, columnMapping, blockModels.modelOutput);
        COLUMN_SLAB_TOP.create(topId, columnMapping, blockModels.modelOutput);

        Identifier doubleId = ModelTemplates.CUBE_COLUMN.create(block, columnMapping, blockModels.modelOutput);

        blockModels.blockStateOutput.accept(
                BlockModelGenerators.createSlab(
                        block,
                        BlockModelGenerators.plainVariant(bottomId),
                        BlockModelGenerators.plainVariant(topId),
                        BlockModelGenerators.plainVariant(doubleId)
                )
        );

        blockModels.registerSimpleItemModel(block, bottomId);
    }

}

