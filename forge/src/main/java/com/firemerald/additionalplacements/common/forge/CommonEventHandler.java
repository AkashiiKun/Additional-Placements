package com.firemerald.additionalplacements.common.forge;

import com.firemerald.additionalplacements.AdditionalPlacementsMod;
import com.firemerald.additionalplacements.common.CommonModEvents;
import com.firemerald.additionalplacements.common.TagMismatchChecker;
import com.firemerald.additionalplacements.forge.AdditionalPlacementsForge;

import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.event.RegisterCommandsEvent;
import net.minecraftforge.event.TagsUpdatedEvent;
import net.minecraftforge.event.TagsUpdatedEvent.UpdateCause;
import net.minecraftforge.event.entity.player.ItemTooltipEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.event.server.ServerStartedEvent;
import net.minecraftforge.event.server.ServerStoppingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.MissingMappingsEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE)
public class CommonEventHandler {
    @SubscribeEvent
    public static void onItemTooltip(ItemTooltipEvent event) {
        CommonModEvents.onItemTooltip(event.getItemStack(), event.getFlags(), event.getToolTip());
    }

    @SubscribeEvent
    public static void onRegisterCommands(RegisterCommandsEvent event) {
        CommonModEvents.onRegisterCommands(event.getDispatcher(), event.getBuildContext(), event.getCommandSelection());
    }

    @SubscribeEvent
    public static void onTagsUpdated(TagsUpdatedEvent event) {
        CommonModEvents.onTagsUpdated(event.getRegistryAccess(), event.getUpdateCause() == UpdateCause.CLIENT_PACKET_RECEIVED);
    }

    @SubscribeEvent
    public static void onServerStarted(ServerStartedEvent event) {
        CommonModEvents.onServerStarted(event.getServer());
    }

    @SubscribeEvent
    public static void onMissingBlockMappings(MissingMappingsEvent event) {
        event.getMappings(ForgeRegistries.BLOCKS.getRegistryKey(), AdditionalPlacementsForge.OLD_ID).forEach(mapping -> {
            String oldPath = mapping.getKey().getPath();
            if (oldPath.indexOf('.') < 0) { //remap original format
                String newPath = "minecraft." + oldPath;
                Block block = ForgeRegistries.BLOCKS.getValue(AdditionalPlacementsMod.rl(newPath));
                if (block != Blocks.AIR) {
                    mapping.remap(block);
                }
            }
            else { //remap old mod ID
                Block block = ForgeRegistries.BLOCKS.getValue(AdditionalPlacementsMod.rl(oldPath));
                if (block != Blocks.AIR) {
                    mapping.remap(block);
                }
            }
        });
    }

    @SubscribeEvent
    public static void onPlayerLogin(PlayerLoggedInEvent event) {
        if (CommonModEvents.misMatchedTags && TagMismatchChecker.canGenerateTags(event.getEntity())) event.getEntity().sendSystemMessage(CommonModEvents.autoGenerateFailed ? TagMismatchChecker.FAILED : TagMismatchChecker.MESSAGE);
    }

    @SubscribeEvent
    public static void onServerStopping(ServerStoppingEvent event) {
        CommonModEvents.onServerStopping(event.getServer());
    }
}