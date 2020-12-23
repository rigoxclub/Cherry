package club.rigox.cherry.api;

import club.rigox.cherry.Cherry;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

import java.util.UUID;

import static club.rigox.cherry.utils.Config.getLangString;
import static club.rigox.cherry.utils.Logger.sendMessage;

public class Economy {
    private final Cherry cherry;

    public Economy (Cherry plugin) {
        this.cherry = plugin;
    }

    public void updateScoreboard(OfflinePlayer player) {
        cherry.getScoreboardAPI().getAPI().setLineMessage(4, "&fCredits: &e%cherry_credits%", player.getPlayer());
    }

    public void sumCredits(CommandSender sender, UUID uuid, Double credits) {
        OfflinePlayer target = cherry.getServer().getOfflinePlayer(uuid);

        double dbCredits = cherry.getMongo().getMongoCredits(uuid);
        double mapCredits = cherry.getCredits().get(uuid);

        if (!target.isOnline()) {

            if (!target.hasPlayedBefore()) {
                sendMessage(sender, String.format(getLangString("PLAYER-NOT-EXISTS"), target.getName()));
                return;
            }
            cherry.getMongo().updateMongoCredits(target.getUniqueId(), dbCredits + credits);

            sendMessage(sender, String.format(getLangString("GIVE.OFFLINE"), credits, target.getName()));
            return;
        }
        cherry.getCredits().put(target.getUniqueId(), mapCredits + credits);

        sendMessage(sender, String.format(getLangString("GIVE.ONLINE"), credits, target.getName()));
        updateScoreboard(target);
    }

//    public void sumCredits(CommandSender sender, OfflinePlayer target, Double credits) {
//        double dbCredits = cherry.getMongo().getMongoCredits(target.getUniqueId());
//        double mapCredits = cherry.getCredits().get(target.getUniqueId());
//
//        if (!target.isOnline()) {
//
//            if (!target.hasPlayedBefore()) {
//                sendMessage(sender, String.format(getLangString("PLAYER-NOT-EXISTS"), target.getName()));
//                return;
//            }
//            cherry.getMongo().updateMongoCredits(target.getUniqueId(), dbCredits + credits);
//
//            sendMessage(sender, String.format(getLangString("GIVE.OFFLINE"), credits, target.getName()));
//            return;
//        }
//        cherry.getCredits().put(target.getUniqueId(), mapCredits + credits);
//
//        sendMessage(sender, String.format(getLangString("GIVE.ONLINE"), credits, target.getName()));
//        updateScoreboard(target);
//    }

//
//    public void setCredits(Player player, Double credits) {
//        cherry.getCredits().put(player, credits);
//        updateScoreboard(player);
//    }
//
//    public void resetCredits(Player player) {
//        cherry.getCredits().put(player, 0.0);
//        updateScoreboard(player);
//    }
}
