package com.firemerald.additionalplacements.client.neoforge;

import com.firemerald.additionalplacements.client.APClientData;
import com.firemerald.additionalplacements.client.ClientModEvents;
import com.firemerald.additionalplacements.client.gui.screen.ConnectionErrorsScreen;
import com.firemerald.additionalplacements.config.APConfigs;

import com.firemerald.additionalplacements.util.PlatformUtils;
import net.minecraft.client.gui.screens.DisconnectedScreen;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
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

    @SubscribeEvent
    public static void addBlockHighlightRenderers(ExtractBlockOutlineRenderStateEvent event) {
        if (!APConfigs.client().enablePlacementHighlight.get()) return;
        BlockHitResult hitResult = event.getHitResult();
        if (hitResult.getType() != HitResult.Type.BLOCK) return;
        ClientModEvents.performBlockHighlight(event.getLevelRenderState(), (block, player, deltaTracker) ->
                event.addCustomRenderer((renderState, buffer, poseStack, translucentPass, levelRenderState) -> {
                    block.additionalplacements$renderHighlight(poseStack, buffer, player, hitResult, levelRenderState, deltaTracker);
                    return false;
                }));
    }
}