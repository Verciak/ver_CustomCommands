package pl.vertty.commands;

import org.bukkit.plugin.java.JavaPlugin;

import pl.vertty.commands.basic.Config;
import pl.vertty.commands.config.CustomCommandsConfig;
import pl.vertty.commands.manager.ConfigManager;

public class Main extends JavaPlugin{
	
	private static Main instance;
	
	public void onLoad() {
        (Main.instance = this).loadConfig();
    }
	
	public static Main getPlugin() {
        return instance;
      }
	
	private void loadConfig() {
		ConfigManager.init();
        ConfigManager.registerConfig("CommandsConfig", new Config("CommandsConfig"));
        CustomCommandsConfig.loadConfig();
    }
	

	public void onEnable() {
		onLoad();
		System.out.println("Uruchamiam");
	}
	
	public void onDisable() {
		System.out.println("Off");
	}
}
