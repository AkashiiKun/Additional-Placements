package com.firemerald.additionalplacements.client.models.neoforge;

import com.firemerald.additionalplacements.client.models.BlockModelUtils;
import com.firemerald.additionalplacements.client.models.WrappedBlockModelPart;
import net.minecraft.client.renderer.chunk.ChunkSectionLayer;
import net.minecraft.util.TriState;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.extensions.BlockModelPartExtension;

public interface WrappedBlockModelPartImpl extends WrappedBlockModelPart, BlockModelPartExtension {
    @Override
    default ChunkSectionLayer getRenderType(BlockState state) {
        return getVisual().getRenderType(BlockModelUtils.getModeledState(state));
    }

    @Override
    default TriState ambientOcclusion() {
        return getVisual().ambientOcclusion();
    }
}
