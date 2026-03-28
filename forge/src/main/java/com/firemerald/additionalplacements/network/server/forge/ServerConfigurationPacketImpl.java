package com.firemerald.additionalplacements.network.server.forge;

import com.firemerald.additionalplacements.network.APPacket;
import com.firemerald.additionalplacements.network.forge.APNetworkImpl;
import com.firemerald.additionalplacements.network.server.ServerConfigurationPacket;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.minecraftforge.network.NetworkContext;

public interface ServerConfigurationPacketImpl extends ServerPacketImpl, ServerConfigurationPacket {
    @Override
    default void handleImpl(CustomPayloadEvent.Context context) {
        APPacket reply = handleServer(context::enqueueWork, context.getConnection()::disconnect, NetworkContext.get(context.getConnection())::finishTask);
        if (reply != null) APNetworkImpl.reply(reply, context);
    }
}
