package github.gntodtndls156.nextlife.playertoplayertrade.inventories;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.Inventory;

public class TradeInv implements Listener {
    Inventory inventory = null;



    @EventHandler
    public void onShiftRightClick(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        Entity entity = event.getRightClicked();
        if (player.isSneaking()) {
            if (entity instanceof Player) {
                Player otherPlayer = (Player) entity;
                if (!(otherPlayer.getName() == null)) {
                    // ACCEPT
                    TextComponent accept = new TextComponent("Accept");
                    accept.setColor(ChatColor.GREEN);
                    accept.setBold(true);
                    accept.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/PlayerToPlayerTrade " + otherPlayer));
                    // DENY
                    TextComponent deny = new TextComponent("Deny");
                    deny.setColor(ChatColor.RED);
                    deny.setBold(true);


                }
            }
        }
    }
}
