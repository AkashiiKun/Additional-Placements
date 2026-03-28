package com.firemerald.additionalplacements.network.forge;

import com.firemerald.additionalplacements.network.CheckDataConfigurationTask;
import com.firemerald.additionalplacements.network.client.CheckDataClientPacket;
import net.minecraft.network.protocol.Packet;
import net.minecraftforge.network.config.ConfigurationTaskContext;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class CheckDataConfigurationTaskImpl extends CheckDataConfigurationTask {
    @Override
    public void start(ConfigurationTaskContext ctx) {
        APNetworkImpl.send(CheckDataClientPacket.of(), ctx.getConnection());
    }

    @Override
    public void start(@NotNull Consumer<Packet<?>> send) {
        throw new IllegalStateException("This should never be called");
    }
}
