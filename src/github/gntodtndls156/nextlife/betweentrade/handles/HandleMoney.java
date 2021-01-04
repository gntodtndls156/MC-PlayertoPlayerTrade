package github.gntodtndls156.nextlife.betweentrade.handles;

import github.gntodtndls156.nextlife.betweentrade.MainBetweenTrade;
import github.gntodtndls156.nextlife.betweentrade.inventories.InvMoney;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class HandleMoney implements Listener {
    private static Map<String, InvMoney> MONEY = new HashMap<>();
    private InvMoney invMoney;
    private boolean viewState = false;
    private MainBetweenTrade plugin;
    public HandleMoney(Player player, int money, MainBetweenTrade plugin) {
        invMoney = new InvMoney(money);
        MONEY.put(player.getName(), invMoney);
        player.openInventory(MONEY.get(player.getName()).getInventory());
    }
    public HandleMoney(MainBetweenTrade plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onClickAtMoneyInventory(InventoryClickEvent event) {
        if (event.getInventory().getTitle().equals("Add Money Trade")) {
            event.setCancelled(true);
            if (event.getCurrentItem().getType() == Material.AIR ||
                event.getRawSlot() == 4) {
                return;
            }

            ItemStack item = event.getCurrentItem();
            Player player = (Player) event.getWhoClicked();
            if (item.getItemMeta().getDisplayName().equals("Add to Trade")) {
                player.openInventory(HandleTrade.TRADE.get(HandleTrade.TRADE_KEY.get(player.getName())).getInventory());
                int slot = addSlotMoney(player);
                if (slot == -1) {
                    player.sendMessage("FULL SLOT.");
                    return;
                }
                plugin.getEconomy().withdrawPlayer(player, MONEY.get(player.getName()).getSum());
                System.out.println(player.getName() + " " + plugin.getEconomy().getBalance(player));
                HandleTrade.TRADE.get(HandleTrade.TRADE_KEY.get(player.getName())).getInventory().setItem(slot, MONEY.get(player.getName()).moneyToTrade());
                remove(player);
                return;
            } else if (item.getItemMeta().getDisplayName().equals("Type in Value")) {
                // TODO
                player.closeInventory();
                player.sendTitle(unit(MONEY.get(player.getName()).getMoney()), "Left Click to close", 10, 9999, 20);
                viewState = true;
                return;
            } else if (item.getItemMeta().getDisplayName().equals("Back")) {
                player.openInventory(HandleTrade.TRADE.get(HandleTrade.TRADE_KEY.get(player.getName())).getInventory());
                remove(player);
                return;
            }

            int increaseMoney = Integer.parseInt(item.getItemMeta().getDisplayName().replaceAll("[^0-9]", ""));
            System.out.println(increaseMoney);
            if (item.getType() == Material.STONE_BUTTON) {
                MONEY.get(player.getName()).plus(increaseMoney);
                System.out.println(MONEY.get(player.getName()).getSum());
            } else if (item.getType() == Material.WOOD_BUTTON) {
                if (MONEY.get(player.getName()).getSum() <= 0) {
                    return;
                }
                MONEY.get(player.getName()).minus(increaseMoney);
                System.out.println(MONEY.get(player.getName()).getSum());
            }
            reload(player);
        }
    }
    @EventHandler
    public void VaultToMoneyInventory(PlayerInteractEvent interactEvent) {
        interactEvent.getPlayer().sendMessage(String.valueOf(viewState));
        if (viewState) {
            Player player = interactEvent.getPlayer();
            if (interactEvent.getAction() == Action.LEFT_CLICK_AIR) {
                player.openInventory(MONEY.get(player.getName()).getInventory());
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

    public int addSlotMoney(Player player) {
        ItemStack[] items = HandleTrade.TRADE.get(HandleTrade.TRADE_KEY.get(player.getName())).getInventory().getContents();
        int[] slots = player.getName().equals(HandleTrade.TRADE.get(HandleTrade.TRADE_KEY.get(player.getName())).getPlayerMe().getName()) ?
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

    private void remove(Player player) {
        MONEY.remove(player.getName());
    }
    private void reload(Player player) {
        MONEY.get(player.getName()).button$reset();
    }
    public String unit(int money) {
        StringBuffer s = new StringBuffer(String.valueOf(money));
        s.reverse();
        int i = 0;
        while(i < s.length()) {
            if (i % 3 == 0) {
                s.insert(i, ",");
            }
            i++;
        }
        s.reverse();
        return s.toString();
    }
}
