package com.firemerald.additionalplacements.client.models;

import net.minecraft.client.renderer.block.model.BlockModelPart;
import net.minecraft.client.renderer.block.model.BlockStateModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.util.RandomSource;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.stream.Stream;

public interface PlacementModelWrapper extends BlockStateModel {
    BlockStateModel getWrappedModel();

    BlockStateModel getParticleModel();

    Stream<BlockModelPart> wrapParts(RandomSource random);

    @Override
    default void collectParts(RandomSource random, List<BlockModelPart> output) {
        wrapParts(random).forEach(output::add);
    }

    @Override
    default @NotNull TextureAtlasSprite particleIcon() {
        return getParticleModel().particleIcon();
    }
}
