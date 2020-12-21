package github.gntodtndls156.nextlife.test.inventories;

import github.gntodtndls156.nextlife.test.InitPlayer;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Wool;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TradeInv {
    public static Map<String, Inventory> INVENTORY = new HashMap<>();
    private boolean[] checks = new boolean[4];
    final Player playerMe, playerYou;

    final int[] player1Slot = new int[]{0, 1, 2, 3, 9, 10, 11, 12, 18, 19, 20, 21, 27, 28, 29, 30};
    final int[] player2Slot = new int[]{5, 6, 7, 8, 14, 15, 16, 17, 23, 24, 25, 26, 32, 23, 24, 35};

    private Inventory registerInventory() {
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
        inventory.setItem(45, createMoneyButton());
        return inventory;
    }

    public TradeInv(Player playerMe, Player playerYou) {
        this.playerMe = playerMe;
        this.playerYou = playerYou;
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

    // EVENTS
    @EventHandler
    public void onClickInventoryType(InventoryClickEvent event) {
        // TODO line 197
        switch (event.getClickedInventory().getType()) {
            case CHEST:
                break;
            case PLAYER:
                break;
        }
    }

    // TODO - reset Inventory

    // TODO - when To success, change the trade inventory between players

    // TODO - function change Line

    // TODO - function money inventory

    // TODO - need to connect the money plugin that Vault plugin or EssentialsX plugin

    @EventHandler
    public void onCanNotClick(InventoryClickEvent event) {
        if (event.isShiftClick()) {
            event.setCancelled(true);
        }
        int[] canNotClick = new int[]{4, 13, 22, 31, 36, 37, 38, 39, 40, 41, 42, 43, 44, 45, 46, 47, 48, 49, 50, 51, 52, 53};
        if (Arrays.stream(canNotClick).anyMatch(x -> x == event.getSlot()) || event.getCurrentItem().getType() == Material.AIR) {
            return;
        }
    }

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
    public boolean setMeLock(boolean state) {
        return this.checks[0] = state;
    }
    public boolean setYouLock(boolean state) {
        return this.checks[1] = state;
    }
    public boolean setMeAccept(boolean state) {
        return this.checks[2] = state;
    }
    public boolean setYouAccept(boolean state) {
        return this.checks[3] = state;
    }

    public Player getPlayerYou() {
        return playerYou;
    }

    public Player getPlayerMe() {
        return playerMe;
    }
}
