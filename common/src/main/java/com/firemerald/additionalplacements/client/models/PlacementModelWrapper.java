package com.firemerald.additionalplacements.client.models;

import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import org.jetbrains.annotations.NotNull;

public interface PlacementModelWrapper extends BakedModel {
    BakedModel getWrappedModel();

    BakedModel getVisualModel();

    @Override
    default boolean useAmbientOcclusion() {
        return getVisualModel().useAmbientOcclusion();
    }

    @Override
    default boolean isGui3d() {
        return getWrappedModel().isGui3d();
    }

    @Override
    default boolean usesBlockLight() {
        return getVisualModel().usesBlockLight();
    }

    @Override
    default @NotNull TextureAtlasSprite getParticleIcon() {
        return getVisualModel().getParticleIcon();
    }

    @Override
    default @NotNull ItemTransforms getTransforms() {
        return getWrappedModel().getTransforms();
    }
}
