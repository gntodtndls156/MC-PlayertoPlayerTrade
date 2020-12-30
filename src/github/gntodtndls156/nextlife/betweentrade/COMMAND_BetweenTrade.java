package github.gntodtndls156.nextlife.betweentrade;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class COMMAND_BetweenTrade implements CommandExecutor {
    // command /BetweenTrade
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (OnlinePlayerCheck(strings[0]) && OnlinePlayerCheck(strings[1])) {

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


