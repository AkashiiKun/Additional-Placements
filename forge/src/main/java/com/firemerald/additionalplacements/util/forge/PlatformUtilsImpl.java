package com.firemerald.additionalplacements.util.forge;

import net.minecraftforge.fml.loading.FMLEnvironment;
import net.minecraftforge.fml.loading.FMLPaths;

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
