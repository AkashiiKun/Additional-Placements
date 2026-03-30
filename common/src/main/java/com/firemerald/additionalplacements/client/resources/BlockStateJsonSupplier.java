package com.firemerald.additionalplacements.client.resources;

import com.firemerald.additionalplacements.AdditionalPlacementsMod;
import com.firemerald.additionalplacements.block.AdditionalPlacementBlock;
import com.firemerald.additionalplacements.client.models.definitions.StateModelDefinition;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import net.minecraft.client.data.models.blockstates.VariantProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;

public record BlockStateJsonSupplier(AdditionalPlacementBlock<?> block, String blockName) implements IJsonInputSupplier {
    @Override
    public JsonElement getJson() {
        JsonObject root = new JsonObject();
        JsonObject variants = new JsonObject();
        root.add("variants", variants);
        BlockState state = block.defaultBlockState();
        parseBlockstates(variants, state, state.getProperties().toArray(Property[]::new), 0, "", "block/" + blockName + "/");
        return root;
    }

    private <T extends Comparable<T>> void parseBlockstates(JsonObject variants, BlockState state, Property<?>[] props, int index, String currentStateDef, String currentStateDir) {
        if (index >= props.length) {
            JsonObject variant = new JsonObject();
            variants.add(currentStateDef.substring(0, currentStateDef.length() - 1), variant);
            StateModelDefinition modelDef = block.getModelDefinition(state);
            VariantProperties.MODEL.withValue(AdditionalPlacementsMod.rl(currentStateDir + "model")).addToVariant(variant);
            VariantProperties.X_ROT.withValue(modelDef.xRotation()).addToVariant(variant);
            VariantProperties.Y_ROT.withValue(modelDef.yRotation()).addToVariant(variant);
            VariantProperties.UV_LOCK.withValue(true).addToVariant(variant);
        }
        else {
            @SuppressWarnings("unchecked")
            Property<T> prop = (Property<T>) props[index];
            String name = prop.getName();
            String stateDef2 = currentStateDef + name + "=";
            prop.getAllValues().forEach(val -> {
                String valName = prop.getName(val.value());
                parseBlockstates(variants, state.setValue(prop, val.value()), props, index + 1, stateDef2 + valName + ",", currentStateDir + valName + "/");
            });
        }
    }
}