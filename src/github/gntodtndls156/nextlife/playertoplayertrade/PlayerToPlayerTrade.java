package github.gntodtndls156.nextlife.playertoplayertrade;

import github.gntodtndls156.nextlife.playertoplayertrade.commands.CommandTrade;
import github.gntodtndls156.nextlife.playertoplayertrade.inventories.TradeInv;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Logger;

public class PlayerToPlayerTrade extends JavaPlugin {
    public static void msg(String Message) {
        Logger log = Bukkit.getLogger();
        log.info("[Player To Player Trade] " + Message);
    }

    private Economy econ = null;

    public Economy getEcon() {
        return econ;
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    @Override
    public void onEnable() {
        super.onEnable();
        msg("Vault Plugin Check");

        if(!setupEconomy()) {
            msg("Vault Plugin no found! Player To Player Trade Plugin is Shutdown. ");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        msg("Vault Plugin Success to Check");

        getServer().getPluginManager().registerEvents(new TradeInv(this), this);
        Commands();
    }

    private void Commands() {
        this.getCommand("playertoplayertrade").setExecutor(new CommandTrade());
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }
}
