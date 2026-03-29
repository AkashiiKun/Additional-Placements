package com.firemerald.additionalplacements.client.models.neoforge;

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
                originalQuad.isShade(),
                originalQuad.hasAmbientOcclusion()
        );
    }

    public static int getIntOffset(VertexFormat format, VertexFormatElement element) {
        return format.getOffset(format.getElements().indexOf(element)) / 4;
    }
}
