package github.gntodtndls156.nextlife.betweentrade.inventories;

import github.gntodtndls156.nextlife.betweentrade.inits.InitButton;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class InvMoney {
    // private Economy economy = new MainBetweenTrade().getEconomy();
    private Inventory inventory;
    private int money = 0; // how much are you have money?
    private int sum = 10; // sum
//    private int minus = 10;
//    private int plus = 10;
    public InvMoney (Player player) {
        this.money = (int) 2323;// economy.getBalance(player);

        inventory = Bukkit.createInventory(null, 9 * 6, "Add Money Trade");

        inventory.setItem(4, new ItemStack() {
            private ItemStack MyMoney() {
                ItemStack item = new ItemStack(Material.GOLD_NUGGET, 1);
                ItemMeta meta = item.getItemMeta();

                meta.setDisplayName(money + " Coins (Selected)");
                meta.setLore(Arrays.asList("You have selected this currency"));

                item.setItemMeta(meta);
                return item;
            }
        }.MyMoney());


        inventory.setItem(52, new InitButton(6, sum, money).ButtonLock());
        // TODO BACK button
        // TODO Type in Value button
        // TODO add to Trade
        player.openInventory(inventory);
    }


    public Inventory getInventory() {
        return inventory;
    }

    public int getMoney() {
        return money;
    }

    public int getSum() {
        return sum;
    }

    public void setMoney(int money) {
        this.money = money;
    }
}
