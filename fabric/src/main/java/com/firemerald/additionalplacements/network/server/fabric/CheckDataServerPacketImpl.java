package com.firemerald.additionalplacements.network.server.fabric;

import com.firemerald.additionalplacements.network.APPacket;
import com.firemerald.additionalplacements.network.client.fabric.CheckDataClientPacketImpl;
import com.firemerald.additionalplacements.network.fabric.APNetworkImpl;
import com.firemerald.additionalplacements.network.server.CheckDataServerPacket;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.Map;
import java.util.function.Consumer;

public class CheckDataServerPacketImpl extends CheckDataServerPacket implements ServerLoginPacketImpl {
    public static final ResourceLocation ID = CheckDataClientPacketImpl.ID;

    public static CheckDataServerPacket of(Map<ResourceLocation, CompoundTag> serverData) {
        return new CheckDataServerPacketImpl(serverData);
    }
    public CheckDataServerPacketImpl(Map<ResourceLocation, CompoundTag> serverData) {
        super(serverData);
    }

    public CheckDataServerPacketImpl(FriendlyByteBuf buf) {
        super(buf);
    }

    @Override
    public ResourceLocation getID() {
        return ID;
    }

    @Override
    public @Nullable APPacket handleServer(Consumer<Runnable> enqueueWork, Consumer<Component> disconnect) {
        APPacket reply = super.handleServer(enqueueWork, disconnect);
        APNetworkImpl.dataCheckWaiter.complete(null);
        return reply;
    }
}
