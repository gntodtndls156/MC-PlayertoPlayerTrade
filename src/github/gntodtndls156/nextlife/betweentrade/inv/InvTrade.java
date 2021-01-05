package github.gntodtndls156.nextlife.betweentrade.inv;

import github.gntodtndls156.nextlife.betweentrade.Main;
import github.gntodtndls156.nextlife.betweentrade.init.InitButton;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class InvTrade {
    private Inventory inventory;
    private boolean[] checks = new boolean[4];
    private Player player$0, player$1;

    final int[] playerMeSlot = new int[]{0, 1, 2, 3, 9, 10, 11, 12, 18, 19, 20, 21, 27, 28, 29, 30};
    final int[] playerYouSlot = new int[]{5, 6, 7, 8, 14, 15, 16, 17, 23, 24, 25, 26, 32, 33, 34, 35};
    final int[] lineLeft = new int[]{36, 37, 38};
    final int[] lineRight = new int[]{44, 43, 42};
    final int[] lineCenter = new int[]{40, 31, 22, 13, 4};

    public InvTrade(Player player$0, Player player$1) {
        this.player$0 = player$0;
        this.player$1 = player$1;

        // setting Inventory
        this.inventory = Bukkit.createInventory(null, 9 * 6, "Player To Player Trade");
        ItemStack line = line(1);

        for (int i = 0; i < 9; i++)
            inventory.setItem(9 * 4 + i, line);
        for (int i = 0; i < 4; i++)
            inventory.setItem(4 + (i * 9), line);

        inventory.setItem(53, new InitButton(1).ButtonLock());
        inventory.setItem(45, new InitButton(8).ButtonLock());

        openInventory();
    }

    private void openInventory() {
        player$1.openInventory(inventory);
        player$0.openInventory(inventory);
    }

    public ItemStack line(int color) {
        return new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) color);
    }

    public Inventory getInventory() {
        return inventory;
    }

    public boolean[] getChecks() {
        return checks;
    }
    public boolean isMeLock() {
        return checks[0];
    }
    public boolean isYouLock() {
        return checks[1];
    }
    public boolean isMeAccept() {
        return checks[2];
    }
    public boolean isYouAccept() {
        return checks[3];
    }
    public void setMeLock(boolean state) {
        this.checks[0] = state;
    }
    public void setYouLock(boolean state) {
        this.checks[1] = state;
    }
    public void setMeAccept(boolean state) {
        this.checks[2] = state;
    }
    public void setYouAccept(boolean state) {
        this.checks[3] = state;
    }

    public boolean isPlayer$0Equals(Player player) {
        if (player == player$0)
            return true;
        return false;
    }

    public int[] getLineCenter() {
        return lineCenter;
    }
    public int[] getLineLeft() {
        return lineLeft;
    }
    public int[] getLineRight() {
        return lineRight;
    }
    public int[] getPlayerMeSlot() {
        return playerMeSlot;
    }
    public int[] getPlayerYouSlot() {
        return playerYouSlot;
    }
}
