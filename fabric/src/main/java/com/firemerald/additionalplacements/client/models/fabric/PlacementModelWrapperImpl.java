package com.firemerald.additionalplacements.client.models.fabric;

import com.firemerald.additionalplacements.client.models.PlacementModelWrapper;
import net.fabricmc.fabric.api.renderer.v1.model.WrapperBakedModel;
import net.fabricmc.fabric.api.renderer.v1.render.RenderContext;
import net.fabricmc.fabric.impl.renderer.VanillaModelEncoder;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public interface PlacementModelWrapperImpl extends PlacementModelWrapper, WrapperBakedModel {
    @Override
    default void emitBlockQuads(BlockAndTintGetter blockView, BlockState state, BlockPos pos, Supplier<RandomSource> randomSupplier, RenderContext context) {
        VanillaModelEncoder.emitBlockQuads(this, state, randomSupplier, context, context.getEmitter());
    }

    @Override
    default void emitItemQuads(ItemStack stack, Supplier<RandomSource> randomSupplier, RenderContext context) {
        VanillaModelEncoder.emitItemQuads(this, null, randomSupplier, context);
    }
}
