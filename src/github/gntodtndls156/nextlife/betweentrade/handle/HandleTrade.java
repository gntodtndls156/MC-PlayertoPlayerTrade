package github.gntodtndls156.nextlife.betweentrade.handle;

import github.gntodtndls156.nextlife.betweentrade.Main;
import github.gntodtndls156.nextlife.betweentrade.init.InitButton;
import github.gntodtndls156.nextlife.betweentrade.inv.InvTrade;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class HandleTrade implements Listener {
    protected static Map<String, InvTrade> TRADE = new HashMap<>();
    protected static Map<Player, String> TRADE_KEY = new HashMap<>();

    private Player player;
    private String player$name;
    private String TRADE_KEY$get;
    private InvTrade invTrade;
    private Main plugin;
    public HandleTrade(Main plugin) {
        this.plugin = plugin;
    }

    public HandleTrade(Player player$0, Player player$1) {
        final SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy년 MM월 dd일 HH시 mm분 ss초");
        final String KEY = simpleDateFormat.format(new Date());

        TRADE_KEY.put(player$0, KEY);
        TRADE_KEY.put(player$1, KEY);

        TRADE.put(KEY, new InvTrade(player$0, player$1));
    }

    @EventHandler
    public void onClickAtTradeInventory(InventoryClickEvent event) {
        if (event.getInventory().getTitle().equals("Player To Player Trade")) {
            if (event.isShiftClick()) {
                return;
            }
            event.setCancelled(true);

            player = (Player) event.getWhoClicked();
            player$name = player.getName();
            TRADE_KEY$get = TRADE_KEY.get(player$name);
            invTrade = TRADE.get(TRADE_KEY$get);

            if (event.getRawSlot() == 53) {
                ItemStack Button = event.getCurrentItem();
                String Button$name = Button.getItemMeta().getDisplayName();

                switch(Button$name) {
                    case "거래 잠그기":
                        for (int i = 0; i < 3; i++) {
                            int finalI = i;
                            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                                invTrade.getInventory().setItem(
                                        invTrade.isPlayer$0Equals(player) ? invTrade.getLineLeft()[finalI] : invTrade.getLineRight()[finalI],
                                        invTrade.line(12)
                                );
                                if (finalI == 2) {
                                    if (invTrade.isPlayer$0Equals(player)) {
                                        invTrade.setMeLock(true);
                                    } else {
                                        invTrade.setYouLock(true);
                                    }

                                    if (invTrade.isMeLock() && invTrade.isYouLock()) {
                                        changeLine(12, event, true);
                                    }

                                }
                            }, (1 + i) * 7L);
                        }
                        break;
                    case "거래 수락하기":
                        for (int i = 0; i < 3; i++) {
                            int finalI = i;
                            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                                invTrade.getInventory().setItem(
                                        invTrade.isPlayer$0Equals(player) ? invTrade.getLineLeft()[finalI] : invTrade.getLineRight()[finalI],
                                        invTrade.line(15)
                                );
                                if (finalI == 2) {
                                    if (invTrade.isPlayer$0Equals(player)) {
                                        invTrade.setMeAccept(true);
                                    } else {
                                        invTrade.setYouAccept(true);
                                    }

                                    if (invTrade.isMeAccept() && invTrade.isYouAccept()) {
                                        changeLine(15, event, false);
                                    }
                                }
                            }, (i + 1) * 7L);
                        }
                }
            }

            if (event.getRawSlot() == 45) {
                if (event.getCurrentItem().getItemMeta().getDisplayName().equals("돈 거래")) {
                    int money = (int) plugin.getEconomy().getBalance(player);
                    if (money <= 0) {
                        player.sendMessage("잔고가 비어있습니다.");
                        return;
                    }
                    // TODO
                    new HandleMoney();
                }
            }
        }
    }
    public void changeLine(int color, InventoryClickEvent event, boolean state) {
        for (int i = 0; i < invTrade.getLineCenter().length; i++) {
            int finalI = i;
            Bukkit.getScheduler().runTaskLater(plugin, () -> {
                invTrade.getInventory().setItem(
                        invTrade.getLineCenter()[finalI],
                        invTrade.line(color)
                );
                if (finalI + 1 == invTrade.getLineCenter().length) {
                    if (state) {
                        invTrade.getInventory().setItem(53, new InitButton(2).ButtonLock());
                    } else {
                        // TODO
                    }
                }
            }, (i + 1) * 7L);
        }
    }

    private void reset() {
        Arrays.fill(invTrade.getChecks(), false);
        Bukkit.getScheduler().cancelTasks(plugin);
        for (int i = 0; i < 11; i++)
            invTrade.getInventory().setItem(new int[] { 40, 31, 22, 13 ,4 ,36, 37, 38, 44, 43, 42 }[i], invTrade.line(1));
        invTrade.getInventory().setItem(53, new InitButton(1).ButtonLock());

    }
    private void success(InventoryClickEvent event) {
        ItemStack line$0 = invTrade.line(4);
        ItemStack line$1 = invTrade.line(1);

        Bukkit.getScheduler().runTaskLater(plugin, () -> {
            reset();
            for (int i = 0; i< 3; i++) {
                invTrade.getInventory().setItem(invTrade.getLineRight()[i], line$0);
                invTrade.getInventory().setItem(invTrade.getLineLeft()[i], line$0);
            }

            for (int i = 2; i >= 0; i--) {
                int finalI = i;
                Bukkit.getScheduler().runTaskLater(plugin, () -> {
                    invTrade.getInventory().setItem(invTrade.getLineRight()[finalI], line$1);
                    invTrade.getInventory().setItem(invTrade.getLineLeft()[finalI], line$1);
                }, 5L + (i * 5));
            }

            ItemStack[] itemStacks = event.getInventory().getContents();
            ItemStack[] temp = new ItemStack[16];

            for (int i = 0; i < 16; i++) {
                temp[i] = itemStacks[invTrade.getPlayerMeSlot()[i]];
                itemStacks[invTrade.getPlayerMeSlot()[i]] = itemStacks[invTrade.getPlayerYouSlot()[i]];
                itemStacks[invTrade.getPlayerYouSlot()[i]] = temp[i];

                invTrade.getInventory().setItem(invTrade.getPlayerMeSlot()[i], itemStacks[invTrade.getPlayerMeSlot()[i]]);
                invTrade.getInventory().setItem(invTrade.getPlayerYouSlot()[i], itemStacks[invTrade.getPlayerYouSlot()[i]]);
            }
        }, 3L);
    }
}
