package com.firemerald.additionalplacements.client.models.rotated.neoforge;

import com.firemerald.additionalplacements.client.models.BlockModelUtils;
import com.firemerald.additionalplacements.client.models.neoforge.WrappedBlockModelPartImpl;
import com.firemerald.additionalplacements.client.models.rotated.RotatedBlockModelPart;
import com.firemerald.additionalplacements.util.BlockRotation;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BlockModelPart;
import net.minecraft.world.level.block.state.BlockState;

public class RotatedBlockModelPartImpl extends RotatedBlockModelPart implements WrappedBlockModelPartImpl {
    public static RotatedBlockModelPart of(BlockModelPart wrapped, BlockRotation modelRotation, boolean rotatesTexture) {
        return new RotatedBlockModelPartImpl(wrapped, modelRotation, rotatesTexture);
    }

    public RotatedBlockModelPartImpl(BlockModelPart wrapped, BlockRotation modelRotation, boolean rotatesTexture) {
        super(wrapped, modelRotation, rotatesTexture);
    }

    @Override
    public RenderType getRenderType(BlockState state) {
        return getWrapped().getRenderType(BlockModelUtils.getModeledState(state));
    }
}
