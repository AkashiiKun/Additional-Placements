package com.firemerald.additionalplacements.client.models.retextured;

import com.firemerald.additionalplacements.client.models.BlockModelUtils;
import com.firemerald.additionalplacements.client.models.PlacementModelWrapper;
import com.firemerald.additionalplacements.client.models.Unwrapper;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BakedRetexturedPlacementModel implements PlacementModelWrapper {
	@ExpectPlatform
	public static BakedRetexturedPlacementModel of(BakedModel ourModel, BlockState theirModelState) {
		throw new AssertionError();
	}

	private BakedModel ourModel;
	private boolean ourModelMissing = false;
	private final BlockState theirModelState;
	private BakedModel theirModel;

	protected BakedRetexturedPlacementModel(BakedModel ourModel, BlockState theirModelState) {
		this.ourModel = ourModel;
		this.theirModelState = theirModelState;
	}

	@Override
	public BakedModel getWrappedModel() {
		if (ourModel == null) {
			ourModel = Unwrapper.unwrap(Minecraft.getInstance().getModelManager().getMissingModel());
			ourModelMissing = true;
		}
		return ourModel;
	}

	public boolean wasModelMissing() {
		return ourModelMissing;
	}

	@Override
	public BakedModel getVisualModel() {
		if (theirModel == null) {
			BakedModel model = Minecraft.getInstance().getBlockRenderer().getBlockModel(theirModelState);
			if (model == null) model = Minecraft.getInstance().getModelManager().getMissingModel();
			theirModel = Unwrapper.unwrap(model);
		}
		return theirModel;
	}

	@Override
	public @NotNull List<BakedQuad> getQuads(BlockState state, Direction side, @NotNull RandomSource rand) {
		BakedModel wrappedModel = getWrappedModel();
		if (wasModelMissing()) return wrappedModel.getQuads(state, side, rand);
		else {
			BakedModel theirModel = getVisualModel();
			BlockState modelState = BlockModelUtils.getModeledState(state);
			return BlockModelUtils.retexturedQuads(side, dir -> wrappedModel.getQuads(state, dir, rand), dir -> theirModel.getQuads(modelState, dir, rand), null);
		}
	}
}
