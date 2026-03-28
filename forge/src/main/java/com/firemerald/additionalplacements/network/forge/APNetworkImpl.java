package com.firemerald.additionalplacements.network.forge;

import java.util.function.Function;

import com.firemerald.additionalplacements.AdditionalPlacementsMod;
import com.firemerald.additionalplacements.network.APPacket;
import com.firemerald.additionalplacements.network.client.*;
import com.firemerald.additionalplacements.network.client.forge.*;
import com.firemerald.additionalplacements.network.server.*;

import com.firemerald.additionalplacements.network.server.forge.*;
import net.minecraft.network.Connection;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.minecraftforge.network.ChannelBuilder;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.PacketDistributor;
import net.minecraftforge.network.SimpleChannel;

public class APNetworkImpl {
    public static SimpleChannel INSTANCE;
    public static final int VERSION = 1;

    private static int packetId = 0;

    private static int id() {
        return packetId++;
    }

    public static void register() {
        INSTANCE = ChannelBuilder
                .named(AdditionalPlacementsMod.rl("network"))
                .networkProtocolVersion(VERSION)
                .simpleChannel();
        registerServerPlayPacket(SetPlacementTogglePacketImpl.class, SetPlacementTogglePacketImpl::new);
        registerClientPlayPacketAsync(CheckDataClientPacketImpl.class, CheckDataClientPacketImpl::new);
        registerServerPlayPacketAsync(CheckDataServerPacketImpl.class, CheckDataServerPacketImpl::new);
        registerClientPlayPacketAsync(ConfigurationCheckFailedPacketImpl.class, ConfigurationCheckFailedPacketImpl::new);
    }

    public static <T extends ClientPacketImpl> void registerClientPlayPacket(Class<T> clazz, Function<FriendlyByteBuf, T> decoder) {
        registerPlayPacket(clazz, decoder, NetworkDirection.PLAY_TO_CLIENT);
    }

    public static <T extends ServerPacketImpl> void registerServerPlayPacket(Class<T> clazz, Function<FriendlyByteBuf, T> decoder) {
        registerPlayPacket(clazz, decoder, NetworkDirection.PLAY_TO_SERVER);
    }

    public static <T extends APPacketImpl> void registerPlayPacket(Class<T> clazz, Function<FriendlyByteBuf, T> decoder, NetworkDirection direction) {
        registerPlayPacket(INSTANCE.messageBuilder(clazz, id(), direction), decoder);
    }

    public static <T extends APPacketImpl> void registerPlayPacket(SimpleChannel.MessageBuilder<T> builder, Function<FriendlyByteBuf, T> decoder) {
        builder
                .decoder(decoder)
                .encoder(APPacketImpl::write)
                .consumerMainThread(APPacketImpl::handleImpl)
                .add();
    }

    public static <T extends ClientPacketImpl> void registerClientPlayPacketAsync(Class<T> clazz, Function<FriendlyByteBuf, T> decoder) {
        registerPlayPacketAsync(clazz, decoder, NetworkDirection.PLAY_TO_CLIENT);
    }

    public static <T extends ServerPacketImpl> void registerServerPlayPacketAsync(Class<T> clazz, Function<FriendlyByteBuf, T> decoder) {
        registerPlayPacketAsync(clazz, decoder, NetworkDirection.PLAY_TO_SERVER);
    }

    public static <T extends APPacketImpl> void registerPlayPacketAsync(Class<T> clazz, Function<FriendlyByteBuf, T> decoder, NetworkDirection direction) {
        registerPlayPacketAsync(INSTANCE.messageBuilder(clazz, id(), direction), decoder);
    }

    public static <T extends APPacketImpl> void registerPlayPacketAsync(SimpleChannel.MessageBuilder<T> builder, Function<FriendlyByteBuf, T> decoder) {
        builder
                .decoder(decoder)
                .encoder(APPacketImpl::write)
                .consumerNetworkThread(APPacketImpl::handle)
                .add();
    }

    public static void sendToServer(ServerPacket packet) {
        sendTo(packet, PacketDistributor.SERVER.noArg());
    }

    public static void sendToClient(ClientPacket packet, ServerPlayer player) {
        sendTo(packet, PacketDistributor.PLAYER.with(player));
    }

    public static void sendTo(APPacket packet, PacketDistributor.PacketTarget target) {
        INSTANCE.send(packet, target);
    }

    public static void send(APPacket packet, Connection connection) {
        INSTANCE.send(packet, connection);
    }

    public static void reply(APPacket packet, CustomPayloadEvent.Context context) {
        INSTANCE.reply(packet, context);
    }
}