package com.firemerald.additionalplacements.client.models.retextured;

import com.firemerald.additionalplacements.client.models.IAPUnbakedModel;
import net.minecraft.client.resources.model.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public class UnbakedRetexturedPlacementModel implements IAPUnbakedModel {
	public final ResourceLocation ourModelLocation;
	public final BlockState theirModelState;

	public UnbakedRetexturedPlacementModel(RetexturedModelData data) {
        this.ourModelLocation = data.ourModel();
        this.theirModelState = data.theirState();
    }

	@Override
	public @NotNull BakedModel bake(ModelBaker baker, ModelState modelState) {
		return BakedRetexturedPlacementModel.of(baker.bake(ourModelLocation, modelState), theirModelState);
	}

	@Override
	public void resolveDependencies(UnbakedModel.Resolver modelGetter) {
		modelGetter.resolve(ourModelLocation);
	}
}
