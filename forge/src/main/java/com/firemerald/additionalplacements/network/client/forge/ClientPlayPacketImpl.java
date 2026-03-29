package com.firemerald.additionalplacements.network.client.forge;

import com.firemerald.additionalplacements.network.APPacket;
import com.firemerald.additionalplacements.network.client.ClientPlayPacket;
import com.firemerald.additionalplacements.network.forge.APNetworkImpl;
import net.minecraftforge.event.network.CustomPayloadEvent;

public interface ClientPlayPacketImpl extends ClientPacketImpl, ClientPlayPacket {
    @Override
    default void handleImpl(CustomPayloadEvent.Context context) {
        APPacket reply = handleClient(context::enqueueWork);
        if (reply != null) APNetworkImpl.reply(reply, context);
    }
}
