package club.rigox.cherry.commands;

import club.rigox.cherry.Cherry;
import club.rigox.cherry.utils.Number;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.HelpCommand;
import co.aikar.commands.annotation.Subcommand;
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

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
    public void giveCommand(CommandSender sender, String[] args) {
        if (args.length == 1) {
            sendMessage(sender, getLangString("USAGE.GIVE"));
            return;
        }

        if (args.length == 2) {
            UUID uuid = cherry.getServer().getPlayerUniqueId(args[0]);

            if (!Number.isANumber(sender, args[1])) return;
            Double credits = Double.parseDouble(args[1]);

            cherry.getEconomy().sumCredits(sender, uuid, credits);
        }
    }

    @Subcommand("take")
    @CommandPermission("cherry.take")
    public void takeCommand(CommandSender sender, String[] args) {
        if (args.length == 1) {
            sendMessage(sender, getLangString("USAGE.TAKE"));
            return;
        }

        if (args.length == 2) {
            UUID uuid = cherry.getServer().getPlayerUniqueId(args[0]);

            if (!Number.isANumber(sender, args[1])) return;
            Double credits = Double.parseDouble(args[1]);

            cherry.getEconomy().subtractCredits(sender, uuid, credits);
        }
    }

    @Subcommand("set")
    @CommandPermission("cherry.set")
    public void setCommand(CommandSender sender, String[] args) {
        if (args.length == 1) {
            sendMessage(sender, getLangString("USAGE.SET"));
            return;
        }

        if (args.length == 2) {
            UUID uuid = cherry.getServer().getPlayerUniqueId(args[0]);

            if (!Number.isANumber(sender, args[1])) return;
            Double credits = Double.parseDouble(args[1]);

            cherry.getEconomy().setCredits(sender, uuid, credits);
        }
    }

    @Subcommand("reset")
    @CommandPermission("cherry.reset")
    public void resetCommand(CommandSender sender, String[] args) {
        if (args.length == 1) {
            UUID uuid = cherry.getServer().getPlayerUniqueId(args[0]);

            cherry.getEconomy().resetCredits(sender, uuid);
        }
    }

    @Subcommand("admin")
    public void onAdminHelp(CommandSender sender) {
        header(sender);
        sendMessage(sender, "&8&l* &b/cherry give (player) (credits) &f- Give credits to a player.");
        sendMessage(sender, "&8&l* &b/cherry take (player) (credits) &f- Take credits to a player.");
        sendMessage(sender, "&8&l* &b/cherry set (player) (credits) &f- Set credits to a player.");
        sendMessage(sender, "&8&l* &b/cherry reset (player) (credits) &f- Reset credits to a player.");
        footer(sender);
    }

    @HelpCommand
    public void onHelp(CommandSender sender) {
        header(sender);
        sendMessage(sender, "&8&l* &b/credits &f- View your credits");
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
