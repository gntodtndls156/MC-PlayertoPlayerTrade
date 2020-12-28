package github.gntodtndls156.nextlife.betweentrade.inventories;

import github.gntodtndls156.nextlife.betweentrade.BetweenTradeMain;
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

public class HandlerInvTrade implements Listener {
    // TODO - InvTrade action
    public static Map<String, InvTrade> INVENTORY = new HashMap<>();
    final int[] playerMeSlot = new int[]{0, 1, 2, 3, 9, 10, 11, 12, 18, 19, 20, 21, 27, 28, 29, 30};
    final int[] playerYouSlot = new int[]{5, 6, 7, 8, 14, 15, 16, 17, 23, 24, 25, 26, 32, 23, 24, 35};
    final int[] lineLeft = new int[]{36, 37, 38};
    final int[] lineRight = new int[]{44, 43, 42};
    final int[] lineCenter = new int[]{40, 31, 22, 13, 4};
    InvTrade invTrade = new InvTrade();

    BetweenTradeMain plugin;
    public HandlerInvTrade(BetweenTradeMain plugin) {
        this.plugin = plugin;
    }

    public HandlerInvTrade(String playerMe, String playerYou) {
        System.out.println(playerMe +" "+ playerYou);
        System.out.println(getPlayerMe() +" "+ getPlayerYou());
        this.invTrade = new InvTrade(Bukkit.getServer().getPlayer(playerMe), Bukkit.getServer().getPlayer(playerYou));
        System.out.println(invTrade.getPlayerMe() + " " + invTrade.getPlayerYou());
        INVENTORY.put(getPlayerMe().getName() + getPlayerYou().getName(), invTrade);
        System.out.println(INVENTORY.get(getPlayerMe().getName() + getPlayerYou().getName()).getPlayerMe() +" "+ INVENTORY.get(getPlayerMe().getName() + getPlayerYou().getName()).getPlayerYou());
        INVENTORY.get(getPlayerMe().getName() + getPlayerYou().getName()).openInventory(invTrade.registerInventory());
    }

