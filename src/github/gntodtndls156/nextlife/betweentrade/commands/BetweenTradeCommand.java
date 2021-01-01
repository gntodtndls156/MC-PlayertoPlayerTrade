package github.gntodtndls156.nextlife.betweentrade.commands;

import github.gntodtndls156.nextlife.betweentrade.handles.HandleTrade;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BetweenTradeCommand implements CommandExecutor {
    // command /betweenTrade
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (OnlinePlayerCheck(strings[0]) && OnlinePlayerCheck(strings[1])) {
            System.out.println("command " + strings); // TODO CHECK
            new HandleTrade(strings[0], strings[1]);
        }
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


