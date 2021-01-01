package github.gntodtndls156.nextlife.betweentrade;

import github.gntodtndls156.nextlife.betweentrade.commands.BetweenTradeCommand;
import github.gntodtndls156.nextlife.betweentrade.handles.HandleTrade;
import github.gntodtndls156.nextlife.betweentrade.inits.InitPlayer;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

public class MainBetweenTrade extends JavaPlugin {

    private Economy economy;
    private Chat chat;

    @Override
    public void onDisable() {
        super.onDisable();
    }

    @Override
    public void onEnable() {
        super.onEnable();

        // To register EVENTS
        getServer().getPluginManager().registerEvents(new HandleTrade(this), this);
        getServer().getPluginManager().registerEvents(new InitPlayer(), this);
        // TO register COMMANDS
        this.getCommand("betweenTrade").setExecutor(new BetweenTradeCommand());
        // TO connect Plugin "Vault"
        if (!setupEconomy()) {
            this.getLogger().severe("Disabled due to no Vault dependency found!");
            Bukkit.getPluginManager().disablePlugin(this);
            return;
        }
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

    private boolean setupChat() {
        RegisteredServiceProvider<Chat> rsp = getServer().getServicesManager().getRegistration(Chat.class);
        chat = rsp.getProvider();
        return chat != null;
    }

    public Chat getChat() {
        return chat;
    }

    public Economy getEconomy() {
        return economy;
    }
}