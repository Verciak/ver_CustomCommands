package pl.vertty.commands.util;

import java.util.List;
import java.util.regex.Pattern;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.Packet;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;

public class ChatUtil {
    public static String fixColors(String message) {
        if(message == null){
            return "";
        }
        return org.bukkit.ChatColor.translateAlternateColorCodes('&', message);
    }
   
    public static void sendMessageBar(Player p, String message, Integer time) {
    	ChatUtil.sendActionbar(p, ChatUtil.fixColors(message));
    }
    
    public static boolean isAlphaNumeric(final String s) {
        return s.matches("^[a-zA-Z0-9_]*$");
    }
    
    public static boolean isFloat(final String string) {
        return Pattern.matches("([0-9]*)\\.([0-9]*)", string);
    }
    
    public static boolean isInteger(final String string) {
        return Pattern.matches("-?[0-9]+", string.subSequence(0, string.length()));
    }

    public static List<String> fixColors(List<String> message) {
        List<String> strings = message;
        for (int i = 0; i < strings.size(); i++) {
            strings.set(i, fixColors(strings.get(i)));
        }
        return strings;
    }
    
    public static void sendActionbar(Player p, String message) {
        IChatBaseComponent icbc = IChatBaseComponent.ChatSerializer.a("{\"text\": \"" + ChatColor.translateAlternateColorCodes('&', message) + "\"}");
        PacketPlayOutChat bar = new PacketPlayOutChat(icbc, (byte)2);
        ((CraftPlayer)p).getHandle().playerConnection.sendPacket((Packet<?>)bar);
    }


    public static void sendMessage(Player p, String message) {
        p.sendMessage(ChatUtil.fixColors(message));
    }

    public static void sendMessage(Player p, List<String> message) {
        for (int i = 0; i < message.size(); i++) {
            sendMessage(p, message.get(i));
        }
    }

    public static void clear(CommandSender sender, int lines) {
        for (int i = 0; i < lines; i++) {
            sender.sendMessage(" ");
        }
    }


    public static void sendMessage(CommandSender sender, String mess) {
        sender.sendMessage(ChatUtil.fixColors(mess));
    }
}
