package com.firemerald.additionalplacements.network.client.forge;

import com.firemerald.additionalplacements.network.APPacket;
import com.firemerald.additionalplacements.network.client.ClientConfigurationPacket;
import com.firemerald.additionalplacements.network.forge.APNetworkImpl;
import net.minecraftforge.event.network.CustomPayloadEvent;

public interface ClientConfigurationPacketImpl extends ClientPacketImpl, ClientConfigurationPacket {
    @Override
    default void handleImpl(CustomPayloadEvent.Context context) {
        APPacket reply = handleClient(context::enqueueWork, context.getConnection()::disconnect);
        if (reply != null) APNetworkImpl.reply(reply, context);
    }
}
