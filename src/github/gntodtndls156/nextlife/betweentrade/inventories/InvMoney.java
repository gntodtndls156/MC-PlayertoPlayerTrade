package github.gntodtndls156.nextlife.betweentrade.inventories;

import github.gntodtndls156.nextlife.betweentrade.inits.InitButton;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class InvMoney {
    // private Economy economy = new MainBetweenTrade().getEconomy();
    private Inventory inventory;
    private int money = 2323; // how much are you have money?
    private int sum = 10; // sum
//    private int minus = 10;
//    private int plus = 10;

    public InvMoney () {
        // this.money = (int) 2323;// economy.getBalance(player);

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

        int[] buttonSlot = new int[] {1, 10, 50, 100, 500, 1000, 5000, 10000, 100000};
        int plus;
        for (int i = 0; i < 9; i++) {
            plus = (money - sum) / buttonSlot[i];
            if (plus != 0) {
                inventory.setItem((2 * 9) + i, new InitButton(4, buttonSlot[i], sum, money - sum).ButtonLock());
            } else {
                inventory.setItem((2 * 9) + i, new InitButton(4, money - sum, sum, money - sum).ButtonLock());
                break;
            }
        }
        int minus;
        for (int i = 0; i < 9; i++) {
            minus = sum / buttonSlot[i];
            if (minus != 0) {
                inventory.setItem((3 * 9) + i, new InitButton(5, buttonSlot[i], sum, money - sum).ButtonLock());
            } else {
                inventory.setItem((3 * 9) + i, new InitButton(5, sum, sum,money - sum).ButtonLock());
                break;
            }
        }
        // TODO Add to Trade
        inventory.setItem(52, new InitButton(6, sum, money).ButtonLock());
        // TODO BACK button
        inventory.setItem(48, new InitButton(7).ButtonLock());
        // TODO Type in Value button
        inventory.setItem(50, new InitButton(8, money).ButtonLock());
    }


    public ItemStack moneyToTrade() {
        return new ItemStack(Material.GOLD_NUGGET) {
            ItemMeta meta = this.getItemMeta();
            public ItemStack money() {
                meta.setLore(null);
                meta.setDisplayName(sum + " Coins");

                this.setItemMeta(meta);
                return this;
            }
        }.money();
    }

    public Inventory getInventory() {
        return inventory;
    }

    public void plus(int number) {
        this.sum += number;
    }
    public void minus(int number) {
        this.sum -= number;
    }

    public int getSum() {
        return sum;
    }

    public int getMoney() {
        return money;
    }
}
