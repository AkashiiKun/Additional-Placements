package com.firemerald.additionalplacements.network.server.neoforge;

import com.firemerald.additionalplacements.network.APPacket;
import com.firemerald.additionalplacements.network.client.neoforge.ClientConfigurationPacketImpl;
import com.firemerald.additionalplacements.network.server.ServerConfigurationPacket;
import net.neoforged.neoforge.network.handling.ConfigurationPayloadContext;

public interface ServerConfigurationPacketImpl extends ServerPacketImpl<ConfigurationPayloadContext>, ServerConfigurationPacket {
    @Override
    default void handleImpl(ConfigurationPayloadContext context) {
        APPacket reply = handleServer(context.workHandler()::execute, context.packetHandler()::disconnect, context.taskCompletedHandler()::onTaskCompleted);
        if (reply instanceof ClientConfigurationPacketImpl packet) packet.reply(context);
    }
}
