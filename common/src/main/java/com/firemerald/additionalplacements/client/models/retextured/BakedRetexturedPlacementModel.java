package com.firemerald.additionalplacements.client.models.retextured;

import com.firemerald.additionalplacements.client.models.PlacementModelWrapper;
import com.firemerald.additionalplacements.client.models.Unwrapper;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BlockModelPart;
import net.minecraft.client.renderer.block.model.BlockStateModel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;
import java.util.stream.Stream;

public abstract class BakedRetexturedPlacementModel implements PlacementModelWrapper {
	@ExpectPlatform
	public static BakedRetexturedPlacementModel of(BlockStateModel ourModel, BlockState theirModelState) {
		throw new AssertionError();
	}

	private BlockStateModel ourModel;
	private boolean ourModelMissing = false;
	private final BlockState theirModelState;
	private BlockStateModel theirModel;

	protected BakedRetexturedPlacementModel(BlockStateModel ourModel, BlockState theirModelState) {
		this.ourModel = ourModel;
		this.theirModelState = theirModelState;
	}

	@Override
	public BlockStateModel getWrappedModel() {
		if (ourModel == null) {
			ourModel = Unwrapper.unwrap(Minecraft.getInstance().getModelManager().getMissingBlockStateModel());
			ourModelMissing = true;
		}
		return ourModel;
	}

	@Override
	public BlockStateModel getVisualModel() {
		if (theirModel == null) {
			BlockStateModel model = Minecraft.getInstance().getBlockRenderer().getBlockModel(theirModelState);
			if (model == null) model = Minecraft.getInstance().getModelManager().getMissingBlockStateModel();
			theirModel = Unwrapper.unwrap(model);
		}
		return theirModel;
	}

	public boolean wasModelMissing() {
		return ourModelMissing;
	}

	@Override
	public Stream<BlockModelPart> wrapParts(RandomSource random) {
		Stream<BlockModelPart> wrappedParts = getWrappedModel().collectParts(random).stream();
		if (wasModelMissing()) return wrappedParts;
		else {
			List<BlockModelPart> theirParts = getVisualModel().collectParts(random);
			if (theirParts.isEmpty()) return Stream.empty();
			return wrappedParts.map(ourPart -> RetexturedBlockModelPart.of(ourPart, theirParts));
		}
	}
}
