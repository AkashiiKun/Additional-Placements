package com.firemerald.additionalplacements.network.server.forge;

import com.firemerald.additionalplacements.network.server.SetPlacementTogglePacket;
import net.minecraft.network.FriendlyByteBuf;

public class SetPlacementTogglePacketImpl extends SetPlacementTogglePacket implements ServerPlayPacketImpl {
    public static SetPlacementTogglePacket of(boolean state) {
        return new SetPlacementTogglePacketImpl(state);
    }

    public SetPlacementTogglePacketImpl(boolean state) {
        super(state);
    }

    public SetPlacementTogglePacketImpl(FriendlyByteBuf buf) {
        super(buf);
    }
}
