package github.gntodtndls156.nextlife.betweentrade.handles;

import github.gntodtndls156.nextlife.betweentrade.inventories.InvMoney;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.HashMap;
import java.util.Map;

public class HandleMoney implements Listener {
    private Map<String, Inventory> INVENTORY = new HashMap<>();
    private InvMoney invMoney;
    private Player player;
    public HandleMoney(Player player, Inventory inventory) {
        invMoney = new InvMoney();
        player.openInventory(invMoney.getInventory());
        INVENTORY.put(player.getName(), inventory);
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
            ItemMeta meta = item.getItemMeta();
            Player player = (Player) event.getWhoClicked();
            int increaseMoney = Integer.parseInt(meta.getDisplayName().replaceAll("[^\\d]", ""));

            if (item.getType() == Material.STONE_BUTTON) {
                invMoney.plus(increaseMoney);
            } else if (item.getType() == Material.WOOD_BUTTON) {
                invMoney.minus(increaseMoney);
            } else if (event.getCurrentItem().getItemMeta().getDisplayName().equals("Add to Trade")) {

            } else if (event.getCurrentItem().getItemMeta().getDisplayName().equals("Type in Vault")) {

            } else if (event.getCurrentItem().getItemMeta().getDisplayName().equals("Back")) {
                player.openInventory(HandleTrade.TRADE.get(HandleTrade.TRADE_KEY.get(player.getName())).getInventory());
                // player.openInventory(INVENTORY.get(player.getName()));

            }
        }
    }
    public Inventory openInventory() {
        return invMoney.getInventory();
    }
}
