package com.firemerald.additionalplacements.network.forge;

import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.function.Supplier;

import com.firemerald.additionalplacements.AdditionalPlacementsMod;
import com.firemerald.additionalplacements.network.APPacket;
import com.firemerald.additionalplacements.network.client.*;
import com.firemerald.additionalplacements.network.client.forge.CheckDataClientPacketImpl;
import com.firemerald.additionalplacements.network.client.forge.ClientLoginPacketImpl;
import com.firemerald.additionalplacements.network.client.forge.ClientPlayPacketImpl;
import com.firemerald.additionalplacements.network.client.forge.ConfigurationCheckFailedPacketImpl;
import com.firemerald.additionalplacements.network.server.*;

import com.firemerald.additionalplacements.network.server.forge.CheckDataServerPacketImpl;
import com.firemerald.additionalplacements.network.server.forge.ServerLoginPacketImpl;
import com.firemerald.additionalplacements.network.server.forge.ServerPlayPacketImpl;
import com.firemerald.additionalplacements.network.server.forge.SetPlacementTogglePacketImpl;
import net.minecraft.network.Connection;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerPlayer;
import net.minecraftforge.network.*;
import net.minecraftforge.network.NetworkRegistry.ChannelBuilder;
import net.minecraftforge.network.PacketDistributor.PacketTarget;
import net.minecraftforge.network.simple.SimpleChannel;
import net.minecraftforge.network.simple.SimpleChannel.MessageBuilder;

public class APNetworkImpl {
    public static SimpleChannel INSTANCE;
    public static final String VERSION = "1";

    private static int packetId = 0;

    private static int id() {
        return packetId++;
    }

    public static void register() {
        INSTANCE = ChannelBuilder
                .named(AdditionalPlacementsMod.rl("network"))
                .networkProtocolVersion(() -> VERSION)
                .clientAcceptedVersions(s -> VERSION.equals(s) || NetworkRegistry.ABSENT.toString().equals(s))
                .serverAcceptedVersions(VERSION::equals)
                .simpleChannel();
        registerServerPlayPacket(SetPlacementTogglePacketImpl.class, SetPlacementTogglePacketImpl::new);
        registerClientLoginPacket(CheckDataClientPacketImpl.class, CheckDataClientPacketImpl::new);
        registerServerLoginReplyPacket(CheckDataServerPacketImpl.class, CheckDataServerPacketImpl::new);
        registerClientLoginReplyPacket(ConfigurationCheckFailedPacketImpl.class, ConfigurationCheckFailedPacketImpl::new);
    }

    public static <T extends ClientPlayPacketImpl> void registerClientPlayPacket(Class<T> clazz, Function<FriendlyByteBuf, T> decoder) {
        registerPlayPacket(clazz, decoder, NetworkDirection.PLAY_TO_CLIENT);
    }

    public static <T extends ServerPlayPacketImpl> void registerServerPlayPacket(Class<T> clazz, Function<FriendlyByteBuf, T> decoder) {
        registerPlayPacket(clazz, decoder, NetworkDirection.PLAY_TO_SERVER);
    }

    public static <T extends APPacketImpl> void registerPlayPacket(Class<T> clazz, Function<FriendlyByteBuf, T> decoder, NetworkDirection direction) {
        registerPlayPacket(INSTANCE.messageBuilder(clazz, id(), direction), decoder);
    }

    public static <T extends APPacketImpl> void registerPlayPacket(MessageBuilder<T> builder, Function<FriendlyByteBuf, T> decoder) {
        builder
                .decoder(decoder)
                .encoder(APPacketImpl::write)
                .consumerMainThread(APPacketImpl::handle)
                .add();
    }

    public static <T extends ClientPlayPacketImpl> void registerClientPlayPacketAsync(Class<T> clazz, Function<FriendlyByteBuf, T> decoder) {
        registerPlayPacketAsync(clazz, decoder, NetworkDirection.PLAY_TO_CLIENT);
    }

