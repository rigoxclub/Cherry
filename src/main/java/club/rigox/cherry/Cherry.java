package club.rigox.cherry;

import club.rigox.cherry.api.Economy;
import club.rigox.cherry.commands.CherryCMD;
import club.rigox.cherry.database.MongoDB;
import club.rigox.cherry.listeners.PlayerListener;
import club.rigox.cherry.utils.Config;
import co.aikar.commands.PaperCommandManager;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.LinkedHashMap;
import java.util.Map;

import static club.rigox.cherry.utils.Logger.*;

public final class Cherry extends JavaPlugin {
    public static Cherry instance;

    private MongoDB mongo;
    private Config config;
    private FileConfiguration database;

    private Economy economy;

    private Map<Player, Double> credits = new LinkedHashMap<>();

    @Override
    public void onEnable() {
        instance = this;

        mongo = new MongoDB(this);
        economy = new Economy(this);

        registerHooks();
        loadConfigs();
        registerListeners();
        registerCommands();
        mongo.connect();
    }

    @Override
    public void onDisable() {
        mongo.close();
        saveOnStop();
        info("Cherry Economy has been disabled!");
    }

    public void registerHooks() {
        if (getServer().getPluginManager().getPlugin("PlaceholderAPI") == null) {
            error("Could not find PlaceholderAPI, disabling plugin...");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        info("Hooked with PlaceholderAPI successfully!");

        if (getServer().getPluginManager().getPlugin("ScoreboardAPI") == null) {
            error("Could not find ScoreboardAPI, disabling plugin...");
            return;
        }
        info("Hooked with ScoreboardAPI successfully!");
    }

    public void registerListeners() {
        new PlayerListener(this);
        info("PlayerListener loaded");
    }

    public void registerCommands() {
        PaperCommandManager manager = new PaperCommandManager(this);
        manager.registerCommand(new CherryCMD(this));
        info("Plugin commands registered");
    }

    public void loadConfigs() {
        config = new Config(this);
        this.database = config.createConfig("database");
    }

    public void saveOnStop() {
        for (Player player : getServer().getOnlinePlayers()) {
            getMongo().updateMongoCredits(player.getUniqueId(), getCredits().get(player));
            debug(String.format("Saving %s credits...", player.getName()));
        }
    }

    public FileConfiguration getDatabase() {
        return database;
    }

    public MongoDB getMongo() {
        return mongo;
    }

    public Map<Player, Double> getCredits() {
        return credits;
    }

    public Economy getEconomy() {
        return economy;
    }
}
