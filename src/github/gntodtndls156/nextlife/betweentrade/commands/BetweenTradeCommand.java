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
        if (strings.length >= 2) {
            if (OnlinePlayerCheck(strings[0]) && OnlinePlayerCheck(strings[1])) {
                new HandleTrade(strings[0], strings[1]);
            }
        }
        if (strings[0] != null) {
            // new HandleMoney(Bukkit.getPlayer(strings[0]));
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


