package github.gntodtndls156.nextlife.betweentrade;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class HANDLE_Trade {
    static Map<String, INV_Trade> TRADE = new HashMap<String, INV_Trade>();
    final int[] playerMeSlot = new int[]{0, 1, 2, 3, 9, 10, 11, 12, 18, 19, 20, 21, 27, 28, 29, 30};
    final int[] playerYouSlot = new int[]{5, 6, 7, 8, 14, 15, 16, 17, 23, 24, 25, 26, 32, 23, 24, 35};
    String TRADE_key;

    public HANDLE_Trade(String playerMe, String playerYou) {
        this.TRADE_key = playerMe + playerYou;
        TRADE.put(TRADE_key, new INV_Trade(Bukkit.getPlayer(playerMe), Bukkit.getPlayer(playerYou)));
        TRADE.get(TRADE_key).openInventory();
    }

    @EventHandler
    public void onClickAtTradeInventory(InventoryClickEvent event) {
        if (event.getInventory().getTitle().equals("Player To Player Trade")) {
            event.setCancelled(true);
            Player player;
            String player$name = null;
            if (event.getWhoClicked() instanceof Player) {
                player = (Player) event.getWhoClicked();
                player$name = player.getName();
            }

            if (event.getRawSlot() == 53) {
                ItemStack Button = event.getCurrentItem();
                String Button$name = Button.getItemMeta().getDisplayName();

                if (Button$name.equals("거래 잠그기")) {
                    // TODO
                } else if (Button$name.equals("거래 수락하기")) {
                    // TODO
                } else if (Button$name.equals("돈 거래")) {
                    // TODO
                }
            }
            
            if (Arrays.stream(new int[] {4, 13, 22, 31, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53}).anyMatch(slot -> slot == event.getSlot()) &&
                event.getCurrentItem().getType() == Material.AIR)
                return;

            switch(event.getClickedInventory().getType()) {
                case CHEST:
                    for (int i = 0; i <= 35; i++) {
                        if (event.getWhoClicked().getInventory().getContents()[i] == null &&
                                IntStream.of(TRADE.get(TRADE_key).getPlayerMe().getName().equals(player$name) ? playerMeSlot : playerYouSlot)
                                        .anyMatch(slot -> slot == event.getSlot())) {
                            event.getWhoClicked().getInventory().setItem(i, event.getCurrentItem());
                            TRADE.get(TRADE_key).getInventory().setItem(event.getSlot(), new ItemStack(Material.AIR));
                        }
                    }
                    break;
                case PLAYER:
                    int[] slot = TRADE.get(TRADE_key).getPlayerMe().getName().equals(player$name) ? playerMeSlot : playerYouSlot;
                    for (int i = 0; i < slot.length; i++) {
                        if (event.getInventory().getContents()[slot[i]] == null) {
                            TRADE.get(TRADE_key).getInventory().setItem(slot[i], event.getCurrentItem());
                            event.getWhoClicked().getInventory().setItem(event.getSlot(), new ItemStack(Material.AIR));
                        }
                    }
            }
        }

        
    }
}
