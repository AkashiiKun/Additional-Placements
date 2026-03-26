package com.firemerald.additionalplacements.client.models.rotated.forge;

import com.firemerald.additionalplacements.client.models.rotated.RotatedModelData;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraftforge.client.model.geometry.IGeometryLoader;

public class RotatedPlacementModelLoader implements IGeometryLoader<UnbakedRotatedPlacementModelImpl> {
    @Override
    public UnbakedRotatedPlacementModelImpl read(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return new UnbakedRotatedPlacementModelImpl(RotatedModelData.fromJson(jsonObject, jsonDeserializationContext));
    }
}
