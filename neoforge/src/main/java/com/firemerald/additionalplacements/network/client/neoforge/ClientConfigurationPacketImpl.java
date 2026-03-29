package com.firemerald.additionalplacements.network.client.neoforge;

import com.firemerald.additionalplacements.network.APPacket;
import com.firemerald.additionalplacements.network.client.ClientConfigurationPacket;
import com.firemerald.additionalplacements.network.server.neoforge.ServerConfigurationPacketImpl;
import net.neoforged.neoforge.network.handling.ConfigurationPayloadContext;

public interface ClientConfigurationPacketImpl extends ClientPacketImpl<ConfigurationPayloadContext>, ClientConfigurationPacket {
    @Override
    default void handleImpl(ConfigurationPayloadContext context) {
        APPacket reply = handleClient(context.workHandler()::execute, context.packetHandler()::disconnect);
        if (reply instanceof ServerConfigurationPacketImpl packet) packet.reply(context);
    }
}
