package club.rigox.cherry.utils;

import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.command.CommandSender;

import static club.rigox.cherry.utils.Config.getLangString;
import static club.rigox.cherry.utils.Logger.sendMessage;

public class Number {
    public static boolean isANumber(CommandSender sender, String str) {
        if (NumberUtils.isNumber(str)) {
            return true;
        }
        sendMessage(sender, getLangString("NOT-A-NUMBER"));
        return false;
    }
}
