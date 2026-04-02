package com.firemerald.additionalplacements.util;

import dev.architectury.injectables.annotations.ExpectPlatform;

import java.nio.file.Path;

public class PlatformUtils {
    @ExpectPlatform
    public static boolean isClient() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static boolean isDedicatedServer() {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static Path getConfigFolder() {
        throw new AssertionError();
    }

    public static void checkIsClient() {
        if (isDedicatedServer()) throw new IllegalStateException("Client-side access detected on dedicated server.");
    }

    public static void checkIsServer() {
        if (isClient()) throw new IllegalStateException("Dedicated server-side access detected on server.");
    }
}
