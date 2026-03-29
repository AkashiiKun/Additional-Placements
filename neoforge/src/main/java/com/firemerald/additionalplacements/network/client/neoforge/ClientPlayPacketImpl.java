package com.firemerald.additionalplacements.network.client.neoforge;

import com.firemerald.additionalplacements.network.APPacket;
import com.firemerald.additionalplacements.network.client.ClientPlayPacket;
import com.firemerald.additionalplacements.network.server.neoforge.ServerPlayPacketImpl;
import net.neoforged.neoforge.network.handling.PlayPayloadContext;

public interface ClientPlayPacketImpl extends ClientPacketImpl<PlayPayloadContext>, ClientPlayPacket {
    @Override
    default void handleImpl(PlayPayloadContext context) {
        APPacket reply = handleClient(context.workHandler()::execute);
        if (reply instanceof ServerPlayPacketImpl packet) packet.reply(context);
    }
}
