package com.firemerald.additionalplacements.client.models.retextured;

import com.firemerald.additionalplacements.client.models.IAPUnbakedModel;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Function;

public class UnbakedRetexturedPlacementModel<T extends UnbakedRetexturedPlacementModel<T>> implements IAPUnbakedModel<T> {
	public final ResourceLocation ourModelLocation;
	public final BlockState theirModelState;

	public UnbakedRetexturedPlacementModel(RetexturedModelData data) {
        this.ourModelLocation = data.ourModel();
        this.theirModelState = data.theirState();
    }

	@Override
	public BakedModel bake(ModelBaker baker, Function<Material, TextureAtlasSprite> spriteGetter, ModelState modelState, ItemOverrides overrides, ResourceLocation modelLocation) {
		return BakedRetexturedPlacementModel.of(baker.bake(ourModelLocation, BlockModelRotation.X0_Y0), theirModelState);
	}

	@Override
	public void resolveParents(Function<ResourceLocation, UnbakedModel> function) {
		function.apply(ourModelLocation);
	}
}
