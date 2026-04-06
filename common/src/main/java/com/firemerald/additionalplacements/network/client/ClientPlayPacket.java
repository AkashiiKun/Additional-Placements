package com.firemerald.additionalplacements.network.client;

import com.firemerald.additionalplacements.network.APPacket;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;

import java.util.function.Consumer;

public interface ClientPlayPacket extends ClientPacket {
	@Environment(EnvType.CLIENT)
	APPacket handleClient(Consumer<Runnable> enqueueWork);
}
