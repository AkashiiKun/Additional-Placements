package com.firemerald.additionalplacements.network.client.forge;

import com.firemerald.additionalplacements.network.client.ConfigurationCheckFailedPacket;
import com.firemerald.additionalplacements.util.MessageTree;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import org.apache.commons.lang3.tuple.Triple;

import java.util.List;

public class ConfigurationCheckFailedPacketImpl extends ConfigurationCheckFailedPacket implements ClientConfigurationPacketImpl {
    public static ConfigurationCheckFailedPacket of(List<Triple<ResourceLocation, List<MessageTree>, List<MessageTree>>> compiledErrors) {
        return new ConfigurationCheckFailedPacketImpl(compiledErrors);
    }

    public ConfigurationCheckFailedPacketImpl(List<Triple<ResourceLocation, List<MessageTree>, List<MessageTree>>> compiledErrors) {
        super(compiledErrors);
    }

    public ConfigurationCheckFailedPacketImpl(FriendlyByteBuf buf) {
        super(buf);
    }
}
