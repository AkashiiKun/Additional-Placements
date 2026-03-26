package com.firemerald.additionalplacements.network.client;

import com.firemerald.additionalplacements.network.APPacket;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.network.Connection;

import java.util.function.Consumer;

public interface ClientPlayPacket extends ClientPacket {
	@Environment(EnvType.CLIENT)
	APPacket handleClient(Connection connection, Consumer<Runnable> enqueueWork);
}
