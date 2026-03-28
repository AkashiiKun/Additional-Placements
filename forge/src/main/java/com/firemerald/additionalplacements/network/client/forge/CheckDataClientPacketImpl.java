package com.firemerald.additionalplacements.network.client.forge;

import com.firemerald.additionalplacements.network.client.CheckDataClientPacket;
import net.minecraft.network.FriendlyByteBuf;

public class CheckDataClientPacketImpl extends CheckDataClientPacket implements ClientConfigurationPacketImpl {
    public static CheckDataClientPacket of() {
        return new CheckDataClientPacketImpl();
    }

    public CheckDataClientPacketImpl() {
        super();
    }

    public CheckDataClientPacketImpl(FriendlyByteBuf buf) {
        super(buf);
    }
}
