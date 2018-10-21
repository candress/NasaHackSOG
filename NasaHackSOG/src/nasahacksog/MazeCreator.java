package Game;

/**
 * This class creates all the information pertaining to a maze.  It utilized disjoint sets within a maze object.
 * The data structures within this class are passed by reference.
 *
 * @author Cameron Andress
 * @version 1.0 (09/02/2018)
 */
class MazeCreator {

    private int mazeLength; // this is the length of the maze
    private int mainPath; // root for main path
    private int mazeArraySize; // size of the paths array inside the maze object

    /**
     * This constructor accepts parameters, takes the information it needs from them and calls the below methods to
     * create a maze.
     *
     * @param theMaze   A maze object to be populated
     * @param percntg   Double - the percentage of the maze to be the main path (at minimum)
     * @param chance    Double - the chance a wall will drop between two paths if they are already in the same set
     */
    MazeCreator(Maze theMaze, double percntg, double chance){
        mazeLength = theMaze.getMazeLength();
        mazeArraySize = theMaze.getArraySize();
        mainPath = -1;
        createMap(theMaze, percntg/100, chance/100);
        theMaze.setMainPath(mainPath);
        populateChildArray(theMaze);
    }


    /**
     * This method applies an algorithm to systematically break down random walls in each "paths" object until
     * the size of the disjoint set = percentage x maze size.
     *
     * @param theMaze       A maze object to be populated
     * @param percentage    Double - the percentage of the maze to be the main path (at minimum)
     * @param chanceWall    Double - the chance a wall will drop between two paths if they are already in the same set
     */
    private void createMap(Maze theMaze, double percentage, double chanceWall){
        for(;;) {
            int random1 = (int) (Math.random() * (mazeArraySize)); // the first position to union
            int random2 = relative(random1, (int) (Math.random() * 4)); // the second position to try to union

            if (mazeCheck(random1, random2, mazeLength, mazeArraySize)){ // random2 is outside the array or not adjacent to random1. Restart.
                continue;
            }
            else{
                int find1 = theMaze.getSets().find(random1); // integer representing the root of random1
                int find2 = theMaze.getSets().find(random2); // integer representing the root of random2

                if (find1 == find2) { // already same root, chance to drop wall, then restart.
                    if (Math.random() <= chanceWall) { //if random number is at least = to chanceWall, drop a wall
                        //determine what direction to drop a wall, then drop it.  2 Method calls in 1
                        wallDrop(direction(random1, random2), random1, random2, theMaze.getPathArray());
                    }
                }
                else{ // not same root
                    theMaze.getSets().union(find1,find2); // union the two sets
                    wallDrop(direction(random1, random2), random1, random2, theMaze.getPathArray()); // drop walls between them
                    int size = theMaze.getSets().getSetSize(theMaze.getSets().find(random1))*-1; //get size of disjoint set at root
                    if(size >= (mazeArraySize)*(percentage)){ // if array reaches percentage, end.
                        mainPath = theMaze.getSets().find(random1);
                        break;
                    }
                }

            }
        }
    }

    /**
     * This method performs a find on the ith location in the array.
     * Then saves the ith location in its root's Array list
     *
     * @param theMaze   A maze object to be populated
     */
    private void populateChildArray(Maze theMaze){
        for (int i = 0; i < theMaze.getParentToChildArray().length; i++){ //iterate over parent to child array
            theMaze.getParentToChildArray()[theMaze.getSets().find(i)].add(i); //gdt the list of children at the root
        }
    }

    /**
     * This method breaks down walls in the "Path" class depending on the direction (W, N, E, S) determined.
     *
     * @param direction  String - cardinal direction (west, north, east, south)
     * @param rand1      integer - first random number
     * @param rand2      integer - second random number
     */
    private void wallDrop(String direction, int rand1, int rand2, Path[] pathArray){
        if (direction.equals("W")) {
            pathArray[rand1].setWestWall(false);
            pathArray[rand2].setEastWall(false);
        } else if (direction.equals("N")) {
            pathArray[rand1].setNorthWall(false);
            pathArray[rand2].setSouthWall(false);
        } else if (direction.equals("E")) {
            pathArray[rand1].setEastWall(false);
            pathArray[rand2].setWestWall(false);
        } else if (direction.equals("S")) {
            pathArray[rand1].setSouthWall(false);
            pathArray[rand2].setNorthWall(false);
        } else {
            System.out.println("  Should not have arrived here. " +
                    "This means that there are more than 4 options, thus random2 is not being generated" +
                    "properly");
        }
    }


    /**
     * This method determines which of the 4 directions (West, North, East, South) the 2nd random
     * number (option) will yield respective to the first (original) number.
     *
     * @param original  the first random number to reference
     * @param option    1 of 4 options given to determine location for 2nd random number
     * @return          integer for position of 2nd random number
     */
    private int relative(int original, int option){
        if(option == 0)         return original - 1; // go west
        else if (option == 1)   return original - mazeLength; // go north
        else if (option == 2)   return original + 1; // go east
        else                    return original + mazeLength; // go south
    }

    /**
     * This method checks if 2nd random number is within the same row as the first random number
     *  and if the 2nd random number is within the bounds of the array
     *
     * @param num1 first random number (integer)
     * @param num2 second random number (integer)
     * @return     boolean value, true if num2 is either not is same row as num1 or outside of array
     */
    private boolean mazeCheck(int num1, int num2, int mazeLength, int arraySize){
        if ( num2 >= arraySize || num2 < 0 ) return true; // num2 is outside top or bottom boundary of array
        else if (Math.abs(num1-num2) == 1 && (num1/ mazeLength != num2/ mazeLength)) return true; // not in same row
        else return false;
    }

    /**
     * This method is used to determine the position (West, North, East, South) of the second random
     * number respective to the first random number (original).
     *
     * @param original      1st random number to be referred to
     * @param second        2nd random number to determine position relative to original
     * @return              returns a string indicating position of second random number
     */
    private String direction(int original, int second) {
        if (original - 1 == second)                return "W"; // west
        else if (original + 1 == second)           return "E"; // east
        else if (original-second == mazeLength)    return "N"; // north
        else                                       return "S"; // south
    }

}
