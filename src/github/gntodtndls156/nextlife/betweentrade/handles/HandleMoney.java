package github.gntodtndls156.nextlife.betweentrade.handles;

import github.gntodtndls156.nextlife.betweentrade.MainBetweenTrade;
import github.gntodtndls156.nextlife.betweentrade.inventories.InvMoney;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import java.util.Arrays;

public class HandleMoney implements Listener {
    private InvMoney invMoney;
    private Player player;

    public HandleMoney(Player player) {
        this.player = player;
        invMoney = new InvMoney();
        player.openInventory(invMoney.getInventory());
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

            if (event.getCurrentItem().getType() == Material.STONE_BUTTON) {

            } else if (event.getCurrentItem().getType() == Material.WOOD_BUTTON) {

            } else if (event.getCurrentItem().getItemMeta().getDisplayName().equals("Add to Trade")) {

            } else if (event.getCurrentItem().getItemMeta().getDisplayName().equals("Type in Vault")) {

            } else if (event.getCurrentItem().getItemMeta().getDisplayName().equals("Back")) {

            }
        }
    }

    public Inventory openInventory() {
        return invMoney.getInventory();
    }
}
