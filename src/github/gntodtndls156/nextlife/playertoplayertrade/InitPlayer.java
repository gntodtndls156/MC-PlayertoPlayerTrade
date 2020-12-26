package github.gntodtndls156.nextlife.playertoplayertrade;

import github.gntodtndls156.nextlife.playertoplayertrade.inventories.TradeInv;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class InitPlayer implements Listener {
    public static Map<String, ItemStack> PLAYER_SKULL = new HashMap<>();
    Player playerMe, playerYou;
    TradeInv tradeInv;

    public InitPlayer() {
        tradeInv = new TradeInv(getPlayerMe(), getPlayerYou());
        TradeInv.INVENTORY.put(getPlayerMe().getName() + getPlayerYou().getName(), tradeInv.registerInventory());
        Inventory inventory = TradeInv.INVENTORY.get(getPlayerMe().getName() + getPlayerYou().getName());
        if (inventory == null) {
            System.out.println("인벤토리가 비었습니다.");
        } else {
            System.out.println("인벤토리가 있습니다.");
        }
    }

    TradeMain plugin;
    public InitPlayer(TradeMain plugin) {
        this.plugin = plugin;
    }

    // EVENTS

    @EventHandler
    public void onCloseTradeInventory(InventoryCloseEvent event) {
        Inventory inventory = TradeInv.INVENTORY.remove(getPlayerMe().getName() + getPlayerYou().getName());
    }

    @EventHandler
    public void onPlayerToPlayerEvent(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        Entity entity = event.getRightClicked();

        setPlayerMe(player);
        setPlayerYou((Player) entity);

        if (getPlayerMe().isSneaking() && getPlayerYou().getType() == EntityType.PLAYER) {
            getPlayerMe().spigot().sendMessage(new ComponentBuilder(getPlayerYou().getName()).color(ChatColor.YELLOW).append("에게 거래를 신청했습니다.").create());
            getPlayerYou().spigot().sendMessage(
                    new ComponentBuilder(getPlayerMe().getName()).color(ChatColor.YELLOW).append("에게 거래 신청을 받았습니다. ")
                            .append("ACCEPT ").color(ChatColor.GREEN).bold(true).event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/playertoplayertrade " + getPlayerMe().getName() + " " + getPlayerYou().getName())).event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("수락").create()))
                            .append("DENY").color(ChatColor.RED).bold(true).event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/playertoplayertrade deny")).event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("거부").create()))
                            .create()
            );
        }
    }

    @EventHandler
    public void onSetPlayerSkull(PlayerJoinEvent event) {
        Thread thread = new Thread(() -> {
            Player player = event.getPlayer();
            Iterator<String> keys = PLAYER_SKULL.keySet().iterator();
            while(keys.hasNext()) {
                if (keys.next().equals(player.getName())) {
                    return;
                }
            }

            PLAYER_SKULL.put(player.getName(), new ItemStack() {
                public ItemStack getSkull() {
                    ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
                    SkullMeta meta = (SkullMeta) item.getItemMeta();

                    meta.setOwningPlayer(player);
                    item.setItemMeta(meta);

                    return item;
                }
            }.getSkull());
        });

        thread.start();
    }

    @EventHandler
    public void onRemovePlayerSkull(PlayerQuitEvent event) {
        String player = event.getPlayer().getName();
        if(PLAYER_SKULL.get(player) != null) {
            PLAYER_SKULL.remove(player);
        }
    }

    public Player getPlayerMe() {
        return playerMe;
    }

    public Player getPlayerYou() {
        return playerYou;
    }

    public void setPlayerMe(Player playerMe) {
        this.playerMe = playerMe;
    }

    public void setPlayerYou(Player playerYou) {
        this.playerYou = playerYou;
    }
}
