package github.gntodtndls156.nextlife.betweentrade.handle;

import github.gntodtndls156.nextlife.betweentrade.Main;
import github.gntodtndls156.nextlife.betweentrade.init.InitButton;
import github.gntodtndls156.nextlife.betweentrade.init.InitGetLang;
import github.gntodtndls156.nextlife.betweentrade.inv.InvTrade;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.IntStream;

public class HandleTrade implements Listener {
    public static Map<String, InvTrade> TRADE = new HashMap<>();
    public static Map<Player, String> TRADE_KEY = new HashMap<>();

    boolean ShiftClickState = false;
    private InvTrade invTrade;
    private Main plugin;
    public HandleTrade(Main plugin) {
        this.plugin = plugin;
    }

    public HandleTrade(Player player$0, Player player$1) {
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분 ss초");
        final String KEY = simpleDateFormat.format(new Date());

        TRADE_KEY.put(player$0, KEY);
        TRADE_KEY.put(player$1, KEY);
        invTrade = new InvTrade(player$0, player$1);
        TRADE.put(KEY, invTrade);
    }

    @EventHandler
    public void onOpenTradeInventory(InventoryOpenEvent event) {
        if (event.getInventory().getTitle().equals("Player To Player Trade")) {
            Player player = (Player) event.getPlayer();
            Inventory inventory = event.getInventory();

            Bukkit.getScheduler().runTask(plugin, () -> {
                if (TRADE.get(TRADE_KEY.get(player)).isPlayer$0Equals(player)) {
                    TRADE.get(TRADE_KEY.get(player)).setMeGotoMoney(false);
                } else {
                    TRADE.get(TRADE_KEY.get(player)).setYouGotoMoney(false);
                }
            });
            Bukkit.getScheduler().runTask(plugin, () -> inventory.setItem(TRADE.get(TRADE_KEY.get(player)).isPlayer$0Equals(player) ? 39 : 41, new ItemStack() {
                public ItemStack getSkull() {
                    ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
                    SkullMeta meta = (SkullMeta) item.getItemMeta();
                    meta.setOwningPlayer(player);
                    item.setItemMeta(meta);

                    return item;
                }
            }.getSkull()));
        }
    }

    public void moveItem(InvTrade invTrade) {
        // move Trade to player inventory
        List<ItemStack> items$0 = new ArrayList<>();
        List<ItemStack> items$1 = new ArrayList<>();
        ItemStack[] inventoryItems = invTrade.getInventory().getContents();
        for (int i = 0; i < 16; i++) {
            if (inventoryItems[invTrade.getPlayerMeSlot()[i]] != null) {
                if (inventoryItems[invTrade.getPlayerMeSlot()[i]].getType() == Material.GOLD_NUGGET) {
                    plugin.getEconomy().depositPlayer(invTrade.getPlayer$0(), Integer.parseInt(inventoryItems[invTrade.getPlayerMeSlot()[i]].getItemMeta().getDisplayName().replaceAll("§[0-9]", "").replaceAll("[^0-9]", "")));
                } else {
                    items$0.add(inventoryItems[invTrade.getPlayerMeSlot()[i]]);
                }
                invTrade.getInventory().setItem(invTrade.getPlayerMeSlot()[i], new ItemStack(Material.AIR));
            }
            if (inventoryItems[invTrade.getPlayerYouSlot()[i]] != null) {
                if (inventoryItems[invTrade.getPlayerYouSlot()[i]].getType() == Material.GOLD_NUGGET) {
                    plugin.getEconomy().depositPlayer(invTrade.getPlayer$1(), Integer.parseInt(inventoryItems[invTrade.getPlayerYouSlot()[i]].getItemMeta().getDisplayName().replaceAll("§[0-9]", "").replaceAll("[^0-9]", "")));
                } else {
                    items$1.add(inventoryItems[invTrade.getPlayerYouSlot()[i]]);
                }
                invTrade.getInventory().setItem(invTrade.getPlayerYouSlot()[i], new ItemStack(Material.AIR));
            }
        }
        int j = 0, k = 0;
        Iterator<ItemStack> iterator$0 = items$0.listIterator();
        Iterator<ItemStack> iterator$1 = items$1.listIterator();
        for (int i = 0; i <= 35; i++) {
            if (invTrade.getPlayer$0().getInventory().getContents()[i] == null && iterator$0.hasNext()) {
                invTrade.getPlayer$0().getInventory().setItem(i, iterator$0.next());
                j++;
            }
            if (invTrade.getPlayer$1().getInventory().getContents()[i] == null && iterator$1.hasNext()) {
                invTrade.getPlayer$1().getInventory().setItem(i, iterator$1.next());
                k++;
            }
        }
    }

