package com.firemerald.additionalplacements.network.client.fabric;

import com.firemerald.additionalplacements.network.APPacket;
import com.firemerald.additionalplacements.network.client.ClientConfigurationPacket;
import com.firemerald.additionalplacements.network.fabric.APNetworkImpl;
import com.firemerald.additionalplacements.network.fabric.APPacketImpl;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientConfigurationPacketListenerImpl;
import net.minecraft.network.protocol.common.ClientboundDisconnectPacket;

public interface ClientConfigurationPacketImpl extends APPacketImpl, ClientConfigurationPacket {
    @Environment(EnvType.CLIENT)
    default void handleClient(Minecraft client, ClientConfigurationPacketListenerImpl handler, PacketSender responseSender) {
        APPacket reply = handleClient(Minecraft.getInstance()::execute, reason -> handler.handleDisconnect(new ClientboundDisconnectPacket(reason)));
        if (reply instanceof APPacketImpl apPacket) APNetworkImpl.send(apPacket, responseSender);
    }
}
