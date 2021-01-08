package github.gntodtndls156.nextlife.betweentrade.init;

import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Wool;

import java.util.Arrays;

public class InitButton {
    int number;
    int unit, sum, result;
    int myMoney;

    public InitButton(int number) { this.number = number; }
    public InitButton(int number, int unit, int sum, int result) {
        this.number = number;
        this.unit = unit;
        this.sum = sum;
        this.result = result;
    }
    public InitButton(int number, int sum, int myMoney) {
        this.number = number;
        this.sum = sum;
        this.myMoney = myMoney;
    }
    public InitButton(int number, int sum) {
        this.sum = sum;
        this.number = number;
    }
//    public InitButton(int number, ) {


    public ItemStack ButtonLock() {
        switch(number) {
            case 1: // InvTrade
                return Button(InitGetLang.LANG.get("Lock"), new String[] {InitGetLang.LANG.get("LockL")}, 14);
            case 2: // InvTrade
                return Button(InitGetLang.LANG.get("Accept"), new String[] {InitGetLang.LANG.get("AcceptL")}, 11);
            case 3: // InvMoney - Back
                return Button(InitGetLang.LANG.get("Back"), new String[]{InitGetLang.LANG.get("BackL")}, Material.BED);
            case 4: // InvMoney - Type in Value
                return Button(InitGetLang.LANG.get("ShowMyMoney"), new String[]{InitGetLang.LANG.get("ShowMyMoneyL")}, Material.BED);
            case 5: // InvMoney
                return Button(InitGetLang.LANG.get("Add") + unit + InitGetLang.LANG.get("unit"),
                        new String[]{InitGetLang.LANG.get("AddL1"), "", String.format(InitGetLang.LANG.get("AddL2"), sum, InitGetLang.LANG.get("unit")), "", String.format(InitGetLang.LANG.get("AddL3"), result, InitGetLang.LANG.get("unit"))},
                        Material.STONE_BUTTON);
            case 6: // InvMoney
                return Button(InitGetLang.LANG.get("Remove") + unit + InitGetLang.LANG.get("unit"),
                        new String[]{String.format(InitGetLang.LANG.get("RemoveL2"), sum, InitGetLang.LANG.get("unit")), "", String.format(InitGetLang.LANG.get("RemoveL3"), result, InitGetLang.LANG.get("unit"))},
                        Material.WOOD_BUTTON);
            case 7: // InvMoney - Add to Trade
                return Button(InitGetLang.LANG.get("AddToTrade"),
                        new String[]{String.format(InitGetLang.LANG.get("AddToTradeL1"), sum, InitGetLang.LANG.get("unit")), "", String.format(InitGetLang.LANG.get("AddToTradeL3"), myMoney, InitGetLang.LANG.get("unit"))},
                        Material.WOOD_AXE);
            case 8: // InvTrade
                return Button(InitGetLang.LANG.get("Money"),
                        new String[]{InitGetLang.LANG.get("MoneyL")},
                        Material.GOLD_NUGGET);
            case 9: // InvTrade
                return Button(InitGetLang.LANG.get("Close"),
                        new String[]{InitGetLang.LANG.get("CloseL")},
                        Material.BOAT);
            case 10: // InvMoney
                return Button(sum + InitGetLang.LANG.get("unit"),
                        null,
                        Material.PAPER);
        }
        return new ItemStack(Material.AIR);
    }

    private ItemStack Button(String name, String[] lore, Material material) {
        ItemStack item = new ItemStack(material, 1);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(name);
        if (lore != null) {
            meta.setLore(Arrays.asList(lore));
        }

        item.setItemMeta(meta);
        return item;
    }
    private ItemStack Button(String name, String[] lore, int color) {
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


}