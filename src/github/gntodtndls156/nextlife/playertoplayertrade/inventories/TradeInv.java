package github.gntodtndls156.nextlife.playertoplayertrade.inventories;

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
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.Wool;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;


public class TradeInv implements Listener {
    static Map<String, Inventory> INVES = new HashMap<>();
    static Map<String, ItemStack> SKULL = new HashMap<>();
    Player player1, player2;

    public TradeInv() {}
    public TradeInv(String player1, String player2) {
        System.out.println("TradeInv " + player1 + " " + player2);
        openInventory(Bukkit.getPlayer(player1), Bukkit.getPlayer(player2));
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
        for(int i = 0; i < 9; i++) {
            inventory.setItem(9 * 4 + i, line);
        }
        int count = 4;
        for(int i = 0; i < 4; i++) {
            inventory.setItem(count + (i * 9), line);
        }
        inventory.setItem(39, SKULL.get(player1.getName()));
        inventory.setItem(41, SKULL.get(player2.getName()));

        Button button = new Button(getPlayer1(), getPlayer2());
        inventory.setItem(53, button.createButton1());

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
        if(getPlayer1().isSneaking()) {
            if(getPlayer2().getType() == EntityType.PLAYER) {
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

    // Event -
    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getInventory().getTitle().equals("Player To Player Trade")) {
            ItemStack[] tradeInventoryItems = event.getInventory().getContents();
            ItemStack[] playerInventoryItems = event.getWhoClicked().getInventory().getContents();
            if (event.isLeftClick()) {
                event.setCancelled(true);
                int[] canNotClick = new int[] {4, 13, 22, 31, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53};
                int[] player1Slot = new int[] {0, 1, 2, 3, 9, 10, 11, 12, 18, 19, 20, 21, 27, 28, 29, 30};
                int[] player2Slot = new int[] {5, 6, 7, 8, 14, 15, 16, 17, 23, 24, 25, 26, 32, 23, 24, 35};
                for (int i = 0; i < canNotClick.length; i++)
                    if (event.getSlot() == canNotClick[i]) {
                        return;
                    }
                switch(event.getClickedInventory().getType()) {
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
                        break;
                    case PLAYER:
                        if (getPlayer1().getName().equals(event.getWhoClicked().getName())) {
                            tradeInventorySlotCheckNull(event, tradeInventoryItems, player1Slot);
                        } else {
                            tradeInventorySlotCheckNull(event, tradeInventoryItems, player2Slot);
                        }
                        break;
                }
            }
            if (event.isShiftClick()) {
                event.setCancelled(true);
            }
        }
    }

    private void playerInventorySlotCheckNull(InventoryClickEvent event, ItemStack[] playerInventoryItems, int[] playerSlot) {
        for(int i = 0; i <= 35; i++) {
            if (playerInventoryItems[i] == null && IntStream.of(playerSlot).anyMatch(n -> event.getSlot() == n)) {
                event.getWhoClicked().getInventory().setItem(i, event.getCurrentItem());
                getInventory(getPlayer1(), getPlayer2()).setItem(event.getSlot(), new ItemStack(Material.AIR));
                break;
            }
        }
    }

    private void tradeInventorySlotCheckNull(InventoryClickEvent event, ItemStack[] tradeInventoryItems, int[] playerSlot) {
        for(int i = 0; i < playerSlot.length; i++) {
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



    /*
    Inventory inv1, inv2;
    Player player1, player2;
    static Map<String, ItemStack> SKULL = new HashMap<>();

    public TradeInv(){}

    public Player getPlayer2() {
        return player2;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Inventory getInv1() {
        return inv1;
    }

    public Inventory getInv2() {
        return inv2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public void setInv1(Inventory inv1) {
        this.inv1 = inv1;
    }

    public void setInv2(Inventory inv2) {
        this.inv2 = inv2;
    }

    public TradeInv(String $player1, String $player2) {
        setPlayer1(Bukkit.getPlayer($player1));
        setPlayer2(Bukkit.getPlayer($player2));
        setInv1(baseFrame());
        setInv2(baseFrame());
        getPlayer1().openInventory(getInv1());
        getPlayer2().openInventory(getInv2());
    }

    private Inventory baseFrame() {d
        Inventory inventory = Bukkit.create Inventory(null, 9 * 6, "Player To Player Trade");
        ItemStack line = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 1);
        for(int i = 0; i < 9; i++) {
            temp.setItem(9 * 4 + i, line);
        }
        int count = 4;
        for(int i = 0; i < 4; i++) {
            temp.setItem(count + (i * 9), line);
        }
        temp.setItem(39, SKULL.get(getPlayer1().getName()));
        temp.setItem(41, SKULL.get(getPlayer2().getName()));

        // temp.setItem(1, createItem("아이템 정보", Material.ACACIA_DOOR, new String[] {"dg", "gd"}, 2));
        return temp;
    }

    // API - Create ItemStack
    private ItemStack createItem(String $name, Material $material, String[] $lore, int $amount) {
        ItemStack item = new ItemStack($material, $amount);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName($name);
        meta.setLore(Arrays.asList($lore));

        item.setItemMeta(meta);
        return item;
    }
    // API - Create Skull d
    private ItemStack createSkull(Player player) {
        ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
        SkullMeta meta = (SkullMeta) item.getItemMeta();

        meta.setOwningPlayer(player);

        item.setItemMeta(meta);
        return item;
    }

    // register Event - When Click in inventory, it's stop to event.
    @EventHandler
    public void onPlayerClickInInventory(InventoryClickEvent event) {
        if (event.getInventory().getTitle().equals("Player To Player Trade")) {
            System.out.println(event.getSlot());
            Player player = (Player) event.getWhoClicked();
            ItemStack[] eventInventoryItems = event.getInventory().getContents();
            ItemStack[] playerInventoryItems = player.getInventory().getContents();
            int[] Slot = new int[] {0, 1, 2, 3, 9, 10, 11, 12, 18, 19, 20, 21, 27, 28, 29, 30};
            if (event.isShiftClick()) {
                event.setCancelled(true);
            }

            if (event.isLeftClick()) {
                event.setCancelled(true);
                switch(event.getClickedInventory().getType()) {
                    case CHEST:
                        if (event.getSlot() < 4 ||
                                event.getSlot() > 8 || event.getSlot() < 13 ||
                                event.getSlot() > 17 || event.getSlot() < 22 ||
                                event.getSlot() > 26 || event.getSlot() < 31) {
                            for(int i = 0; i <= 35; i++) {
                                if (playerInventoryItems[i] == null) {
                                    player.getInventory().setItem(i, event.getCurrentItem());
                                    getInv2().setItem(event.getSlot() + 5, new ItemStack(Material.AIR));
                                    event.getInventory().setItem(event.getSlot(), new ItemStack(Material.AIR));
                                    break;
                                }
                            }
                        }
                        break;
                    case PLAYER:
                        for(int i = 0; i < Slot.length; i++) {
                            if (eventInventoryItems[Slot[i]] == null) {
                                event.getInventory().setItem(Slot[i], event.getCurrentItem());
                                getInv2().setItem(Slot[i] + 5, event.getCurrentItem());
                                player.getInventory().setItem(event.getSlot(), new ItemStack(Material.AIR));
                                break;
                            }
                        }
                        break;
                }
            }
        }
    }

    // register Event - When Join Player, register player's Skull.
    @EventHandler
    public void onPlayerJoinSkull(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        SKULL.put(player.getName(), createSkull(player));
    }
    // register Event - When Quit Player, remove player's Skull.
    @EventHandler
    public void onPlayerExitSkull(PlayerQuitEvent event) {
        Player player =event.getPlayer();
        SKULL.remove(player.getName());
    }

    // register Event - When Close Inventory, other Inventory close at the same time.
    @EventHandler
    public void onTradeInventoryClosing(InventoryCloseEvent event) {
        if (event.getInventory().getTitle().equals("Player To Player Trade")) {
            getPlayer2().closeInventory();
            getPlayer1().closeInventory();
        }
    }
    // register Event - Player Sneaking and Right click
    @EventHandler
    public void onPlayerSneakingAndRightClick(PlayerInteractEntityEvent event) {
        boolean state;
        Player player = event.getPlayer();
        Entity entity = event.getRightClicked();
        if (player instanceof Player && entity instanceof Player) {
            setPlayer2(((Player) entity).getPlayer());
            setPlayer1(player);
        }
        if (getPlayer1().isSneaking()) {
            if (getPlayer2().getType() == EntityType.PLAYER) {
                

                getPlayer1().sendMessage(getPlayer2().getName() + "에게 1대1 거래 신청했습니다.");

                TextComponent ACCEPT = new TextComponent("Accept ");
                ACCEPT.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/playertoplayertrade " + getPlayer1().getName() + " " + getPlayer2().getName()));
                ACCEPT.setBold(true);
                ACCEPT.setColor(ChatColor.GREEN);

                TextComponent DENY = new TextComponent("Deny");
                DENY.setClickEvent(null);
                DENY.setBold(true);
                DENY.setColor(ChatColor.RED);

                TextComponent TEXT = new TextComponent(getPlayer1().getName() + "로부터 1대1 거래 신청받았습니다. ");
                TEXT.addExtra(ACCEPT);
                TEXT.addExtra(DENY);

                getPlayer2().spigot().sendMessage(TEXT);
                // getPlayer2().sendMessage(getPlayer1().getName() + "로부터 1대1 거래 신청받았습니다. " + ACCEPT + " " + DENY);

                        *//* Timer timer = new Timer();
                        TimerTask timerTask = new TimerTask() {
                            @Override
                            public void run() {
                                state[0] = false;
                            }
                        };
                        timer.schedule(timerTask, 60000); // 1분 후 true 로 바꿈.
                        *//*
            }
        }
    }*/
}
