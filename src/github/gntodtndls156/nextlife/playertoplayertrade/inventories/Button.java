package github.gntodtndls156.nextlife.playertoplayertrade.inventories;

import github.gntodtndls156.nextlife.playertoplayertrade.PlayerToPlayerTrade;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.material.Wool;

import java.util.Arrays;


public class Button implements Listener {
    Player player1, player2;
    boolean state1, state2;

    public Button(Player player1, Player player2) {
        setPlayer1(player1);
        setPlayer2(player2);
    }
    public Button() {}

    PlayerToPlayerTrade main;
    public Button(PlayerToPlayerTrade playerToPlayerTrade) {
        this.main = playerToPlayerTrade;
    }

    public ItemStack createButton1() {
        ItemStack button = new ItemStack(WoolColors(14));
        ItemMeta meta = button.getItemMeta();

        meta.setDisplayName("거래 잠그기");
        meta.setLore(Arrays.asList("이 재화로 거래하자고 제안합니다."));
        button.setItemMeta(meta);
        return button;
    }

    public ItemStack createButton2() {
        ItemStack button = new ItemStack(WoolColors(11));
        ItemMeta meta = button.getItemMeta();

        meta.setDisplayName("거래 수락하기");
        meta.setLore(Arrays.asList("최종적으로 거래를 수락합니다.", "상대방도 수락할 경우 거래가 완료됩니다."));
        button.setItemMeta(meta);
        return button;
    }

    private ItemStack WoolColors(int number) {
        DyeColor[] colors = DyeColor.values();
        return new Wool(colors[number]).toItemStack(1);
        //Wool wool = new Wool(colors[number]);
    }

    @EventHandler
    public void onClickButton(InventoryClickEvent event) {
        if (event.getInventory().getTitle().equals("Player To Player Trade")) {
            event.setCancelled(true);
            if (event.getSlot() == 53 && event.getCurrentItem().getItemMeta().getDisplayName().equals("거래 잠그기")) {
                if (event.getWhoClicked().equals(getPlayer1().getName())) {
                    int[] line = new int[] {36, 37, 38};
                    changeLine(line, 8);
                }
                else if (event.getWhoClicked().equals(getPlayer2().getName())) {
                    int[] line = new int[] {44, 43, 42};
                    changeLine(line, 8);
                }
            } else if (event.getSlot() == 53 && event.getCurrentItem().getItemMeta().getDisplayName().equals("거래 수락하기")) {
                if (event.getWhoClicked().equals(getPlayer1().getName())) {
                    int[] line = new int[] {36, 37, 38};
                    changeLine(line, 15);
                }
                else if (event.getWhoClicked().equals(getPlayer2().getName())) {
                    int[] line = new int[] {44, 43, 42};
                    changeLine(line, 15);
                }
            }
        }
    }

    private void changeLine(int[] line, int color) {
        for (int i = 0; i <= line.length; i++) {
            int finalI = i;
            Bukkit.getScheduler().runTaskLater(main, () -> {
                TradeInv.INVES.get(getPlayer1().getName() + " TO " + getPlayer2().getName()).setItem(line[finalI], new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) color));
            }, 1 + (7*i));
        }
    }


    public void setState2(boolean state2) {
        this.state2 = state2;
    }

    public void setState1(boolean state1) {
        this.state1 = state1;
    }

    public boolean isState1() {
        return state1;
    }

    public boolean isState2() {
        return state2;
    }

    public Player getPlayer2() {
        return player2;
    }

    public Player getPlayer1() {
        return player1;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }
}
