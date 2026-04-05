package com.firemerald.additionalplacements.mixin.fabric;

import java.util.List;

public class APMixinPluginImpl {
    public static List<String> getPlatformMixins() {
        return List.of(
                "fabric.MixinBlockModelDefinition",
                "fabric.MixinLevelRenderer",
                "fabric.MixinModResourcePackCreator"
        );
    }
}
