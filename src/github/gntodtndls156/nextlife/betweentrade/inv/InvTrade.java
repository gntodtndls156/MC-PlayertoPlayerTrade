package github.gntodtndls156.nextlife.betweentrade.inv;

import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

public class InvTrade {
    private Inventory inventory;
    private boolean[] checks = new boolean[4];
    private Player player$0, player$1;

    // TODO - 생성자

    public Inventory getInventory() {
        return inventory;
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
}
