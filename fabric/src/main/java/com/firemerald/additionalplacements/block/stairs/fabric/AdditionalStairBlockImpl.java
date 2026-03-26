package com.firemerald.additionalplacements.block.stairs.fabric;

import com.firemerald.additionalplacements.block.interfaces.IAdditionalBeaconBeamBlock;
import com.firemerald.additionalplacements.block.stairs.AdditionalStairBlock;
import com.firemerald.additionalplacements.block.stairs.StairConnectionsType;
import net.minecraft.world.level.block.StairBlock;

public class AdditionalStairBlockImpl extends AdditionalStairBlock {
    private AdditionalStairBlockImpl(StairBlock block, StairConnectionsType connectionsType) {
        super(block, connectionsType);
    }

    private static class BeaconBeamAdditionalCarpetBlockImpl extends AdditionalStairBlockImpl implements IAdditionalBeaconBeamBlock<StairBlock> {
        BeaconBeamAdditionalCarpetBlockImpl(StairBlock block, StairConnectionsType connectionsType) {
            super(block, connectionsType);
        }
    }

    public static AdditionalStairBlock ofNonBeaconBeam(StairBlock block, StairConnectionsType connectionsType) {
        return new AdditionalStairBlockImpl(block, connectionsType);
    }

    public static AdditionalStairBlock ofBeaconBeam(StairBlock block, StairConnectionsType connectionsType) {
        return new BeaconBeamAdditionalCarpetBlockImpl(block, connectionsType);
    }
}
