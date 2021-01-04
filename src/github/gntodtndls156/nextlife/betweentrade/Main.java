package github.gntodtndls156.nextlife.betweentrade;

import github.gntodtndls156.nextlife.betweentrade.command.CommandbetweenTrade;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class Main extends JavaPlugin {
    @Override
    public void onDisable() {
        super.onDisable();
    }

    @Override
    public void onEnable() {
        super.onEnable();

        EVENTS();
        COMMANDS();


    }

    // VARIABLES
    private Economy economy;


    private void EVENTS() {
        getServer().getPluginManager().registerEvents();
        getServer().getPluginManager().registerEvents();
        getServer().getPluginManager().registerEvents();
    }
    private void COMMANDS() {
        this.getCommand("betweenTrade").setExecutor(new CommandbetweenTrade());
    }

    private boolean setupEconomy() {
        if (Bukkit.getPluginManager().getPlugin("Vault") == null) {
            return false;
        }

        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }

        economy = rsp.getProvider();
        return economy != null;
    }
}
