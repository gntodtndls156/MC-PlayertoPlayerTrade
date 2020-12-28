package github.gntodtndls156.nextlife.betweentrade.init;

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
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class InitPlayer implements Listener {
    // TODO - about player
    public static Map<String, ItemStack> PLAYER_SKULL = new HashMap<>();
    Player playerMe, playerYou;

    @EventHandler
    public void onClickPlayerToPlayer(PlayerInteractEntityEvent event) {
        Player player = event.getPlayer();
        Entity entity = event.getRightClicked();
        if (entity.getType() == EntityType.PLAYER) {
            if (player.isSneaking()) {
                setPlayerMe(player);
                setPlayerYou((Player) entity);

                getPlayerMe().spigot().sendMessage(new ComponentBuilder(getPlayerYou().getName()).color(ChatColor.YELLOW).append("에게 거래를 신청했습니다.").create());
                getPlayerYou().spigot().sendMessage(
                        new ComponentBuilder(getPlayerMe().getName()).color(ChatColor.YELLOW).append("에게 거래 신청을 받았습니다. ")
                                .append("ACCEPT ").color(ChatColor.GREEN).bold(true).event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/playertoplayertrade " + getPlayerMe().getName() + " " + getPlayerYou().getName())).event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("수락").create()))
                                .append("DENY").color(ChatColor.RED).bold(true).event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/playertoplayertrade deny")).event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("거부").create()))
                                .create()
                );
            }
        }
    }

    @EventHandler
    public void onAddPlayerSKULL(PlayerJoinEvent event) {
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

                    System.out.println(player.getName() + " ADD THE PLAYER SKULL");
                    return item;
                }
            }.getSkull());
        });

        thread.start();
    }

    // TODO - To remove the player skull when player quit at server.

    public void setPlayerYou(Player playerYou) {
        this.playerYou = playerYou;
    }
    public void setPlayerMe(Player playerMe) {
        this.playerMe = playerMe;
    }
    public Player getPlayerYou() {
        return playerYou;
    }
    public Player getPlayerMe() {
        return playerMe;
    }
}
