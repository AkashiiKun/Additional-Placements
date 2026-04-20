package com.firemerald.additionalplacements.client.models.rotated;

import com.firemerald.additionalplacements.client.models.BlockModelUtils;
import com.firemerald.additionalplacements.client.models.WrappedBlockModelPart;
import com.firemerald.additionalplacements.util.BlockRotation;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.client.renderer.block.dispatch.BlockStateModelPart;
import net.minecraft.client.resources.model.geometry.BakedQuad;
import net.minecraft.core.Direction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class RotatedBlockModelPart implements WrappedBlockModelPart {
    @ExpectPlatform
    public static RotatedBlockModelPart of(BlockStateModelPart wrapped, BlockRotation modelRotation, boolean rotatesTexture) {
        throw new AssertionError();
    }

    public final BlockStateModelPart wrapped;
    public final BlockRotation modelRotation;
    public final boolean rotatesTexture;

    public RotatedBlockModelPart(BlockStateModelPart wrapped, BlockRotation modelRotation, boolean rotatesTexture) {
        this.wrapped = wrapped;
        this.modelRotation = modelRotation;
        this.rotatesTexture = rotatesTexture;
    }

    @Override
    public BlockStateModelPart getWrapped() {
        return wrapped;
    }

    @Override
    public BlockStateModelPart getVisual() {
        return wrapped;
    }

    @Override
    public @NotNull List<BakedQuad> getQuads(@Nullable Direction direction) {
        return BlockModelUtils.rotatedQuads(wrapped, modelRotation, rotatesTexture, direction);
    }
}
