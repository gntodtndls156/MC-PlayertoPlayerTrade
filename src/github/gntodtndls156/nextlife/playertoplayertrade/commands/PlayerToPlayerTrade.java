package github.gntodtndls156.nextlife.playertoplayertrade.commands;

import github.gntodtndls156.nextlife.playertoplayertrade.inventories.TradeInv;
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
            if (OnlinePlayerCheck(strings[1])) {
                new TradeInv(strings[0], strings[1]);
                return true;
            }
        }
        return false;
    }
    private boolean OnlinePlayerCheck(String name) {
        for(Player player : Bukkit.getServer().getOnlinePlayers()) {
            System.out.println(player.getName() + name);
            if (name.equals(player.getName()))
                return true;
        }
        return false;
    }
}
