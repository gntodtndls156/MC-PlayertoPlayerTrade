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
//    public InitButton(int number, ) {}


    public ItemStack ButtonLock() {
        switch(number) {
            case 1: // InvTrade
                return Button("거래 잠그기", new String[] {"이 재화로 거래하자고 제안합니다."}, 14);
            case 2: // InvTrade
                return Button("거래 수락하기", new String[] {"최종적으로 거래를 수락합니다.", "상대방도 수락할 경우 거래가 완료됩니다."}, 11);
            case 3: // InvMoney - Back
                return Button("Back", new String[]{"Click to go back"}, Material.BED);
            case 4: // InvMoney - Type in Value
                return Button("Type in Value", new String[]{"Click to type in an exact value"}, Material.BED);
            case 5: // InvMoney
                return Button("Add " + unit + "Coins",
                        new String[]{"Click to add more", "", sum + "Coins will be moved in you Inventory", "", "After moving, you balance is " + result},
                        Material.STONE_BUTTON);
            case 6: // InvMoney
                return Button("Remove " + unit + "Coins",
                        new String[]{"Click to remove " + sum + "Coins to this trade", "", "Your balance afterwards will be " + result },
                        Material.WOOD_BUTTON);
            case 7: // InvMoney - Add to Trade
                return Button("Add to Trade",
                        new String[]{"Click to add " + sum + "Coins to this trade", "", "Your balance afterwards will be " + myMoney},
                        Material.WOOD_AXE);
            case 8: // InvTrade
                return Button("돈 거래",
                        new String[]{"돈을 얼만큼 주고 받을지 요구합니다."},
                        Material.GOLD_NUGGET);
        }
        return new ItemStack(Material.AIR);
    }

    private ItemStack Button(String name, String[] lore, Material material) {
        ItemStack item = new ItemStack(material, 1);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(lore));

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