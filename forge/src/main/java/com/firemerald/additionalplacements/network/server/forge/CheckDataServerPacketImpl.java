package com.firemerald.additionalplacements.network.server.forge;

import com.firemerald.additionalplacements.network.server.CheckDataServerPacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;

public class CheckDataServerPacketImpl extends CheckDataServerPacket implements ServerLoginPacketImpl {
    public static CheckDataServerPacket of(Map<ResourceLocation, CompoundTag> serverData) {
        return new CheckDataServerPacketImpl(serverData);
    }

    private int loginIndex = -1;

    public CheckDataServerPacketImpl(Map<ResourceLocation, CompoundTag> serverData) {
        super(serverData);
    }

    public CheckDataServerPacketImpl(FriendlyByteBuf buf) {
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
