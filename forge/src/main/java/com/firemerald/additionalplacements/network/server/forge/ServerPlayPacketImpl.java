package com.firemerald.additionalplacements.network.server.forge;

import com.firemerald.additionalplacements.network.APPacket;
import com.firemerald.additionalplacements.network.forge.APNetworkImpl;
import com.firemerald.additionalplacements.network.server.ServerPlayPacket;
import net.minecraftforge.event.network.CustomPayloadEvent;

public interface ServerPlayPacketImpl extends ServerPacketImpl, ServerPlayPacket {
    @Override
    default void handleImpl(CustomPayloadEvent.Context context) {
        APPacket reply = handleServer(context.getSender(), context::enqueueWork);
        if (reply != null) APNetworkImpl.reply(reply, context);
    }
}
