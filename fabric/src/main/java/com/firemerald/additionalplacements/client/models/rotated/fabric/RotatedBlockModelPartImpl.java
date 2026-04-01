package com.firemerald.additionalplacements.client.models.rotated.fabric;

import com.firemerald.additionalplacements.client.fabric.WrappedBlockModelPartImpl;
import com.firemerald.additionalplacements.client.models.rotated.RotatedBlockModelPart;
import com.firemerald.additionalplacements.util.BlockRotation;
import net.minecraft.client.renderer.block.model.BlockModelPart;

public class RotatedBlockModelPartImpl extends RotatedBlockModelPart implements WrappedBlockModelPartImpl {
    public static RotatedBlockModelPart of(BlockModelPart wrapped, BlockRotation modelRotation, boolean rotatesTexture) {
        return new RotatedBlockModelPartImpl(wrapped, modelRotation, rotatesTexture);
    }

    public RotatedBlockModelPartImpl(BlockModelPart wrapped, BlockRotation modelRotation, boolean rotatesTexture) {
        super(wrapped, modelRotation, rotatesTexture);
    }
}