    @EventHandler
    public void onCloseTradeInventory(InventoryCloseEvent event) {
        if (event.getInventory().getTitle().equals("Player To Player Trade")) {
            if (ShiftClickState) {
                ShiftClickState = false;
                return;
            }
            Player player = (Player) event.getPlayer();
            InvTrade invTrade = TRADE.get(TRADE_KEY.get(player));
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                if (invTrade.isPlayer$0Equals(player)) {
                    if (invTrade.isMeGotoMoney()) {
                        return;
                    }
                } else {
                    if (invTrade.isYouGotoMoney()) {
                        return;
                    }
                }
                if (invTrade.isCloseState()) {
                    invTrade.setCloseState(false);
                    closingFalseViewState(player, invTrade);
                }
                invTrade.getPlayer$1().closeInventory();
                invTrade.getPlayer$0().closeInventory();

                moveItem(invTrade);

                TRADE.remove(TRADE_KEY.get(player));
                TRADE_KEY.remove(invTrade.getPlayer$0());
                TRADE_KEY.remove(invTrade.getPlayer$1());
            }, 5L);
        }
    }

    private void closingFalseViewState(Player player, InvTrade invTrade) {
        try {
            HandleMoney.MONEY.get(invTrade.getPlayer$0()).setViewState(false);
        } catch (NullPointerException exception) {
            // TODO
        }
        try {
            HandleMoney.MONEY.get(invTrade.getPlayer$1()).setViewState(false);
        } catch (NullPointerException exception) {
            // TODO
        }

        if (invTrade.isPlayer$0Equals(player)) {
            invTrade.getPlayer$1().sendMessage("상대방이 거래 종료했습니다.");
        } else {
            invTrade.getPlayer$0().sendMessage("상대방이 거래 종료했습니다.");
        }
    }

    @EventHandler
    public void onClickAtTradeInventory(InventoryClickEvent event) {
        if (event.getInventory().getTitle().equals("Player To Player Trade")) {
            event.setCancelled(true);

            Player player = (Player) event.getWhoClicked();
            invTrade = TRADE.get(TRADE_KEY.get(player));
            if (event.isShiftClick()) {
                ShiftClickState = true;
                player.openInventory(invTrade.getInventory());
                return;
            }

            if (event.getRawSlot() == 53) {
                ItemStack Button = event.getCurrentItem();
                String Button$name = Button.getItemMeta().getDisplayName();

                if (Button$name.equals(InitGetLang.LANG.get("Lock"))) {
                    for (int i = 0; i < 3; i++) {
                        int finalI = i;
                        Bukkit.getScheduler().runTaskLater(plugin, () -> {
                            invTrade.getInventory().setItem(
                                    invTrade.isPlayer$0Equals(player) ? invTrade.getLineLeft()[finalI] : invTrade.getLineRight()[finalI],
                                    invTrade.line(12)
                            );
                            if (finalI == 2) {
                                if (invTrade.isPlayer$0Equals(player)) {
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
                } else if (Button$name.equals(InitGetLang.LANG.get("Accept"))) {
                    for (int i = 0; i < 3; i++) {
                        int finalI = i;
                        Bukkit.getScheduler().runTaskLater(plugin, () -> {
                            invTrade.getInventory().setItem(
                                    invTrade.isPlayer$0Equals(player) ? invTrade.getLineLeft()[finalI] : invTrade.getLineRight()[finalI],
                                    invTrade.line(15)
                            );
                            if (finalI == 2) {
                                if (invTrade.isPlayer$0Equals(player)) {
                                    invTrade.setMeAccept(true);
                                } else {
                                    invTrade.setYouAccept(true);
                                }

                                if (invTrade.isMeAccept() && invTrade.isYouAccept()) {
                                    changeLine(15, event, false);
                                }
                            }
                        }, (i + 1) * 7L);
                    }
                }
            }

            if (event.getRawSlot() == 45) {
                if (event.getCurrentItem().getItemMeta().getDisplayName().equals(InitGetLang.LANG.get("Money"))) {
                    int money = (int) plugin.getEconomy().getBalance(player);
                    if (money <= 0) {
                        player.sendMessage("잔고가 비어있습니다.");
                        return;
                    }
                    new HandleMoney(player, money);
                }
            }

            if (event.getRawSlot() == 47) {
                if (event.getCurrentItem().getItemMeta().getDisplayName().equals(InitGetLang.LANG.get("Close"))) {
                    if (invTrade.isCloseState()) {
                        invTrade.setCloseState(false);
                        closingFalseViewState(player, invTrade);
                    }

                    invTrade.getPlayer$0().closeInventory();
                    invTrade.getPlayer$1().closeInventory();
                    TRADE.remove(TRADE_KEY.get(player));
                    TRADE_KEY.remove(invTrade.getPlayer$0());
                    TRADE_KEY.remove(invTrade.getPlayer$1());
                }
            }

            if (event.getCurrentItem().getType() == Material.AIR ||
                    Arrays.stream(new int[]{4, 13, 22, 31, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53}).anyMatch(slot -> slot == event.getRawSlot())) {
                return;
            }

            switch(event.getClickedInventory().getType()) {
                case CHEST:
                    for (int i = 0; i < 35; i++) {
                        if (player.getInventory().getContents()[i] == null &&
                                IntStream.of(invTrade.isPlayer$0Equals(player) ? invTrade.getPlayerMeSlot() : invTrade.getPlayerYouSlot()).anyMatch(slot -> slot == event.getSlot())) {
                            reset();
                            if (event.getCurrentItem().getType() == Material.GOLD_NUGGET) {
                                plugin.getEconomy().depositPlayer(player, Integer.parseInt(event.getCurrentItem().getItemMeta().getDisplayName().replaceAll("§[0-9]", "").replaceAll("[^0-9]", "")));
                                invTrade.getInventory().setItem(event.getSlot(), new ItemStack(Material.AIR));
                                break;
                            }
                            player.getInventory().setItem(i, event.getCurrentItem());
                            invTrade.getInventory().setItem(event.getSlot(), new ItemStack(Material.AIR));
                        }
                    }
                    break;
                case PLAYER:
                    final int[] slot = invTrade.isPlayer$0Equals(player) ? invTrade.getPlayerMeSlot() : invTrade.getPlayerYouSlot();
                    for (int i = 0; i < 16; i++) {
                        if (invTrade.getInventory().getContents()[slot[i]] == null) {
                            reset();
                            invTrade.getInventory().setItem(slot[i], event.getCurrentItem());
                            player.getInventory().setItem(event.getSlot(), new ItemStack(Material.AIR));
                        }
                    }
            }
        }
    }
    public void changeLine(int color, InventoryClickEvent event, boolean state) {
        for (int i = 0; i < invTrade.getLineCenter().length; i++) {
            int finalI = i;
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                invTrade.getInventory().setItem(
                        invTrade.getLineCenter()[finalI],
                        invTrade.line(color)
                );
                if (finalI + 1 == invTrade.getLineCenter().length) {
                    if (state) {
                        invTrade.getInventory().setItem(53, new InitButton(2).ButtonLock());
                    } else {
                        success(event);
                    }
                }
            }, (i + 1) * 7L);
        }
    }

    private void reset() {
        Arrays.fill(invTrade.getChecks(), false);
        Bukkit.getScheduler().cancelTasks(plugin);
        for (int i = 0; i < 11; i++)
            invTrade.getInventory().setItem(new int[] { 40, 31, 22, 13 ,4 ,36, 37, 38, 44, 43, 42 }[i], invTrade.line(1));
        invTrade.getInventory().setItem(53, new InitButton(1).ButtonLock());

    }
    private void success(InventoryClickEvent event) {
        ItemStack line$0 = invTrade.line(4);
        ItemStack line$1 = invTrade.line(1);

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            reset();
            for (int i = 0; i< 3; i++) {
                invTrade.getInventory().setItem(invTrade.getLineRight()[i], line$0);
                invTrade.getInventory().setItem(invTrade.getLineLeft()[i], line$0);
            }

            for (int i = 2; i >= 0; i--) {
                int finalI = i;
                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                    invTrade.getInventory().setItem(invTrade.getLineRight()[finalI], line$1);
                    invTrade.getInventory().setItem(invTrade.getLineLeft()[finalI], line$1);
                }, 5L + (i * 5));
            }

            ItemStack[] itemStacks = event.getInventory().getContents();
            ItemStack[] temp = new ItemStack[16];

            for (int i = 0; i < 16; i++) {
                temp[i] = itemStacks[invTrade.getPlayerMeSlot()[i]];
                itemStacks[invTrade.getPlayerMeSlot()[i]] = itemStacks[invTrade.getPlayerYouSlot()[i]];
                itemStacks[invTrade.getPlayerYouSlot()[i]] = temp[i];

                invTrade.getInventory().setItem(invTrade.getPlayerMeSlot()[i], itemStacks[invTrade.getPlayerMeSlot()[i]]);
                invTrade.getInventory().setItem(invTrade.getPlayerYouSlot()[i], itemStacks[invTrade.getPlayerYouSlot()[i]]);
            }
        }, 3L);
    }
}
