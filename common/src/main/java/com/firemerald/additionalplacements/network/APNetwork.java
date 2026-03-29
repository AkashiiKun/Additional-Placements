package com.firemerald.additionalplacements.network;

import com.firemerald.additionalplacements.network.client.*;
import com.firemerald.additionalplacements.network.server.*;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.server.level.ServerPlayer;
import org.jetbrains.annotations.ApiStatus;

public class APNetwork {
    @ExpectPlatform
    @ApiStatus.Internal
    public static void sendToServer(ServerPacket<?> packet) {
        throw new AssertionError();
    }

    @ExpectPlatform
    @ApiStatus.Internal
    public static void sendToClient(ClientPacket<?> packet, ServerPlayer player) {
        throw new AssertionError();
    }
}