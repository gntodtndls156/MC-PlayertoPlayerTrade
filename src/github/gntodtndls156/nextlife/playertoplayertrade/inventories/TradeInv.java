package github.gntodtndls156.nextlife.playertoplayertrade.inventories;

import github.gntodtndls156.nextlife.playertoplayertrade.TradeMain;
import github.gntodtndls156.nextlife.playertoplayertrade.InitPlayer;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Wool;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.IntStream;

public class TradeInv implements Listener {
    public static Map<String, Inventory> INVENTORY = new HashMap<>();
    private final boolean[] checks = new boolean[4];
    Player playerMe, playerYou;

    final int[] playerMeSlot = new int[]{0, 1, 2, 3, 9, 10, 11, 12, 18, 19, 20, 21, 27, 28, 29, 30};
    final int[] playerYouSlot = new int[]{5, 6, 7, 8, 14, 15, 16, 17, 23, 24, 25, 26, 32, 23, 24, 35};

    final int[] lineLeft = new int[]{36, 37, 38};
    final int[] lineRight = new int[]{44, 43, 42};
    final int[] lineCenter = new int[]{40, 31, 22, 13, 4};

    TradeMain plugin;
    public TradeInv(TradeMain plugin) {
        setPlugin(plugin);
    }
    public TradeInv(Player playerMe, Player playerYou) {
        this.playerMe = playerMe;
        this.playerYou = playerYou;
    }


    public Inventory registerInventory() {
        Inventory inventory = Bukkit.createInventory(null, 9 * 6, "Player To Player Trade");
        ItemStack line = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 1);
        for (int i = 0; i < 9; i++) {
            inventory.setItem(9 * 4 + i, line);
        }
        int count = 4;
        for (int i = 0; i < 4; i++) {
            inventory.setItem(count + (i * 9), line);
        }
        inventory.setItem(39, InitPlayer.PLAYER_SKULL.get(getPlayerMe().getName()));
        inventory.setItem(41, InitPlayer.PLAYER_SKULL.get(getPlayerYou().getName()));

