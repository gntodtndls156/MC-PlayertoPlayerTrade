package github.gntodtndls156.nextlife.betweentrade.handle;

import github.gntodtndls156.nextlife.betweentrade.Main;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.ComponentBuilder;
import net.md_5.bungee.api.chat.HoverEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.EquipmentSlot;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class HandlePlayer implements Listener{
    Main plugin;
    public HandlePlayer(Main plugin) {
        this.plugin = plugin;
    }

    public static Map<Player, Boolean> DENYState = new HashMap<>();

    @EventHandler
    public void PlayerClickToPlayer(PlayerInteractEntityEvent event) {
        Entity entity = event.getRightClicked();
        if (entity.getType() == EntityType.PLAYER) {
            Player player = event.getPlayer();
            if (player.isSneaking() && event.getHand().equals(EquipmentSlot.HAND)) {
                if (unNullCheckDENYState(player)) {
                    player.sendMessage("잠시 후 거래 신청하시오.");
                    return;
                }
                player.spigot().sendMessage(new ComponentBuilder(entity.getName()).color(ChatColor.YELLOW).append("에게 거래를 신청했습니다.").create());
                entity.spigot().sendMessage(
                        new ComponentBuilder(player.getName()).color(ChatColor.YELLOW).append("에게 거래 신청을 받았습니다. ")
                                .append("ACCEPT ").color(ChatColor.GREEN).bold(true).event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/betweenTrade " + player.getName() + " " + entity.getName())).event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("수락").create()))
                                .append("DENY").color(ChatColor.RED).bold(true).event(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/betweenTrade " + player.getName() + " deny")).event(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new ComponentBuilder("거부").create()))
                                .create()
                );
                DENYState.put(player, true);
                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                    try {
                        if (DENYState.get(player)) {
                            player.sendMessage("상대방이 거래에 응하지 않았습니다.");
                            DENYState.remove(player);
                        }
                    } catch (NullPointerException exception) {
                        // TODO
                    }
                }, 100L);
            }
        }
    }

    private boolean unNullCheckDENYState(Player player) {
        Iterator<Player> iterator = DENYState.keySet().iterator();
        while (iterator.hasNext()) {
            if (player == iterator.next()) {
                return true;
            }
        }
        return false;
    }
//    @EventHandler
//    public void onAddPlayerSKULL(PlayerJoinEvent event) {
//        Bukkit.getScheduler().runTask(plugin, () -> {
//            Player player = event.getPlayer();
//            Iterator<Player> keys = InitPlayer.playerItemStackMap.keySet().iterator();
//            while(keys.hasNext()) {
//                if (keys.next().equals(player)) {
//                    return;
//                }
//            }
//
//            InitPlayer.playerItemStackMap.put(player, new ItemStack() {
//                public ItemStack getSkull() {
//                    ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
//                    SkullMeta meta = (SkullMeta) item.getItemMeta();
//
//                    meta.setOwningPlayer(player);
//                    item.setItemMeta(meta);
//
//                    System.out.println(player.getName() + " ADD THE PLAYER SKULL");
//                    return item;
//                }
//            }.getSkull());
//        });
//    }
}
