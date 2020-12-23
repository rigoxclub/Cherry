package club.rigox.cherry.listeners;

import club.rigox.cherry.Cherry;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.UUID;

public class PlayerListener implements Listener {
    private final Cherry cherry;

    public PlayerListener (Cherry plugin) {
        this.cherry = plugin;
        cherry.getServer().getPluginManager().registerEvents(this, cherry);
    }

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent e) {
        UUID player = e.getPlayer().getUniqueId();
        Double dbCredits = cherry.getMongo().getMongoCredits(player);

        cherry.getCredits().put(player, dbCredits);
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e) {
        UUID player = e.getPlayer().getUniqueId();
        Double mapCredits = cherry.getCredits().get(player);

        cherry.getMongo().updateMongoCredits(player, mapCredits);
        cherry.getCredits().remove(player);
    }

}
