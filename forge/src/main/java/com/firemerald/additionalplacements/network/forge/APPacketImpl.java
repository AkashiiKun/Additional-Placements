package com.firemerald.additionalplacements.network.forge;

import com.firemerald.additionalplacements.AdditionalPlacementsMod;
import com.firemerald.additionalplacements.network.APPacket;
import net.minecraftforge.network.NetworkDirection;
import net.minecraftforge.network.NetworkEvent;

import java.util.function.Supplier;

public interface APPacketImpl extends APPacket {
    NetworkDirection getDirection();

    void handle(NetworkEvent.Context context);

    default void handle(Supplier<NetworkEvent.Context> supplier) {
        NetworkEvent.Context context = supplier.get();
        if (context.getDirection() == getDirection()) {
            context.setPacketHandled(true);
            handle(context);
        }
        else AdditionalPlacementsMod.LOGGER.error("Tried to handle {} with invalid direction {}", getClass(), context.getDirection());
    }

    default void reply(NetworkEvent.Context context) {
        APNetworkImpl.reply(this, context);
    }
}
