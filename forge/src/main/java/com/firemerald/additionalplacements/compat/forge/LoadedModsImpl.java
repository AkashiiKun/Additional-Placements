package com.firemerald.additionalplacements.compat.forge;

import net.minecraftforge.fml.ModList;

public enum LoadedModsImpl {
    /* disabled due to only being on neoforge and not forge
    CTM("ctm", () -> {
        if (FMLEnvironment.dist == Dist.CLIENT) {
            AdditionalPlacementsMod.LOGGER.info("Connected Textures Mod detected, registering ctm BakedModel unwrappers");
            Unwrapper.registerUnwrapper(model -> {
                if (model instanceof AbstractCTMBakedModel ctm) return ctm.getParent();
                else return null;
            });
        }
    })
     */
    ;

    public static void populatePlatform() {
        for (LoadedModsImpl val : LoadedModsImpl.values()) {
            if (ModList.get().isLoaded(val.modId)) {
                val.isPresent = true;
                val.whenDetected.run();
            }
        }
    }

    public final String modId;
    public final Runnable whenDetected;
    public boolean isPresent;

    LoadedModsImpl(String modId, Runnable whenDetected) {
        this.modId = modId;
        this.whenDetected = whenDetected;
    }
}
