package pl.vertty.commands.basic;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import pl.vertty.commands.Main;

public class Config {
    private String name;
    private File file;
    private FileConfiguration config;
    
    public Config() {}

    public static void loadConfigs()
    {
      try {
        Main.getPlugin().saveDefaultConfig();
        FileConfiguration c = Main.getPlugin().getConfig();
        Field[] arrayOfField;
        int j = (arrayOfField = Config.class.getFields()).length;
        for (int i = 0; i < j; i++)
        {
          Field f = arrayOfField[i];
          if (c.isSet("config." + f.getName().toLowerCase().replace("$", "-").replace("_", "."))) {
            f.set(null, c.get("config." + f.getName().toLowerCase().replace("$", "-").replace("_", ".")));
          }
        }
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
    }
    
    public static void saveConfigs()
    {
      try
      {
        FileConfiguration c = Main.getPlugin().getConfig();
        Field[] arrayOfField;
        int j = (arrayOfField = Config.class.getFields()).length;
        for (int i = 0; i < j; i++)
        {
          Field f = arrayOfField[i];
          c.set("config." + f.getName().toLowerCase().replace("$", "-").replace("_", "."), f.get(null));
        }
        Main.getPlugin().saveConfig();
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
    }
    
    public static void reloadConfigs()
    {
    	Main.getPlugin().reloadConfig();
      loadConfigs();
      saveConfigs();
    }
    
    public Config(String name) {
        this.name = name;
        loadConfig();
        saveConfig();
    }

    public String getName() {
        return name;
    }
    public FileConfiguration getConfig(){
        return config;
    }

    public void loadConfig(){
    	this.file = new File(Main.getPlugin().getDataFolder(), String.valueOf(this.name) + ".yml");
        if(!file.exists()){
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        config = YamlConfiguration.loadConfiguration(file);
        saveConfig();
    }

    public void saveConfig(){
        try {
            config.options().copyDefaults(true);
            config.options().configuration().save(file);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    public void reloadConfig(){
    	this.file = new File(Main.getPlugin().getDataFolder(), String.valueOf(this.name) + ".yml");
    	config = YamlConfiguration.loadConfiguration(file);
    }
}
