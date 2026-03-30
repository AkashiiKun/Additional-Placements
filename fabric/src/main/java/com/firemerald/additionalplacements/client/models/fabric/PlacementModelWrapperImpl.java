package com.firemerald.additionalplacements.client.models.fabric;

import com.firemerald.additionalplacements.client.models.PlacementModelWrapper;
import net.fabricmc.fabric.api.renderer.v1.mesh.QuadEmitter;
import net.fabricmc.fabric.impl.renderer.VanillaModelEncoder;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.function.Predicate;
import java.util.function.Supplier;

public interface PlacementModelWrapperImpl extends PlacementModelWrapper {
    @Override
    default void emitBlockQuads(QuadEmitter emitter, BlockAndTintGetter blockView, BlockState state, BlockPos pos, Supplier<RandomSource> randomSupplier, Predicate<@Nullable Direction> cullTest) {
        VanillaModelEncoder.emitBlockQuads(emitter, this, state, randomSupplier, cullTest);
    }

    @Override
    default void emitItemQuads(QuadEmitter emitter, Supplier<RandomSource> randomSupplier) {
        VanillaModelEncoder.emitItemQuads(emitter, this, null, randomSupplier);
    }
}
