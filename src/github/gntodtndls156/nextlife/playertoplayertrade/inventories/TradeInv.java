package github.gntodtndls156.nextlife.playertoplayertrade.inventories;

import github.gntodtndls156.nextlife.playertoplayertrade.PlayerToPlayerTrade;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.*;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.Wool;
import org.bukkit.scheduler.BukkitTask;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.stream.IntStream;


public class TradeInv implements Listener {
    static Map<String, Inventory> INVES = new HashMap<>();
    static Map<String, ItemStack> SKULL = new HashMap<>();
    Player player1, player2;
    boolean isPlayer1State1 = false, isPlayer1State2 = false;
    boolean isPlayer2State1 = false, isPlayer2State2 = false;
    final int[] player1Slot = new int[]{0, 1, 2, 3, 9, 10, 11, 12, 18, 19, 20, 21, 27, 28, 29, 30};
    final int[] player2Slot = new int[]{5, 6, 7, 8, 14, 15, 16, 17, 23, 24, 25, 26, 32, 23, 24, 35};

    public TradeInv() {
    }

    public TradeInv(String player1, String player2) {
        System.out.println("TradeInv " + player1 + " " + player2);
        openInventory(Bukkit.getPlayer(player1), Bukkit.getPlayer(player2));
    }

    PlayerToPlayerTrade plugin;

    public TradeInv(PlayerToPlayerTrade plugin) {
        this.plugin = plugin;
    }

    private void openInventory(Player player1, Player player2) {
        Inventory inventory = putInventory(player1, player2);
        // Inventory inventory = INVES.get(player1.getName() + " TO " + player2.getName());
        player1.openInventory(inventory);
        player2.openInventory(inventory);
    }

    // API - register Inventory Trade
    private Inventory putInventory(Player player1, Player player2) {
        INVES.put(player1.getName() + " TO " + player2.getName(), registerTradeInventory(player1, player2));
        return INVES.get(player1.getName() + " TO " + player2.getName());
    }

    public Inventory getInventory(Player player1, Player player2) {
        return INVES.get(player1.getName() + " TO " + player2.getName());
    }

    // API - register Inventory Base
    private Inventory registerTradeInventory(Player player1, Player player2) {
        Inventory inventory = Bukkit.createInventory(null, 9 * 6, "Player To Player Trade");

        ItemStack line = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 1);
        for (int i = 0; i < 9; i++) {
            inventory.setItem(9 * 4 + i, line);
        }
        int count = 4;
        for (int i = 0; i < 4; i++) {
            inventory.setItem(count + (i * 9), line);
        }
        inventory.setItem(39, SKULL.get(player1.getName()));
        inventory.setItem(41, SKULL.get(player2.getName()));

        inventory.setItem(53, createButton1());

