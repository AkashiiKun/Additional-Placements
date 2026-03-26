package com.firemerald.additionalplacements.common.forge;

import java.util.ArrayList;
import java.util.List;

import com.firemerald.additionalplacements.common.CommonModEvents;
import com.firemerald.additionalplacements.compat.LoadedMods;
import com.firemerald.additionalplacements.datagen.ModelGenerator;
import com.firemerald.additionalplacements.forge.AdditionalPlacementsForge;
import com.firemerald.additionalplacements.generation.forge.RegistrationImpl;
import com.firemerald.additionalplacements.network.forge.APNetworkImpl;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import org.apache.commons.lang3.tuple.Pair;

import com.firemerald.additionalplacements.AdditionalPlacementsMod;
import com.firemerald.additionalplacements.config.APConfigs;
import com.firemerald.additionalplacements.generation.Registration;

import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.NewRegistryEvent;
import net.minecraftforge.registries.RegisterEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class CommonModEventHandler
{
    private static boolean init = false;

    @SubscribeEvent
    public static void onNewRegistry(NewRegistryEvent event) { //best hook I could find for loading a config after all mods have been processed but before registries are built
        if (!init) {
            RegistrationImpl.registerTypes();
            ModLoadingContext ctx = ModLoadingContext.get();
            APConfigs.init(ctx::registerConfig);
            init = true;
        }
    }

    @SubscribeEvent
    public static void onModConfigsLoaded(ModConfigEvent.Loading event) {
        CommonModEvents.onConfigLoaded(event.getConfig());
    }

    @SubscribeEvent
    public static void onModConfigsReloaded(ModConfigEvent.Reloading event) {
        CommonModEvents.onConfigReloaded(event.getConfig());
    }

    @SubscribeEvent
    public static void onBlockRegistry(RegisterEvent event) {
        if (event.getForgeRegistry() != null && ForgeRegistries.BLOCKS.getRegistryKey().equals(event.getForgeRegistry().getRegistryKey())) {
            IForgeRegistry<Block> registry = event.getForgeRegistry();
            List<Pair<ResourceLocation, Block>> created = new ArrayList<>();
            registry.getEntries().forEach(entry -> {
                ResourceLocation name = entry.getKey().location();
                Block block = entry.getValue();
                Registration.tryApply(block, name, (id, obj) -> created.add(Pair.of(id, obj)));
            });
            created.forEach(pair -> registry.register(pair.getLeft(), pair.getRight()));
            AdditionalPlacementsForge.dynamicRegistration = true;
        }
    }

    @SubscribeEvent
    public static void onFMLCommonSetup(FMLCommonSetupEvent event) {
        LoadedMods.populate();
        CommonModEvents.modifyWaxables();
        APNetworkImpl.register();
    }

    @SubscribeEvent
    public static void onGatherData(GatherDataEvent event) {
        if (event.includeClient()) {
            event.getGenerator().addProvider(true, (DataProvider.Factory<ModelGenerator>) (PackOutput pack) -> new ModelGenerator(pack, AdditionalPlacementsMod.MOD_ID, event.getExistingFileHelper()));
        }
    }
}