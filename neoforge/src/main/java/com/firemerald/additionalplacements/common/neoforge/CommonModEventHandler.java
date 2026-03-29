package com.firemerald.additionalplacements.common.neoforge;

import java.util.ArrayList;
import java.util.List;

import com.firemerald.additionalplacements.common.CommonModEvents;
import com.firemerald.additionalplacements.compat.LoadedMods;
import com.firemerald.additionalplacements.datagen.ModelGenerator;
import com.firemerald.additionalplacements.neoforge.AdditionalPlacementsNeoForge;
import com.firemerald.additionalplacements.generation.neoforge.RegistrationImpl;
import com.firemerald.additionalplacements.network.neoforge.APNetworkImpl;
import com.firemerald.additionalplacements.network.neoforge.CheckDataConfigurationTaskImpl;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModLoadingContext;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.data.event.GatherDataEvent;
import net.neoforged.neoforge.network.event.OnGameConfigurationEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlerEvent;
import net.neoforged.neoforge.registries.NewRegistryEvent;
import net.neoforged.neoforge.registries.RegisterEvent;
import org.apache.commons.lang3.tuple.Pair;

import com.firemerald.additionalplacements.AdditionalPlacementsMod;
import com.firemerald.additionalplacements.config.APConfigs;
import com.firemerald.additionalplacements.generation.Registration;

import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonModEventHandler {
    private static boolean init = false;

    @SubscribeEvent
    public static void onNewRegistry(NewRegistryEvent event) { //best hook I could find for loading a config after all mods have been processed but before registries are built
        if (!init) {
            RegistrationImpl.registerTypes();
            ModLoadingContext ctx = ModLoadingContext.get();
            APConfigs.init((type, configSpec) -> {
                ctx.registerConfig(switch (type) {
                    case COMMON -> ModConfig.Type.COMMON;
                    case CLIENT -> ModConfig.Type.CLIENT;
                    case SERVER -> ModConfig.Type.SERVER;
                }, configSpec);
                return configSpec;
            });
            init = true;
        }
    }

    @SubscribeEvent
    public static void onModConfigsLoaded(ModConfigEvent.Loading event) {
        CommonModEvents.onConfigLoaded(event.getConfig().getSpec());
    }

    @SubscribeEvent
    public static void onModConfigsReloaded(ModConfigEvent.Reloading event) {
        CommonModEvents.onConfigReloaded(event.getConfig().getSpec());
    }

    @SubscribeEvent
    public static void onBlockRegistry(RegisterEvent event) {
        if (event.getRegistry() == BuiltInRegistries.BLOCK) {
            List<Pair<ResourceLocation, Block>> created = new ArrayList<>();
            BuiltInRegistries.BLOCK.entrySet().forEach(entry -> {
                ResourceLocation name = entry.getKey().location();
                Block block = entry.getValue();
                Registration.tryApply(block, name, (id, obj) -> created.add(Pair.of(id, obj)));
            });
            created.forEach(pair -> Registry.register(BuiltInRegistries.BLOCK, pair.getLeft(), pair.getRight()));
            AdditionalPlacementsNeoForge.dynamicRegistration = true;
        }
    }

    @SubscribeEvent
    public static void onFMLCommonSetup(FMLCommonSetupEvent event) {
        LoadedMods.populate();
        CommonModEvents.modifyWaxables();
    }

    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event) {
        if (event.includeClient()) {
            event.getGenerator().addProvider(true, (DataProvider.Factory<ModelGenerator>) (PackOutput pack) -> new ModelGenerator(pack, AdditionalPlacementsMod.MOD_ID, event.getExistingFileHelper()));
        }
    }

    @SubscribeEvent
    public static void register(RegisterPayloadHandlerEvent event) {
        APNetworkImpl.register(event);
    }

    @SubscribeEvent
    public static void onGetherLoginConfigurationTasks(OnGameConfigurationEvent event) {
        event.register(new CheckDataConfigurationTaskImpl());
    }
}