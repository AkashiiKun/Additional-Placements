package com.firemerald.additionalplacements.client.models;

import net.minecraft.client.renderer.block.model.BlockModelPart;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import org.jetbrains.annotations.NotNull;

public interface WrappedBlockModelPart extends BlockModelPart {
    BlockModelPart getWrapped();

    BlockModelPart getParticle();

    @Override
    default boolean useAmbientOcclusion() {
        return getWrapped().useAmbientOcclusion();
    }

    @Override
    default @NotNull TextureAtlasSprite particleIcon() {
        return getWrapped().particleIcon();
    }
}
