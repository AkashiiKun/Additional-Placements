package com.firemerald.additionalplacements.network.client.forge;

import com.firemerald.additionalplacements.network.APPacket;
import com.firemerald.additionalplacements.network.client.ClientPlayPacket;
import com.firemerald.additionalplacements.network.forge.APNetworkImpl;
import com.firemerald.additionalplacements.network.forge.APPacketImpl;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

public interface ClientPlayPacketImpl extends APPacketImpl, ClientPlayPacket {
    @Override
    default NetworkDirection getDirection() {
        return NetworkDirection.PLAY_TO_CLIENT;
    }

    @Override
    default void handle(NetworkEvent.Context context) {
        APPacket reply = handleClient(context.getNetworkManager(), context::enqueueWork);
        if (reply != null) APNetworkImpl.reply(reply, context);
    }
}
