package club.rigox.cherry.utils;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class Logger {
    public static String color (String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }

    public static void debug (String str) {
        Bukkit.getConsoleSender().sendMessage(color(String.format("&f[&9DEBUG &7| &fCherry] %s", str)));
    }

    public static void warn (String str) {
        Bukkit.getConsoleSender().sendMessage(color(String.format("&f[&eWARN &7| &fCherry] %s", str)));
    }

    public static void error (String str) {
        Bukkit.getConsoleSender().sendMessage(color(String.format("&f[&cERROR &7| &fCherry] %s", str)));
    }

    public static void info (String str) {
        Bukkit.getConsoleSender().sendMessage(color(String.format("&f[&aINFO &7| &fCherry] %s", str)));
    }

    public static void sendMessage(CommandSender sender, String str) {
        sender.sendMessage(color(str));
    }

//    public static String parseField(String field, Player p) {
//        return ChatColor.translateAlternateColorCodes('&', PlaceholderAPI.setPlaceholders(p, field));
//    }
}
