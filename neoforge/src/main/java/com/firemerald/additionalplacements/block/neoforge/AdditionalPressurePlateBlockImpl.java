package com.firemerald.additionalplacements.block.neoforge;

import com.firemerald.additionalplacements.block.AdditionalPressurePlateBlock;
import com.firemerald.additionalplacements.block.interfaces.IAdditionalBeaconBeamBlock;
import com.firemerald.additionalplacements.block.interfaces.neoforge.INeoForgeAdditionalPlacementBlock;
import net.minecraft.world.level.block.PressurePlateBlock;

public class AdditionalPressurePlateBlockImpl extends AdditionalPressurePlateBlock implements INeoForgeAdditionalPlacementBlock<PressurePlateBlock> {
    private AdditionalPressurePlateBlockImpl(PressurePlateBlock block) {
        super(block);
    }

    private static class BeaconBeamAdditionalPressurePlateBlockImpl extends AdditionalPressurePlateBlockImpl implements IAdditionalBeaconBeamBlock<PressurePlateBlock> {
        BeaconBeamAdditionalPressurePlateBlockImpl(PressurePlateBlock block) {
            super(block);
        }
    }

    public static AdditionalPressurePlateBlock ofNonBeaconBeam(PressurePlateBlock block) {
        return new AdditionalPressurePlateBlockImpl(block);
    }

    public static AdditionalPressurePlateBlock ofBeaconBeam(PressurePlateBlock block) {
        return new BeaconBeamAdditionalPressurePlateBlockImpl(block);
    }
}
