package club.rigox.cherry.listeners;

import club.rigox.cherry.Cherry;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerListener implements Listener {
    private final Cherry cherry;

    public PlayerListener (Cherry plugin) {
        this.cherry = plugin;
        cherry.getServer().getPluginManager().registerEvents(this, cherry);
    }

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent e) {
        Player player = e.getPlayer();
        Double dbCredits = cherry.getMongo().getMongoCredits(player.getUniqueId());

        cherry.getCredits().put(player, dbCredits);
    }

    @EventHandler
    public void onPlayerLeave(PlayerQuitEvent e) {
        Player player = e.getPlayer();
        Double mapCredits = cherry.getCredits().get(player);

        cherry.getMongo().updateMongoCredits(player.getUniqueId(), mapCredits);
        cherry.getCredits().remove(player);
    }

}
