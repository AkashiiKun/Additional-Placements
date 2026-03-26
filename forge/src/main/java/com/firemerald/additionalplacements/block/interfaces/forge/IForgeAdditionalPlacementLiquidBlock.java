package com.firemerald.additionalplacements.block.interfaces.forge;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BucketPickup;
import net.minecraft.world.level.block.LiquidBlockContainer;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Optional;

public interface IForgeAdditionalPlacementLiquidBlock<T extends Block & BucketPickup & LiquidBlockContainer> extends IForgeAdditionalPlacementBlock<T>, BucketPickup, LiquidBlockContainer {
    @Override
    default Optional<SoundEvent> getPickupSound(BlockState blockState) {
        return this.getOtherBlock().getPickupSound(this.getModelState(blockState));
    }
}
