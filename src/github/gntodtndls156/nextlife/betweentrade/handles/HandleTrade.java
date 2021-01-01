package github.gntodtndls156.nextlife.betweentrade.handles;

import github.gntodtndls156.nextlife.betweentrade.MainBetweenTrade;
import github.gntodtndls156.nextlife.betweentrade.inits.InitButton;
import github.gntodtndls156.nextlife.betweentrade.inventories.InvMoney;
import github.gntodtndls156.nextlife.betweentrade.inventories.InvTrade;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class HandleTrade implements Listener {
    private static Map<String, InvTrade> TRADE = new HashMap<>();
    private static Map<String, String> TRADE_KEY = new HashMap<>();

    final int[] playerMeSlot = new int[]{0, 1, 2, 3, 9, 10, 11, 12, 18, 19, 20, 21, 27, 28, 29, 30};
    final int[] playerYouSlot = new int[]{5, 6, 7, 8, 14, 15, 16, 17, 23, 24, 25, 26, 32, 23, 24, 35};
    final int[] lineLeft = new int[]{36, 37, 38};
    final int[] lineRight = new int[]{44, 43, 42};
    final int[] lineCenter = new int[]{40, 31, 22, 13, 4};

    private String TRADE_KEY$get;
    public HandleTrade(String playerMe, String playerYou) {
        TRADE_KEY.put(playerMe, playerMe + playerYou);
        TRADE_KEY.put(playerYou, playerMe + playerYou);

        TRADE.put(playerMe + playerYou, new InvTrade(Bukkit.getPlayer(playerMe), Bukkit.getPlayer(playerYou)));
    }

    @EventHandler
    public void onClickAtTradeInventory(InventoryClickEvent event) {
        if (event.getInventory().getTitle().equals("Player To Player Trade")) {
            event.setCancelled(true);

            final Player player = (Player) event.getWhoClicked();
            final String player$name = player.getName();
            this.TRADE_KEY$get = TRADE_KEY.get(player$name);

            if (event.getRawSlot() == 53) {
                ItemStack Button = event.getCurrentItem();
                String Button$name = Button.getItemMeta().getDisplayName();

                switch(Button$name) {
                    case "거래 잠그기":
                        for (int i = 0; i < 3; i++) {
                            int finalI = i;
                            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                                TRADE.get(TRADE_KEY$get).getInventory().setItem(
                                        TRADE.get(TRADE_KEY$get).getPlayerMe().getName().equals(player$name) ? lineLeft[finalI] : lineRight[finalI],
                                        new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 12)
                                );
                                if (finalI == 2) {
                                    if (TRADE.get(TRADE_KEY$get).getPlayerMe().getName().equals(player$name)) {
                                        TRADE.get(TRADE_KEY$get).setMeLock(true);
                                    } else {
                                        TRADE.get(TRADE_KEY$get).setYouLock(true);
                                    }

                                    if (TRADE.get(TRADE_KEY$get).isMeLock() && TRADE.get(TRADE_KEY$get).isYouLock()) {
                                        // TODO
                                        changeLine(12, event, true);
                                    }
                                }
                            }, (1 + i) * 7L);
                        }
                        break;
                    case "거래 수락하기":
                        // TODO
                        for (int i = 0; i < 3; i++) {
                            int finalI = i;
                            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                                TRADE.get(TRADE_KEY$get).getInventory().setItem(
                                        TRADE.get(TRADE_KEY$get).getPlayerMe().getName().equals(player$name) ? lineLeft[finalI] : lineRight[finalI],
                                        new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 15)
                                );
                                if (finalI == 2) {
                                    if (TRADE.get(TRADE_KEY$get).getPlayerMe().getName().equals(player$name)) {
                                        TRADE.get(TRADE_KEY$get).setMeAccept(true);
                                    } else {
                                        TRADE.get(TRADE_KEY$get).setYouAccept(true);
                                    }

                                    if (TRADE.get(TRADE_KEY$get).isMeAccept() && TRADE.get(TRADE_KEY$get).isYouAccept()) {
                                        changeLine(15, event, false);
                                    }
                                }
                            }, (i + 1) * 7L);
                        }
                }
            }

            if (event.getRawSlot() == 45) {
                if (event.getCurrentItem().getItemMeta().getDisplayName().equals("돈 거래")) {
                    player.openInventory(new InvMoney().getInventory());
                }
            }

            if (event.getCurrentItem().getType() == Material.AIR ||
                    Arrays.stream(new int[]{4, 13, 22, 31, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53}).anyMatch(slot -> slot == event.getRawSlot()) ||
                    event.getInventory().getType() == null) {
                return;
            }

            switch(event.getClickedInventory().getType()) {
                case CHEST:
                    // TODO
                    resetToTrade();
                    for (int i = 0; i <= 35; i++) {
                        if (event.getWhoClicked().getInventory().getContents()[i] == null && //
                                IntStream.of(TRADE.get(TRADE_KEY$get).getPlayerMe().getName().equals(player$name) ? playerMeSlot : playerYouSlot).anyMatch(slot -> slot == event.getSlot())) {
                            event.getWhoClicked().getInventory().setItem(i, event.getCurrentItem());
                            TRADE.get(TRADE_KEY$get).getInventory().setItem(event.getSlot(), new ItemStack(Material.AIR));
                        }
                    }
                    break;
                case PLAYER:
                    // TODO
                    resetToTrade();
                    final int[] slot = TRADE.get(TRADE_KEY$get).getPlayerMe().getName().equals(player$name) ? playerMeSlot : playerYouSlot;
                    for (int i = 0; i < 16; i++) {
                        if (event.getInventory().getContents()[slot[i]] == null) {
                            TRADE.get(TRADE_KEY$get).getInventory().setItem(slot[i], event.getCurrentItem());
                            event.getWhoClicked().getInventory().setItem(event.getSlot(), new ItemStack(Material.AIR));
                        }
                    }
            }
        }
    }

    private void changeLine(int color, InventoryClickEvent event, boolean state) {
        for (int i = 0; i < lineCenter.length; i++) {
            int finalI = i;
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                TRADE.get(TRADE_KEY$get).getInventory().setItem(
                        lineCenter[finalI],
                        new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) color)
                );
                if (finalI + 1 == lineCenter.length) {
                    if (state) {
                        TRADE.get(TRADE_KEY$get).getInventory().setItem(53, new InitButton(2).ButtonLock());
                    } else {
                        // TODO
                        successToTrade(event);
                    }
                }
            }, (i + 1) * 7L);
        }
    }

    private void resetToTrade() {
        Arrays.fill(TRADE.get(TRADE_KEY$get).getChecks(), false);
        Bukkit.getScheduler().cancelTasks(plugin);
        int[] slot = new int[]{ 40, 31, 22, 13 ,4 ,36, 37, 38, 44, 43, 42 };
        for (int i = 0; i < slot.length; i++) {
            TRADE.get(TRADE_KEY$get).getInventory().setItem(slot[i], new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 1));
        }
        TRADE.get(TRADE_KEY$get).getInventory().setItem(53, new InitButton(1).ButtonLock());
    }

    private void successToTrade(InventoryClickEvent event) {
        ItemStack line$0 = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 4);
        ItemStack line$1 = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 1);

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            resetToTrade();
            for (int i = 0; i < 3; i++) {
                TRADE.get(TRADE_KEY$get).getInventory().setItem(lineRight[i], line$0);
                TRADE.get(TRADE_KEY$get).getInventory().setItem(lineLeft[i], line$0);
            }

            for (int i = 2; i >= 0; i--) {
                int finalI = i;
                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                    TRADE.get(TRADE_KEY$get).getInventory().setItem(lineRight[finalI], line$1);
                    TRADE.get(TRADE_KEY$get).getInventory().setItem(lineLeft[finalI], line$1);
                }, 5L + (i * 5));
            }

            ItemStack[] toPlayer = event.getInventory().getContents();
            ItemStack[] temp = new ItemStack[16];
            for(int i = 0; i < 16; i++) {
                temp[i] = toPlayer[playerMeSlot[i]];
                toPlayer[playerMeSlot[i]] = toPlayer[playerYouSlot[i]];
                toPlayer[playerYouSlot[i]] = temp[i];

                TRADE.get(TRADE_KEY$get).getInventory().setItem(playerMeSlot[i], toPlayer[playerMeSlot[i]]);
                TRADE.get(TRADE_KEY$get).getInventory().setItem(playerYouSlot[i], toPlayer[playerYouSlot[i]]);
            }
        }, 3L);
    }

    private MainBetweenTrade plugin;
    public HandleTrade(MainBetweenTrade plugin) {
        this.plugin = plugin;
    }
}
