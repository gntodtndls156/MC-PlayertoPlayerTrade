package github.gntodtndls156.nextlife.betweentrade;

import github.gntodtndls156.nextlife.betweentrade.commands.CommandBetweenTrade;
import github.gntodtndls156.nextlife.betweentrade.init.InitPlayer;
import github.gntodtndls156.nextlife.betweentrade.inventories.HandlerInvTrade;
import org.bukkit.plugin.java.JavaPlugin;

public class BetweenTradeMain extends JavaPlugin {
    @Override
    public void onDisable() {
        super.onDisable();
    }

    @Override
    public void onEnable() {
        super.onEnable();

        // To register EVENTS
        getServer().getPluginManager().registerEvents(new HandlerInvTrade(this), this);
        getServer().getPluginManager().registerEvents(new InitPlayer(), this);
        // To register Commands
        this.getCommand("playertoplayertrade").setExecutor(new CommandBetweenTrade());
    }
}
