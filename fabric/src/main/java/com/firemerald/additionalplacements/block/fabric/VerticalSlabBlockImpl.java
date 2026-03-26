package com.firemerald.additionalplacements.block.fabric;

import com.firemerald.additionalplacements.block.VerticalSlabBlock;
import com.firemerald.additionalplacements.block.interfaces.IAdditionalBeaconBeamBlock;
import net.minecraft.world.level.block.SlabBlock;

public class VerticalSlabBlockImpl extends VerticalSlabBlock {
    private VerticalSlabBlockImpl(SlabBlock block) {
        super(block);
    }

    private static class BeaconBeamVerticalSlabBlockImpl extends VerticalSlabBlockImpl implements IAdditionalBeaconBeamBlock<SlabBlock> {
        BeaconBeamVerticalSlabBlockImpl(SlabBlock block) {
            super(block);
        }
    }

    public static VerticalSlabBlock ofNonBeaconBeam(SlabBlock block) {
        return new VerticalSlabBlockImpl(block);
    }

    public static VerticalSlabBlock ofBeaconBeam(SlabBlock block) {
        return new BeaconBeamVerticalSlabBlockImpl(block);
    }
}
