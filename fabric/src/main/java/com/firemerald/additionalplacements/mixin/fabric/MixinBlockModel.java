package com.firemerald.additionalplacements.mixin.fabric;

import com.firemerald.additionalplacements.client.fabric.IBlockModelExtensions;
import com.firemerald.additionalplacements.client.models.IAPUnbakedModel;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.block.model.TextureSlots;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.client.resources.model.*;

@Mixin(value = BlockModel.class, priority = 900)
public abstract class MixinBlockModel implements IBlockModelExtensions {
    @Unique
    private IAPUnbakedModel additionalplacements$model = null;

    @Inject(method = "bake(Lnet/minecraft/client/renderer/block/model/TextureSlots;Lnet/minecraft/client/resources/model/ModelBaker;Lnet/minecraft/client/resources/model/ModelState;ZZLnet/minecraft/client/renderer/block/model/ItemTransforms;)Lnet/minecraft/client/resources/model/BakedModel;",
            at = @At("HEAD"), cancellable = true)
    private void bake(TextureSlots textureSlots, ModelBaker baker, ModelState modelState, boolean hasAmbientOcclusion, boolean useBlockLight, ItemTransforms transforms, CallbackInfoReturnable<BakedModel> cli) {
        if (additionalplacements$model != null)
            cli.setReturnValue(additionalplacements$model.bake(baker, modelState));
    }

    @Inject(method = "resolveDependencies(Lnet/minecraft/client/resources/model/ResolvableModel$Resolver;)V", at = @At("HEAD"))
    private void resolveDependencies(ResolvableModel.Resolver resolver, CallbackInfo ci) {
        if (additionalplacements$model != null)
            additionalplacements$model.resolveDependencies(resolver);
    }

    @Override
    public void additionalplacements$setAPModel(IAPUnbakedModel model) {
        this.additionalplacements$model = model;
    }
}
