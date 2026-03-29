package com.firemerald.additionalplacements.client.models;

import net.minecraft.client.renderer.block.model.ItemOverride;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.*;

import java.util.List;
import java.util.function.Function;

public interface IAPUnbakedModel<T extends IAPUnbakedModel<T>> {
    BakedModel bake(ModelBaker baker, Function<Material, TextureAtlasSprite> spriteGetter, ModelState modelState, List<ItemOverride> overrides);

    void resolveDependencies(UnbakedModel.Resolver modelGetter);
}
