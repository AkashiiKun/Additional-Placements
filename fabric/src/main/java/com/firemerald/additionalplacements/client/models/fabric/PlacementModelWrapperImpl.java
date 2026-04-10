package com.firemerald.additionalplacements.client.models.fabric;

import com.firemerald.additionalplacements.client.models.BlockModelUtils;
import com.firemerald.additionalplacements.client.models.PlacementModelWrapper;
import net.fabricmc.fabric.api.renderer.v1.model.FabricBlockStateModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

public interface PlacementModelWrapperImpl extends PlacementModelWrapper, FabricBlockStateModel {
    @Override
    @Nullable
    Object createGeometryKey(BlockAndTintGetter blockView, BlockPos pos, BlockState state, RandomSource random);

    @Override
    default TextureAtlasSprite particleSprite(BlockAndTintGetter blockView, BlockPos pos, BlockState state) {
        return getVisualModel().particleSprite(blockView, pos, BlockModelUtils.getModeledState(state));
    }
}
