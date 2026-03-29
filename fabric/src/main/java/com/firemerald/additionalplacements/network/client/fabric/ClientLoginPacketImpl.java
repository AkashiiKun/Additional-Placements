package com.firemerald.additionalplacements.network.client.fabric;

import com.firemerald.additionalplacements.network.APPacket;
import com.firemerald.additionalplacements.network.client.ClientLoginPacket;
import com.firemerald.additionalplacements.network.fabric.APPacketImpl;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientHandshakePacketListenerImpl;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.protocol.login.ClientboundLoginDisconnectPacket;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

public interface ClientLoginPacketImpl extends APPacketImpl, ClientLoginPacket {
    @Environment(EnvType.CLIENT)
    default CompletableFuture<@Nullable FriendlyByteBuf> handleClient(Minecraft client, ClientHandshakePacketListenerImpl handler, Consumer<GenericFutureListener<? extends Future<? super Void>>> listenerAdder) {
        return CompletableFuture.supplyAsync(() -> {
            APPacket reply = handleClient(client::execute, reason -> handler.handleDisconnect(new ClientboundLoginDisconnectPacket(reason)));
            if (reply instanceof APPacketImpl apPacket) return apPacket.getBuf();
            else return null;
        });
    }
}
