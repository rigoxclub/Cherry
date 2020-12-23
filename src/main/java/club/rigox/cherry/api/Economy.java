package club.rigox.cherry.api;

import club.rigox.cherry.Cherry;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;

import static club.rigox.cherry.utils.Config.getLangString;
import static club.rigox.cherry.utils.Logger.sendMessage;

public class Economy {
    private final Cherry cherry;

    public Economy (Cherry plugin) {
        this.cherry = plugin;
    }

    public void updateScoreboard(OfflinePlayer player) {
        if (!player.isOnline()) return;
        cherry.getScoreboardAPI().getAPI().setLineMessage(4, "&fCredits: &e%cherry_credits%", player.getPlayer());
    }

    public void sumCredits(CommandSender sender, OfflinePlayer target, Double credits) {
        if (!target.isOnline()) {

            if (!target.hasPlayedBefore()) {
                sendMessage(sender, String.format(getLangString("PLAYER-NOT-EXISTS"), target.getName()));
                return;
            }

            double dbCredits = cherry.getMongo().getMongoCredits(target.getUniqueId());
            cherry.getMongo().updateMongoCredits(target.getUniqueId(), dbCredits + credits);

            sendMessage(sender, String.format(getLangString("GIVE.OFFLINE"), credits, target.getName()));
            return;
        }

        double mapCredits = cherry.getCredits().get(target.getUniqueId());
        cherry.getCredits().put(target.getUniqueId(), mapCredits + credits);

        sendMessage(sender, String.format(getLangString("GIVE.ONLINE"), credits, target.getName()));
        updateScoreboard(target);
    }

//    public void subtractCredits(Player player, Double credits) {
//        double mapCredits = cherry.getCredits().get(player);
//        cherry.getCredits().put(player, mapCredits - credits);
//        updateScoreboard(player);
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
