package com.firemerald.additionalplacements.client.models.fabric;

import com.firemerald.additionalplacements.client.fabric.IVertexFormatExtensions;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.blaze3d.vertex.VertexFormatElement;
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

    public static int getIntOffset(VertexFormat format, VertexFormatElement element) {
        return ((IVertexFormatExtensions) format).additionalplacements$getIntOffset(element);
    }
}
