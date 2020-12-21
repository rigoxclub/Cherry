package club.rigox.cherry.commands;

import club.rigox.cherry.Cherry;
import co.aikar.commands.BaseCommand;
import co.aikar.commands.annotation.CommandAlias;
import co.aikar.commands.annotation.CommandPermission;
import co.aikar.commands.annotation.Subcommand;
import org.apache.commons.lang.math.NumberUtils;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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
            sendMessage(sender, "&cPlease specify credits to give!");
            return;
        }

        if (args.length == 2) {
            Player target = cherry.getServer().getPlayer(args[0]);

            if (!NumberUtils.isNumber(args[1])) {
                sendMessage(sender, "&cYou should provide a Number!");
                return;
            }

            Double credits = Double.parseDouble(args[1]);

            cherry.getEconomy().sumCredits(target, credits);
            sendMessage(sender, String.format("&aYou give %s credits to %s economy!", credits, target.getName()));
        }
    }
}
