package github.gntodtndls156.nextlife.betweentrade.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandBetweenTrade implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        // TODO - make /BetWeenTrade


        return false;
    }

    private boolean OnlinePlayerCheck(String name) {
        for(Player player : Bukkit.getOnlinePlayers()) {
            if (player.getName().equals(name)) {
                return true;
            }
        }
        return false;
    }
}
