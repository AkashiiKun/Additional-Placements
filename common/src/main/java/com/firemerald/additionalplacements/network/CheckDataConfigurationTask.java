package com.firemerald.additionalplacements.network;

import com.firemerald.additionalplacements.AdditionalPlacementsMod;
import net.minecraft.server.network.ConfigurationTask;

public abstract class CheckDataConfigurationTask implements ConfigurationTask {
	public static final Type TYPE = new Type(AdditionalPlacementsMod.MOD_ID + ":configuration_checks");

    @Override
    public Type type() {
        return TYPE;
    }
}
