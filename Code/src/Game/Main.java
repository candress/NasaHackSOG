package Game;

import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.LinkedList;
import java.util.Scanner;

/**
 * This class is responsible for asking and obtaining all of the information needed to provide instructions to the
 * rest of the program. This class then delegates most of the work onto other classes which perform their roles
 * accordingly.  This class also randomly generates a boss encounter and runs continuously until the user tells it
 * to stop.
 *
 * @author Cameron Andress
 * @version 1.0 (09/02/2018)
 */
class Main {
    private int score = 0;
//    private Picture background = new Picture();
    private ArrayList<Location> locations = new ArrayList<>();
//    private ArrayList<>


    private Main() {
        // create start page
        // if user clicks start, break out of loop and start program
        // else, if user clicks close, end program

        // display location 0 (home) picture
        // display intro text box, disable controls (except for next button or enter)
        // enable controls, allow user to pan, find coordinates
        // enter button sends user to level 1

        // iterate to next location in location array, level 1
        // load its background image, check for click at anomaly, load anomaly, check for click of photo process button
        // after last anomaly image processed, display message image, then display zoom out image

        // display home image, increment score, messages, minigame (how keep track of minigames?), message from commander setting location (choose nebula again)

        // repeat.

    }

    //Methods ---------------------------------------------------------------------------

    /**
     * This method is responsible for asking the user to provide all of the information required to start building
     * a maze object.  It then calls the MazeCreator class to implement the information provided.
     *
     * @return  a fully realized maze.
     */
    private void mazeInput(){

    }


    public static void main(String[] args) {
        Main d = new Main();
    }
}

