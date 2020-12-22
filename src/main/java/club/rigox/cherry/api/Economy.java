package club.rigox.cherry.api;

import club.rigox.cherry.Cherry;
import org.bukkit.entity.Player;

import static club.rigox.cherry.utils.Logger.parseField;

public class Economy {
    private Cherry cherry;

    public Economy (Cherry plugin) {
        this.cherry = plugin;
    }

    public void updateScoreboard(Player player) {
        cherry.getScoreboardAPI().getAPI().setLineMessage(4, "&fCredits: &e%cherry_credits%", player);
    }

    public void sumCredits(Player player, Double credits) {
        double mapCredits = cherry.getCredits().get(player);
        cherry.getCredits().put(player, mapCredits + credits);
        updateScoreboard(player);
    }

    public void subtractCredits(Player player, Double credits) {
        double mapCredits = cherry.getCredits().get(player);
        cherry.getCredits().put(player, mapCredits - credits);
        updateScoreboard(player);
    }

    public void setCredits(Player player, Double credits) {
        cherry.getCredits().put(player, credits);
        updateScoreboard(player);
    }

    public void resetCredits(Player player) {
        cherry.getCredits().put(player, 0.0);
        updateScoreboard(player);
    }
}
