package com.firemerald.additionalplacements.client.models.retextured.neoforge;

import com.firemerald.additionalplacements.client.models.retextured.RetexturedModelData;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.neoforged.neoforge.client.model.UnbakedModelLoader;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class RetexturedPlacementModelLoader implements UnbakedModelLoader<UnbakedRetexturedPlacementModelImpl> {
    @Override
    public UnbakedRetexturedPlacementModelImpl read(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return new UnbakedRetexturedPlacementModelImpl(RetexturedModelData.fromJson(jsonObject, jsonDeserializationContext));
    }
}
