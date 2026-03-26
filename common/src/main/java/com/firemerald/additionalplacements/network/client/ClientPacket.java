package com.firemerald.additionalplacements.network.client;

import com.firemerald.additionalplacements.network.APNetwork;
import com.firemerald.additionalplacements.network.APPacket;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.server.level.ServerPlayer;

import java.util.function.Consumer;

public interface ClientPacket extends APPacket {
	default void send(ServerPlayer player) {
		APNetwork.sendToClient(this, player);
	}
}
