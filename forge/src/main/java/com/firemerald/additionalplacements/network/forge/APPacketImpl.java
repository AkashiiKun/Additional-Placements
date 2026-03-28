package com.firemerald.additionalplacements.network.forge;

import com.firemerald.additionalplacements.AdditionalPlacementsMod;
import com.firemerald.additionalplacements.network.APPacket;
import net.minecraftforge.event.network.CustomPayloadEvent;
import net.minecraftforge.network.NetworkDirection;

public interface APPacketImpl extends APPacket {
    NetworkDirection getDirection();

    void handleImpl(CustomPayloadEvent.Context context);

    default void handle(CustomPayloadEvent.Context context) {
        if (context.getDirection() == getDirection()) {
            context.setPacketHandled(true);
            handleImpl(context);
        }
        else AdditionalPlacementsMod.LOGGER.error("Tried to handle {} with invalid direction {}", getClass(), context.getDirection());
    }

    default void reply(CustomPayloadEvent.Context context) {
        APNetworkImpl.reply(this, context);
    }
}
