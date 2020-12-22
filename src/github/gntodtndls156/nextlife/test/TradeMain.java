package github.gntodtndls156.nextlife.test;

import github.gntodtndls156.nextlife.test.commands.CommandTrade;
import github.gntodtndls156.nextlife.test.inventories.TradeInv;
import org.bukkit.Bukkit;
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

        // REGISTER COMMAND
        this.getCommand("playertoplayertrade").setExecutor(new CommandTrade());
    }
}
