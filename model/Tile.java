package model;
/** Represents a tile on the board
 * @author Deveza, Jerry King 
 * @author Rivera, Dustine Gian
 * @version 1.0
 */
public class Tile {

    private Plant plant = null;

    /**
     * This gets the plant occupying the tile
     * @return The plant occupying the tile
     */
    public Plant getPlant() {
        return plant;
    }

    /**
     * This modifies the plant occupying the tile
     * @param plant the plant to be assigned
     */
    public void setPlant(Plant plant) {
        this.plant = plant;
    }

    /**
     * This check if tile is occupied by a Plant Object
     * @return a booelan to check if tile is plant occupied
     */
    public boolean isPlantOccupied(){
        return plant != null;
    }

    /**
     * Removes the plant on the tile
     */
    public void removePlant(){
        this.plant = null;
    }

    public void toS(){
        System.out.println(plant);
    }
}
