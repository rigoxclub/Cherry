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

        if (!target.hasPlayedBefore()) {
            sendMessage(sender, getLangString("PLAYER-NOT-EXISTS"));
            return;
        }

        if (!target.isOnline()) {
            double dbCredits = cherry.getMongo().getMongoCredits(uuid);
            cherry.getMongo().updateMongoCredits(target.getUniqueId(), dbCredits + credits);

            sendMessage(sender, String.format(getLangString("GIVE.OFFLINE"), credits, target.getName()));
            return;
        }
        double mapCredits = cherry.getCredits().get(uuid);
        cherry.getCredits().put(target.getUniqueId(), mapCredits + credits);

        sendMessage(sender, String.format(getLangString("GIVE.ONLINE"), credits, target.getName()));
        updateScoreboard(target);
    }

    public void subtractCredits(CommandSender sender, UUID uuid, Double credits) {
        OfflinePlayer target = cherry.getServer().getOfflinePlayer(uuid);

        if (!target.hasPlayedBefore()) {
            sendMessage(sender, getLangString("PLAYER-NOT-EXISTS"));
            return;
        }

        if (!target.isOnline()) {
            double dbCredits = cherry.getMongo().getMongoCredits(uuid);
            cherry.getMongo().updateMongoCredits(target.getUniqueId(), dbCredits - credits);

            sendMessage(sender, String.format(getLangString("TAKE.OFFLINE"), credits, target.getName()));
            return;
        }
        double mapCredits = cherry.getCredits().get(uuid);
        cherry.getCredits().put(target.getUniqueId(), mapCredits - credits);

        sendMessage(sender, String.format(getLangString("TAKE.ONLINE"), credits, target.getName()));
        updateScoreboard(target);
    }

    public void setCredits(CommandSender sender, UUID uuid, Double credits) {
        OfflinePlayer target = cherry.getServer().getOfflinePlayer(uuid);

        if (!target.hasPlayedBefore()) {
            sendMessage(sender, getLangString("PLAYER-NOT-EXISTS"));
            return;
        }

        if (!target.isOnline()) {
            cherry.getMongo().updateMongoCredits(target.getUniqueId(), credits);

            sendMessage(sender, String.format(getLangString("SET.OFFLINE"), credits, target.getName()));
            return;
        }
        double mapCredits = cherry.getCredits().get(uuid);
        cherry.getCredits().put(target.getUniqueId(), credits);

        sendMessage(sender, String.format(getLangString("SET.ONLINE"), credits, target.getName()));
        updateScoreboard(target);
    }

    public void resetCredits(CommandSender sender, UUID uuid) {
        OfflinePlayer target = cherry.getServer().getOfflinePlayer(uuid);

        if (!target.hasPlayedBefore()) {
            sendMessage(sender, getLangString("PLAYER-NOT-EXISTS"));
            return;
        }

        if (!target.isOnline()) {
            cherry.getMongo().updateMongoCredits(target.getUniqueId(), 0.0);

            sendMessage(sender, String.format(getLangString("RESET.OFFLINE"), target.getName()));
            return;
        }
        cherry.getCredits().put(target.getUniqueId(), 0.0);

        sendMessage(sender, String.format(getLangString("RESET.ONLINE"), target.getName()));
        updateScoreboard(target);
    }
}
