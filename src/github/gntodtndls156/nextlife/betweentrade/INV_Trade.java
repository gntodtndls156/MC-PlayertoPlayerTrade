package github.gntodtndls156.nextlife.betweentrade;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

public class INV_Trade {
    private boolean[] checks = new boolean[4];
    private Player playerMe, playerYou;
    private Inventory inventory;

    public INV_Trade(Player playerMe, Player playerYou) {
        this.playerMe = playerMe;
        this.playerYou = playerYou;

        Inventory inventory;
        inventory = Bukkit.createInventory(null, 9 * 6, "Player To Player Trade");
        ItemStack line = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 1);
        for(int i = 0; i < 9; i++) {
            inventory.setItem(9 * 4 + i, line);
        }
        int count = 4;
        for(int i = 0; i < 4; i++) {
            inventory.setItem(count + (i * 9), line);
        }

        inventory.setItem(39, INIT_Player.PLAYER_SKULL.get(playerMe));
        inventory.setItem(41, INIT_Player.PLAYER_SKULL.get(playerYou));

        inventory.setItem(53, new INIT_Button(1).ButtonLock());
        inventory.setItem(45, new INIT_Button(3).ButtonLock());

        this.inventory = inventory;

        playerMe.openInventory(this.inventory);
        playerYou.openInventory(this.inventory);
    }

    public void setChecks(boolean[] checks) {
        this.checks = checks;
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

    public Player getPlayerYou() {
        return playerYou;
    }
    public Player getPlayerMe() {
        return playerMe;
    }
    public void setPlayerYou(Player playerYou) {
        this.playerYou = playerYou;
    }
    public void setPlayerMe(Player playerMe) {
        this.playerMe = playerMe;
    }

    public Inventory getInventory() {
        return inventory;
    }
    public void setInventory(Inventory inventory) {
        this.inventory = inventory;
    }
}
