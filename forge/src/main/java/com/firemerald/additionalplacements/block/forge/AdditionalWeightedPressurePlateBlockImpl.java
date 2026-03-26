package com.firemerald.additionalplacements.block.forge;

import com.firemerald.additionalplacements.block.AdditionalWeightedPressurePlateBlock;
import com.firemerald.additionalplacements.block.interfaces.IAdditionalBeaconBeamBlock;
import com.firemerald.additionalplacements.block.interfaces.forge.IForgeAdditionalPlacementBlock;
import net.minecraft.world.level.block.CarpetBlock;
import net.minecraft.world.level.block.WeightedPressurePlateBlock;

public class AdditionalWeightedPressurePlateBlockImpl extends AdditionalWeightedPressurePlateBlock implements IForgeAdditionalPlacementBlock<WeightedPressurePlateBlock> {
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
