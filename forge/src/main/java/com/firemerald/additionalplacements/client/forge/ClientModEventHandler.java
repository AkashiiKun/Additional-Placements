package com.firemerald.additionalplacements.client.forge;

import com.firemerald.additionalplacements.client.APClientData;
import com.firemerald.additionalplacements.client.ClientModEvents;
import com.firemerald.additionalplacements.client.models.retextured.RetexturedModelData;
import com.firemerald.additionalplacements.client.models.retextured.forge.RetexturedPlacementModelLoader;
import com.firemerald.additionalplacements.client.models.rotated.RotatedModelData;
import com.firemerald.additionalplacements.client.models.rotated.forge.RotatedPlacementModelLoader;
import com.firemerald.additionalplacements.client.resources.APDynamicResources;
import net.minecraft.server.packs.PackType;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ModelEvent;
import net.minecraftforge.client.event.RegisterColorHandlersEvent;
import net.minecraftforge.client.event.RegisterKeyMappingsEvent;
import net.minecraftforge.event.AddPackFindersEvent;
import net.minecraftforge.eventbus.api.EventPriority;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(value = Dist.CLIENT, bus = Mod.EventBusSubscriber.Bus.MOD)
@OnlyIn(Dist.CLIENT)
public class ClientModEventHandler {
    @SubscribeEvent(priority = EventPriority.LOWEST)
    public static void onRegisterBlockColorHandlers(RegisterColorHandlersEvent.Block event) {
        ClientModEvents.addBlockColors(event::register);
    }

    @SubscribeEvent
    public static void onRegisterKeyMappings(RegisterKeyMappingsEvent event) {
        event.register(APClientData.AP_PLACEMENT_KEY);
    }

    @SubscribeEvent
    public static void onModelRegistryEvent(ModelEvent.RegisterGeometryLoaders event) {
        event.register(RotatedModelData.ID.getPath(), new RotatedPlacementModelLoader());
        event.register(RetexturedModelData.ID.getPath(), new RetexturedPlacementModelLoader());
    }

    @SubscribeEvent
    public static void onAddPackFinders(AddPackFindersEvent event) {
        if (event.getPackType() == PackType.CLIENT_RESOURCES) event.addRepositorySource(addPack -> addPack.accept(APDynamicResources.PACK));
    }
}