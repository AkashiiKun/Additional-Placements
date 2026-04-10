package com.firemerald.additionalplacements.client.models.rotated;

import com.firemerald.additionalplacements.client.models.BlockModelUtils;
import com.firemerald.additionalplacements.client.models.PlacementModelWrapper;
import com.firemerald.additionalplacements.client.models.Unwrapper;
import com.firemerald.additionalplacements.util.BlockRotation;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class BakedRotatedPlacementModel implements PlacementModelWrapper {
	@ExpectPlatform
	public static BakedRotatedPlacementModel of(BlockState theirModelState, BlockRotation modelRotation, boolean rotatesTexture) {
		throw new AssertionError();
	}

	public final BlockState theirModelState;
	private BakedModel theirModel;
	public final BlockRotation modelRotation;
	public final boolean rotatesTexture;

	protected BakedRotatedPlacementModel(BlockState theirModelState, BlockRotation modelRotation, boolean rotatesTexture) {
		this.theirModelState = theirModelState;
		this.modelRotation = modelRotation;
		this.rotatesTexture = rotatesTexture;
	}

	@Override
	public BakedModel getWrappedModel() {
		if (theirModel == null) {
			BakedModel model = Minecraft.getInstance().getBlockRenderer().getBlockModel(theirModelState);
			if (model == null) model = Minecraft.getInstance().getModelManager().getMissingModel();
			theirModel = Unwrapper.unwrap(model);
		}
		return theirModel;
	}

	@Override
	public BakedModel getVisualModel() {
		return getWrappedModel();
	}

	@Override
	@Deprecated
	public @NotNull List<BakedQuad> getQuads(BlockState state, Direction side, @NotNull RandomSource rand) {
		BlockState modelState = BlockModelUtils.getModeledState(state);
		BakedModel wrappedModel = getWrappedModel();
		return BlockModelUtils.rotatedQuads(modelRotation, rotatesTexture, side, dir -> wrappedModel.getQuads(modelState, dir, rand), null);
	}
}
