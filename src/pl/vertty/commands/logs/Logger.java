package pl.vertty.commands.logs;

import java.util.logging.Level;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.ConsoleCommandSender;

import pl.vertty.commands.Main;
import pl.vertty.commands.util.ChatUtil;

public class Logger {
    private static ConsoleCommandSender console = Bukkit.getConsoleSender();



    public static void send(String message) {
        console.sendMessage(ChatUtil.fixColors(message));
    }
    
    public static void info(String... logs)
    {
      String[] arrayOfString = logs;int j = logs.length;
      for (int i = 0; i < j; i++)
      {
        String s = arrayOfString[i];
        log(Level.INFO, s);
      }
    }
    
    public static void warning(String... logs)
    {
      String[] arrayOfString = logs;int j = logs.length;
      for (int i = 0; i < j; i++)
      {
        String s = arrayOfString[i];
        log(Level.WARNING, s);
      }
    }
    
    public static void log(Level level, String log)
    {
      Main.getPlugin().getLogger().log(level, log);
    }
    
    public static void exception(Throwable cause)
    {
      cause.printStackTrace();
    }
    
    public static void error(final Exception e) {
        error("[HostFire]");
        error("  Bukkit: " + Bukkit.getBukkitVersion());
        error("  Java: " + System.getProperty("java.version"));
        error("  Thread: " + Thread.currentThread());
        error("  Blad: " + e.toString());
        for (int i = 0; i < e.getStackTrace().length; ++i) {
            final String[] splited = e.getStackTrace()[i].toString().split("\\(");
            if (splited[0].contains("pl.vertty")) {
                String line = splited[1];
                line = line.replace(".java", "");
                line = line.replace(":", " Linijka: ");
                line = line.replace("\\)", "");
                error("Klasa - " + line);
            }
        }
    }
    
    private static void error(final String message) {
        Logger.console.sendMessage(ChatColor.RED + message);

    }


}
