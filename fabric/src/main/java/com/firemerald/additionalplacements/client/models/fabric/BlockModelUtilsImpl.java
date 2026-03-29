package com.firemerald.additionalplacements.client.models.fabric;

import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;

public class BlockModelUtilsImpl {
    public static BakedQuad transformed(BakedQuad originalQuad, int[] vertexData, int tintIndex, Direction direction, TextureAtlasSprite sprite) {
        return new BakedQuad(
                vertexData,
                tintIndex,
                direction,
                sprite,
                originalQuad.isShade()
        );
    }
}
