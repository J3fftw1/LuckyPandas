package dev.j3fftw.luckypanda;

import dev.j3fftw.luckypanda.surprise.Surprise;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.concurrent.ThreadLocalRandom;

public class LuckyCommand implements CommandExecutor {

    @Override
    @ParametersAreNonnullByDefault
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {
        if (commandSender instanceof ConsoleCommandSender) {
            commandSender.sendMessage("This command can only be ran by a player.");
            return true;
        }
        final Player player = (Player) commandSender;
        if (player.hasPermission("luckypanda.admin")) {
            if (args.length != 0) {
                for (Surprise surprise : LuckyPanda.getInstance().getEnableSurprises()) {
                    if (surprise.getId().getKey().equalsIgnoreCase(args[0])) {
                        surprise.process(player, player);
                        return true;
                    }
                }
                player.sendMessage(ChatColor.RED + "There isn't a surprise with this ID, please try again.");
            } else {
                chooseSurprise().process(player, player);
            }
        } else {
            player.sendMessage(ChatColor.RED + "You don't have the permissions to run this command.");
        }
        return true;
    }

    private Surprise chooseSurprise() {
        double random = ThreadLocalRandom.current().nextDouble() + ThreadLocalRandom.current().nextInt(101);

        return LuckyPanda.getInstance().getRandomSurprise();
    }
}
