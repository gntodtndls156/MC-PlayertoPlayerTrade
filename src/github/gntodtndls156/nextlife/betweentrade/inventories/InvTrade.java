package github.gntodtndls156.nextlife.betweentrade.inventories;

import org.bukkit.entity.Player;

public class InvTrade {
    private boolean[] checks = new boolean[4];
    Player playerMe, playerYou;

    final int[] playerMeSlot = new int[]{0, 1, 2, 3, 9, 10, 11, 12, 18, 19, 20, 21, 27, 28, 29, 30};
    final int[] playerYouSlot = new int[]{5, 6, 7, 8, 14, 15, 16, 17, 23, 24, 25, 26, 32, 23, 24, 35};

    final int[] lineLeft = new int[]{36, 37, 38};
    final int[] lineRight = new int[]{44, 43, 42};
    final int[] lineCenter = new int[]{40, 31, 22, 13, 4};


    // TODO TradeInv.java를 보며 작성



    // TRASH

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

    public Player getPlayerMe() {
        return playerMe;
    }
    public Player getPlayerYou() {
        return playerYou;
    }
    public void setPlayerYou(Player playerYou) {
        this.playerYou = playerYou;
    }
    public void setPlayerMe(Player playerMe) {
        this.playerMe = playerMe;
    }
}
