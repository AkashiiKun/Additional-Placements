package com.firemerald.additionalplacements.client.resources;

import java.io.ByteArrayInputStream;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;

import net.minecraft.server.packs.resources.IoSupplier;
import org.jetbrains.annotations.NotNull;

public interface IJsonInputSupplier extends IoSupplier<InputStream> {
    Gson GSON = new GsonBuilder().setPrettyPrinting().create();

    @Override
    default @NotNull InputStream get() {
        return new ByteArrayInputStream(GSON.toJson(getJson()).getBytes(StandardCharsets.UTF_8));
    }

    JsonElement getJson();
}
