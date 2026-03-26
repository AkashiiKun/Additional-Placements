package com.firemerald.additionalplacements.client.models;

import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import org.jetbrains.annotations.NotNull;

public interface PlacementModelWrapper extends BakedModel {
    BakedModel getWrappedModel();

    BakedModel getParticleModel();

    @Override
    default boolean useAmbientOcclusion() {
        return getWrappedModel().useAmbientOcclusion();
    }

    @Override
    default boolean isGui3d() {
        return getWrappedModel().isGui3d();
    }

    @Override
    default boolean usesBlockLight() {
        return getWrappedModel().isGui3d();
    }

    @Override
    default boolean isCustomRenderer() {
        return getWrappedModel().isCustomRenderer();
    }

    @Override
    default @NotNull TextureAtlasSprite getParticleIcon() {
        return getParticleModel().getParticleIcon();
    }

    @Override
    default @NotNull ItemTransforms getTransforms() {
        return getWrappedModel().getTransforms();
    }

    @Override
    default @NotNull ItemOverrides getOverrides() {
        return getWrappedModel().getOverrides();
    }
}
