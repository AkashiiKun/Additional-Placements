package com.firemerald.additionalplacements.network.server.forge;

import com.firemerald.additionalplacements.network.APNetwork;
import com.firemerald.additionalplacements.network.APPacket;
import com.firemerald.additionalplacements.network.forge.APNetworkImpl;
import com.firemerald.additionalplacements.network.forge.APPacketImpl;
import com.firemerald.additionalplacements.network.server.ServerPlayPacket;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

public interface ServerPlayPacketImpl extends APPacketImpl, ServerPlayPacket {
    @Override
    default NetworkDirection getDirection() {
        return NetworkDirection.PLAY_TO_SERVER;
    }

    @Override
    default void handle(NetworkEvent.Context context) {
        APPacket reply = handleServer(context.getSender(), context.getNetworkManager(), context::enqueueWork);
        if (reply != null) APNetworkImpl.reply(reply, context);
    }
}