    // EVENTS
    @EventHandler
    public void onClickAtTradeInventory(InventoryClickEvent event) {
        if (event.getInventory().getTitle().equals("Player To Player Trade")) {
            event.setCancelled(true);
            if (event.isShiftClick()) {
                return;
            }
            String player = event.getWhoClicked().getName();
            if (event.getSlot() == 53) {
                String Button = event.getCurrentItem().getItemMeta().getDisplayName();
                if (player != null) {
                    if (Button.equals("거래 잠그기")) {
                        tradeLock(12, event, player);
                    } else if (Button.equals("거래 수락하기")) {
                        tradeAccept(15, event, player);
                    } else if (Button.equals("돈 거래")) {
                        // event.getWhoClicked().openInventory(moneyInventory);
                    }
                }
            }

            int[] canNotClick = new int[]{4, 13, 22, 31, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53};
            if (Arrays.stream(canNotClick).anyMatch(n -> n == event.getSlot()) || event.getCurrentItem().getType() == Material.AIR) {
                return;
            }

            switch (event.getClickedInventory().getType()) {
                case CHEST:
                    getPlayerInventorySlotNullCheck(event, getPlayerMe().getName().equals(player) ? playerMeSlot : playerYouSlot);
                    break;
                case PLAYER:
                    getTradeInventorySlotNullCheck(event, getPlayerMe().getName().equals(player) ? playerMeSlot : playerYouSlot);
                    break;
            }
        }
    }
    private void getPlayerInventorySlotNullCheck(InventoryClickEvent event, int[] Slot) {
        for(int i = 0; i <= 35; i++) {
            if (event.getWhoClicked().getInventory().getContents()[i] == null && IntStream.of(Slot).anyMatch(n -> event.getSlot() == n)) {
                event.getWhoClicked().getInventory().setItem(i, event.getCurrentItem());
                INVENTORY.get(getPlayerMe().getName() + getPlayerYou().getName()).getInventory().setItem(event.getSlot(), new ItemStack(Material.AIR));
                break;
            }
        }
    }
    private void getTradeInventorySlotNullCheck(InventoryClickEvent event, int[] playerSlot) {
        for(int i = 0; i < playerSlot.length; i++) {
            if (event.getInventory().getContents()[playerSlot[i]] == null) {
                INVENTORY.get(getPlayerMe().getName() + getPlayerYou().getName()).getInventory().setItem(playerSlot[i], event.getCurrentItem());
                event.getWhoClicked().getInventory().setItem(event.getSlot(), new ItemStack(Material.AIR));
            }
        }
    }
    private void tradeAccept(int color, InventoryClickEvent event, String player) {
        if (event.getCurrentItem().getItemMeta().getDisplayName().equals("거래 수락하기")) {
            for (int i = 0; i < 3; i++) {
                int finalI = i;
                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                    INVENTORY.get(getPlayerMe().getName() + getPlayerYou().getName()).getInventory().setItem(
                            getPlayerMe().getName().equals(player) ? lineLeft[finalI] : lineRight[finalI],
                            new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) color)
                    );
                    if (finalI == 3) {
                        if (getPlayerMe().getName().equals(player)) {
                            INVENTORY.get(getPlayerMe().getName() + getPlayerYou().getName()).setMeAccept(true);
                        } else {
                            INVENTORY.get(getPlayerMe().getName() + getPlayerYou().getName()).setYouAccept(true);
                        }
                        if (INVENTORY.get(getPlayerMe().getName() + getPlayerYou().getName()).isMeAccept() && INVENTORY.get(getPlayerMe().getName() + getPlayerYou().getName()).isYouAccept()) {
                            changeLine(color, event, false);
                        }
                    }
                }, (i + 1) * 7L) ;
            }
        }
    }
    private void tradeLock(int color, InventoryClickEvent event, String player) {
        System.out.println(player);
        System.out.println(getPlayerMe());
        System.out.println(getPlayerYou());
        System.out.println(INVENTORY.get(getPlayerMe().getName() + getPlayerYou().getName()).getPlayerMe());
        System.out.println(INVENTORY.get(getPlayerMe().getName() + getPlayerYou().getName()).getPlayerYou());
        for (int i = 0; i < 3; i++) {
            int finalI = i;
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                INVENTORY.get(getPlayerMe().getName() + getPlayerYou().getName()).getInventory().setItem(
                        getPlayerMe().getName().equals(player) ? lineLeft[finalI] : lineRight[finalI],
                        new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) color)
                );
                if (finalI == 3) {
                    if (getPlayerMe().getName().equals(player)) {
                        INVENTORY.get(getPlayerMe().getName() + getPlayerYou().getName()).setMeLock(true);
                    } else {
                        INVENTORY.get(getPlayerYou().getName() + getPlayerYou().getName()).setYouLock(true);
                    }

                    if (INVENTORY.get(getPlayerMe().getName() + getPlayerYou().getName()).isMeLock() && INVENTORY.get(getPlayerMe().getName() + getPlayerYou().getName()).isYouLock()) {
                        changeLine(color, event, true);

                    }
                }
            }, (1 + i) * 7L);
        }
    }
    private void changeLine(int color, InventoryClickEvent event, boolean state) {
        for(int i = 0; i < lineCenter.length; i++) {
            int finalI = i;
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                INVENTORY.get(getPlayerMe().getName() + getPlayerYou().getName()).getInventory().setItem(
                        lineCenter[finalI],
                        new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) color)
                );
                if (finalI + 1 == lineCenter.length) {
                    if (state)
                        INVENTORY.get(getPlayerMe().getName() + getPlayerYou().getName()).getInventory().setItem(53, invTrade.ButtonLock(2));
                    else
                        inventoryTradeSuccess(event);
                }
            }, (i + 1) * 7L);
        }
    }
    private void inventoryReset() {
        Arrays.fill(INVENTORY.get(getPlayerMe().getName() + getPlayerYou().getName()).checks, false);
        Bukkit.getScheduler().cancelTasks(plugin); // Bukkit.getScheduler().cancelTask(1);
        int[] line = new int[] { 40, 31, 22, 13 ,4 ,36, 37, 38, 44, 43, 42 };
        for(int i = 0; i < line.length; i++)
            INVENTORY.get(getPlayerMe().getName() + getPlayerYou().getName()).getInventory().setItem(line[i], new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 1));
        INVENTORY.get(getPlayerMe().getName() + getPlayerYou().getName()).getInventory().setItem(53, invTrade.ButtonLock(1));
    }
    private void inventoryTradeSuccess(InventoryClickEvent event) {
        final int[] lineLeft = new int[]{42, 43, 44};
        final int[] lineRight = new int[]{38, 37, 36};
        ItemStack line = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 4);
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            inventoryReset();
            for(int i = 0; i < 3; i++) {
                INVENTORY.get(getPlayerMe().getName() + getPlayerYou().getName()).getInventory().setItem(lineLeft[i], line);
                INVENTORY.get(getPlayerMe().getName() + getPlayerYou().getName()).getInventory().setItem(lineRight[i], line);
            }
            for(int i = 0; i < 3; i++) {
                int finalI = i;
                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                    INVENTORY.get(getPlayerMe().getName() + getPlayerYou().getName()).getInventory().setItem(lineLeft[finalI], line);
                    INVENTORY.get(getPlayerMe().getName() + getPlayerYou().getName()).getInventory().setItem(lineRight[finalI], line);
                }, (1 + i) * 7L);
            }
            INVENTORY.get(getPlayerMe().getName() + getPlayerYou().getName()).getInventory().setItem(53, invTrade.ButtonLock(1));

            ItemStack[] toPlayer = event.getInventory().getContents();
            ItemStack[] temp = new ItemStack[16];
            for(int i = 0; i < 16; i++) {
                temp[i] = toPlayer[playerMeSlot[i]];
                toPlayer[playerMeSlot[i]] = toPlayer[playerYouSlot[i]];
                toPlayer[playerYouSlot[i]] = temp[i];

                INVENTORY.get(getPlayerMe().getName() + getPlayerYou().getName()).getInventory().setItem(playerYouSlot[i], toPlayer[playerYouSlot[i]]);
                INVENTORY.get(getPlayerMe().getName() + getPlayerYou().getName()).getInventory().setItem(playerMeSlot[i], toPlayer[playerMeSlot[i]]);
            }
        }, 3L);
    }



    public Player getPlayerMe() {
        return invTrade.getPlayerMe();
    }

    public Player getPlayerYou() {
        return invTrade.getPlayerYou();
    }
}
