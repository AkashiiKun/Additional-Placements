package com.firemerald.additionalplacements.network.fabric;

import com.firemerald.additionalplacements.network.APPacket;
import net.fabricmc.fabric.api.networking.v1.PacketByteBufs;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public interface APPacketImpl extends APPacket {
    ResourceLocation getID();

    default FriendlyByteBuf getBuf() {
        FriendlyByteBuf buf = PacketByteBufs.create();
        write(buf);
        return buf;
    }

    default void send(PacketSender sender) {
        APNetworkImpl.send(this, sender);
    }
}
