package github.gntodtndls156.nextlife.playertoplayertrade.inventories;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.SkullType;
import org.bukkit.block.Skull;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import java.util.Arrays;


public class TradeInv implements Listener {
    Inventory invTrade = null;
    Player player1, Player2;

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public void setPlayer2(Player player2) {
        Player2 = player2;
    }

    public Player getPlayer1() {
        return player1;
    }

    public Player getPlayer2() {
        return Player2;
    }

    private Inventory baseFrame() {
        Inventory temp = Bukkit.createInventory(null, 9 * 6, "Player To Player");
        ItemStack line = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 1);
        for(int i = 0; i < 9; i++) {
            invTrade.setItem(9 * 5 + i, line);
        }
        int count = 4;
        for(int i = 0; i < 4; i++) {
            invTrade.setItem(count + (i * 9), line);
        }
        temp.setItem(39, createSkull(getPlayer1()));
        temp.setItem(41, createSkull(getPlayer2()));

        temp.setItem(1, createItem("아이템 정보", Material.ACACIA_DOOR, new String[] {"dg", "gd"}, 2));

        return temp;
    }

    public TradeInv(String $player) {
        Player player = Bukkit.getPlayer($player); // get player
        invTrade = baseFrame(); // base frame
        getPlayer1().openInventory(invTrade); // open inventory to player
        getPlayer2().openInventory(invTrade);
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

    @EventHandler
    public void onInventoryItemClick(InventoryClickEvent event) {

    }
/*
    Player player;
    Player otherplayer;

    public Player getPlayer() {
        return player;
    }

    public Player getOtherplayer() {
        return otherplayer;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public void setOtherplayer(Player otherplayer) {
        this.otherplayer = otherplayer;
    }

    @EventHandler
    public void onShiftRightClick(PlayerInteractEntityEvent event) {
        boolean[] state = {false};
        Player player = event.getPlayer();
        setPlayer(player);
        Entity entity = event.getRightClicked();
        if (player.isSneaking()) {
            if (entity instanceof Player) {
                Player otherPlayer = (Player) entity;
                setOtherplayer(otherPlayer);
                if (!(otherPlayer.getName() == null)) {
                    if (!state[0]) {
                        // ACCEPT
                        TextComponent accept = new TextComponent("Accept");
                        accept.setColor(ChatColor.GREEN);
                        accept.setBold(true);
                        accept.setClickEvent(new ClickEvent(ClickEvent.Action.RUN_COMMAND, "/PlayerToPlayerTrade " + otherPlayer));
                        // DENY
                        TextComponent deny = new TextComponent("Deny");
                        deny.setColor(ChatColor.RED);
                        deny.setBold(true);

                        Timer timer = new Timer();
                        TimerTask timerTask = new TimerTask() {
                            @Override
                            public void run() {
                                state[0] = false;
                            }
                        };
                        timer.schedule(timerTask, 60000); // 1분 후 true 로 바꿈.
                        otherPlayer.sendMessage(player.getName() + "님이 당신에게 1대1 거래 신청했습니다. " + accept + " " + deny);
                        player.sendMessage(otherPlayer.getName() + "에게 1대1 거래 신청했습니다.");
                        state[0] = true;
                    } else {
                        player.sendMessage("이미 거래 신청 중입니다.");
                    }

                }
            }
        }
    }
    */
}
