package github.gntodtndls156.nextlife.betweentrade.handle;

import github.gntodtndls156.nextlife.betweentrade.Main;
import github.gntodtndls156.nextlife.betweentrade.inv.InvTrade;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import java.util.HashMap;
import java.util.Map;

public class HandleTrade implements Listener {
    protected static Map<Integer, InvTrade> TRADE = new HashMap<>();
    protected static Map<Player, Integer> TRADE_KEY = new HashMap<>();

    Main plugin;
    public HandleTrade(Main plugin) {
        this.plugin = plugin;
    }

    public HandleTrade(String player$0, String player$1) {
        Bukkit.getPlayer(player$0);
        Bukkit.getPlayer(player$1);
    }
}
