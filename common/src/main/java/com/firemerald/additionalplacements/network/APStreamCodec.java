package com.firemerald.additionalplacements.network;

import java.util.function.Function;

import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public record APStreamCodec<T extends FriendlyByteBuf, U extends APPacket<T>>(Function<T, U> constructor) implements StreamCodec<T, U> {
    @Override
    public U decode(T buf) {
        return constructor.apply(buf);
    }

    @Override
    public void encode(T buf, U packet) {
        packet.write(buf);
    }
}