    public static <T extends ServerPlayPacketImpl> void registerServerPlayPacketAsync(Class<T> clazz, Function<FriendlyByteBuf, T> decoder) {
        registerPlayPacketAsync(clazz, decoder, NetworkDirection.PLAY_TO_SERVER);
    }

    public static <T extends APPacketImpl> void registerPlayPacketAsync(Class<T> clazz, Function<FriendlyByteBuf, T> decoder, NetworkDirection direction) {
        registerPlayPacketAsync(INSTANCE.messageBuilder(clazz, id(), direction), decoder);
    }

    public static <T extends APPacketImpl> void registerPlayPacketAsync(MessageBuilder<T> builder, Function<FriendlyByteBuf, T> decoder) {
        builder
                .decoder(decoder)
                .encoder(APPacketImpl::write)
                .consumerNetworkThread((BiConsumer<T, Supplier<NetworkEvent.Context>>) APPacketImpl::handle)
                .add();
    }

    public static <T extends ClientLoginPacketImpl> void registerClientLoginPacket(Class<T> clazz, Function<FriendlyByteBuf, T> decoder) {
        registerClientLoginPacket(INSTANCE.messageBuilder(clazz, id(), NetworkDirection.LOGIN_TO_CLIENT), decoder);
    }

    public static <T extends ClientLoginPacketImpl> void registerClientLoginPacket(MessageBuilder<T> builder, Function<FriendlyByteBuf, T> decoder) {
        builder
                .loginIndex(ClientLoginPacketImpl::getLoginIndex, ClientLoginPacketImpl::setLoginIndex)
                .decoder(decoder)
                .encoder(ClientLoginPacketImpl::write)
                .markAsLoginPacket()
                .consumerNetworkThread(HandshakeHandler.biConsumerFor(APLoginPacket::handle))
                .add();
    }

    public static <T extends ClientLoginPacketImpl> void registerClientLoginReplyPacket(Class<T> clazz, Function<FriendlyByteBuf, T> decoder) {
        registerClientLoginReplyPacket(INSTANCE.messageBuilder(clazz, id(), NetworkDirection.LOGIN_TO_CLIENT), decoder);
    }

    public static <T extends ClientLoginPacketImpl> void registerClientLoginReplyPacket(MessageBuilder<T> builder, Function<FriendlyByteBuf, T> decoder) {
        builder
                .loginIndex(ClientLoginPacketImpl::getLoginIndex, ClientLoginPacketImpl::setLoginIndex)
                .decoder(decoder)
                .encoder(ClientLoginPacketImpl::write)
                .consumerNetworkThread(HandshakeHandler.biConsumerFor(APLoginPacket::handle))
                .add();
    }

    public static <T extends ServerLoginPacketImpl> void registerServerLoginReplyPacket(Class<T> clazz, Function<FriendlyByteBuf, T> decoder) {
        registerServerLoginReplyPacket(INSTANCE.messageBuilder(clazz, id(), NetworkDirection.LOGIN_TO_SERVER), decoder);
    }

    public static <T extends ServerLoginPacketImpl> void registerServerLoginReplyPacket(MessageBuilder<T> builder, Function<FriendlyByteBuf, T> decoder) {
        builder
                .loginIndex(ServerLoginPacketImpl::getLoginIndex, ServerLoginPacketImpl::setLoginIndex)
                .decoder(decoder)
                .encoder(ServerLoginPacketImpl::write)
                .consumerNetworkThread(HandshakeHandler.indexFirst(APLoginPacket::handle))
                .add();
    }

    public static void sendToServer(ServerPacket packet) {
        sendTo(packet, PacketDistributor.SERVER.noArg());
    }

    public static void sendToClient(ClientPacket packet, ServerPlayer player) {
        sendTo(packet, PacketDistributor.PLAYER.with(() -> player));
    }

    public static void sendTo(APPacket packet, PacketTarget target) {
        INSTANCE.send(target, packet);
    }

    public static void send(APPacket packet, Connection connection, NetworkDirection direction) {
        INSTANCE.sendTo(packet, connection, direction);
    }

    public static void reply(APPacket packet, NetworkEvent.Context context) {
        INSTANCE.reply(packet, context);
    }
}