package github.gntodtndls156.nextlife.betweentrade.inventories;

import github.gntodtndls156.nextlife.betweentrade.MainBetweenTrade;
import github.gntodtndls156.nextlife.betweentrade.inits.InitButton;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class InvMoney {
    private Economy economy = new MainBetweenTrade().getEconomy();
    private Inventory inventory;
    private double money; // how much are you have money?
    private double sum; // sum
    public InvMoney (Player player) {
        this.money = economy.getBalance(player);

        inventory = Bukkit.createInventory(null, 9 * 6, "Add Money Trade");
        for (int i = 3; i <= 4; i++) {
            for (int j = 1; j <= 7; j++) {
                double count = money;
                inventory.setItem((i * 9) + j, new InitButton(4, count % (Math.pow(10, j)), )); // TODO
            }
        }
    }


    public Inventory getInventory() {
        return inventory;
    }
}
