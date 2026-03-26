package com.firemerald.additionalplacements.client.models.rotated;

import com.firemerald.additionalplacements.client.models.IAPUnbakedModel;
import com.firemerald.additionalplacements.util.BlockRotation;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Function;

public class UnbakedRotatedPlacementModel<T extends UnbakedRotatedPlacementModel<T>> implements IAPUnbakedModel<T> {
	public final BlockState modelState;
	public final BlockRotation modelRotation;
	public final boolean rotatesTexture;

	public UnbakedRotatedPlacementModel(RotatedModelData data) {
		this.modelState = data.theirState();
		this.modelRotation = data.rotation();
		this.rotatesTexture = data.rotateTexture();
	}

	@Override
	public BakedModel bake(ModelBaker baker, Function<Material, TextureAtlasSprite> spriteGetter, ModelState modelState, ItemOverrides overrides, ResourceLocation modelLocation) {
		return BakedRotatedPlacementModel.of(this.modelState, modelRotation, rotatesTexture);
	}

	@Override
	public void resolveParents(Function<ResourceLocation, UnbakedModel> function) {}
}
