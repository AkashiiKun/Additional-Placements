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

	public final BlockStateModel ourModel;
	private final BlockState theirModelState;
	private BlockStateModel theirModel;

	protected BakedRetexturedPlacementModel(BlockStateModel ourModel, BlockState theirModelState) {
		this.ourModel = ourModel;
		this.theirModelState = theirModelState;
	}

	public BlockStateModel theirModel() {
		if (theirModel != null) return theirModel;
		else return theirModel = Unwrapper.unwrap(Minecraft.getInstance().getBlockRenderer().getBlockModel(theirModelState));
	}

	@Override
	public BlockStateModel getWrappedModel() {
		return ourModel;
	}

	@Override
	public BlockStateModel getParticleModel() {
		return theirModel();
	}

	@Override
	public Stream<BlockModelPart> wrapParts(RandomSource random) {
		List<BlockModelPart> theirParts = theirModel().collectParts(random);
		return getWrappedModel().collectParts(random).stream().map(ourPart -> RetexturedBlockModelPart.of(ourPart, theirParts));
	}
}
