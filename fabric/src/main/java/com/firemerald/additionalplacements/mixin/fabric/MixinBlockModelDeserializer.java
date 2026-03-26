package com.firemerald.additionalplacements.mixin.fabric;

import java.lang.reflect.Type;

import com.firemerald.additionalplacements.client.fabric.IBlockModelExtensions;
import com.firemerald.additionalplacements.client.models.retextured.RetexturedModelData;
import com.firemerald.additionalplacements.client.models.retextured.fabric.UnbakedRetexturedPlacementModelImpl;
import com.firemerald.additionalplacements.client.models.rotated.RotatedModelData;
import com.firemerald.additionalplacements.client.models.rotated.fabric.UnbakedRotatedPlacementModelImpl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.client.renderer.block.model.BlockModel;
import net.minecraft.util.GsonHelper;

@Mixin(value = BlockModel.Deserializer.class, priority = 900)
public class MixinBlockModelDeserializer {
    @Inject(method = "deserialize(Lcom/google/gson/JsonElement;Ljava/lang/reflect/Type;Lcom/google/gson/JsonDeserializationContext;)Lnet/minecraft/client/renderer/block/model/BlockModel;", at = @At("RETURN"))
    public void deserialize(JsonElement json, Type type, JsonDeserializationContext context, CallbackInfoReturnable<BlockModel> cli) {
        JsonObject jsonObject = json.getAsJsonObject();
        if (jsonObject.has("loader")) {
            String loader = GsonHelper.getAsString(jsonObject, "loader");
            if (loader.equals(RotatedModelData.ID.toString())) {
                jsonObject.remove("loader"); //Prevent PortingLib from reading our loader
                ((IBlockModelExtensions) cli.getReturnValue()).additionalplacements$setAPModel(new UnbakedRotatedPlacementModelImpl(RotatedModelData.fromJson(jsonObject, context)));
            }
            else if (loader.equals(RetexturedModelData.ID.toString())) {
                jsonObject.remove("loader"); //Prevent PortingLib from reading our loader
                ((IBlockModelExtensions) cli.getReturnValue()).additionalplacements$setAPModel(new UnbakedRetexturedPlacementModelImpl(RetexturedModelData.fromJson(jsonObject, context)));
            }
        }
    }
}
