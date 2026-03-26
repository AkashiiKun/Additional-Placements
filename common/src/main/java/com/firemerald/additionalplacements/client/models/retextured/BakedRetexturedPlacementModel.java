package com.firemerald.additionalplacements.client.models.retextured;

import com.firemerald.additionalplacements.client.models.BlockModelUtils;
import com.firemerald.additionalplacements.client.models.PlacementModelWrapper;
import com.firemerald.additionalplacements.client.models.Unwrapper;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
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

	public final BakedModel ourModel;
	private final BlockState theirModelState;
	private BakedModel theirModel;

	protected BakedRetexturedPlacementModel(BakedModel ourModel, BlockState theirModelState) {
		this.ourModel = ourModel;
		this.theirModelState = theirModelState;
	}

	public BakedModel theirModel() {
		if (theirModel != null) return theirModel;
		else return theirModel = Unwrapper.unwrap(Minecraft.getInstance().getBlockRenderer().getBlockModel(theirModelState));
	}

	@Override
	public BakedModel getWrappedModel() {
		return ourModel;
	}

	@Override
	public BakedModel getParticleModel() {
		return theirModel();
	}

	@Override
	public @NotNull List<BakedQuad> getQuads(BlockState state, Direction side, RandomSource rand) {
		BlockState modelState = BlockModelUtils.getModeledState(state);
		return BlockModelUtils.retexturedQuads(side, dir -> ourModel.getQuads(state, dir, rand), dir -> theirModel().getQuads(modelState, dir, rand), null);
	}
}
