package com.firemerald.additionalplacements.mixin.fabric;

import java.util.function.Consumer;

import com.firemerald.additionalplacements.client.resources.APDynamicResources;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import net.fabricmc.fabric.impl.resource.loader.ModResourcePackCreator;
import net.minecraft.server.packs.repository.Pack;

@Mixin(ModResourcePackCreator.class)
public class MixinModResourcePackCreator {
    @Inject(method="loadPacks", at = @At("HEAD"))
    public void loadPacks(Consumer<Pack> consumer, CallbackInfo info) {
        consumer.accept(APDynamicResources.PACK);
    }
}
