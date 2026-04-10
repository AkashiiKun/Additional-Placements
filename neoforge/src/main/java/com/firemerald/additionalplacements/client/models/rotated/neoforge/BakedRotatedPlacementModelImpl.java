package com.firemerald.additionalplacements.client.models.rotated.neoforge;

import com.firemerald.additionalplacements.client.models.neoforge.PlacementModelWrapperImpl;
import com.firemerald.additionalplacements.client.models.rotated.BakedRotatedPlacementModel;
import com.firemerald.additionalplacements.client.models.BlockModelUtils;
import com.firemerald.additionalplacements.util.BlockRotation;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import net.neoforged.neoforge.client.model.data.ModelData;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BakedRotatedPlacementModelImpl extends BakedRotatedPlacementModel implements PlacementModelWrapperImpl {
	public static BakedRotatedPlacementModel of(BlockState theirModelState, BlockRotation modelRotation, boolean rotatesTexture) {
		return new BakedRotatedPlacementModelImpl(theirModelState, modelRotation, rotatesTexture);
	}

	private BakedRotatedPlacementModelImpl(BlockState theirModelState, BlockRotation modelRotation, boolean rotatesTexture) {
		super(theirModelState, modelRotation, rotatesTexture);
    }

	@Override
	public @NotNull List<BakedQuad> getQuads(@Nullable BlockState state, @Nullable Direction side, @NotNull RandomSource rand, @NotNull ModelData data, @Nullable RenderType renderType) {
		BlockState modelState = BlockModelUtils.getModeledState(state);
		BakedModel wrappedModel = getWrappedModel();
		return BlockModelUtils.rotatedQuads(modelRotation, rotatesTexture, side, dir -> wrappedModel.getQuads(modelState, dir, rand, data, renderType), renderType);
	}
}
