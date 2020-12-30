package github.gntodtndls156.nextlife.betweentrade;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class INV_Trade {
    boolean[] checks = new boolean[4];
    private Player playerMe, playerYou;
    private Inventory inventory;

    public INV_Trade(Player playerMe, Player playerYou) {
        this.playerMe = playerMe;
        this.playerYou = playerYou;
    }

    public void openInventory() {
        System.out.println("INV_Trade " + playerMe + " " + playerYou); // TODO CHECK
        System.out.println("INV_Trade " + inventory.getTitle());
        playerMe.openInventory(inventory);
        playerYou.openInventory(inventory);
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
