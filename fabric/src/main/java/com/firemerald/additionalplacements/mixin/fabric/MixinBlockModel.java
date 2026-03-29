package com.firemerald.additionalplacements.mixin.fabric;

import java.util.List;
import java.util.function.Function;

import com.firemerald.additionalplacements.client.fabric.IBlockModelExtensions;
import com.firemerald.additionalplacements.client.models.IAPUnbakedModel;
import net.minecraft.client.renderer.block.model.ItemOverride;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.*;

@Mixin(value = BlockModel.class, priority = 900)
public abstract class MixinBlockModel implements IBlockModelExtensions {
    @Unique
    private IAPUnbakedModel<?> additionalplacements$model = null;
    @Shadow
    public abstract List<ItemOverride> getOverrides();

    @Inject(method = "bake(Lnet/minecraft/client/resources/model/ModelBaker;Ljava/util/function/Function;Lnet/minecraft/client/resources/model/ModelState;)Lnet/minecraft/client/resources/model/BakedModel;",
            at = @At("HEAD"), cancellable = true)
    private void bake(ModelBaker baker, Function<Material, TextureAtlasSprite> spriteGetter, ModelState state, CallbackInfoReturnable<BakedModel> cli) {
        if (additionalplacements$model != null)
            cli.setReturnValue(additionalplacements$model.bake(baker, spriteGetter, state, getOverrides()));
    }

    @Inject(method = "resolveDependencies(Lnet/minecraft/client/resources/model/UnbakedModel$Resolver;)V", at = @At("HEAD"))
    private void resolveDependencies(UnbakedModel.Resolver resolver, CallbackInfo ci) {
        if (additionalplacements$model != null)
            additionalplacements$model.resolveDependencies(resolver);
    }

    @Override
    public void additionalplacements$setAPModel(IAPUnbakedModel<?> model) {
        this.additionalplacements$model = model;
    }
}
