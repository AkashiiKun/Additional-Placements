package com.firemerald.additionalplacements.client.models.neoforge;

import com.firemerald.additionalplacements.client.models.PlacementModelWrapper;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.renderer.block.model.BlockModelPart;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.extensions.BlockStateModelExtension;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.stream.Stream;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public interface PlacementModelWrapperImpl extends PlacementModelWrapper, BlockStateModelExtension {
    Stream<BlockModelPart> wrapParts(BlockAndTintGetter level, BlockPos pos, BlockState state, RandomSource random);

    @Override
    @Nullable
    Object createGeometryKey(BlockAndTintGetter level, BlockPos pos, BlockState state, RandomSource random);

    @Override
    default void collectParts(BlockAndTintGetter level, BlockPos pos, BlockState state, RandomSource random, List<BlockModelPart> parts) {
        wrapParts(level, pos, state, random).forEach(parts::add);
    }

    @Override
    default TextureAtlasSprite particleIcon(BlockAndTintGetter level, BlockPos pos, BlockState state) {
        return getParticleModel().particleIcon(level, pos, state);
    }
}
