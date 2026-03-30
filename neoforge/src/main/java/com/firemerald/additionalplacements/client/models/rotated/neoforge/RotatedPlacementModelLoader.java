package com.firemerald.additionalplacements.client.models.rotated.neoforge;

import com.firemerald.additionalplacements.client.models.rotated.RotatedModelData;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.neoforged.neoforge.client.model.UnbakedModelLoader;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class RotatedPlacementModelLoader implements UnbakedModelLoader<UnbakedRotatedPlacementModelImpl> {
    @Override
    public UnbakedRotatedPlacementModelImpl read(JsonObject jsonObject, JsonDeserializationContext jsonDeserializationContext) throws JsonParseException {
        return new UnbakedRotatedPlacementModelImpl(RotatedModelData.fromJson(jsonObject, jsonDeserializationContext));
    }
}
