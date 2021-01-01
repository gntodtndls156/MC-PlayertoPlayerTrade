package github.gntodtndls156.nextlife.betweentrade.inits;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Wool;

import java.util.Arrays;

public class InitButton {
    int number;
    int count$0, count$1;
    public InitButton(int number) {
        this.number = number;
    }
    public InitButton(int number, int count$0, int count$1) {
        this.number = number;
        this.count$0 = count$0;
        this.count$1 = count$1;
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
                return ButtonMoney$stone(count$0, count$1);
            case 5:
                return ButtonMoney$wood(count$0, count$1);
            case 6:
                return ButtonMoney$add(count$0, count$1);
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
    private ItemStack ButtonMoney$stone(int count$0, int count$1) {
        ItemStack item = new ItemStack(Material.STONE_BUTTON, 1); // TODO
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName("Add " + count$0 + "Coins");
        meta.setLore(Arrays.asList("Click to add more", "", count$1 + " Coins will be moved in you Inventory", "", "After moving, your balance is "));

        item.setItemMeta(meta);
        return item;
    }
    private ItemStack ButtonMoney$wood(int count$0, int count$1) {
        ItemStack item = new ItemStack(Material.WOOD_BUTTON, 1); // TODO
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName("Remove " + count$0 + "Coins");
        meta.setLore(Arrays.asList("Click to remove more", "", count$1 + " Coins will be moved in you Inventory", "", "After moving, your balance is "));

        item.setItemMeta(meta);
        return item;
    }
    private ItemStack ButtonMoney$add(int count$0, int count$1) {
        ItemStack item = new ItemStack(Material.WOOD, 1);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName("Add to Trade");
        meta.setLore(Arrays.asList("Click to add " + count$0 + " Coins to this trade", "", "Your balance afterwards will be " + count$1));

        item.setItemMeta(meta);
        return item;
    }
    private ItemStack ButtonMoney$show(int count$0) {
        // TODO
        ItemStack item = null;
        return item;
    }
    private ItemStack ButtonMoney$back() {
        ItemStack item = null;
        return item;
    }
}
