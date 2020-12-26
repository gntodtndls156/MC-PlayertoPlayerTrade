package github.gntodtndls156.nextlife.playertoplayertrade;

import github.gntodtndls156.nextlife.playertoplayertrade.commands.CommandTrade;
import github.gntodtndls156.nextlife.playertoplayertrade.inventories.TradeInv;
import org.bukkit.plugin.java.JavaPlugin;

public class TradeMain extends JavaPlugin {
    @Override
    public void onDisable() {
        super.onDisable();
    }

    @Override
    public void onEnable() {
        super.onEnable();

        // REGISTER EVENT
        getServer().getPluginManager().registerEvents(new TradeInv(this), this);
        getServer().getPluginManager().registerEvents(new InitPlayer(this), this);
        // REGISTER COMMAND
        this.getCommand("playertoplayertrade").setExecutor(new CommandTrade());
    }
}
