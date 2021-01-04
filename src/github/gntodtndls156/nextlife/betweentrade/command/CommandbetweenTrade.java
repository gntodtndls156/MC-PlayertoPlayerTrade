package github.gntodtndls156.nextlife.betweentrade.command;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class CommandbetweenTrade implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        if (strings.length >= 2) {
            if (OnlinePlayerCheck(strings[0]) && OnlinePlayerCheck(strings[1])) {

            }
        }

        return false;
    }

    private boolean OnlinePlayerCheck(String player$name) {
        if (Bukkit.getServer().getOnlinePlayers().stream().anyMatch(player -> player.getName().equals(player$name)))
            return true;
        return false;
    }
}
