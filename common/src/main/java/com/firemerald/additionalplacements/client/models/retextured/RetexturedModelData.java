package com.firemerald.additionalplacements.client.models.retextured;

import com.firemerald.additionalplacements.AdditionalPlacementsMod;
import com.firemerald.additionalplacements.client.resources.IJsonInputSupplier;
import com.firemerald.additionalplacements.util.BlockRotation;
import com.firemerald.additionalplacements.util.GeneralUtils;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;

public record RetexturedModelData(ResourceLocation ourModel, BlockState theirState) implements IJsonInputSupplier {
    public static final ResourceLocation ID = AdditionalPlacementsMod.rl("retextured");

    public static RetexturedModelData fromJson(JsonObject jsonObject, JsonDeserializationContext deserializationContext) throws JsonParseException {
        ResourceLocation ourModel = new ResourceLocation(jsonObject.get("model").getAsString());
        BlockState theirState;
        try {
            theirState = GeneralUtils.parseStateString(jsonObject.get("state").getAsString());
        } catch (IllegalArgumentException e) {
            throw new JsonParseException(e);
        }
        return new RetexturedModelData(ourModel, theirState);
    }

    @Override
    public JsonElement getJson() {
        JsonObject root = new JsonObject();
        root.addProperty("loader", ID.toString());
        root.addProperty("model", ourModel.toString());
        root.addProperty("state", GeneralUtils.makeStateString(theirState));
        return root;
    }
}
