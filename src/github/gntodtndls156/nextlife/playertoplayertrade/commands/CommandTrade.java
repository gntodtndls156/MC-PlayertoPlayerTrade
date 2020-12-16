package github.gntodtndls156.nextlife.playertoplayertrade.commands;

import github.gntodtndls156.nextlife.playertoplayertrade.inventories.TradeInv;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandTrade implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] strings) {
        // PlayerToPlayerTrade
        if (OnlinePlayerCheck(strings[0])) {
            if (OnlinePlayerCheck(strings[1])) {
                new TradeInv(strings[0], strings[1]);
                return true;
            }
        }
        return false;
    }
    private boolean OnlinePlayerCheck(String name) {
        for(Player player : Bukkit.getServer().getOnlinePlayers()) {
            if (player.getName().equals(name))
                return true;
        }
        return false;

    }


}

/*

if (event.isLeftClick()) {
        int inventorySlot = event.getRawSlot();
        event.setCancelled(true);
        if (inventorySlot > 53) {
        ItemStack[] SlotItem = event.getInventory().getContents();
        int [] Slot = new int[] {0, 1, 2, 3, 9, 10, 11, 12, 18, 19, 20, 21, 27, 28, 29, 30};
        for(int i = 0; i < Slot.length; i++) {
        if (SlotItem[Slot[i]] == null) {
        event.getInventory().setItem(Slot[i], event.getCurrentItem());
        event.getInventory().setItem(inventorySlot, new ItemStack(Material.AIR));
        break;
        }
        }
        } else if (inventorySlot < 4 ||
        inventorySlot > 8 ||
        inventorySlot < 13 ||
        inventorySlot > 17 ||
        inventorySlot < 22 ||
        inventorySlot > 26 ||
        inventorySlot < 31) {
        ItemStack[] SlotItem = event.getInventory().getContents();
        for(int i = 54; i < 90; i++) {
        if (SlotItem[i] == null) {
        event.getInventory().setItem(i, event.getCurrentItem());
        event.getInventory().setItem(inventorySlot, new ItemStack(Material.AIR));
        break;
        }
        }
        }
        }*/
