package com.firemerald.additionalplacements.client.models.rotated;

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

public record RotatedModelData(BlockState theirState, BlockRotation rotation, boolean rotateTexture) implements IJsonInputSupplier {
    public static final ResourceLocation ID = AdditionalPlacementsMod.rl("rotated");

    public static RotatedModelData fromJson(JsonObject jsonObject, JsonDeserializationContext deserializationContext) throws JsonParseException {
        BlockState theirState;
        BlockRotation rotation;
        try {
            theirState = GeneralUtils.parseStateString(jsonObject.get("state").getAsString());
            rotation = BlockRotation.valueOf(jsonObject.get("rotation").getAsString());
        } catch (IllegalArgumentException e) {
            throw new JsonParseException(e);
        }
        boolean rotateTexture = jsonObject.has("rotateTexture") && jsonObject.get("rotateTexture").getAsBoolean();
        return new RotatedModelData(theirState, rotation, rotateTexture);
    }

    @Override
    public JsonElement getJson() {
        JsonObject root = new JsonObject();
        root.addProperty("loader", ID.toString());
        root.addProperty("state", GeneralUtils.makeStateString(theirState));
        root.addProperty("rotation", rotation.toString());
        root.addProperty("rotateTexture", rotateTexture);
        return root;
    }
}
