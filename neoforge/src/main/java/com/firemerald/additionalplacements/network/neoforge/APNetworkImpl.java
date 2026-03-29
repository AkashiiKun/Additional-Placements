package com.firemerald.additionalplacements.network.neoforge;

import com.firemerald.additionalplacements.AdditionalPlacementsMod;
import com.firemerald.additionalplacements.network.client.*;
import com.firemerald.additionalplacements.network.client.neoforge.CheckDataClientPacketImpl;
import com.firemerald.additionalplacements.network.client.neoforge.ClientPacketImpl;
import com.firemerald.additionalplacements.network.client.neoforge.ConfigurationCheckFailedPacketImpl;
import com.firemerald.additionalplacements.network.server.*;

import com.firemerald.additionalplacements.network.server.neoforge.CheckDataServerPacketImpl;
import com.firemerald.additionalplacements.network.server.neoforge.ServerPacketImpl;
import com.firemerald.additionalplacements.network.server.neoforge.SetPlacementTogglePacketImpl;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlerEvent;
import net.neoforged.neoforge.network.registration.IPayloadRegistrar;

public class APNetworkImpl {
    public static void register(RegisterPayloadHandlerEvent event) {
        IPayloadRegistrar registrar = event.registrar(AdditionalPlacementsMod.MOD_ID);
        registrar.play(SetPlacementTogglePacketImpl.ID, SetPlacementTogglePacketImpl::new, SetPlacementTogglePacketImpl::handle);
        registrar.configuration(CheckDataClientPacketImpl.ID, CheckDataClientPacketImpl::new, CheckDataClientPacketImpl::handle);
        registrar.configuration(CheckDataServerPacketImpl.ID, CheckDataServerPacketImpl::new, CheckDataServerPacketImpl::handle);
        registrar.configuration(ConfigurationCheckFailedPacketImpl.ID, ConfigurationCheckFailedPacketImpl::new, ConfigurationCheckFailedPacketImpl::handle);
    }

    public static void sendToServer(ServerPacket packet) {
        if (packet instanceof ServerPacketImpl serverPacket) sendTo(serverPacket, PacketDistributor.SERVER.noArg());
    }

    public static void sendToClient(ClientPacket packet, ServerPlayer player) {
        if (packet instanceof ClientPacketImpl clientPacket) sendTo(clientPacket, PacketDistributor.PLAYER.with(player));
    }

    public static void sendTo(CustomPacketPayload packet, PacketDistributor.PacketTarget target) {
        target.send(packet);
    }
}