package pl.vertty.commands.config;

import java.util.Arrays;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;

import pl.vertty.commands.manager.ConfigManager;
import pl.vertty.commands.util.ChatUtil;


public class CustomCommandsConfig {
	
    private static FileConfiguration cfg;
    
	public static void loadConfig() {
        cfg = ConfigManager.getConfig("CommandsConfig").getConfig();
        
        cfg.addDefault("Komendy.Ilosc", 3);
        
        int size = cfg.getInt("Komendy.Ilosc");
        for (int i = 0; i < size; ++i) {
            int l = i;
            ++l;
            cfg.addDefault("Komendy." + l + ".Komenda", ("pomoc" + l));
            cfg.addDefault("Komendy." + l + ".Wiadomosc", Arrays.asList(ChatUtil.fixColors("test 1"), ChatUtil.fixColors("test 2")));
        }
        for (int i = 0; i < size; ++i) {
            int l = i;
            ++l;
            String cmd = cfg.getString("Komendy." + l + ".Komenda", "pomoc" + l);
            List<String> list = (List<String>)cfg.getStringList("Komendy." + l + ".Wiadomosc");
            new ConfigCommand(cmd, list);
        }

        ConfigManager.getConfig("CommandsConfig").saveConfig();
    }

}
