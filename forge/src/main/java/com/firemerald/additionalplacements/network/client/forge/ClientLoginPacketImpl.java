package com.firemerald.additionalplacements.network.client.forge;

import com.firemerald.additionalplacements.network.APPacket;
import com.firemerald.additionalplacements.network.client.ClientLoginPacket;
import com.firemerald.additionalplacements.network.forge.APLoginPacket;
import com.firemerald.additionalplacements.network.forge.APNetworkImpl;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

public interface ClientLoginPacketImpl extends APLoginPacket, ClientLoginPacket {
    @Override
    default NetworkDirection getDirection() {
        return NetworkDirection.LOGIN_TO_CLIENT;
    }

    @Override
    default void handle(NetworkEvent.Context context) {
        APPacket reply = handleClient(context::enqueueWork, context.getNetworkManager()::disconnect);
        if (reply != null) APNetworkImpl.reply(reply, context);
    }
}
