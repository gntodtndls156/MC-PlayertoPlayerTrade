package github.gntodtndls156.nextlife.playertoplayertrade;

import github.gntodtndls156.nextlife.playertoplayertrade.commands.CommandTrade;
import github.gntodtndls156.nextlife.playertoplayertrade.inventories.Button;
import github.gntodtndls156.nextlife.playertoplayertrade.inventories.TradeInv;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;
import java.net.URL;
import java.util.logging.Logger;

public class PlayerToPlayerTrade extends JavaPlugin {
    public static void msg(String Message) {
        Logger log = Bukkit.getLogger();
        log.info("[Player To Player Trade] " + Message);
    }

    // public static Economy economy;

    @Override
    public void onDisable() {
        super.onDisable();
    }

    @Override
    public void onEnable() {
        super.onEnable();
        msg("Vault Plugin Check");
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            msg("NOT HAVE THE PLUGIN (Vault).");
            try {
                new URL("https://www.spigotmc.org/resources/vault.34315/").openConnection();
            } catch (IOException e) {
                msg("Browser no open url.");
            }
            getServer().getPluginManager().disablePlugin(this);
        } else {
            // economy = getServer().getServicesManager().getRegistration(Economy.class).getProvider();
        }
        msg("Vault Plugin Success to Check");

        getServer().getPluginManager().registerEvents(new TradeInv(), this);
        getServer().getPluginManager().registerEvents(new Button(), this);
        Commands();
        new Button(this);

    }

    private void Commands() {
        this.getCommand("playertoplayertrade").setExecutor(new CommandTrade());
    }
}
