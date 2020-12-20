package github.gntodtndls156.nextlife.playertoplayertrade.inventories;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class TradeInventory {
    public static Map<String, Inventory> TRADEINVENTORY = new HashMap<>();
    public static Map<String, ItemStack> PLAYERSKULL = new HashMap<>();
    Player playerMe, playerYou;

    private Inventory makeInventory() {
        Inventory inventory = Bukkit.createInventory(null, 9 * 6, "Player To Player Trade");
        ItemStack line = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 1);
        for (int i = 0; i < 9; i++) {
            inventory.setItem(9 * 4 + i, line);
        }
        int count = 4;
        for (int i = 0; i < 4; i++) {
            inventory.setItem(count + (i * 9), line);
        }
//        inventory.setItem(41, SKULL.get(player2.getName()));
//        inventory.setItem(39, SKULL.get(player1.getName()));
//
//        inventory.setItem(53, createButton1());
//        inventory.setItem(45, createMoneyButton());

        return inventory;
    }

    private void
}
