package com.firemerald.additionalplacements.client.models.neoforge;

import com.firemerald.additionalplacements.client.models.IAPUnbakedModel;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.client.renderer.block.model.ItemOverride;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.*;
import net.neoforged.neoforge.client.model.geometry.IGeometryBakingContext;
import net.neoforged.neoforge.client.model.geometry.IUnbakedGeometry;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.List;
import java.util.function.Function;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public interface IAPUnbakedModelImpl<T extends IAPUnbakedModelImpl<T>> extends IAPUnbakedModel<T>, IUnbakedGeometry<T> {
    @Override
    default BakedModel bake(IGeometryBakingContext context, ModelBaker baker, Function<Material, TextureAtlasSprite> spriteGetter, ModelState modelState, List<ItemOverride> overrides) {
        return bake(baker, spriteGetter, modelState, overrides);
    }

    @Override
    default void resolveDependencies(UnbakedModel.Resolver modelGetter, IGeometryBakingContext context) {
        resolveDependencies(modelGetter);
    }
}
