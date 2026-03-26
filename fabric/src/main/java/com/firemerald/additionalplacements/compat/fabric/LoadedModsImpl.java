package com.firemerald.additionalplacements.compat.fabric;

import com.firemerald.additionalplacements.compat.LoadedMods;
import dev.architectury.platform.Platform;
import net.fabricmc.loader.api.FabricLoader;

public enum LoadedModsImpl {
    ;
    public static void populatePlatform() {
        for (LoadedModsImpl val : LoadedModsImpl.values()) {
            if (FabricLoader.getInstance().isModLoaded(val.modId)) {
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
