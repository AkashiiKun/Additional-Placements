package com.firemerald.additionalplacements.network.neoforge;

import com.firemerald.additionalplacements.AdditionalPlacementsMod;
import com.firemerald.additionalplacements.network.APPacket;
import net.minecraft.network.protocol.PacketFlow;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.neoforged.neoforge.network.handling.IPayloadContext;

public interface APPacketImpl<T extends IPayloadContext> extends APPacket, CustomPacketPayload {
    PacketFlow getFlow();

    void handleImpl(T context);

    default void handle(T context) {
        if (context.flow() == getFlow()) handleImpl(context);
        else AdditionalPlacementsMod.LOGGER.error("Tried to handle {} with invalid flow {}", getClass(), context.flow());
    }

    default void reply(T context) {
        context.replyHandler().send(this);
    }
}
