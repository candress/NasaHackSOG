package Game;

/**
 * This class is meant to be used in an array and expresses whether certain node has all 4 walls.
 *
 * @author Cameron Andress
 * @version 1.0 (09/02/2018)
 */
class Path {
    private boolean westWall; // True false, node has west wall.
    private boolean northWall; // True false, node has north wall.
    private boolean eastWall; // True false, node has east wall.
    private boolean southWall; // True false, node has south wall.

    /**
     * This constructor initialized the class variables as true
     */
    Path(){
        westWall = true; // wall exists = true
        northWall = true; // wall exists = true
        eastWall = true; // wall exists = true
        southWall = true; // wall exists = true

    }

    /**
     * Method that returns the value inside the west wall.
     *
     * @return  Boolean value for west wall
     */
    boolean hasWestWall() {
        return westWall;
    }

    /**
     * Method that returns the value inside the north wall.
     *
     * @return  Boolean value for north wall
     */
    boolean hasNorthWall() {
        return northWall;
    }

    /**
     * Method that returns the value inside the east wall.
     *
     * @return  Boolean value for east wall
     */
    boolean hasEastWall() {
        return eastWall;
    }

    /**
     * Method that returns the value inside the south wall.
     *
     * @return  Boolean value for south wall
     */
    boolean hasSouthWall() {
        return southWall;
    }

    /**
     * Method that sets the boolean value for the designated wall
     *
     * @param ww    Boolean value for designated wall
     */
    void setWestWall(boolean ww) {
        this.westWall = ww;
    }

    /**
     * Method that sets the boolean value for the designated wall
     *
     * @param nw    Boolean value for designated wall
     */
    void setNorthWall(boolean nw) {
        this.northWall = nw;
    }

    /**
     * Method that sets the boolean value for the designated wall
     *
     * @param ew    Boolean value for designated wall
     */
    void setEastWall(boolean ew) {
        this.eastWall = ew;
    }

    /**
     * Method that sets the boolean value for the designated wall
     *
     * @param sw    Boolean value for designated wall
     */
    void setSouthWall(boolean sw) {
        this.southWall = sw;
    }
}
