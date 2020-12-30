package github.gntodtndls156.nextlife.betweentrade;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class HANDLE_Trade implements Listener {
    static Map<String, INV_Trade> TRADE = new HashMap<>();
    final int[] playerMeSlot = new int[]{0, 1, 2, 3, 9, 10, 11, 12, 18, 19, 20, 21, 27, 28, 29, 30};
    final int[] playerYouSlot = new int[]{5, 6, 7, 8, 14, 15, 16, 17, 23, 24, 25, 26, 32, 23, 24, 35};
    final int[] lineLeft = new int[]{36, 37, 38};
    final int[] lineRight = new int[]{44, 43, 42};
    final int[] lineCenter = new int[]{40, 31, 22, 13, 4};
    String TRADE_key;
    INV_Trade invTrade;

    public HANDLE_Trade(String playerMe, String playerYou) {
        this.TRADE_key = playerMe + playerYou;
        this.invTrade = new INV_Trade(Bukkit.getPlayer(playerMe), Bukkit.getPlayer(playerYou));
        TRADE.put(TRADE_key, invTrade);
        System.out.println("HANDLE_Trade " + playerMe + " " + playerYou); // TODO CHECK
        System.out.println("HANDLE_Trade " + Bukkit.getPlayer(playerMe) + " " + Bukkit.getPlayer(playerYou));
        invTrade.setInventory(registerInventory(playerMe, playerYou));
        invTrade.openInventory();
    }
    MAIN_BetweenTrade plugin;
    public HANDLE_Trade(MAIN_BetweenTrade plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onClickAtTradeInventory(InventoryClickEvent event) {
        if (event.getInventory().getTitle().equals("Player To Player Trade")) {
            event.setCancelled(true);

            final Player player = (Player) event.getWhoClicked();
            final String player$name = player.getName();
            // TODO CHECK
            System.out.println("onClickAtTradeInventory " + player + " " + player$name);
            System.out.println("onClickAtTradeInventory " + invTrade.getInventory().getTitle());
            System.out.println("onClickAtTradeInventory " + invTrade.getPlayerMe() + " " + TRADE.get(TRADE_key).getPlayerYou());


            if (event.getRawSlot() == 53) {
                ItemStack Button = event.getCurrentItem();
                String Button$name = Button.getItemMeta().getDisplayName();

                if (Button$name.equals("거래 잠그기")) {
                    // TODO
                    for(int i = 0; i < 3; i++) {
                        int finalI = i;
                        Bukkit.getScheduler().runTaskLater(plugin, () -> {
                            invTrade.getInventory().setItem(
                                    invTrade.getPlayerMe().getName().equals(player$name) ? lineLeft[finalI] : lineRight[finalI],
                                    new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 12)
                            );
                            if (finalI == 3) {
                                if (invTrade.getPlayerMe().getName().equals(player$name)) {
                                    invTrade.setMeLock(true);
                                } else {
                                    invTrade.setYouLock(true);
                                }
                                if (invTrade.isMeLock() && invTrade.isYouLock()) {
                                    changeLine(12, event, true);
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
            
            if (Arrays.stream(new int[] {4, 13, 22, 31, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53}).anyMatch(slot -> slot == event.getSlot()) ||
                event.getCurrentItem().getType() == Material.AIR)
                return;

            switch(event.getClickedInventory().getType()) {
                case CHEST:
                    resetToTrade();
                    for (int i = 0; i <= 35; i++) {
                        if (event.getWhoClicked().getInventory().getContents()[i] == null &&
                                IntStream.of(invTrade.getPlayerMe().getName().equals(player$name) ? playerMeSlot : playerYouSlot)
                                        .anyMatch(slot -> slot == event.getSlot())) {
                            event.getWhoClicked().getInventory().setItem(i, event.getCurrentItem());
                            invTrade.getInventory().setItem(event.getSlot(), new ItemStack(Material.AIR));
                        }
                    }
                    break;
                case PLAYER:
                    resetToTrade();
                    int[] slot = invTrade.getPlayerMe().getName().equals(player$name) ? playerMeSlot : playerYouSlot;
                    for (int i = 0; i < slot.length; i++) {
                        if (event.getInventory().getContents()[slot[i]] == null) {
                            invTrade.getInventory().setItem(slot[i], event.getCurrentItem());
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
                invTrade.getInventory().setItem(
                        lineCenter[finalI],
                        new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) color)
                );
                if (finalI + 1 == lineCenter.length) {
                    if (state) {
                        invTrade.getInventory().setItem(53, new INIT_Button(2).ButtonLock());
                    } else {
                    // TODO
                    }
                }
            }, (i + 1) * 7);
        }
    }

    private void resetToTrade() {
        Arrays.fill(invTrade.checks, false);
        Bukkit.getScheduler().cancelTasks(plugin);
        int[] line = new int[] { 40, 31, 22, 13 ,4 ,36, 37, 38, 44, 43, 42 };
        for(int i = 0; i < line.length; i++) {
            invTrade.getInventory().setItem(line[i], new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 1));
        }
        invTrade.getInventory().setItem(53, new INIT_Button(1).ButtonLock());
    }
    private void successToTrade(InventoryClickEvent event) {
        ItemStack line$0 = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 4);
        ItemStack line$1 = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 1);
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            resetToTrade();
            for(int i = 0; i < 3; i++) {
                invTrade.getInventory().setItem(lineRight[i], line$0);
                invTrade.getInventory().setItem(lineLeft[i], line$0);
            }

            for(int i = 2; i >= 0; i--) {
                int finalI = i;
                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                    invTrade.getInventory().setItem(lineRight[finalI], line$1);
                    invTrade.getInventory().setItem(lineRight[finalI], line$1);
                }, 5 + (i * 5));
            }

            ItemStack[] toPlayer = event.getInventory().getContents();
            ItemStack[] temp = new ItemStack[16];
            for(int i = 0; i < 16; i++) {
                temp[i] = toPlayer[playerMeSlot[i]];
                toPlayer[playerMeSlot[i]] = toPlayer[playerYouSlot[i]];
                toPlayer[playerYouSlot[i]] = temp[i];

                invTrade.getInventory().setItem(playerMeSlot[i], toPlayer[playerMeSlot[i]]);
                invTrade.getInventory().setItem(playerYouSlot[i], toPlayer[playerYouSlot[i]]);
            }
        }, 3L);
    }

    private Inventory registerInventory(String playerMe, String playerYou) {
        Inventory inventory;
        inventory = Bukkit.createInventory(null, 9 * 6, "Player To Player Trade");
        ItemStack line = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 1);

        for(int i = 0; i < 9; i++) {
            inventory.setItem(9 * 4 + i, line);
        }
        int count = 4;
        for(int i = 0; i < 4; i++) {
            inventory.setItem(count + (i * 9), line);
        }

        inventory.setItem(39, INIT_Player.PLAYER_SKULL.get(playerMe));
        inventory.setItem(41, INIT_Player.PLAYER_SKULL.get(playerYou));

        inventory.setItem(53, new INIT_Button(1).ButtonLock());
        inventory.setItem(45, new INIT_Button(3).ButtonLock());
        return inventory;
    }
}
