package com.firemerald.additionalplacements.block.neoforge;

import com.firemerald.additionalplacements.block.AdditionalWeightedPressurePlateBlock;
import com.firemerald.additionalplacements.block.interfaces.IAdditionalBeaconBeamBlock;
import com.firemerald.additionalplacements.block.interfaces.neoforge.INeoForgeAdditionalPlacementBlock;
import net.minecraft.world.level.block.WeightedPressurePlateBlock;

public class AdditionalWeightedPressurePlateBlockImpl extends AdditionalWeightedPressurePlateBlock implements INeoForgeAdditionalPlacementBlock<WeightedPressurePlateBlock> {
    private AdditionalWeightedPressurePlateBlockImpl(WeightedPressurePlateBlock block) {
        super(block);
    }

    private static class BeaconBeamAdditionalWeightedPressurePlateBlockImpl extends AdditionalWeightedPressurePlateBlockImpl implements IAdditionalBeaconBeamBlock<WeightedPressurePlateBlock> {
        BeaconBeamAdditionalWeightedPressurePlateBlockImpl(WeightedPressurePlateBlock block) {
            super(block);
        }
    }

    public static AdditionalWeightedPressurePlateBlock ofNonBeaconBeam(WeightedPressurePlateBlock block) {
        return new AdditionalWeightedPressurePlateBlockImpl(block);
    }

    public static AdditionalWeightedPressurePlateBlock ofBeaconBeam(WeightedPressurePlateBlock block) {
        return new BeaconBeamAdditionalWeightedPressurePlateBlockImpl(block);
    }
}
