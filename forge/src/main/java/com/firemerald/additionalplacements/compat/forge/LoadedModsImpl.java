package com.firemerald.additionalplacements.compat.forge;

import com.firemerald.additionalplacements.AdditionalPlacementsMod;
import com.firemerald.additionalplacements.client.models.Unwrapper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.loading.FMLEnvironment;
import team.chisel.ctm.client.model.AbstractCTMBakedModel;

public enum LoadedModsImpl {
    CTM("ctm", () -> {
        if (FMLEnvironment.dist == Dist.CLIENT) {
            AdditionalPlacementsMod.LOGGER.info("Connected Textures Mod detected, registering ctm BakedModel unwrappers");
            Unwrapper.registerUnwrapper(model -> {
                if (model instanceof AbstractCTMBakedModel ctm) return ctm.getParent();
                else return null;
            });
        }
    });

    public static void populatePlatform() {
        for (LoadedModsImpl val : LoadedModsImpl.values()) {
            if (isModLoaded(val.modId)) {
                val.isPresent = true;
                val.whenDetected.run();
            }
        }
    }

    public static boolean isModLoaded(String modId) {
        return ModList.get().isLoaded(modId);
    }

    public final String modId;
    public final Runnable whenDetected;
    public boolean isPresent;

    LoadedModsImpl(String modId, Runnable whenDetected) {
        this.modId = modId;
        this.whenDetected = whenDetected;
    }
}
