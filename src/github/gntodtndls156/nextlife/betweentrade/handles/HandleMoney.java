package github.gntodtndls156.nextlife.betweentrade.handles;

import github.gntodtndls156.nextlife.betweentrade.inventories.InvMoney;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class HandleMoney implements Listener {
    private static Map<String, InvMoney> MONEY = new HashMap<>();
    private InvMoney invMoney;

    public HandleMoney(Player player) {
        invMoney = new InvMoney();
        MONEY.put(player.getName(), invMoney);
        player.openInventory(MONEY.get(player.getName()).getInventory());
    }
    public HandleMoney() {

    }

    @EventHandler
    public void onClickAtMoneyInventory(InventoryClickEvent event) {
        if (event.getInventory().getTitle().equals("Add Money Trade")) {
            event.setCancelled(true);
            if (event.getCurrentItem().getType() == Material.AIR) {
                return;
            }

            ItemStack item = event.getCurrentItem();
            Player player = (Player) event.getWhoClicked();
            if (item.getItemMeta().getDisplayName().equals("Add to Trade")) {
                // TODO
                player.openInventory(HandleTrade.TRADE.get(HandleTrade.TRADE_KEY.get(player.getName())).getInventory());
                int slot = addSlotMoney(player);
                if (slot == -1) {
                    player.sendMessage("FULL SLOT.");
                    return;
                }
                HandleTrade.TRADE.get(HandleTrade.TRADE_KEY.get(player.getName())).getInventory().setItem(slot, MONEY.get(player.getName()).moneyToTrade());
                reset(player);
                return;
            } else if (item.getItemMeta().getDisplayName().equals("Type in Vault")) {
                // TODO
                return;
            } else if (item.getItemMeta().getDisplayName().equals("Back")) {
                player.openInventory(HandleTrade.TRADE.get(HandleTrade.TRADE_KEY.get(player.getName())).getInventory());
                return;
            }

            int increaseMoney = Integer.parseInt(item.getItemMeta().getDisplayName().replaceAll("[^0-9]", ""));
            System.out.println(increaseMoney);
            if (item.getType() == Material.STONE_BUTTON) {
                MONEY.get(player.getName()).plus(increaseMoney);
                System.out.println(MONEY.get(player.getName()).getSum());
            } else if (item.getType() == Material.WOOD_BUTTON) {
                MONEY.get(player.getName()).minus(increaseMoney);
                System.out.println(MONEY.get(player.getName()).getSum());
            }
        }
    }

    public int addSlotMoney(Player player) {
        ItemStack[] items = HandleTrade.TRADE.get(HandleTrade.TRADE_KEY.get(player.getName())).getInventory().getContents();
        int[] slots = player.getName().equals(HandleTrade.TRADE.get(HandleTrade.TRADE_KEY.get(player.getName())).getPlayerMe().getName()) ?
                new int[]{0, 1, 2, 3, 9, 10, 11, 12, 18, 19, 20, 21, 27, 28, 29, 30} :
                new int[]{5, 6, 7, 8, 14, 15, 16, 17, 23, 24, 25, 26, 32, 33, 34, 35};
        int i;
        for (i = 0; i < slots.length; i++) {
            if (items[slots[i]] == null) {
                return slots[i];
            }
        }
        return -1;
    }

    private void reset(Player player) {
        MONEY.remove(player.getName());
    }
    public Inventory openInventory() {
        return invMoney.getInventory();
    }
}
