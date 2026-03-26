package com.firemerald.additionalplacements.network.client;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;

import com.firemerald.additionalplacements.network.APPacket;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.network.Connection;
import net.minecraft.network.chat.Component;
import org.jetbrains.annotations.Nullable;

import com.firemerald.additionalplacements.AdditionalPlacementsMod;
import com.firemerald.additionalplacements.generation.Registration;
import com.firemerald.additionalplacements.network.server.CheckDataServerPacket;

import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientHandshakePacketListenerImpl;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;

public abstract class CheckDataClientPacket implements ClientLoginPacket {
	@ExpectPlatform
	public static CheckDataClientPacket of() {
		throw new AssertionError();
	}

	private final Map<ResourceLocation, CompoundTag> data;

	public CheckDataClientPacket() {
		data = new HashMap<>();
		Registration.forEach((id, type) -> {
			CompoundTag tag = type.getServerCheckData();
			if (tag != null) data.put(id, tag);
		});
	}

	public CheckDataClientPacket(FriendlyByteBuf buf) {
		data = buf.readMap(FriendlyByteBuf::readResourceLocation, FriendlyByteBuf::readNbt);
	}

	@Override
	public void write(FriendlyByteBuf buf) {
		buf.writeMap(data, FriendlyByteBuf::writeResourceLocation, FriendlyByteBuf::writeNbt);
	}

	@Override
	@Environment(EnvType.CLIENT)
	public @Nullable APPacket handleClient(Consumer<Runnable> enqueueWork, Consumer<Component> disconnect) {
		return CheckDataServerPacket.of(data);
	}
}