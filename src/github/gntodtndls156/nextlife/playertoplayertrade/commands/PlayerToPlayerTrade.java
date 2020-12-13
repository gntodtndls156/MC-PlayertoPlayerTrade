package github.gntodtndls156.nextlife.playertoplayertrade.commands;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlayerToPlayerTrade implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        // PlayerToPlayerTrade
        if (OnlinePlayerCheck(strings[0])) {

        }
        return false;
    }
    private boolean OnlinePlayerCheck(String name) {
        for(Player player : Bukkit.getServer().getOnlinePlayers()) {
            if (name.equals(player.getName()))
                return true;
        }
        return false;
    }
}
