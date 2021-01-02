package github.gntodtndls156.nextlife.betweentrade.inits;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Wool;

import java.util.Arrays;

public class InitButton {
    int number;
    int count$0, count$1, coins;
    public InitButton(int number) {
        this.number = number;
    }
    public InitButton(int number, int coins, int count$0, int count$1) {
        this.number = number;
        this.count$0 = count$0;
        this.count$1 = count$1;
        this.coins = coins;
    }
    public InitButton(int number, int sum, int money) {
        this.number = number;
        this.count$0 = sum;
        this.count$1 = money;
    }
    public InitButton(int number, int money) {
        this.number = number;
        this.count$0 = money;
    }
    public ItemStack ButtonLock() {
        switch (number) {
            case 1:
                return ButtonLock("거래 잠그기", new String[] {"이 재화로 거래하자고 제안합니다."}, 14);
            case 2:
                return ButtonLock("거래 수락하기", new String[]{"최종적으로 거래를 수락합니다.", "상대방도 수락할 경우 거래가 완료됩니다."}, 11);
            case 3:
                return ButtonMoney();
            case 4:
                return ButtonMoney("Add " + coins + "Coins",
                        new String[]{"Click to add more", "", count$0 + " Coins will be moved in you Inventory", "", "After moving, your balance is " + count$1},
                        Material.STONE_BUTTON);
            case 5:
                return ButtonMoney("Remove " + coins + "Coins",
                        new String[]{"Click to add more", "", count$0 + " Coins will be moved in you Inventory", "", "After moving, your balance is " + count$1},
                        Material.WOOD_BUTTON);
            case 6:
                return ButtonMoney("Add to Trade",
                        new String[]{"Click to add " + count$0 + " Coins to this trade", "", "Your balance afterwards will be " + count$1},
                        Material.WOOD_AXE);
            case 7:
                return ButtonMoney$back();
            case 8:
                return ButtonMoney$show(count$0);
        }
        return null;
    }
    private ItemStack ButtonLock(String name, String[] lore, int color) {
        ItemStack lock = new ItemStack(WoolColors(color));
        ItemMeta meta = lock.getItemMeta();

        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(lore));
        lock.setItemMeta(meta);
        return lock;
    }

    private ItemStack WoolColors(int number) {
        DyeColor[] colors = DyeColor.values();
        return new Wool(colors[number]).toItemStack(1);
        //Wool wool = new Wool(colors[number]);
    }

    private ItemStack ButtonMoney() {
        ItemStack item = new ItemStack(Material.GOLD_NUGGET, 1);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName("돈 거래");
        meta.setLore(Arrays.asList("돈을 얼만큼 주고 받을지 요구합니다."));

        item.setItemMeta(meta);
        return item;
    }
    private ItemStack ButtonMoney(String name, String[] lore, Material material) {
        ItemStack item = new ItemStack(material, 1); // TODO
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(lore));

        item.setItemMeta(meta);
        return item;
    }
    private ItemStack ButtonMoney$show(double count$0) {
        // TODO
        ItemStack item = new ItemStack(Material.BED, 1);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName("Type in Value");
        meta.setLore(Arrays.asList("Click to type in an exact value"));

        item.setItemMeta(meta);
        return item;
    }
    private ItemStack ButtonMoney$back() {
        ItemStack item = new ItemStack(Material.BED, 1);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName("Back");
        meta.setLore(Arrays.asList("Click to go back"));

        item.setItemMeta(meta);
        return item;
    }
}
