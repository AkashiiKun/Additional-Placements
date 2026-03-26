package com.firemerald.additionalplacements.network.server.forge;

import com.firemerald.additionalplacements.network.APPacket;
import com.firemerald.additionalplacements.network.forge.APLoginPacket;
import com.firemerald.additionalplacements.network.forge.APNetworkImpl;
import com.firemerald.additionalplacements.network.server.ServerLoginPacket;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

public interface ServerLoginPacketImpl extends APLoginPacket, ServerLoginPacket {
    @Override
    default NetworkDirection getDirection() {
        return NetworkDirection.LOGIN_TO_SERVER;
    }

    @Override
    default void handle(NetworkEvent.Context context) {
        APPacket reply = handleServer(context::enqueueWork, context.getNetworkManager()::disconnect);
        if (reply != null) APNetworkImpl.reply(reply, context);
    }
}
