package com.firemerald.additionalplacements.network.server;

import com.firemerald.additionalplacements.network.APNetwork;
import com.firemerald.additionalplacements.network.APPacket;

public interface ServerPacket extends APPacket {
    default void sendToServer() {
        APNetwork.sendToServer(this);
    }
}
