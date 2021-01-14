package github.gntodtndls156.nextlife.betweentrade.inv;

import github.gntodtndls156.nextlife.betweentrade.init.InitButton;
import github.gntodtndls156.nextlife.betweentrade.init.InitGetLang;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Arrays;

public class InvMoney {
    private Inventory inventory;
    private int myMoney;
    private int sum = 10;
    private boolean viewState = true;

    public InvMoney(int myMoney) {
        this.myMoney = myMoney;

        inventory = Bukkit.createInventory(null, 9 * 6, "Add Money Trade");

        inventory.setItem(4, new ItemStack() {
            private ItemStack myMoney() {
                ItemStack item = new ItemStack(Material.GOLD_NUGGET, 1);
                ItemMeta meta = item.getItemMeta();

                meta.setDisplayName(myMoney + InitGetLang.LANG.get("unit") + " (SELECTED)");
                meta.setLore(Arrays.asList("You have selected this currency"));

                item.setItemMeta(meta);
                return item;
            }
        }.myMoney());

        button$reset();

        // Back
        inventory.setItem(48, new InitButton(3).ButtonLock());
        // Type in Value
//        inventory.setItem(50, new InitButton(4).ButtonLock());
    }

    public void button$reset() {
        int[] buttonSlot = new int[] {1, 10, 50, 100, 500, 1000, 5000, 10000, 100000};
        int plus;
        boolean state = true;
        for (int i = 0; i < 9; i++) {
            plus = (myMoney - sum) / buttonSlot[i];
            if (plus != 0) {
                inventory.setItem((2 * 9) + i, new InitButton(5, buttonSlot[i], sum, myMoney - sum).ButtonLock());
            } else {
                if (state) {
                    inventory.setItem((2 * 9) + i, new InitButton(5, myMoney - sum, sum, myMoney - sum).ButtonLock());
                    state = false;
                } else {
                    inventory.setItem((2 * 9) + i, new ItemStack(Material.AIR));
                }
            }
        }
        int minus;
        state = true;
        for (int i = 0; i < 9; i++) {
            minus = sum / buttonSlot[i];
            if (minus != 0) {
                inventory.setItem((3 * 9) + i, new InitButton(6, buttonSlot[i], sum, myMoney - sum).ButtonLock());
            } else {
                if (state) {
                    inventory.setItem((3 * 9) + i, new InitButton(6, sum, sum, myMoney - sum).ButtonLock());
                    state = false;
                } else {
                    inventory.setItem((3 * 9) + i, new ItemStack(Material.AIR));
                }
            }
        }
        // Add to Trade
        inventory.setItem(52, new InitButton(7, sum, myMoney - sum).ButtonLock());
        // show me the sum
        inventory.setItem(13, new InitButton(10, sum).ButtonLock());
    }

    public ItemStack sumToTrade() {
        return new ItemStack(Material.GOLD_NUGGET) {
            ItemMeta meta = this.getItemMeta();
            private ItemStack money() {
                meta.setLore(null);
                meta.setDisplayName(sum + InitGetLang.LANG.get("unit"));

                this.setItemMeta(meta);
                return this;
            }
        }.money();
    }

    public Inventory getInventory() {
        return inventory;
    }

    public int getSum() {
        return sum;
    }

    public int getMyMoney() {
        return myMoney;
    }

    public void plus(int number) {
        this.sum += number;
    }
    public void minus(int number) {
        this.sum -= number;
    }

    public boolean isViewState() {
        return viewState;
    }
    public void setViewState(boolean viewState) {
        this.viewState = viewState;
    }
}
