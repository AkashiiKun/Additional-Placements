package com.firemerald.additionalplacements.network.fabric;

import java.util.function.Function;

import com.firemerald.additionalplacements.network.client.*;
import com.firemerald.additionalplacements.network.client.fabric.CheckDataClientPacketImpl;
import com.firemerald.additionalplacements.network.client.fabric.ClientConfigurationPacketImpl;
import com.firemerald.additionalplacements.network.client.fabric.ClientPlayPacketImpl;
import com.firemerald.additionalplacements.network.client.fabric.ConfigurationCheckFailedPacketImpl;
import com.firemerald.additionalplacements.network.server.*;

import com.firemerald.additionalplacements.network.server.fabric.CheckDataServerPacketImpl;
import com.firemerald.additionalplacements.network.server.fabric.ServerConfigurationPacketImpl;
import com.firemerald.additionalplacements.network.server.fabric.ServerPlayPacketImpl;
import com.firemerald.additionalplacements.network.server.fabric.SetPlacementTogglePacketImpl;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.client.networking.v1.ClientConfigurationNetworking;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.*;
import net.fabricmc.fabric.impl.networking.server.ServerConfigurationNetworkAddon;
import net.fabricmc.fabric.impl.networking.server.ServerNetworkingImpl;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientConfigurationPacketListenerImpl;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerConfigurationPacketListenerImpl;
import net.minecraft.server.network.ServerGamePacketListenerImpl;

public class APNetworkImpl
{
    public static void register() {
        registerServerPlayPacket(SetPlacementTogglePacketImpl.ID, SetPlacementTogglePacketImpl::new);
        registerClientConfigurationPacket(CheckDataClientPacketImpl.ID, CheckDataClientPacketImpl::new);
        registerServerConfigurationPacket(CheckDataServerPacketImpl.ID, CheckDataServerPacketImpl::new);
        registerClientConfigurationPacket(ConfigurationCheckFailedPacketImpl.ID, ConfigurationCheckFailedPacketImpl::new);

        ServerConfigurationConnectionEvents.CONFIGURE.register((handler, server) -> {
            final ServerConfigurationNetworkAddon addon = ServerNetworkingImpl.getAddon(handler);
            handler.addTask(new CheckDataConfigurationTaskImpl(addon));
        });
    }

    public static <T extends ClientPlayPacketImpl> void registerClientPlayPacket(ResourceLocation id, Function<FriendlyByteBuf, T> fromBuffer) {
        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) ClientPlayNetworking.registerGlobalReceiver(id, (Minecraft client, ClientPacketListener handler, FriendlyByteBuf buf, PacketSender responseSender) -> fromBuffer.apply(buf).handleClient(client, handler, responseSender));
    }

    public static <T extends ServerPlayPacketImpl> void registerServerPlayPacket(ResourceLocation id, Function<FriendlyByteBuf, T> fromBuffer) {
        ServerPlayNetworking.registerGlobalReceiver(id, (MinecraftServer server, ServerPlayer player, ServerGamePacketListenerImpl handler, FriendlyByteBuf buf, PacketSender responseSender) -> fromBuffer.apply(buf).handleServer(server, player, handler, responseSender));
    }

    public static <T extends ClientConfigurationPacketImpl> void registerClientConfigurationPacket(ResourceLocation id, Function<FriendlyByteBuf, T> fromBuffer) {
        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) ClientConfigurationNetworking.registerGlobalReceiver(id, (Minecraft client, ClientConfigurationPacketListenerImpl handler, FriendlyByteBuf buf, PacketSender responseSender) -> fromBuffer.apply(buf).handleClient(client, handler, responseSender));
    }

    public static <T extends ServerConfigurationPacketImpl> void registerServerConfigurationPacket(ResourceLocation id, Function<FriendlyByteBuf, T> fromBuffer) {
        ServerConfigurationNetworking.registerGlobalReceiver(id, (MinecraftServer server, ServerConfigurationPacketListenerImpl handler, FriendlyByteBuf buf, PacketSender responseSender) -> fromBuffer.apply(buf).handleServer(server, handler, responseSender));
    }

    public static <T extends APPacketImpl> void send(T packet, PacketSender sender) {
        sender.sendPacket(packet.getID(), packet.getBuf());
    }

    public static void sendToServer(ServerPacket packet) {
        assert packet instanceof APPacketImpl;
        APPacketImpl apPacket = (APPacketImpl) packet;
        ClientPlayNetworking.send(apPacket.getID(), apPacket.getBuf());
    }

    public static void sendToClient(ClientPacket packet, ServerPlayer player) {
        assert packet instanceof APPacketImpl;
        APPacketImpl apPacket = (APPacketImpl) packet;
        ServerPlayNetworking.send(player, apPacket.getID(), apPacket.getBuf());
    }
}