        return inventory;
    }

    // API - Create Skull
    private static ItemStack createSkull(Player player) {
        ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
        SkullMeta meta = (SkullMeta) item.getItemMeta();

        meta.setOwningPlayer(player);

        item.setItemMeta(meta);
        return item;
    }

    // API -

    // Event - When Player Click to Player, send chat link
    @EventHandler
    public void PlayerToPlayerEvent(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        setPlayer1(player);
        Entity entity = event.getRightClicked();
        setPlayer2((Player) entity);
        if (getPlayer1().isSneaking()) {
            if (getPlayer2().getType() == EntityType.PLAYER) {
                getPlayer1().spigot().sendMessage(new ComponentBuilder(getPlayer2().getName()).color(ChatColor.YELLOW).append("에게 거래를 신청했습니다.").create());
                getPlayer2().spigot().sendMessage(
                        new ComponentBuilder(getPlayer1().getName()).color(ChatColor.YELLOW).append("에게 거래 신청을 받았습니다. ")
                                .append("ACCEPT ").color(ChatColor.GREEN).bold(true).event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/playertoplayertrade " + getPlayer1().getName() + " " + getPlayer2().getName())).event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("수락").create()))
                                .append("DENY").color(ChatColor.RED).bold(true).event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/playertoplayertrade deny")).event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("거부").create()))
                                .create()
                );
            }
        }
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getInventory().getTitle().equals("Player To Player Trade")) {
            ItemStack[] tradeInventoryItems = event.getInventory().getContents();
            ItemStack[] playerInventoryItems = event.getWhoClicked().getInventory().getContents();
            if (event.isLeftClick()) {
                event.setCancelled(true);
                if (event.getSlot() == 53 && event.getCurrentItem().getItemMeta().getDisplayName().equals("거래 잠그기")) {
                    if (event.getWhoClicked().getName().equals(getPlayer1().getName())) {
                        int[] line = new int[]{36, 37, 38};
                        changeLine(line, 12, event);
                    } else if (event.getWhoClicked().getName().equals(getPlayer2().getName())) {
                        int[] line = new int[]{44, 43, 42};
                        changeLine(line, 12, event);
                    }
                    if (isPlayer1State1() && isPlayer2State1()) {
                        //TODO

                        //getInventory(getPlayer1(), getPlayer2()).setItem(53, createButton2());
                    }
                } else if (event.getSlot() == 53 && event.getCurrentItem().getItemMeta().getDisplayName().equals("거래 수락하기")) {
                    if (event.getWhoClicked().getName().equals(getPlayer1().getName())) {
                        int[] line = new int[]{36, 37, 38};
                        changeLine(line, 15, event);
                    } else if (event.getWhoClicked().getName().equals(getPlayer2().getName())) {
                        int[] line = new int[]{44, 43, 42};
                        changeLine(line, 15, event);
                    }
                    if (isPlayer1State2() && isPlayer2State2()) {
                        //TODO
                    }
                }

                int[] canNotClick = new int[]{4, 13, 22, 31, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53};
                for (int i = 0; i < canNotClick.length; i++) {
                    if (event.getSlot() == canNotClick[i] && event.getClickedInventory().getType() == InventoryType.CHEST) {
                        return;
                    } else if (event.getCurrentItem().getType() == Material.AIR)
                        return;
                }
                switch (event.getClickedInventory().getType()) {
                    case CHEST:
                        if (getPlayer1().getName().equals(event.getWhoClicked().getName())) {
                            System.out.println(getPlayer1().getName() + " " + event.getWhoClicked().getName());
                            playerInventorySlotCheckNull(event, playerInventoryItems, player1Slot);
                        } else if (getPlayer2().getName().equals(event.getWhoClicked().getName())) {
                            System.out.println(getPlayer2().getName() + " " + event.getWhoClicked().getName());
                            playerInventorySlotCheckNull(event, playerInventoryItems, player2Slot);
                        } else {
                            event.setCancelled(true);
                        }
                        lineReset();
                        break;
                    case PLAYER:
                        if (getPlayer1().getName().equals(event.getWhoClicked().getName())) {
                            tradeInventorySlotCheckNull(event, tradeInventoryItems, player1Slot);
                        } else {
                            tradeInventorySlotCheckNull(event, tradeInventoryItems, player2Slot);
                        }
                        lineReset();
                        break;
                }
            }
            if (event.isShiftClick()) {
                event.setCancelled(true);
            }
        }
    }

    private void lineReset() {
        setPlayer1State1(false);
        setPlayer2State1(false);
        setPlayer2State2(false);
        setPlayer1State2(false);
        Bukkit.getScheduler().cancelAllTasks();
        int[] line = new int[] { 40, 31, 22, 13, 4, 36, 37, 38, 44, 43, 42 };
        for(int i = 0; i < line.length; i++) {
            getInventory(getPlayer1(), getPlayer2()).setItem(line[i], new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 1));
        }
        getInventory(getPlayer1(), getPlayer2()).setItem(53, createButton1());
    }

    private void successTrade(InventoryClickEvent event) {
        int[] line1 = new int[] {42, 43, 44};
        int[] line2 = new int[] {38, 37, 36};
        int[] line3 = new int[] { 40, 31, 22, 13, 4 };
        for(int i = 0; i < 5; i++) {
            getInventory(getPlayer1(), getPlayer2()).setItem(line3[i], new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 1));
        }
        for(int i = 1; i <= line1.length; i++) {
            int finalI = i;
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                getInventory(getPlayer1(), getPlayer2()).setItem(line1[finalI - 1], new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 1));
                getInventory(getPlayer1(), getPlayer2()).setItem(line2[finalI - 1], new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 1));
            }, 7L * i);
        }

        setPlayer1State1(false);
        setPlayer2State1(false);
        setPlayer2State2(false);
        setPlayer1State2(false);
        getInventory(getPlayer1(), getPlayer2()).setItem(53, createButton1());

        ItemStack[] tradeInventoryItems = event.getInventory().getContents();
        ItemStack[] temp = new ItemStack[player1Slot.length];
        for(int i = 0; i < player2Slot.length; i++) {
            temp[i] = tradeInventoryItems[player1Slot[i]];
            tradeInventoryItems[player1Slot[i]] = tradeInventoryItems[player2Slot[i]];
            tradeInventoryItems[player2Slot[i]] = temp[i];
            getInventory(getPlayer1(), getPlayer2()).setItem(player1Slot[i], tradeInventoryItems[player1Slot[i]]);
            getInventory(getPlayer1(), getPlayer2()).setItem(player2Slot[i], tradeInventoryItems[player2Slot[i]]);
        }

    }

    private void changeLine(int[] line, int color, InventoryClickEvent event) {
        for (int i = 1; i <= line.length; i++) {
            int finalI = i;
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                TradeInv.INVES.get(getPlayer1().getName() + " TO " + getPlayer2().getName()).setItem(line[finalI - 1], new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) color));
                if (event.getWhoClicked().getName().equals(getPlayer1().getName()) && finalI == line.length && event.getCurrentItem().getItemMeta().getDisplayName().equals("거래 잠그기")) {
                    setPlayer1State1(true);
                } else if (event.getWhoClicked().getName().equals(getPlayer2().getName()) && finalI == line.length && event.getCurrentItem().getItemMeta().getDisplayName().equals("거래 잠그기")) {
                    setPlayer2State1(true);
                } else if (event.getWhoClicked().getName().equals(getPlayer1().getName()) && finalI == line.length && event.getCurrentItem().getItemMeta().getDisplayName().equals("거래 수락하기")) {
                    setPlayer1State2(true);
                } else if (event.getWhoClicked().getName().equals(getPlayer2().getName()) && finalI == line.length && event.getCurrentItem().getItemMeta().getDisplayName().equals("거래 수락하기")) {
                    setPlayer2State2(true);
                }
                System.out.println(isPlayer1State1() + " " + isPlayer1State2() + " " + isPlayer2State1() + " " + isPlayer2State2());
                if (isPlayer1State2() && isPlayer2State2()) {
                    System.out.println(isPlayer1State2() + " " + isPlayer2State2());
                    changeColorSec((short) color, event);
                } else if (isPlayer2State1() && isPlayer1State1()) {
                    changeColorSec((short) color, event);
                }
            }, 7L * i);
        }
    }

    private void changeColorSec(short color, InventoryClickEvent event) {
        int[] line1 = new int[] { 40, 31, 22, 13, 4 };
        for(int j = 1; j <= line1.length; j++) {
            int finalJ = j;
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                getInventory(getPlayer1(), getPlayer2()).setItem(line1[finalJ - 1], new ItemStack(Material.STAINED_GLASS_PANE, 1, color));
                if (finalJ == line1.length) {
                    getInventory(getPlayer1(), getPlayer2()).setItem(53, createButton2());
                    setPlayer1State1(false);
                    if (isPlayer2State2() && isPlayer1State2()) {
                        successTrade(event);

                    }
                }
            }, 7L * j);
        }
    }

    private void playerInventorySlotCheckNull(InventoryClickEvent event, ItemStack[] playerInventoryItems, int[] playerSlot) {
        for (int i = 0; i <= 35; i++) {
            if (playerInventoryItems[i] == null && IntStream.of(playerSlot).anyMatch(n -> event.getSlot() == n)) {
                event.getWhoClicked().getInventory().setItem(i, event.getCurrentItem());
                getInventory(getPlayer1(), getPlayer2()).setItem(event.getSlot(), new ItemStack(Material.AIR));
                break;
            }
        }
    }

    private void tradeInventorySlotCheckNull(InventoryClickEvent event, ItemStack[] tradeInventoryItems, int[] playerSlot) {
        for (int i = 0; i < playerSlot.length; i++) {
            if (tradeInventoryItems[playerSlot[i]] == null) {
                getInventory(getPlayer1(), getPlayer2()).setItem(playerSlot[i], event.getCurrentItem());
                event.getWhoClicked().getInventory().setItem(event.getSlot(), new ItemStack(Material.AIR));
                break;
            }
        }
    }

    // Event - SKULL
    @EventHandler
    public void PlayerJoinSKULL(PlayerJoinEvent event) {
        Player player = event.getPlayer();
//        Iterator<String> keys = SKULL.keySet().iterator();
//        while(keys.hasNext()) {
//            String Key = keys.next();
//            if (Key.equals(player.getName())) {
//                return;
//            }
//        }
        SKULL.put(player.getName(), createSkull(player));
    }

    public ItemStack createButton1() {
        ItemStack button = new ItemStack(WoolColors(14));
        ItemMeta meta = button.getItemMeta();

        meta.setDisplayName("거래 잠그기");
        meta.setLore(Arrays.asList("이 재화로 거래하자고 제안합니다."));
        button.setItemMeta(meta);
        return button;
    }

    public ItemStack createButton2() {
        ItemStack button = new ItemStack(WoolColors(11));
        ItemMeta meta = button.getItemMeta();

        meta.setDisplayName("거래 수락하기");
        meta.setLore(Arrays.asList("최종적으로 거래를 수락합니다.", "상대방도 수락할 경우 거래가 완료됩니다."));
        button.setItemMeta(meta);
        return button;
    }

    private ItemStack WoolColors(int number) {
        DyeColor[] colors = DyeColor.values();
        return new Wool(colors[number]).toItemStack(1);
        //Wool wool = new Wool(colors[number]);
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public boolean isPlayer1State1() {
        return isPlayer1State1;
    }

    public boolean isPlayer1State2() {
        return isPlayer1State2;
    }

    public boolean isPlayer2State1() {
        return isPlayer2State1;
    }

    public boolean isPlayer2State2() {
        return isPlayer2State2;
    }

    public void setPlayer1State1(boolean player1State1) {
        isPlayer1State1 = player1State1;
    }

    public void setPlayer1State2(boolean player1State2) {
        isPlayer1State2 = player1State2;
    }

    public void setPlayer2State1(boolean player2State1) {
        isPlayer2State1 = player2State1;
    }

    public void setPlayer2State2(boolean player2State2) {
        isPlayer2State2 = player2State2;
    }
}