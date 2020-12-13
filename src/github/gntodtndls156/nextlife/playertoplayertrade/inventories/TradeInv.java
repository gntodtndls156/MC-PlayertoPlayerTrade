package github.gntodtndls156.nextlife.playertoplayertrade.inventories;

import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.ClickEvent;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;


public class TradeInv1 implements Listener {
    Inventory inv1, inv2;
    Player player1, player2;

    public TradeInv1(String $player1, String $player2) {
        setPlayer1(Bukkit.getPlayer($player1));
        setPlayer2(Bukkit.getPlayer($player2));
        setInv1(baseFrame());
        setInv2(baseFrame());
        getPlayer1().openInventory(getInv1());
        getPlayer2().openInventory(getInv2());
    }

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



    private Inventory baseFrame() {
        Inventory temp = Bukkit.createInventory(null, 9 * 6, "Player To Player Trade");
        ItemStack line = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 1);
        for(int i = 0; i < 9; i++) {
            temp.setItem(9 * 5 + i, line);
        }
        int count = 4;
        for(int i = 0; i < 4; i++) {
            temp.setItem(count + (i * 9), line);
        }
        temp.setItem(39, createSkull(getPlayer1()));
        temp.setItem(41, createSkull(getPlayer2()));

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
    // API - Create Skull
    private ItemStack createSkull(Player player) {
        ItemStack item = new ItemStack(Material.SKULL_ITEM, 1, (short) SkullType.PLAYER.ordinal());
        SkullMeta meta = (SkullMeta) item.getItemMeta();

        meta.setOwningPlayer(player);

        item.setItemMeta(meta);
        return item;
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
                TextComponent ACCEPT = new TextComponent("Accept");
                ACCEPT.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/PlayerToPlayerTrade " + getPlayer1() + " " + getPlayer2()));
                ACCEPT.setBold(true);
                ACCEPT.setColor(ChatColor.GREEN);

                TextComponent DENY = new TextComponent("Deny");
                DENY.setClickEvent(null);
                DENY.setBold(true);
                DENY.setColor(ChatColor.RED);

                getPlayer1().sendMessage(getPlayer2().getName() + "에게 1대1 거래 신청했습니다.");
                getPlayer2().sendMessage(getPlayer1().getName() + "로부터 1대1 거래 신청받았습니다. " + ACCEPT + " " + DENY);

                        /* Timer timer = new Timer();
                        TimerTask timerTask = new TimerTask() {
                            @Override
                            public void run() {
                                state[0] = false;
                            }
                        };
                        timer.schedule(timerTask, 60000); // 1분 후 true 로 바꿈.
                        */
            }
        }
    }
}
