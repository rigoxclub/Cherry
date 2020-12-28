package club.rigox.cherry.commands;

import club.rigox.cherry.Cherry;
import club.rigox.cherry.utils.Number;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.*;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.UUID;

import static club.rigox.cherry.utils.Config.getLangString;
import static club.rigox.cherry.utils.Logger.sendMessage;

@CommandAlias("cherry")
public class CherryCMD extends BaseCommand {
    private final Cherry cherry;

    public CherryCMD (Cherry plugin) {
        this.cherry = plugin;
    }

    @Subcommand("give")
    @CommandPermission("cherry.give")
    @Syntax("<player> <credits>")
    @CommandCompletion("@players @range:100-100")
    public void giveCommand(CommandSender sender, OfflinePlayer player, double credits) {
        cherry.getEconomy().sumCredits(sender, player, credits);
    }

    @Subcommand("take")
    @CommandPermission("cherry.take")
    @Syntax("<player> <credits>")
    @CommandCompletion("@players 100|300|500")
    public void takeCommand(CommandSender sender, OfflinePlayer player, double credits) {
        cherry.getEconomy().subtractCredits(sender, player, credits);
    }

    @Subcommand("set")
    @CommandPermission("cherry.set")
    @Syntax("<player> <credits>")
    @CommandCompletion("@players 100|300|500")
    public void setCommand(CommandSender sender, OfflinePlayer player, double credits) {
        cherry.getEconomy().setCredits(sender, player, credits);
    }

    @Subcommand("reset")
    @CommandPermission("cherry.reset")
    @Syntax("<player>")
    @CommandCompletion("@players")
    @Description("Reset credits to a player.")
    public void resetCommand(CommandSender sender, OfflinePlayer player) {
        cherry.getEconomy().resetCredits(sender, player);
    }

    @Subcommand("admin")
    public void onAdminHelp(CommandSender sender) {
        header(sender);
        sendMessage(sender, "&8&l* &d/cherry give (player) (credits) &f- Give credits to a player.");
        sendMessage(sender, "&8&l* &d/cherry take (player) (credits) &f- Take credits to a player.");
        sendMessage(sender, "&8&l* &d/cherry set (player) (credits) &f- Set credits to a player.");
        sendMessage(sender, "&8&l* &d/cherry reset (player) &f- Reset credits to a player.");
        footer(sender);
    }

    @HelpCommand
    public void onHelp(CommandSender sender) {
        header(sender);
        sendMessage(sender, "&8&l* &d/credits &f- View your credits");
        sendMessage(sender, "&8&l* &c/cherry admin &f- Show admin command help");
        footer(sender);
    }

    public void header(CommandSender sender) {
        sendMessage(sender, "&7&m------------------------------------------------");
        sendMessage(sender, "&d&lCherry Economy");
        sendMessage(sender, "&7&oCommand Help.");
        sendMessage(sender, "&7&m------------------------------------------------");
    }

    public void footer(CommandSender sender) {
        sendMessage(sender, "&7&m------------------------------------------------");
    }
}
