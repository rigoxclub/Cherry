package club.rigox.cherry.utils;

import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import static club.rigox.cherry.utils.Config.getLangString;
import static club.rigox.cherry.utils.Logger.sendMessage;

public class Validator {
    public boolean isConsole(CommandSender sender) {
        if (!(sender instanceof Player)) {
            sendMessage(sender, getLangString("NOT-A-PLAYER"));
            return true;
        }
        return false;
    }
}
