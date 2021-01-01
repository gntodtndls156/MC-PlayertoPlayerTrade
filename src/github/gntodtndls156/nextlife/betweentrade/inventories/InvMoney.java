package github.gntodtndls156.nextlife.betweentrade.inventories;

import github.gntodtndls156.nextlife.betweentrade.MainBetweenTrade;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.inventory.Inventory;

public class InvMoney { // TODO
    private Economy economy = new MainBetweenTrade().getEconomy();
    private Inventory inventory;

    public InvMoney () {

    }

    private Inventory registerInventory() {
        inventory = Bukkit.createInventory(null, 9 * 6, "Add Money Trade");


    }

    public Inventory getInventory() {
        return inventory;
    }
}
