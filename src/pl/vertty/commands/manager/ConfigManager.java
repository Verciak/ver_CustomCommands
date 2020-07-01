package pl.vertty.commands.manager;

import java.io.File;
import java.util.HashMap;

import pl.vertty.commands.Main;
import pl.vertty.commands.basic.Config;


public class ConfigManager {

    private static HashMap<String, Config> configs = new HashMap<>();

    public static void registerConfig(String name, Config config) {
        configs.put(name, config);
    }

    public static boolean isConfig(String name) {
        return configs.containsKey(name);
    }

    public static Config getConfig(String name) {
        return configs.get(name);
    }
    
    public static void reloadConfig() {
    	Main.getPlugin().reloadConfig();
    	init();
    }


    public static void init() {
        File file = new File(Main.getPlugin().getDataFolder(), "");
        if (!file.exists()) {
            file.mkdir();
        }
    }


}
