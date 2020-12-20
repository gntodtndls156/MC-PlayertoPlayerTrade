package github.gntodtndls156.nextlife.playertoplayertrade.listeners;

import github.gntodtndls156.nextlife.playertoplayertrade.inventories.TradeInventory;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;
import java.util.Iterator;


public class TradeListeners implements Listener  {
    Player playerMe, playerYou;

    public TradeListeners(Player Me, Player You) {
        this.playerMe = Me;
        this.playerYou = You;
    }

    @EventHandler
    public void onCanNotClick(InventoryClickEvent event) {
        if (event.getInventory().getTitle().equals("Player To Player Trade")) {
            boolean state = false;
            int[] canNotClick = new int[]{4, 13, 22, 31, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52};
            if (Arrays.stream(canNotClick).anyMatch(x -> x == event.getSlot()) && event.getInventory().getType() == InventoryType.CHEST) {
                return;
            } else if (event.getCurrentItem().getType() == Material.AIR) {
                return;
            }
        }
    }

    @EventHandler
    public void onClickToButton(InventoryClickEvent event) {
        if (event.getInventory().getTitle().equals("Player To Player Trade")) {
            if (event.isLeftClick()) {
                event.setCancelled(true);
                int[] playerMeSlot = new int[]{36, 37, 38};
                int[] playerYouSlot = new int[]{44, 43, 42};
                if (event.getSlot() == 53 && event.getCurrentItem().getItemMeta().getDisplayName().equals("거래 잠그기")) {
                    if (event.getWhoClicked().getName().equals(getPlayerMe().getName())) {

                    }
                }
            }
        }
    }

    private void changeLine(int[] line, int color, InventoryClickEvent event, short switching) {

    }



    @EventHandler
    public void skullPlayerJoin(PlayerJoinEvent event) {
        Thread thread = new Thread(() -> {
            Player player = event.getPlayer();
            Iterator<String> keys = TradeInventory.PLAYERSKULL.keySet().iterator();
            while(keys.hasNext())
                if (keys.next().equals(player.getName()))
                    return; // currentThread().stop();
            TradeInventory.PLAYERSKULL.put(player.getName(), new ItemStack() {
                public ItemStack setItem() {
                    ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
                    SkullMeta meta = (SkullMeta) item.getItemMeta();

                    meta.setOwningPlayer(player);
                    item.setItemMeta(meta);
                    return item;
                }
            }.setItem());
        });
        thread.start();
    }

    public Player getPlayerMe() {
        return playerMe;
    }

    public Player getPlayerYou() {
        return playerYou;
    }
}
