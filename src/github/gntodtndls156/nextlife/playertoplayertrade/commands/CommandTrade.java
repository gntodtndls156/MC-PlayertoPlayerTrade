package github.gntodtndls156.nextlife.playertoplayertrade.commands;

import github.gntodtndls156.nextlife.playertoplayertrade.InitPlayer;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandTrade implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        // player to player trade
        if (OnlinePlayerCheck(strings[0]) && OnlinePlayerCheck(strings[1]))
            new InitPlayer();
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
