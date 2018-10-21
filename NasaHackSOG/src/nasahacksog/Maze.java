package Game;

import java.util.ArrayList;

/**
 * This class is a wrapper class meant to hold all the information pertaining to a maze.  It utilized disjoint sets
 * and creates a path array accordingly. See disjoint set class for more info.  The arrays within this class are
 * passed by reference.
 *
 * @author Cameron Andress
 * @version 1.0 (09/02/2018)
 */
class Maze {

    private DisJSets sets; // object to modify and find the roots of the set
    private Path[] pathArray; // to hold boolean values representing whether a position has been sets
    private int mazeLength; // this is the length of the maze
    private int mazeHeight; // this is the width or height of the maze
    private int mainPath; // root for main path
    private ArrayList<Integer>[] parentToChildArray; // list of roots and their children

    /**
     * This constructor takes in the maze length and maze height. Then it initiates a set object and 2 other lists.
     *
     * @param mazeLen   Integer - length of maze
     * @param mazeHgt   Integer - width of maze
     */
    Maze(int mazeLen, int mazeHgt){
        mazeHeight = mazeHgt;
        mazeLength = mazeLen;
        sets = new DisJSets(mazeLength * mazeHeight);
        pathArray = new Path[mazeLength * mazeHeight];
        parentToChildArray = new ArrayList[pathArray.length];
        initialize();
        mainPath = -1;
    }


    /**
     * This method initializes an array of paths
     */
    private void initialize(){
        for (int i = 0; i < pathArray.length; i++){
            pathArray[i] = new Path();
            parentToChildArray[i] = new ArrayList<>();
        }
    }

    /**
     * Method to set the main path (root containing the "main path")
     *
     * @param mainPath  Integer - root representing where the main path is in the parent to child array
     */
    void setMainPath(int mainPath) {
        this.mainPath = mainPath;
    }


    /**
     * Method to get integer representing the root of the main path
     *
     * @return  integer - main path root
     */
    int getMainPath() {
        return mainPath;
    }

    /**
     * Method to get the sets object
     *
     * @return  DisJSets object containing the sets array
     */
    DisJSets getSets() {
        return sets;
    }

    /**
     * Method to return the path array containing all the path objects
     *
     * @return  Path array containing path objects
     */
    Path[] getPathArray() {
        return pathArray;
    }

    /**
     * Method to return the parentToChildArray containing all the roots and their children
     *
     * @return  parentToChildArray
     */
    ArrayList<Integer>[] getParentToChildArray() {
        return parentToChildArray;
    }

    /**
     * Method to return the path array size
     *
     * @return  integer - path array size
     */
    int getArraySize() {
        return pathArray.length;
    }

    /**
     * Method to return the maze length
     *
     * @return  integer maze length
     */
    int getMazeLength() {
        return mazeLength;
    }

    /**
     * Method to return the maze height
     *
     * @return  integer maze height
     */
    int getMazeHeight() {
        return mazeHeight;
    }
}
