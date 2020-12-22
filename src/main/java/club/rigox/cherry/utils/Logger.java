package club.rigox.cherry.utils;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class Logger {
    public static String color (String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }

    public static void debug (String str) {
        Bukkit.getConsoleSender().sendMessage(color(String.format("&f[&9DEBUG&f] %s", str)));
    }

    public static void warn (String str) {
        Bukkit.getConsoleSender().sendMessage(color(String.format("&f[&eWARN&f] %s", str)));
    }

    public static void error (String str) {
        Bukkit.getConsoleSender().sendMessage(color(String.format("&f[&cERROR&f] %s", str)));
    }

    public static void info (String str) {
        Bukkit.getConsoleSender().sendMessage(color(String.format("&f[&aINFO&f] %s", str)));
    }

    public static void sendMessage(CommandSender sender, String str) {
        sender.sendMessage(color(str));
    }

    public static String parseField(String field, Player p) {
        return ChatColor.translateAlternateColorCodes('&', PlaceholderAPI.setPlaceholders(p, field));
    }
}
