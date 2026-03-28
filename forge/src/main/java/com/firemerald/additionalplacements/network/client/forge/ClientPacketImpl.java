package com.firemerald.additionalplacements.network.client.forge;

import com.firemerald.additionalplacements.network.forge.APPacketImpl;
import net.minecraftforge.network.NetworkDirection;

public interface ClientPacketImpl extends APPacketImpl {
    @Override
    default NetworkDirection getDirection() {
        return NetworkDirection.PLAY_TO_CLIENT;
    }
}
