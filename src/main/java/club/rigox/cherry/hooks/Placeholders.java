package club.rigox.cherry.hooks;

import club.rigox.cherry.Cherry;
import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Placeholders extends PlaceholderExpansion {
    private Cherry cherry;

    public Placeholders (Cherry plugin) {
        this.cherry = plugin;
    }

    @Override
    public boolean persist() {
        return true;
    }

    @Override
    public @NotNull String getIdentifier() {
        return "cherry";
    }

    @Override
    public @NotNull String getAuthor() {
        return cherry.getDescription().getAuthors().toString();
    }

    @Override
    public @NotNull String getVersion() {
        return null;
    }

    @Override
    public String onPlaceholderRequest(Player player, @NotNull String identifier) {
        if (player == null) {
            return "";
        }

        if (identifier.equals("credits")) {
            return cherry.getCredits().get(player.getUniqueId()).toString();
        }

        return null;
    }
}
