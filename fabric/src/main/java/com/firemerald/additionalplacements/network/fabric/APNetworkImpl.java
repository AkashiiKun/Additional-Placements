package com.firemerald.additionalplacements.network.fabric;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Function;

import com.firemerald.additionalplacements.network.client.*;
import com.firemerald.additionalplacements.network.client.fabric.CheckDataClientPacketImpl;
import com.firemerald.additionalplacements.network.client.fabric.ClientLoginPacketImpl;
import com.firemerald.additionalplacements.network.client.fabric.ClientPlayPacketImpl;
import com.firemerald.additionalplacements.network.client.fabric.ConfigurationCheckFailedPacketImpl;
import com.firemerald.additionalplacements.network.server.*;

import com.firemerald.additionalplacements.network.server.fabric.CheckDataServerPacketImpl;
import com.firemerald.additionalplacements.network.server.fabric.ServerLoginPacketImpl;
import com.firemerald.additionalplacements.network.server.fabric.ServerPlayPacketImpl;
import com.firemerald.additionalplacements.network.server.fabric.SetPlacementTogglePacketImpl;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import net.fabricmc.api.EnvType;
import net.fabricmc.fabric.api.client.networking.v1.ClientLoginNetworking;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.ServerLoginConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerLoginNetworking;
import net.fabricmc.fabric.api.networking.v1.ServerLoginNetworking.LoginSynchronizer;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientHandshakePacketListenerImpl;
import net.minecraft.client.multiplayer.ClientPacketListener;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.server.network.ServerLoginPacketListenerImpl;

public class APNetworkImpl
{
    public static void register() {
        registerServerPlayPacket(SetPlacementTogglePacketImpl.ID, SetPlacementTogglePacketImpl::new);
        registerLoginResponsePackets(CheckDataClientPacketImpl.ID, CheckDataClientPacketImpl::new, CheckDataServerPacketImpl::new);
        registerClientLoginPacket(ConfigurationCheckFailedPacketImpl.ID, ConfigurationCheckFailedPacketImpl::new);
        ServerLoginConnectionEvents.QUERY_START.register(APNetworkImpl::onLoginQuery);
    }

    public static <T extends ClientPlayPacketImpl> void registerClientPlayPacket(ResourceLocation id, Function<FriendlyByteBuf, T> fromBuffer) {
        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) ClientPlayNetworking.registerGlobalReceiver(id, (Minecraft client, ClientPacketListener handler, FriendlyByteBuf buf, PacketSender responseSender) -> fromBuffer.apply(buf).handleClient(client, handler, responseSender));
    }

    public static <T extends ServerPlayPacketImpl> void registerServerPlayPacket(ResourceLocation id, Function<FriendlyByteBuf, T> fromBuffer) {
        ServerPlayNetworking.registerGlobalReceiver(id, (MinecraftServer server, ServerPlayer player, ServerGamePacketListenerImpl handler, FriendlyByteBuf buf, PacketSender responseSender) -> fromBuffer.apply(buf).handleServer(server, player, handler, responseSender));
    }

    public static <T extends ClientLoginPacketImpl> void registerClientLoginPacket(ResourceLocation id, Function<FriendlyByteBuf, T> fromBuffer) {
        if (FabricLoader.getInstance().getEnvironmentType() == EnvType.CLIENT) ClientLoginNetworking.registerGlobalReceiver(id, (Minecraft client, ClientHandshakePacketListenerImpl handler, FriendlyByteBuf buf, Consumer<GenericFutureListener<? extends Future<? super Void>>> listenerAdder) -> fromBuffer.apply(buf).handleClient(client, handler, listenerAdder));
    }

    public static <T extends ServerLoginPacketImpl> void registerServerLoginPacket(ResourceLocation id, Function<FriendlyByteBuf, T> fromBuffer) {
        ServerLoginNetworking.registerGlobalReceiver(id, (MinecraftServer server, ServerLoginPacketListenerImpl handler, boolean understood, FriendlyByteBuf buf, LoginSynchronizer synchronizer, PacketSender responseSender) -> fromBuffer.apply(buf).handleServer(server, handler, understood, synchronizer, responseSender));
    }

    public static <T extends ClientLoginPacketImpl, U extends ServerLoginPacketImpl> void registerLoginResponsePackets(ResourceLocation id, Function<FriendlyByteBuf, T> clientFromBuffer, Function<FriendlyByteBuf, U> serverFromBuffer) {
        registerClientLoginPacket(id, clientFromBuffer);
        registerServerLoginPacket(id, serverFromBuffer);
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

    public static CompletableFuture<Void> dataCheckWaiter;

    public static void onLoginQuery(ServerLoginPacketListenerImpl handler, MinecraftServer server, PacketSender sender, ServerLoginNetworking.LoginSynchronizer synchronizer) {
        dataCheckWaiter = new CompletableFuture<>();
        new CheckDataClientPacketImpl().send(sender);
        synchronizer.waitFor(dataCheckWaiter);
    }
}