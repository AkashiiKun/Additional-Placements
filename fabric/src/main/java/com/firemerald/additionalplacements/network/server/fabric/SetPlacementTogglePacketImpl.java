package com.firemerald.additionalplacements.network.server.fabric;

import com.firemerald.additionalplacements.AdditionalPlacementsMod;
import com.firemerald.additionalplacements.network.server.SetPlacementTogglePacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public class SetPlacementTogglePacketImpl extends SetPlacementTogglePacket implements ServerPlayPacketImpl {
    public static final ResourceLocation ID = AdditionalPlacementsMod.rl("set_placement_toggle");

    public static SetPlacementTogglePacket of(boolean state) {
        return new SetPlacementTogglePacketImpl(state);
    }

    public SetPlacementTogglePacketImpl(boolean state) {
        super(state);
    }

    public SetPlacementTogglePacketImpl(FriendlyByteBuf buf) {
        super(buf);
    }

    @Override
    public ResourceLocation getID() {
        return ID;
    }
}
