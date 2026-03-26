package com.firemerald.additionalplacements.network.client.forge;

import com.firemerald.additionalplacements.network.client.ConfigurationCheckFailedPacket;
import com.firemerald.additionalplacements.util.MessageTree;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import org.apache.commons.lang3.tuple.Triple;

import java.util.List;

public class ConfigurationCheckFailedPacketImpl extends ConfigurationCheckFailedPacket implements ClientLoginPacketImpl {
    public static ConfigurationCheckFailedPacket of(List<Triple<ResourceLocation, List<MessageTree>, List<MessageTree>>> compiledErrors) {
        return new ConfigurationCheckFailedPacketImpl(compiledErrors);
    }

    private int loginIndex = -1;

    public ConfigurationCheckFailedPacketImpl(List<Triple<ResourceLocation, List<MessageTree>, List<MessageTree>>> compiledErrors) {
        super(compiledErrors);
    }

    public ConfigurationCheckFailedPacketImpl(FriendlyByteBuf buf) {
        super(buf);
    }

    @Override
    public void setLoginIndex(int loginIndex) {
        this.loginIndex = loginIndex;
    }

    @Override
    public int getLoginIndex() {
        return loginIndex;
    }
}
