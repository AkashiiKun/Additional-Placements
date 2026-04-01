package com.firemerald.additionalplacements.client.models.rotated;

import com.firemerald.additionalplacements.client.models.PlacementModelWrapper;
import com.firemerald.additionalplacements.client.models.Unwrapper;
import com.firemerald.additionalplacements.util.BlockRotation;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.BlockModelPart;
import net.minecraft.client.renderer.block.model.BlockStateModel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;
import java.util.stream.Stream;

public abstract class BakedRotatedPlacementModel implements PlacementModelWrapper {
	@ExpectPlatform
	public static BakedRotatedPlacementModel of(BlockState theirModelState, BlockRotation modelRotation, boolean rotatesTexture) {
		throw new AssertionError();
	}

	public final BlockState theirModelState;
	private BlockStateModel theirModel;
	public final BlockRotation modelRotation;
	public final boolean rotatesTexture;

	protected BakedRotatedPlacementModel(BlockState theirModelState, BlockRotation modelRotation, boolean rotatesTexture) {
		this.theirModelState = theirModelState;
		this.modelRotation = modelRotation;
		this.rotatesTexture = rotatesTexture;
	}

	@Override
	public BlockStateModel getWrappedModel() {
		if (theirModel != null) return theirModel;
		else return theirModel = Unwrapper.unwrap(Minecraft.getInstance().getBlockRenderer().getBlockModel(theirModelState));
	}

	@Override
	public BlockStateModel getParticleModel() {
		return getWrappedModel();
	}

	@Override
	public Stream<BlockModelPart> wrapParts(RandomSource random) {
		return getWrappedModel().collectParts(random).stream().map(toWrap -> RotatedBlockModelPart.of(toWrap, modelRotation, rotatesTexture));
	}
}
