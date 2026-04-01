package com.firemerald.additionalplacements.client.models.rotated.neoforge;

import com.firemerald.additionalplacements.client.models.neoforge.PlacementModelWrapperImpl;
import com.firemerald.additionalplacements.client.models.rotated.BakedRotatedPlacementModel;
import com.firemerald.additionalplacements.client.models.BlockModelUtils;
import com.firemerald.additionalplacements.client.models.rotated.RotatedBlockModelPart;
import com.firemerald.additionalplacements.util.BlockRotation;
import net.minecraft.client.renderer.block.model.BlockModelPart;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.stream.Stream;

public class BakedRotatedPlacementModelImpl extends BakedRotatedPlacementModel implements PlacementModelWrapperImpl {
	public static BakedRotatedPlacementModel of(BlockState theirModelState, BlockRotation modelRotation, boolean rotatesTexture) {
		return new BakedRotatedPlacementModelImpl(theirModelState, modelRotation, rotatesTexture);
	}

	protected BakedRotatedPlacementModelImpl(BlockState theirModelState, BlockRotation modelRotation, boolean rotatesTexture) {
		super(theirModelState, modelRotation, rotatesTexture);
    }

	@Override
	public Stream<BlockModelPart> wrapParts(BlockAndTintGetter level, BlockPos pos, BlockState state, RandomSource random) {
		return getWrappedModel().collectParts(level, pos, BlockModelUtils.getModeledState(state), random).stream().map(toWrap -> RotatedBlockModelPart.of(toWrap, modelRotation, rotatesTexture));
	}

	@Override
	public @Nullable Object createGeometryKey(BlockAndTintGetter level, BlockPos pos, BlockState state, RandomSource random) {
		return this;
	}
}
