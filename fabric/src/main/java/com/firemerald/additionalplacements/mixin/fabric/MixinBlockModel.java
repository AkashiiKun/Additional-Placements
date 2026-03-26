package com.firemerald.additionalplacements.mixin.fabric;

import java.util.function.Function;

import com.firemerald.additionalplacements.client.fabric.IBlockModelExtensions;
import com.firemerald.additionalplacements.client.models.IAPUnbakedModel;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.*;
import net.minecraft.resources.ResourceLocation;

@Mixin(value = BlockModel.class, priority = 900)
public abstract class MixinBlockModel implements IBlockModelExtensions {
    @Unique
    private IAPUnbakedModel<?> additionalplacements$model = null;
    @Shadow
    public abstract ItemOverrides getItemOverrides(ModelBaker baker, BlockModel model);

    @Inject(method = "bake(Lnet/minecraft/client/resources/model/ModelBaker;Lnet/minecraft/client/renderer/block/model/BlockModel;Ljava/util/function/Function;Lnet/minecraft/client/resources/model/ModelState;Lnet/minecraft/resources/ResourceLocation;Z)Lnet/minecraft/client/resources/model/BakedModel;",
            at = @At("HEAD"), cancellable = true)
    private void bake(ModelBaker baker, BlockModel model, Function<Material, TextureAtlasSprite> spriteGetter, ModelState state, ResourceLocation location, boolean guiLight3d, CallbackInfoReturnable<BakedModel> cli) {
        if (additionalplacements$model != null)
            cli.setReturnValue(additionalplacements$model.bake(baker, spriteGetter, state, getItemOverrides(baker, model), location));
    }

    @Inject(method = "resolveParents", at = @At("HEAD"))
    private void resolveParents(Function<ResourceLocation, UnbakedModel> function, CallbackInfo ci) {
        if (additionalplacements$model != null)
            additionalplacements$model.resolveParents(function);
    }

    @Override
    public void additionalplacements$setAPModel(IAPUnbakedModel<?> model) {
        this.additionalplacements$model = model;
    }
}
