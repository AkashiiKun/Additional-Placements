package com.firemerald.additionalplacements.network.forge;

import com.firemerald.additionalplacements.network.APPacket;
import net.minecraftforge.network.HandshakeHandler;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.IntSupplier;
import java.util.function.Supplier;

public interface APLoginPacket extends APPacketImpl, IntSupplier {
    void setLoginIndex(final int loginIndex);

    int getLoginIndex();

    @Override
    default int getAsInt() {
        return getLoginIndex();
    }

    static <T extends APLoginPacket> void handle(HandshakeHandler handler, T msg, Supplier<NetworkEvent.Context> context) {
        msg.handle(context);
    }
}
