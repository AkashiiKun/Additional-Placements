package com.firemerald.additionalplacements.client.fabric;

import com.firemerald.additionalplacements.client.APClientData;
import com.firemerald.additionalplacements.client.ClientModEvents;
import com.firemerald.additionalplacements.common.CommonModEvents;
import org.jetbrains.annotations.Nullable;

import com.firemerald.additionalplacements.block.AdditionalPlacementBlock;
import com.firemerald.additionalplacements.config.APConfigs;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.blockrenderlayer.v1.BlockRenderLayerMap;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.item.v1.ItemTooltipCallback;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayConnectionEvents;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderContext;
import net.fabricmc.fabric.api.client.rendering.v1.WorldRenderEvents;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;

@Environment(EnvType.CLIENT)
public class FabricModEvents implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ItemTooltipCallback.EVENT.register(CommonModEvents::onItemTooltip);
        WorldRenderEvents.BEFORE_BLOCK_OUTLINE.register(FabricModEvents::onHighlightBlock);
        ClientLifecycleEvents.CLIENT_STARTED.register(client -> com.firemerald.additionalplacements.common.fabric.FabricModEvents.init());
        ClientLifecycleEvents.CLIENT_STARTED.register(FabricModEvents::init);
        ClientTickEvents.END_CLIENT_TICK.register(FabricModEvents::onClientEndTick);
        ClientPlayConnectionEvents.JOIN.register(FabricModEvents::onServerJoined);
        KeyBindingHelper.registerKeyBinding(APClientData.AP_PLACEMENT_KEY);
    }

    private static boolean hasInit = false;

    public static void init(Minecraft client) {
        if (!hasInit) {
            BuiltInRegistries.BLOCK.forEach(block -> {
                if (block instanceof AdditionalPlacementBlock<?> additionalPlacementBlock) {
                    BlockState modelState = additionalPlacementBlock.getOtherBlockState();
                    BlockRenderLayerMap.INSTANCE.putBlock(block, ItemBlockRenderTypes.getChunkRenderType(modelState));
                }
            });
            ClientModEvents.addBlockColors(client.getBlockColors()::register);
            hasInit = true;
        }
    }

    public static boolean onHighlightBlock(WorldRenderContext context, @Nullable HitResult hitResult) {
        if (hitResult != null && hitResult.getType() == HitResult.Type.BLOCK && hitResult instanceof BlockHitResult blockHitResult) {
            ClientModEvents.onHighlightBlock(context.worldRenderer(), context.camera(), blockHitResult, context.tickCounter(), context.matrixStack(), context.consumers());
        }
        return true;
    }

    public static void onServerJoined(ClientPacketListener handler, PacketSender sender, Minecraft client) {
        APClientData.setPlacementEnabledAndSynchronize(APConfigs.client().defaultPlacementLogicState.get(), APConfigs.client().loginPlacementLogicStateMessage.get());
    }

    public static void onClientEndTick(Minecraft mc) {
        ClientModEvents.onInput();
        ClientModEvents.onClientPostTick();
    }
}