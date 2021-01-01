package github.gntodtndls156.nextlife.betweentrade.inits;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Wool;

import java.util.Arrays;

public class InitButton {
    int number;
    public InitButton(int number) {
        this.number = number;
    }

    public ItemStack ButtonLock() {
        switch (number) {
            case 1:
                return ButtonLock("거래 잠그기", new String[] {"이 재화로 거래하자고 제안합니다."}, 14);
            case 2:
                return ButtonLock("거래 수락하기", new String[]{"최종적으로 거래를 수락합니다.", "상대방도 수락할 경우 거래가 완료됩니다."}, 11);
            case 3:
                return ButtonMoney();
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

    private ItemStack ButtonMoney$stone() {
        ItemStack item; // TODO
    }
    private ItemStack ButtonMoney$wood() {
        ItemStack item; // TODO
    }
}
