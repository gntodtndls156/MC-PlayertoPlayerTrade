package github.gntodtndls156.nextlife.betweentrade;

import github.gntodtndls156.nextlife.betweentrade.command.CommandBetweenTrade;
import github.gntodtndls156.nextlife.betweentrade.handle.HandleMoney;
import github.gntodtndls156.nextlife.betweentrade.handle.HandlePlayer;
import github.gntodtndls156.nextlife.betweentrade.handle.HandleTrade;
import github.gntodtndls156.nextlife.betweentrade.init.InitGetLang;
import github.gntodtndls156.nextlife.betweentrade.init.InitJsonFile;
import github.gntodtndls156.nextlife.betweentrade.inv.InvTrade;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Iterator;

public class Main extends JavaPlugin {

    @Override
    public void onDisable() {
        super.onDisable();

        Iterator<InvTrade> invTradeIterator = HandleTrade.TRADE.values().iterator();
        while (invTradeIterator.hasNext()) {
            InvTrade invTrade = invTradeIterator.next();

            invTrade.getPlayer$0().closeInventory();
            invTrade.getPlayer$1().closeInventory();

            new HandleTrade(this).moveItem(invTrade);
        }
    }

    @Override
    public void onEnable() {
        super.onEnable();

        if (!setupEconomy()) {
            getLogger().info("경제 관련 플러그인이 설치되어 있지 않습니다.");
            getServer().getPluginManager().disablePlugin(this);
            return;
        }

        EVENTS();
        COMMANDS();

        // set LANG
        InitJsonFile initJsonFile = new InitJsonFile();
        if (initJsonFile.getLANGFile() == null) {
            getLogger().warning("LANG.json 파일이 없습니다. ko_KR을 기본으로 실행합니다.");
            initJsonFile.setLANGFile("ko_KR");
        }
        new InitGetLang(initJsonFile.getLANGFile());
        Iterator iterator = InitGetLang.LANG.keySet().iterator();
        while (iterator.hasNext()) {
            String str = (String) iterator.next();
            System.out.println(str + " - " + InitGetLang.LANG.get(str));
        }
    }

    // VARIABLES
    private Economy economy;

    private void EVENTS() {
        getServer().getPluginManager().registerEvents(new HandleMoney(this), this);
        getServer().getPluginManager().registerEvents(new HandlePlayer(this), this);
        getServer().getPluginManager().registerEvents(new HandleTrade(this), this);
    }
    private void COMMANDS() {
        this.getCommand("betweenTrade").setExecutor(new CommandBetweenTrade());
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

    public Economy getEconomy() {
        return economy;
    }
}
