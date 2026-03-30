package com.firemerald.additionalplacements.client.models.rotated;

import com.firemerald.additionalplacements.client.models.IAPUnbakedModel;
import com.firemerald.additionalplacements.util.BlockRotation;
import net.minecraft.client.resources.model.*;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class UnbakedRotatedPlacementModel implements IAPUnbakedModel {
	public final BlockState modelState;
	public final BlockRotation modelRotation;
	public final boolean rotatesTexture;

	public UnbakedRotatedPlacementModel(RotatedModelData data) {
		this.modelState = data.theirState();
		this.modelRotation = data.rotation();
		this.rotatesTexture = data.rotateTexture();
	}

	@Override
	public @NotNull BakedModel bake(ModelBaker baker, ModelState modelState) {
		return BakedRotatedPlacementModel.of(this.modelState, modelRotation, rotatesTexture);
	}

	@Override
	public void resolveDependencies(UnbakedModel.Resolver modelGetter) {}
}
