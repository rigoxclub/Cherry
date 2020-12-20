package club.rigox.cherry.listeners;

import club.rigox.cherry.Cherry;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerLoginEvent;

public class PlayerListener implements Listener {
    private Cherry cherry;

    public PlayerListener (Cherry plugin) {
        this.cherry = plugin;
        cherry.getServer().getPluginManager().registerEvents(this, cherry);
    }

    @EventHandler
    public void onPlayerLogin(PlayerLoginEvent e) {
        Player player = e.getPlayer();

//        cherry.getCredits().put(Player);
    }

}
