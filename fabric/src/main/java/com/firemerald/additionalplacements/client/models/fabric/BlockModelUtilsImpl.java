package com.firemerald.additionalplacements.client.models.fabric;

import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.Direction;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3fc;

public class BlockModelUtilsImpl {
    public static BakedQuad transformed(BakedQuad originalQuad, Vector3fc[] newPos, long[] newTex, Vector3fc @Nullable [] newNorm, int @Nullable [] newColor, int tintIndex, Direction direction, TextureAtlasSprite sprite) {
        return new BakedQuad(
                newPos[0], newPos[1], newPos[2], newPos[3],
                newTex[0], newTex[1], newTex[2], newTex[3],
                tintIndex,
                direction,
                sprite,
                originalQuad.shade(),
                originalQuad.lightEmission()
        );
    }

    @Nullable
    public static Vector3fc[] getNorms(BakedQuad quad) {
        return null;
    }

    public static int @Nullable [] getColors(BakedQuad quad) {
        return null;
    }
}
