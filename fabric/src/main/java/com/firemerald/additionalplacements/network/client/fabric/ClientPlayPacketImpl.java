package com.firemerald.additionalplacements.network.client.fabric;

import com.firemerald.additionalplacements.network.APPacket;
import com.firemerald.additionalplacements.network.client.ClientPlayPacket;
import com.firemerald.additionalplacements.network.fabric.APPacketImpl;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientPacketListener;

public interface ClientPlayPacketImpl extends APPacketImpl, ClientPlayPacket {
    @Environment(EnvType.CLIENT)
    default void handleClient(Minecraft client, ClientPacketListener handler, PacketSender responseSender) {
        APPacket reply = handleClient(handler.getConnection(), client::execute);
        if (reply instanceof APPacketImpl apPacket) apPacket.send(responseSender);
    }
}
