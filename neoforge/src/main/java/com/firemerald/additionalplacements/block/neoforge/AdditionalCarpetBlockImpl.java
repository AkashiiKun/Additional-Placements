package com.firemerald.additionalplacements.block.neoforge;

import com.firemerald.additionalplacements.block.AdditionalCarpetBlock;
import com.firemerald.additionalplacements.block.interfaces.IAdditionalBeaconBeamBlock;
import com.firemerald.additionalplacements.block.interfaces.neoforge.INeoForgeAdditionalPlacementBlock;
import net.minecraft.world.level.block.CarpetBlock;

public class AdditionalCarpetBlockImpl extends AdditionalCarpetBlock implements INeoForgeAdditionalPlacementBlock<CarpetBlock> {
    private AdditionalCarpetBlockImpl(CarpetBlock block) {
        super(block);
    }

    private static class BeaconBeamAdditionalCarpetBlockImpl extends AdditionalCarpetBlockImpl implements IAdditionalBeaconBeamBlock<CarpetBlock> {
        BeaconBeamAdditionalCarpetBlockImpl(CarpetBlock block) {
            super(block);
        }
    }

    public static AdditionalCarpetBlock ofNonBeaconBeam(CarpetBlock block) {
        return new AdditionalCarpetBlockImpl(block);
    }

    public static AdditionalCarpetBlock ofBeaconBeam(CarpetBlock block) {
        return new BeaconBeamAdditionalCarpetBlockImpl(block);
    }
}
