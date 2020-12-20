package club.rigox.cherry;

import club.rigox.cherry.database.MongoDB;
import club.rigox.cherry.utils.Config;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.LinkedHashMap;
import java.util.Map;

import static club.rigox.cherry.utils.Logger.error;
import static club.rigox.cherry.utils.Logger.info;

public final class Cherry extends JavaPlugin {
    public static Cherry instance;

    private MongoDB mongo;
    private Config config;
    private FileConfiguration database;

    private Map<Player, Double> credits = new LinkedHashMap<>();

    @Override
    public void onEnable() {
        instance = this;

        mongo = new MongoDB(this);

        registerHooks();
        loadConfigs();
        mongo.connect();
    }

    @Override
    public void onDisable() {
        mongo.close();
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

    public void loadConfigs() {
        config = new Config(this);
        this.database = config.createConfig("database");
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
}
