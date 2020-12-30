package github.gntodtndls156.nextlife.betweentrade;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Wool;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class HANDLE_Trade {
    static Map<String, INV_Trade> TRADE = new HashMap<String, INV_Trade>();
    final int[] playerMeSlot = new int[]{0, 1, 2, 3, 9, 10, 11, 12, 18, 19, 20, 21, 27, 28, 29, 30};
    final int[] playerYouSlot = new int[]{5, 6, 7, 8, 14, 15, 16, 17, 23, 24, 25, 26, 32, 23, 24, 35};
    final int[] lineLeft = new int[]{36, 37, 38};
    final int[] lineRight = new int[]{44, 43, 42};
    final int[] lineCenter = new int[]{40, 31, 22, 13, 4};
    String TRADE_key;

    public HANDLE_Trade(String playerMe, String playerYou) {
        this.TRADE_key = playerMe + playerYou;
        TRADE.put(TRADE_key, new INV_Trade(Bukkit.getPlayer(playerMe), Bukkit.getPlayer(playerYou)));
        TRADE.get(TRADE_key).openInventory();
    }
    BetweenTradeMain plugin
    public HANDLE_Trade(BetweenTradeMain plugin) {
        this.plugin = plugin;
    }


    @EventHandler
    public void onClickAtTradeInventory(InventoryClickEvent event) {
        if (event.getInventory().getTitle().equals("Player To Player Trade")) {
            event.setCancelled(true);

            final Player player = (Player) event.getWhoClicked();
            final String player$name = player.getName();

            if (event.getRawSlot() == 53) {
                ItemStack Button = event.getCurrentItem();
                String Button$name = Button.getItemMeta().getDisplayName();

                if (Button$name.equals("거래 잠그기")) {
                    // TODO
                    for(int i = 0; i < 3; i++) {
                        int finalI = i;
                        Bukkit.getScheduler().runTaskLater(plugin, () -> {
                            TRADE.get(TRADE_key).getInventory().setItem(
                                    TRADE.get(TRADE_key).getPlayerMe().getName().equals(player$name) ? lineLeft[finalI] : lineRight[finalI],
                                    new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 12)
                            );
                            if (finalI == 3) {
                                if (TRADE.get(TRADE_key).getPlayerMe().getName().equals(player$name)) {
                                    TRADE.get(TRADE_key).setMeLock(true);
                                } else {
                                    TRADE.get(TRADE_key).setYouLock(true);
                                }
                                if (TRADE.get(TRADE_key).isMeLock() && TRADE.get(TRADE_key).isYouLock()) {

                                }
                            }
                        }, (1 + i) * 7L);
                    }
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
    private void changeLine(int color, InventoryClickEvent event, boolean state) {
        for(int i = 0; i < lineCenter.length; i++) {
            int finalI = i;
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                TRADE.get(TRADE_key).getInventory().setItem(
                        lineCenter[finalI],
                        new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) color)
                );
                if (finalI + 1 == lineCenter.length) {
                    if (state) {
                        TRADE.get(TRADE_key).getInventory().setItem(53, new INIT_Button(2).ButtonLock());
                    } else {

                    }
                }
            }, (i + 1) * 7);
        }
    }

    
}
