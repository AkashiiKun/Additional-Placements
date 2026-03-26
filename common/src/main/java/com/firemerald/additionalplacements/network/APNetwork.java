package com.firemerald.additionalplacements.network;

import com.firemerald.additionalplacements.network.client.*;
import com.firemerald.additionalplacements.network.server.*;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.server.level.ServerPlayer;

public class APNetwork
{
    @ExpectPlatform
    @Deprecated
    public static void sendToServer(ServerPacket packet) {
        throw new AssertionError();
    }

    @ExpectPlatform
    @Deprecated
    public static void sendToClient(ClientPacket packet, ServerPlayer player) {
        throw new AssertionError();
    }
}