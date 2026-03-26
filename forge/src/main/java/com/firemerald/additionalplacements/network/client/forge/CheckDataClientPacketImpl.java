package com.firemerald.additionalplacements.network.client.forge;

import com.firemerald.additionalplacements.network.client.CheckDataClientPacket;
import net.minecraft.network.FriendlyByteBuf;

public class CheckDataClientPacketImpl extends CheckDataClientPacket implements ClientLoginPacketImpl {
    public static CheckDataClientPacket of() {
        return new CheckDataClientPacketImpl();
    }

    private int loginIndex = -1;

    public CheckDataClientPacketImpl() {
        super();
    }

    public CheckDataClientPacketImpl(FriendlyByteBuf buf) {
        super(buf);
    }

    @Override
    public void setLoginIndex(int loginIndex) {
        this.loginIndex = loginIndex;
    }

    @Override
    public int getLoginIndex() {
        return loginIndex;
    }
}
