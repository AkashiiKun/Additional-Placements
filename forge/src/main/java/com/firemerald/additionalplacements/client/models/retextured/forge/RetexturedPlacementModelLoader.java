package com.firemerald.additionalplacements.client.models.retextured.forge;

import com.firemerald.additionalplacements.client.models.retextured.RetexturedModelData;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraftforge.client.model.geometry.IGeometryLoader;

public class RetexturedPlacementModelLoader implements IGeometryLoader<UnbakedRetexturedPlacementModelImpl> {
    @Override
    public UnbakedRetexturedPlacementModelImpl read(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return new UnbakedRetexturedPlacementModelImpl(RetexturedModelData.fromJson(jsonObject, jsonDeserializationContext));
    }
}
