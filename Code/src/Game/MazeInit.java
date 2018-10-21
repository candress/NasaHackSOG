package Game;

/**
 * This class is responsible for asking and obtaining all of the information needed to provide instructions to the
 * rest of the program. This class then delegates most of the work onto other classes which perform their roles
 * accordingly.  This class also randomly generates a boss encounter and runs continuously until the user tells it
 * to stop.
 *
 * @author Cameron Andress
 * @version 1.0 (09/02/2018)
 */
class MazeInit {

//    private Scanner input; //input for the user

    private MazeInit() {
        Maze theMaze; // a maze object to be populated
        String name = "Maze"; // the name of the file when written

        // creating maze ---------------
        theMaze = mazeInput(); // calling the mazeInput method and setting the result to the maze object
        new SVGWriter(theMaze, name); // printing the maze only
    }

    //Methods ---------------------------------------------------------------------------

    /**
     * This method is responsible for asking the user to provide all of the information required to start building
     * a maze object.  It then calls the MazeCreator class to implement the information provided.
     *
     * @return  a fully realized maze.
     */
    private Maze mazeInput(){
        Maze theMaze;
        int dunLength = 15; //the length of the dungeon
        int percentMainPath = 100; //the percentage of the maze which will be connected
        int chanceWall = 0; // the chance a disconnected wall will occur in the maze

        theMaze = new Maze(dunLength, dunLength); // an under-developed maze
        new MazeCreator(theMaze, percentMainPath, chanceWall); // creating a MazeCreator class to develop the maze
        return theMaze; // return the developed maze
    }


    public static void main(String[] args) {
        MazeInit d = new MazeInit();
    }
}

