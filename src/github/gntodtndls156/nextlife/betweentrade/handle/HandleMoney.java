package github.gntodtndls156.nextlife.betweentrade.handle;

import github.gntodtndls156.nextlife.betweentrade.Main;
import github.gntodtndls156.nextlife.betweentrade.init.InitGetLang;
import github.gntodtndls156.nextlife.betweentrade.inv.InvMoney;
import github.gntodtndls156.nextlife.betweentrade.inv.InvTrade;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class HandleMoney implements Listener {
    public static Map<Player, InvMoney> MONEY = new HashMap<>();
    private Main plugin;
    private boolean viewState = false;

    public HandleMoney(Player player , int money) {
        InvMoney invMoney = new InvMoney(money);
        MONEY.put(player, invMoney);
        player.openInventory(invMoney.getInventory());
    }
    public HandleMoney(Main plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onOpenMoneyInventory(InventoryOpenEvent event) {
        if (event.getInventory().getTitle().equals("Add Money Trade")) {
            Player player = (Player) event.getPlayer();
            InvTrade invTrade = HandleTrade.TRADE.get(HandleTrade.TRADE_KEY.get(player));

            if (invTrade.isPlayer$0Equals(player)) {
                invTrade.setMeGotoMoney(true);
            } else {
                invTrade.setYouGotoMoney(true);
            }
        }
    }
    @EventHandler
    public void onCloseMoneyInventory(InventoryCloseEvent event) {
        if (event.getInventory().getTitle().equals("Add Money Trade")) {
            if (!viewState) {
                Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, () -> {
                    Player player = (Player) event.getPlayer();
                    try {
                        player.openInventory(MONEY.get(player).getInventory());
                    } catch (NullPointerException exception) {
                        // TODO
                    }
                }, 1L);
            }
        }
    }

    @EventHandler
    public void onClickAtMoneyInventory(InventoryClickEvent event) {
        if (event.getInventory().getTitle().equals("Add Money Trade")) {
            event.setCancelled(true);
            if (event.getCurrentItem().getType() == Material.AIR ||
                    event.getSlot() == 4)
                return;

            ItemStack item = event.getCurrentItem();
            Player player = (Player) event.getWhoClicked();
            if (item.getItemMeta().getDisplayName().equals(InitGetLang.LANG.get("AddToTrade"))) {
                player.openInventory(HandleTrade.TRADE.get(HandleTrade.TRADE_KEY.get(player)).getInventory());
                if (MONEY.get(player).getSum() <= 0) {
                    player.sendMessage("ZERO MONEY");
                    return;
                }
                int slot = addSlotMoney(player);
                if (slot == -1) {
                    player.sendMessage("FULL SLOT");
                    return;
                }
                plugin.getEconomy().withdrawPlayer(player, MONEY.get(player).getSum());
                HandleTrade.TRADE.get(HandleTrade.TRADE_KEY.get(player)).getInventory().setItem(slot, MONEY.get(player).sumToTrade());
                remove(player);
                return;
            } else if (item.getItemMeta().getDisplayName().equals(InitGetLang.LANG.get("ShowMyMoney"))) {
                viewState = true;
                player.closeInventory();
                player.sendTitle(unit(MONEY.get(player).getMyMoney()), "Left Click to close", 10, 9999, 20);
                return;
            } else if (item.getItemMeta().getDisplayName().equals(InitGetLang.LANG.get("Back"))) {
                player.openInventory(HandleTrade.TRADE.get(HandleTrade.TRADE_KEY.get(player)).getInventory());
                remove(player);
                return;
            }

            try {
                int num = Integer.parseInt(item.getItemMeta().getDisplayName().replaceAll("§[0-9]", "").replaceAll("[^0-9]", ""));
                switch(item.getType()) {
                    case STONE_BUTTON:
                        MONEY.get(player).plus(num);
                        break;
                    case WOOD_BUTTON:
                        MONEY.get(player).minus(num);
                }
            } catch (NullPointerException exception) {}
            reload(player);
        }
    }

    public int addSlotMoney(Player player) {
        ItemStack[] items = HandleTrade.TRADE.get(HandleTrade.TRADE_KEY.get(player)).getInventory().getContents();
        int[] slots = HandleTrade.TRADE.get(HandleTrade.TRADE_KEY.get(player)).isPlayer$0Equals(player) ?
                new int[]{0, 1, 2, 3, 9, 10, 11, 12, 18, 19, 20, 21, 27, 28, 29, 30} :
                new int[]{5, 6, 7, 8, 14, 15, 16, 17, 23, 24, 25, 26, 32, 33, 34, 35};
        int i;
        for (i = 0; i < slots.length; i++) {
            if (items[slots[i]] == null) {
                return slots[i];
            }
        }
        return -1;
    }

    private void remove(Player player) { MONEY.remove(player); }
    private void reload(Player player) {
        MONEY.get(player).button$reset();
    }
    public String unit(int money) {
        StringBuffer s =new StringBuffer(String.valueOf(money));
        s.reverse();
        for (int i = 0; i < s.length(); i++) {
            if (i % 3 == 0)
                s.insert(i, ",");
        }
        return s.reverse().toString();
    }

    @EventHandler
    public void VaultToMoneyInventory(PlayerInteractEvent event) {
        if (viewState) {
            Player player = event.getPlayer();
            if (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK || event.getAction() == Action.PHYSICAL) {
                player.openInventory(MONEY.get(player).getInventory());
                player.sendTitle("","",1, 1, 1);
                viewState = false;
                return;
            }
        }
    }
    @EventHandler
    public void VaultToMoneyInventory(PlayerMoveEvent event) {
        if (viewState) {
            event.setCancelled(true);
        }
    }
}
