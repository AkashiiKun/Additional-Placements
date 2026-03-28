package com.firemerald.additionalplacements.network.server.fabric;

import com.firemerald.additionalplacements.AdditionalPlacementsMod;
import com.firemerald.additionalplacements.network.server.CheckDataServerPacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

import java.util.Map;

public class CheckDataServerPacketImpl extends CheckDataServerPacket implements ServerConfigurationPacketImpl {
    public static final ResourceLocation ID = AdditionalPlacementsMod.rl("check_data_server");

    public static CheckDataServerPacket of(Map<ResourceLocation, CompoundTag> serverData) {
        return new CheckDataServerPacketImpl(serverData);
    }

    public CheckDataServerPacketImpl(Map<ResourceLocation, CompoundTag> serverData) {
        super(serverData);
    }

    public CheckDataServerPacketImpl(FriendlyByteBuf buf) {
        super(buf);
    }

    @Override
    public ResourceLocation getID() {
        return ID;
    }
}
