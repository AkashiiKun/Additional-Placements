package com.firemerald.additionalplacements.client.models.retextured.neoforge;

import com.firemerald.additionalplacements.client.models.neoforge.PlacementModelWrapperImpl;
import com.firemerald.additionalplacements.client.models.retextured.BakedRetexturedPlacementModel;
import com.firemerald.additionalplacements.client.models.BlockModelUtils;
import com.firemerald.additionalplacements.client.models.retextured.RetexturedBlockModelPart;
import net.minecraft.client.renderer.block.model.BlockModelPart;
import net.minecraft.client.renderer.block.model.BlockStateModel;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.stream.Stream;

public class BakedRetexturedPlacementModelImpl extends BakedRetexturedPlacementModel implements PlacementModelWrapperImpl {
	public static BakedRetexturedPlacementModel of(BlockStateModel ourModel, BlockState theirModelState) {
		return new BakedRetexturedPlacementModelImpl(ourModel, theirModelState);
	}

	protected BakedRetexturedPlacementModelImpl(BlockStateModel ourModel, BlockState theirModelState) {
		super(ourModel, theirModelState);
	}

	@Override
	public Stream<BlockModelPart> wrapParts(BlockAndTintGetter level, BlockPos pos, BlockState state, RandomSource random) {
		Stream<BlockModelPart> wrappedParts = getWrappedModel().collectParts(level, pos, state, random).stream();
		if (wasModelMissing()) return wrappedParts;
		else {
			List<BlockModelPart> theirParts = getVisualModel().collectParts(level, pos, BlockModelUtils.getModeledState(state), random);
			if (theirParts.isEmpty()) return Stream.empty();
			return wrappedParts.map(ourPart -> RetexturedBlockModelPart.of(ourPart, theirParts));
		}
	}

	@Override
	public @Nullable Object createGeometryKey(BlockAndTintGetter level, BlockPos pos, BlockState state, RandomSource random) {
		return this;
	}
}
