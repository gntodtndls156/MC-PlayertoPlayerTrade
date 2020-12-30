package github.gntodtndls156.nextlife.betweentrade;

import org.bukkit.plugin.java.JavaPlugin;

public class MAIN_BetweenTrade extends JavaPlugin {
    @Override
    public void onDisable() {
        super.onDisable();
    }

    @Override
    public void onEnable() {
        super.onEnable();

        // To register EVENTS
        getServer().getPluginManager().registerEvents(new HANDLE_Trade(this), this);
        getServer().getPluginManager().registerEvents(new INIT_Player(), this);
        // TO register COMMANDS
        this.getCommand("betweenTrade").setExecutor(new COMMAND_BetweenTrade());

    }
}
