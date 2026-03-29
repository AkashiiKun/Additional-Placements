package com.firemerald.additionalplacements.client.neoforge;

import com.firemerald.additionalplacements.client.APClientData;
import com.firemerald.additionalplacements.client.ClientModEvents;
import com.firemerald.additionalplacements.client.models.retextured.RetexturedModelData;
import com.firemerald.additionalplacements.client.models.retextured.neoforge.RetexturedPlacementModelLoader;
import com.firemerald.additionalplacements.client.models.rotated.RotatedModelData;
import com.firemerald.additionalplacements.client.models.rotated.neoforge.RotatedPlacementModelLoader;
import com.firemerald.additionalplacements.client.resources.APDynamicResources;
import net.minecraft.server.packs.PackType;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.api.distmarker.OnlyIn;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.neoforge.client.event.ModelEvent;
import net.neoforged.neoforge.client.event.RegisterColorHandlersEvent;
import net.neoforged.neoforge.client.event.RegisterKeyMappingsEvent;
import net.neoforged.neoforge.event.AddPackFindersEvent;

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
        event.register(RotatedModelData.ID, new RotatedPlacementModelLoader());
        event.register(RetexturedModelData.ID, new RetexturedPlacementModelLoader());
    }

    @SubscribeEvent
    public static void onAddPackFinders(AddPackFindersEvent event) {
        if (event.getPackType() == PackType.CLIENT_RESOURCES) event.addRepositorySource(addPack -> addPack.accept(APDynamicResources.PACK));
    }
}