        inventory.setItem(53, ButtonLock(1));
        inventory.setItem(45, ButtonMoney());
        return inventory;
    }

    public void openInventory(Inventory inventory) {
        getPlayerMe().openInventory(inventory);
        getPlayerYou().openInventory(inventory);
    }


    // BUTTONS
    private ItemStack ButtonLock(int number) {
        switch (number) {
            case 1:
                return ButtonLock("거래 잠그기", new String[] {"이 재화로 거래하자고 제안합니다."}, 14);
            case 2:
                return ButtonLock("거래 수락하기", new String[]{"최종적으로 거래를 수락합니다.", "상대방도 수락할 경우 거래가 완료됩니다."}, 11);
        }
        return null;
    }
    private ItemStack ButtonLock(String name, String[] lore, int color) {
        ItemStack lock = new ItemStack(WoolColors(color));
        ItemMeta meta = lock.getItemMeta();

        meta.setDisplayName(name);
        meta.setLore(Arrays.asList(lore));
        lock.setItemMeta(meta);
        return lock;
    }
    private ItemStack WoolColors(int number) {
        DyeColor[] colors = DyeColor.values();
        return new Wool(colors[number]).toItemStack(1);
        //Wool wool = new Wool(colors[number]);
    }

    private ItemStack ButtonMoney() {
        ItemStack item = new ItemStack(Material.GOLD_NUGGET, 1);
        ItemMeta meta = item.getItemMeta();

        meta.setDisplayName("돈 거래");
        meta.setLore(Arrays.asList("돈을 얼만큼 주고 받을지 요구합니다."));

        item.setItemMeta(meta);
        return item;
    }

    // EVENTS
    @EventHandler
    public void onInventoryClickAtTradeInv(InventoryClickEvent event) {
        if (event.getInventory().getTitle().equals("Player To Player Trade")) {
            // Can not to Click on the Slot
            if (event.isShiftClick()) {
                return;
            }
            int[] canNotClick = new int[]{4, 13, 22, 31, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52};
            if (Arrays.stream(canNotClick).anyMatch(n -> n == event.getSlot()) || event.getCurrentItem().getType() == Material.AIR) {
                return;
            }
            event.setCancelled(true);
            String player = event.getWhoClicked().getName();
            switch (event.getClickedInventory().getType()) {
                case CHEST:
                    if (getPlayerMe().getName().equals(player)) {
                        getPlayerInventorySlotNullCheck(event, playerMeSlot);
                    } else {
                        getPlayerInventorySlotNullCheck(event, playerYouSlot);
                    }
                    break;
                case PLAYER:
                    if (getPlayerMe().getName().equals(player)) {
                        getTradeInventorySlotNullCheck(event, playerMeSlot);
                    } else {
                        getTradeInventorySlotNullCheck(event, playerYouSlot);
                    }
                    break;
            }
        }
    }

    // TODO - function change Line.
    private void tradeLock(int color, InventoryClickEvent event, String player) {
        if (event.getCurrentItem().getItemMeta().getDisplayName().equals("거래 잠그기")) {
            for (int i = 0; i < 3; i++) {
                int finalI = i;
                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                    INVENTORY.get(getPlayerMe().getName() + getPlayerYou().getName()).setItem(
                            getPlayerMe().getName().equals(player) ? lineLeft[finalI] : lineRight[finalI],
                            new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) color)
                    );
                    if (finalI == 3) {
                        if (getPlayerMe().getName().equals(player)) {
                            setMeLock(true);
                        } else {
                            setYouLock(true);
                        }

                        if (isMeLock() && isYouLock()) {
                            changeLine(color, event, true);

                        }
                    }
                }, (i + 1) * 7L);
            }
        }
    }

    // TODO - function change Line Sec.
    private void tradeAccept(int color, InventoryClickEvent event, String player) {
        if (event.getCurrentItem().getItemMeta().getDisplayName().equals("거래 수락하기")) {
            for (int i = 0; i < 3; i++) {
                int finalI = i;
                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                    INVENTORY.get(getPlayerMe().getName() + getPlayerYou().getName()).setItem(
                            getPlayerMe().getName().equals(player) ? lineLeft[finalI] : lineRight[finalI],
                            new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) color)
                    );
                    if (finalI == 3) {
                        if (getPlayerMe().getName().equals(player)) {
                            setMeAccept(true);
                        } else {
                            setYouAccept(true);
                        }
                        if (isMeAccept() && isYouAccept()) {
                            changeLine(color, event, false);
                        }
                    }
                }, (i + 1) * 7L) ;
            }
        }
    }

    private void changeLine(int color, InventoryClickEvent event, boolean state) {
        for(int i = 0; i < lineCenter.length; i++) {
            int finalI = i;
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                INVENTORY.get(getPlayerMe().getName() + getPlayerYou().getName()).setItem(
                        lineCenter[finalI],
                        new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) color)
                );
                if (finalI + 1 == lineCenter.length) {
                    if (state)
                        INVENTORY.get(getPlayerMe().getName() + getPlayerYou().getName()).setItem(53, ButtonLock(2));
                    else
                        inventoryTradeSuccess(event);
                }
            }, (i + 1) * 7L);
        }
    }

    @EventHandler
    public void onInventoryClickToButtonAtTradeInv(InventoryClickEvent event) {
        if (event.getInventory().getTitle().equals("Player To Player Trade")) {
            event.setCancelled(true);
            if (event.isLeftClick() && event.getSlot() == 53) {
                String getItem = event.getCurrentItem().getItemMeta().getDisplayName();
                String player = event.getWhoClicked().getName();
                if (getItem.equals("거래 잠그기")) {
                    if (player != null)
                        tradeLock(12, event, player);
                }

                if (getItem.equals("거래 수락하기")) {
                    if (player != null)
                        tradeAccept(15, event, player);
                }

                if (getItem.equals("돈 거래")) {
                    // event.getWhoClicked().openInventory(moneyInventory);
                }
            }
        }
    }

    // APIS
    private void getPlayerInventorySlotNullCheck(InventoryClickEvent event, int[] playerSlot) {
        for(int i = 0; i <= 35; i++) {
            if (event.getWhoClicked().getInventory().getContents()[i] == null && IntStream.of(playerSlot).anyMatch(n -> event.getSlot() == n)) {
                event.getWhoClicked().getInventory().setItem(i, event.getCurrentItem());
                INVENTORY.get(getPlayerMe().getName() + getPlayerYou().getName()).setItem(event.getSlot(), new ItemStack(Material.AIR));
                break;
            }
        }
    }
    private void getTradeInventorySlotNullCheck(InventoryClickEvent event, int[] playerSlot) {
        for(int i = 0; i < playerSlot.length; i++) {
            if (event.getInventory().getContents()[playerSlot[i]] == null) {
                INVENTORY.get(getPlayerMe().getName() + getPlayerYou().getName()).setItem(playerSlot[i], event.getCurrentItem());
                event.getWhoClicked().getInventory().setItem(event.getSlot(), new ItemStack(Material.AIR));
            }
        }
    }

    private void inventoryReset() {
        Arrays.fill(checks, false);
        Bukkit.getScheduler().cancelTasks(plugin); // Bukkit.getScheduler().cancelTask(1);
        int[] line = new int[] { 40, 31, 22, 13 ,4 ,36, 37, 38, 44, 43, 42 };
        for(int i = 0; i < line.length; i++)
            INVENTORY.get(getPlayerMe().getName() + getPlayerYou().getName()).setItem(line[i], new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 1));
        INVENTORY.get(getPlayerMe().getName() + getPlayerYou().getName()).setItem(53, ButtonLock( 1));
    }

    private void inventoryTradeSuccess(InventoryClickEvent event) {
        final int[] lineLeft = new int[]{42, 43, 44};
        final int[] lineRight = new int[]{38, 37, 36};
        ItemStack line = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 4);
        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            inventoryReset();
            for(int i = 0; i < 3; i++) {
                INVENTORY.get(getPlayerMe().getName() + getPlayerYou().getName()).setItem(lineLeft[i], line);
                INVENTORY.get(getPlayerMe().getName() + getPlayerYou().getName()).setItem(lineRight[i], line);
            }
            for(int i = 0; i < 3; i++) {
                int finalI = i;
                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                    INVENTORY.get(getPlayerMe().getName() + getPlayerYou().getName()).setItem(lineLeft[finalI], line);
                    INVENTORY.get(getPlayerMe().getName() + getPlayerYou().getName()).setItem(lineRight[finalI], line);
                }, (1 + i) * 7L);
            }
            INVENTORY.get(getPlayerMe().getName() + getPlayerYou().getName()).setItem(53, ButtonLock(1));

            ItemStack[] toPlayer = event.getInventory().getContents();
            ItemStack[] temp = new ItemStack[16];
            for(int i = 0; i < 16; i++) {
                temp[i] = toPlayer[playerMeSlot[i]];
                toPlayer[playerMeSlot[i]] = toPlayer[playerYouSlot[i]];
                toPlayer[playerYouSlot[i]] = temp[i];

                INVENTORY.get(getPlayerMe().getName() + getPlayerYou().getName()).setItem(playerYouSlot[i], toPlayer[playerYouSlot[i]]);
                INVENTORY.get(getPlayerYou().getName() + getPlayerYou().getName()).setItem(playerMeSlot[i], toPlayer[playerMeSlot[i]]);
            }
        }, 3L);
    }

    // TODO - function money inventory
    /*private void registerInventory() {
        should do to make a class that about money.
    }*/
    // TODO - need to connect the money plugin that Vault plugin or EssentialsX plugin

    public boolean isMeLock() {
        return checks[0];
    }
    public boolean isYouLock() {
        return checks[1];
    }
    public boolean isMeAccept() {
        return checks[2];
    }
    public boolean isYouAccept() {
        return checks[3];
    }
    public void setMeLock(boolean state) {
        this.checks[0] = state;
    }
    public void setYouLock(boolean state) {
        this.checks[1] = state;
    }
    public void setMeAccept(boolean state) {
        this.checks[2] = state;
    }
    public void setYouAccept(boolean state) {
        this.checks[3] = state;
    }

    public Player getPlayerYou() {
        return playerYou;
    }

    public Player getPlayerMe() {
        return playerMe;
    }

    public void setPlugin(TradeMain plugin) {
        this.plugin = plugin;
    }
}
