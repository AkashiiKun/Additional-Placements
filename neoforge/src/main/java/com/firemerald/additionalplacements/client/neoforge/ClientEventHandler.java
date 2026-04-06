package com.firemerald.additionalplacements.client.neoforge;

import com.firemerald.additionalplacements.client.APClientData;
import com.firemerald.additionalplacements.client.ClientModEvents;
import com.firemerald.additionalplacements.client.gui.screen.ConnectionErrorsScreen;
import com.firemerald.additionalplacements.config.APConfigs;

import com.firemerald.additionalplacements.util.PlatformUtils;
import net.minecraft.client.gui.screens.DisconnectedScreen;
import net.neoforged.api.distmarker.Dist;
import net.neoforged.bus.api.EventPriority;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.client.event.*;

@EventBusSubscriber(value = Dist.CLIENT)
public class ClientEventHandler {
    static {
        PlatformUtils.checkIsClient();
    }

    @SubscribeEvent
    public static void onHighlightBlock(RenderHighlightEvent.Block event) {
        ClientModEvents.onHighlightBlock(event.getLevelRenderer(), event.getCamera(), event.getTarget(), event.getDeltaTracker(), event.getPoseStack(), event.getMultiBufferSource());
    }

    @SubscribeEvent
    public static void onKeyInput(InputEvent.Key event) {
        ClientModEvents.onInput();
    }

    @SubscribeEvent
    public static void onKeyInput(InputEvent.MouseButton.Post event) {
        ClientModEvents.onInput();
    }

    @SubscribeEvent
    public static void onKeyInput(InputEvent.MouseScrollingEvent event) {
        ClientModEvents.onInput();
    }

    @SubscribeEvent
    public static void onPlayerLoggingIn(ClientPlayerNetworkEvent.LoggingIn event) {
        APClientData.setPlacementEnabledAndSynchronize(APConfigs.client().defaultPlacementLogicState.get(), APConfigs.client().loginPlacementLogicStateMessage.get());
    }

    @SubscribeEvent
    public static void onClientTick(ClientTickEvent.Post event) {
        ClientModEvents.onClientPostTick();
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public static void onScreenOpening(ScreenEvent.Opening event) {
        if (event.getCurrentScreen() instanceof ConnectionErrorsScreen && event.getNewScreen() instanceof DisconnectedScreen) event.setCanceled(true);
    }
}