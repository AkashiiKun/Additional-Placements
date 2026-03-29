package com.firemerald.additionalplacements.util.neoforge;

import net.neoforged.fml.loading.FMLEnvironment;
import net.neoforged.fml.loading.FMLPaths;

import java.nio.file.Path;

public class PlatformUtilsImpl {
    public static boolean isClient() {
        return FMLEnvironment.dist.isClient();
    }

    public static boolean isDedicatedServer() {
        return FMLEnvironment.dist.isDedicatedServer();
    }

    public static Path getConfigFolder() {
        return FMLPaths.CONFIGDIR.get();
    }
}
