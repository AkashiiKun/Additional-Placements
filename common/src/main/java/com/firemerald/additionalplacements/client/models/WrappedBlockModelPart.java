package com.firemerald.additionalplacements.client.models;

import net.minecraft.client.renderer.block.model.BlockModelPart;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import org.jetbrains.annotations.NotNull;

public interface WrappedBlockModelPart extends BlockModelPart {
    BlockModelPart getWrapped();

    BlockModelPart getVisual();

    @Override
    default boolean useAmbientOcclusion() {
        return getVisual().useAmbientOcclusion();
    }

    @Override
    default @NotNull TextureAtlasSprite particleIcon() {
        return getVisual().particleIcon();
    }
}
