package com.firemerald.additionalplacements.network.server.forge;

import com.firemerald.additionalplacements.network.forge.APPacketImpl;
import net.minecraftforge.network.NetworkDirection;

public interface ServerPacketImpl extends APPacketImpl {
    @Override
    default NetworkDirection getDirection() {
        return NetworkDirection.PLAY_TO_SERVER;
    }
}
