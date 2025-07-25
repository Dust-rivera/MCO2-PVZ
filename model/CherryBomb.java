package model;
/**
 * Represents a Cherry Bomb plant that explodes and damages zombies in an area
 * @author Deveza, Jerry King 
 * @author Rivera, Dustine Gian
 * @version 1.0
 */
public class CherryBomb extends Plant implements PlantMechanics {
    public static int cherrybombCD;
    private static final int CHERRY_REGEN = 30; // to be changed
    public CherryBomb(int x, int y) {
        super(150, 9999, x, y, 0, 1, 9999, 9999); // Massive damage, instant
    }

    /**
     * Triggers the explosion effect (to be implemented in Board/Controller)
     */
    @Override
    public void plantTurn(Board board) {
        this.increaseTick();

        if (cherrybombCD != 0) {
            cherrybombCD--;
        }

        if (this.getTick() % this.getSPEED() == 0) {
            // Trigger explosion effect
            board.triggerCherryBombExplosion(this.getXPosition(), this.getYPosition());
            this.setTick(0); // Reset tick after explosion
        }

    }

    /**
     * Gets the cooldown for Cherry Bomb
     * @return cooldown in ticks
     */
    public static int getCherryBombCD() {
        return cherrybombCD;
    }
    public static int getRegen() {
        return CHERRY_REGEN;
    }
   
} 