package com.firemerald.additionalplacements.client.models.retextured;

import com.firemerald.additionalplacements.client.models.BlockModelUtils;
import com.firemerald.additionalplacements.client.models.WrappedBlockModelPart;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.BlockModelPart;
import net.minecraft.core.Direction;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class RetexturedBlockModelPart implements WrappedBlockModelPart {
    @ExpectPlatform
    public static RetexturedBlockModelPart of(BlockModelPart wrapped, List<BlockModelPart> originalModel) {
        throw new AssertionError();
    }

    public final BlockModelPart wrapped;
    public final List<BlockModelPart> originalModel;

    protected RetexturedBlockModelPart(BlockModelPart wrapped, List<BlockModelPart> originalModel) {
        this.wrapped = wrapped;
        this.originalModel = originalModel;
    }

    @Override
    public BlockModelPart getWrapped() {
        return wrapped;
    }

    @Override
    public @NotNull List<BakedQuad> getQuads(@Nullable Direction direction) {
        return BlockModelUtils.retexturedQuads(originalModel, wrapped, direction);
    }
}
