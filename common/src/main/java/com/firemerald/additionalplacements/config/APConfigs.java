package com.firemerald.additionalplacements.config;

import com.firemerald.additionalplacements.util.PlatformUtils;
import net.neoforged.fml.config.IConfigSpec;
import net.neoforged.neoforge.common.ModConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

import org.jetbrains.annotations.ApiStatus;

import java.util.function.BiFunction;

public class APConfigs {
    private static StartupConfig startup;
    private static ModConfigSpec startupSpec;
    private static CommonConfig common;
    private static ModConfigSpec commonSpec;
    private static Object commonHolder;
    private static ServerConfig server;
    private static ModConfigSpec serverSpec;
    private static Object serverHolder;
    private static ClientConfig client;
    private static ModConfigSpec clientSpec;
    private static Object clientHolder;

    public static void init(BiFunction<ConfigType, IConfigSpec<?>, Object> registerConfig) {
        final Pair<StartupConfig, ModConfigSpec> startupSpecPair = new ModConfigSpec.Builder().configure(StartupConfig::new);
        startup = startupSpecPair.getLeft();
        startupSpec = startupSpecPair.getRight();
		startup.loadConfig(PlatformUtils.getConfigFolder().resolve("additionalplacements-startup.toml"), startupSpec);
        final Pair<CommonConfig, ModConfigSpec> commonSpecPair = new ModConfigSpec.Builder().configure(CommonConfig::new);
        common = commonSpecPair.getLeft();
        commonHolder = registerConfig.apply(ConfigType.COMMON, commonSpec = commonSpecPair.getRight());
        final Pair<ServerConfig, ModConfigSpec> serverSpecPair = new ModConfigSpec.Builder().configure(ServerConfig::new);
        server = serverSpecPair.getLeft();
        serverHolder = registerConfig.apply(ConfigType.SERVER, serverSpec = serverSpecPair.getRight());
        final Pair<ClientConfig, ModConfigSpec> clientSpecPair = new ModConfigSpec.Builder().configure(ClientConfig::new);
        client = clientSpecPair.getLeft();
        clientHolder = registerConfig.apply(ConfigType.CLIENT, clientSpec = clientSpecPair.getRight());
    }

    public static StartupConfig startup() {
    	return startup;
    }

    public static boolean startupLoaded() {
    	return startupSpec.isLoaded();
    }

    public static CommonConfig common() {
    	return common;
    }

    public static boolean commonLoaded() {
    	return commonSpec.isLoaded();
    }

    public static ServerConfig server() {
    	return server;
    }

    public static boolean serverLoaded() {
    	return serverSpec.isLoaded();
    }

    public static ClientConfig client() {
    	return client;
    }

    public static boolean clientLoaded() {
    	return clientSpec.isLoaded();
    }

    @ApiStatus.Internal
    public static void onConfigLoaded(Object configHolder) {
    	if (configHolder == commonHolder) common.onConfigLoaded();
    	else if (configHolder == serverHolder) server.onConfigLoaded();
    	else if (configHolder == clientHolder) client.onConfigLoaded();
    }

	public static boolean isColorString(Object o) {
		if (o instanceof String s) {
            if (s.length() == 8) { //must be 8 characters (AARRGGBB)
				for (int i = 0; i < 8; ++i) {
					char c = s.charAt(i);
                    if ((c < '0' || c > '9') && (c < 'a' || c > 'f') && (c < 'A' || c > 'F')) return false; //only 0-9, a-f, or A-F allowed
				}
				return true;
			}
		}
		return false;
	}

	public static float[] parseColorString(String s) {
		return new float[] {
				Integer.parseInt(s.substring(2, 4), 16) / 255f, //XXRRXXXX
				Integer.parseInt(s.substring(4, 6), 16) / 255f, //XXXXGGXX
				Integer.parseInt(s.substring(6, 8), 16) / 255f, //XXXXXXBB
				Integer.parseInt(s.substring(0, 2), 16) / 255f  //AAXXXXXX
		};
	}

	public static float[] parseColorString(ModConfigSpec.ConfigValue<String> s) {
		return parseColorString(s.get());
	}
}