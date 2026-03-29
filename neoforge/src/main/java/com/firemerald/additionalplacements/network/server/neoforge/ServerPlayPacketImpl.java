package com.firemerald.additionalplacements.network.server.neoforge;

import com.firemerald.additionalplacements.network.APPacket;
import com.firemerald.additionalplacements.network.client.neoforge.ClientPlayPacketImpl;
import com.firemerald.additionalplacements.network.server.ServerPlayPacket;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

public interface ServerPlayPacketImpl extends ServerPacketImpl<PlayPayloadContext>, ServerPlayPacket {
    @SuppressWarnings("OptionalGetWithoutIsPresent")
    @Override
    default void handleImpl(PlayPayloadContext context) {
        APPacket reply = handleServer((ServerPlayer) context.player().get(), context.workHandler()::execute);
        if (reply instanceof ClientPlayPacketImpl packet) packet.reply(context);
    }
}
