package com.firemerald.additionalplacements.forge;

import com.firemerald.additionalplacements.AdditionalPlacementsMod;
import com.firemerald.additionalplacements.config.APConfigs;
import com.firemerald.additionalplacements.generation.APGenerationTypes;
import com.firemerald.additionalplacements.generation.Registration;
import com.firemerald.additionalplacements.network.APNetwork;
import com.firemerald.additionalplacements.network.forge.APNetworkImpl;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(AdditionalPlacementsMod.MOD_ID)
public final class AdditionalPlacementsForge {
    public static final String OLD_ID = "dvsas";
    public static boolean dynamicRegistration = false;

    public AdditionalPlacementsForge() {
        Registration.addRegistration(new APGenerationTypes());
        AdditionalPlacementsMod.LOGGER.warn("During block registration you may receive several reports of \"Potentially Dangerous alternative prefix `additionalplacements`\". Ignore these, they are intended.");
    }
}
