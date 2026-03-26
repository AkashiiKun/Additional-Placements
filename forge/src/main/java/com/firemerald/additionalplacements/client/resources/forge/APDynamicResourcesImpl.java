package com.firemerald.additionalplacements.client.resources.forge;

import net.minecraft.network.chat.Component;
import net.minecraft.server.packs.repository.Pack;
import net.minecraft.world.flag.FeatureFlagSet;

public class APDynamicResourcesImpl {
    public static Pack.Info getPackInfo(Component description, int format, FeatureFlagSet featureFlagSet) {
        return new Pack.Info(description, format, format, featureFlagSet, true);
    }
}
