package com.firemerald.additionalplacements.mixin.forge;

import java.util.List;

public class APMixinPluginImpl {
    public static List<String> getPlatformMixins() {
        return List.of(
                "forge.MixinForgeRegistry"
        );
    }
}
