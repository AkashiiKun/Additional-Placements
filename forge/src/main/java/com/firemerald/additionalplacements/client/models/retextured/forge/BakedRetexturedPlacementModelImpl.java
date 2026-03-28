package com.firemerald.additionalplacements.client.models.retextured.forge;

import com.firemerald.additionalplacements.client.models.forge.PlacementModelWrapperImpl;
import com.firemerald.additionalplacements.client.models.retextured.BakedRetexturedPlacementModel;
import com.firemerald.additionalplacements.client.models.BlockModelUtils;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.client.model.data.ModelData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BakedRetexturedPlacementModelImpl extends BakedRetexturedPlacementModel implements PlacementModelWrapperImpl {
	public static BakedRetexturedPlacementModel of(BakedModel ourModel, BlockState theirModelState) {
		return new BakedRetexturedPlacementModelImpl(ourModel, theirModelState);
	}

	private BakedRetexturedPlacementModelImpl(BakedModel ourModel, BlockState theirModelState) {
		super(ourModel, theirModelState);
	}

	@Override
	public @NotNull List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @NotNull RandomSource rand, @NotNull ModelData data, @Nullable RenderType renderType) {
		BlockState modelState = BlockModelUtils.getModeledState(state);
		return BlockModelUtils.retexturedQuads(side, dir -> ourModel.getQuads(state, dir, rand, data, renderType), dir -> theirModel().getQuads(modelState, dir, rand, data, renderType), renderType);
	}
}
