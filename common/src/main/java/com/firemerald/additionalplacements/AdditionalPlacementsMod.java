package com.firemerald.additionalplacements;

import net.minecraft.resources.ResourceLocation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class AdditionalPlacementsMod {
    public static final String MOD_ID = "additionalplacements";
    public static final Logger LOGGER = LoggerFactory.getLogger("Additional Placements");

    public static ResourceLocation rl(String path) {
        return new ResourceLocation(MOD_ID, path);
    }
}
