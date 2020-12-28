package club.rigox.cherry;

import club.rigox.cherry.api.Economy;
import club.rigox.cherry.commands.CherryCMD;
import club.rigox.cherry.commands.CreditsCMD;
import club.rigox.cherry.database.MongoDB;
import club.rigox.cherry.hooks.Placeholders;
import club.rigox.cherry.listeners.PlayerListener;
import club.rigox.cherry.utils.Config;
import club.rigox.scoreboard.ScoreboardAPI;
import co.aikar.commands.PaperCommandManager;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;
import java.util.UUID;

import static club.rigox.cherry.utils.Logger.*;

public final class Cherry extends JavaPlugin {
    public static Cherry instance;
    public static ScoreboardAPI scoreboardAPI;

    private MongoDB mongo;

    private FileConfiguration database;
    private FileConfiguration lang;

    private Economy economy;

    private final Map<UUID, Double> credits = new LinkedHashMap<>();

    @Override
    public void onEnable() {
        instance = this;
        scoreboardAPI = ScoreboardAPI.instance;

        mongo = new MongoDB(this);
        economy = new Economy(this);

        registerHooks();
        loadConfigs();
        registerListeners();
        registerACF();
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
        new Placeholders(this).register();
        info("Hooked with PlaceholderAPI successfully!");

        if (getServer().getPluginManager().getPlugin("ScoreboardAPI") == null) {
            error("Could not find ScoreboardAPI, disabling plugin...");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }
        info("Hooked with ScoreboardAPI successfully!");
    }

    public void registerListeners() {
        new PlayerListener(this);
        info("PlayerListener loaded");
    }

    public void registerACF() {
        PaperCommandManager manager = new PaperCommandManager(this);
        manager.enableUnstableAPI("brigadier");

        manager.registerCommand(new CherryCMD(this));
        manager.registerCommand(new CreditsCMD(this));

        info("Plugin commands registered");

        manager.addSupportedLanguage(Locale.ENGLISH);
        try {
            manager.getLocales().loadYamlLanguageFile("lang.yml", Locale.ENGLISH);
        } catch (InvalidConfigurationException | IOException e) {
            error("[ACF] An error ocurred while reading lang.yml file. " + e);
            e.printStackTrace();
            return;
        }
        info("ACF hooked successfully on lang.yml");
    }

    public void loadConfigs() {
        Config config = new Config(this);
        this.database = config.createConfig("database");
        this.lang = config.createConfig("lang");
    }

    public void saveOnStop() {
        for (Player player : getServer().getOnlinePlayers()) {
            getMongo().updateMongoCredits(player.getUniqueId(), getCredits().get(player.getUniqueId()));
            debug(String.format("Saving %s credits...", player.getName()));
        }
    }

    public FileConfiguration getDatabase() {
        return database;
    }

    public FileConfiguration getLang() {
        return lang;
    }

    public MongoDB getMongo() {
        return mongo;
    }

    public Map<UUID, Double> getCredits() {
        return credits;
    }

    public Economy getEconomy() {
        return economy;
    }

    public ScoreboardAPI getScoreboardAPI() {
        return scoreboardAPI;
    }
}
