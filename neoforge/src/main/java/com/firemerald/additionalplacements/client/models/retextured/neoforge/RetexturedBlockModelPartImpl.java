package com.firemerald.additionalplacements.client.models.retextured.neoforge;

import com.firemerald.additionalplacements.client.models.neoforge.WrappedBlockModelPartImpl;
import com.firemerald.additionalplacements.client.models.retextured.RetexturedBlockModelPart;
import net.minecraft.client.renderer.block.model.BlockModelPart;

import java.util.List;

public class RetexturedBlockModelPartImpl extends RetexturedBlockModelPart implements WrappedBlockModelPartImpl {
    public static RetexturedBlockModelPart of(BlockModelPart wrapped, List<BlockModelPart> originalModel) {
        return new RetexturedBlockModelPartImpl(wrapped, originalModel);
    }

    protected RetexturedBlockModelPartImpl(BlockModelPart wrapped, List<BlockModelPart> originalModel) {
        super(wrapped, originalModel);
    }
}
