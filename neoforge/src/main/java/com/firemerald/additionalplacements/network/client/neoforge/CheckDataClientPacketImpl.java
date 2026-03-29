package com.firemerald.additionalplacements.network.client.neoforge;

import com.firemerald.additionalplacements.AdditionalPlacementsMod;
import com.firemerald.additionalplacements.network.client.CheckDataClientPacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public class CheckDataClientPacketImpl extends CheckDataClientPacket implements ClientConfigurationPacketImpl {
    public static final ResourceLocation ID = AdditionalPlacementsMod.rl("check_data");

    public static CheckDataClientPacket of() {
        return new CheckDataClientPacketImpl();
    }

    public CheckDataClientPacketImpl() {
        super();
    }

    public CheckDataClientPacketImpl(FriendlyByteBuf buf) {
        super(buf);
    }

    @Override
    public ResourceLocation id() {
        return ID;
    }
}
