package com.firemerald.additionalplacements.network;

import net.minecraft.network.FriendlyByteBuf;

public interface APPacket {
	void write(FriendlyByteBuf buf);
}