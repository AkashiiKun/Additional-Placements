package com.firemerald.additionalplacements.client.models;

import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.*;
import net.minecraft.resources.ResourceLocation;

import java.util.function.Function;

public interface IAPUnbakedModel<T extends IAPUnbakedModel<T>> {
    BakedModel bake(ModelBaker baker, Function<Material, TextureAtlasSprite> spriteGetter, ModelState modelState, ItemOverrides overrides);

    void resolveParents(Function<ResourceLocation, UnbakedModel> modelGetter);
}
