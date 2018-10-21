package Game;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * This class iterates over all data types in the Dungeon class (or its subtypes) and writes instructions to an SVG
 * file to visualize all of the data.
 *
 * @author Cameron Andress
 * @version 1.0 (09/02/2018)
 */
class SVGWriter {
    private int dunHeight; // height of the maze
    private int dunLength; // length of the maze
    private PrintWriter writer; // writer to output SVG file
    private static final int SCALE = 15; // scale of the dungeon
    private ArrayList<Integer> mainList; // main path array

    // Constructors --------------------------------------------------------------
    /**
     * This constructor is only responsible for calling the start method and printing a maze, then closing the file.
     *
     * @param aMaze Maze object to print
     * @param name  String - name of the file
     */
    SVGWriter(Maze aMaze, String name) {
        start(aMaze, name);
        mazeToSVG(aMaze.getPathArray());
        writer.println("</svg>");
        writer.close();
    }

//    /**
//     * This constructor is only responsible for calling the start method, printing a maze, printing the chambers,
//     * then closing the file.
//     *
//     * @param aMaze     Maze object to print
//     * @param chambers  ChamberList object to iterate over and draw the chambers within
//     * @param name      String - name of the file
//     */
//    SVGWriter(Maze aMaze, ChamberList chambers, String name){
//        mainList = aMaze.getParentToChildArray()[aMaze.getMainPath()];
//        start(aMaze, name);
//        mazeToSVG(aMaze.getPathArray());
//        if (chambers != null) addChambers(chambers.getPlacedChambers()); //if chambers exist, add them
//        writer.println("</svg>");
//        writer.close();
//    }
//
//
//    /**
//     * This constructor is responsible for calling the start method, printing everything in the Dungeon object,
//     * then closing the file.  Everything includes the Maze, Chambers, POIs, Doors, and potentially boss battles
//     *
//     * @param theDungeon    Dungeon object - wrapper class behaving as a more complex data type
//     */
//    SVGWriter(Dungeon theDungeon){
//        Maze aMaze = theDungeon.getTheMaze(); // getting the maze
//        mainList = aMaze.getParentToChildArray()[aMaze.getMainPath()]; // getting the main list of indices
//        start(theDungeon.getTheMaze(), theDungeon.getName());
//        mazeToSVG(aMaze.getPathArray());
//        addChambers(theDungeon.getChambers().getPlacedChambers());
//        addDoors(theDungeon.getChambers().getPlacedChambers());
//        addPOIs(theDungeon.getChambers().getPlacedChambers());
//        addBoss(theDungeon.getChambers().getPlacedChambers());
//        writer.println("</svg>");
//        writer.close();
//    }


    // Methods ----------------------------------------------------------------------------------

    /**
     * This method writes the starting block of text/instructions to an SVG file
     *
     * @param aMaze Maze object to get dimensions
     * @param name  String - name of the file
     */
    private void start(Maze aMaze, String name){
        dunLength = aMaze.getMazeLength(); //populating the class variable
        dunHeight = aMaze.getMazeHeight();
        try {
            writer = new PrintWriter( name + ".svg");
        }catch (FileNotFoundException e){
            System.out.println("Issue found. Please retry.");
        }
        writer.println("<svg xmlns=\"http://www.w3.org/2000/svg\"\n" +
                "    xmlns:xlink=\"http://www.w3.org/1999/xlink\">");
    }

    /**
     * This method checks to see if the main list is null or not.  If null, call hasNullMainList method. If not,
     * first call hasMainList method, then call hasNullMainList method.
     *
     * Note: I tried only printing the main list walls, but the SVG ended up looking odd.  So I instead opted to
     * simply draw all of the walls and have the black background cancel out the non-main path walls.
     *
     * @param pathArray Path array containing all the path objects
     */
    private void mazeToSVG(Path[] pathArray){
        if(mainList == null) { //drawing entire maze
            hasNullMainList(pathArray);
        }else{ //drawing main path
            hasMainList();
            hasNullMainList(pathArray);
        }
    }

    /**
     * This method draws the entire maze by translating the information in the pathArray and the SCALE class variable
     * into instruction in the SVG file to create lines.
     *
     * @param pathArray Path array containing all the path objects
     */
    private void hasNullMainList(Path[] pathArray){
        for (int i = 0; i < pathArray.length; i++) {
            int x = i % dunLength; // to translate into grid x direction
            int y = i / dunLength; // to translate into grid y direction
            if (pathArray[i].hasWestWall()) {
                writer.println("    <line x1=\"" + x * SCALE + "\"  y1=\"" + y * SCALE + "\" x2=\"" + x * SCALE +
                        "\"   y2=\"" + (y + 1) * SCALE + "\" style=\"stroke:black;stroke-width:1\"/>");
            }
            if (pathArray[i].hasNorthWall()) {
                writer.println("    <line x1=\"" + x * SCALE + "\"  y1=\"" + y * SCALE + "\" x2=\"" + (x + 1) * SCALE +
                        "\"   y2=\"" + y * SCALE + "\" style=\"stroke:black;stroke-width:1\"/>");
            }
            if (pathArray[i].hasEastWall()) {
                writer.println("    <line x1=\"" + (x + 1) * SCALE + "\"  y1=\"" + y * SCALE + "\" x2=\"" + (x + 1) * SCALE +
                        "\"   y2=\"" + (y + 1) * SCALE + "\" style=\"stroke:black;stroke-width:1\"/>");
            }
            if (pathArray[i].hasSouthWall()) {
                writer.println("    <line x1=\"" + x * SCALE + "\"  y1=\"" + (y + 1) * SCALE + "\" x2=\"" + (x + 1) * SCALE +
                        "\"   y2=\"" + (y + 1) * SCALE + "\" style=\"stroke:black;stroke-width:1\"/>");
            }
        }
    }

    /**
     * This method lays a black square down over the entire area of the Maze (before the maze is written) and then
     * traverses over the main path to lay down white squares.
     */
    private void hasMainList(){
        writer.println("    <rect x=\"" + 0 + "\"  y=\"" + 0 + "\" height=\"" + dunHeight * SCALE + "\" width=\"" +
                dunLength * SCALE +"\"      style=\"stroke:black; fill:black\"/>");
        for (int i = 0; i < mainList.size(); i++) { // drawing white squares on main path
            int x = mainList.get(i) % dunLength; // to translate into grid x direction
            int y = mainList.get(i) / dunLength; // to translate into grid y direction

            writer.println("    <rect x=\"" + x * SCALE + "\"  y=\"" + y * SCALE + "\" height=\"" + SCALE +
                    "\" width=\"" + SCALE +"\"      style=\"stroke:white; fill:white\"/>");
        }
    }

//    /**
//     * This method iterates over the placed chamber array and writes instructions to the SVG file to render the chambers
//     *
//     * @param chambers  ArrayList of chambers to display
//     */
//    private void addChambers(ArrayList<Chamber> chambers){
//        for (Chamber i : chambers){
//            int x = i.getLocation() % dunLength; // to translate into grid x direction
//            int y = i.getLocation() / dunLength; // to translate into grid y direction
//            int height = i.getHeight(); // height of chamber
//            int length = i.getLength(); // length of chamber
//            writer.println("    <rect x=\"" + x * SCALE + "\"  y=\"" + y * SCALE + "\" height=\"" + height * SCALE +
//                    "\" width=\"" + length * SCALE + "\"      style=\"stroke:blue; fill:white; stroke-width: 2;\"/>");
//        }
//    }
//
//    /**
//     * This method iterates over the placed POI array and writes instructions to the SVG file to render the POIs
//     *
//     * @param chambers  ArrayList of chambers to display
//     */
//    private void addPOIs(ArrayList<Chamber> chambers){
//
//        for (Chamber i : chambers){
//            if (!(i.getEncounters().isEmpty())) { // while the encounter list is not empty (in the event a chamber is not placed)
//                for (POI j : i.getEncounters()) {
//                    int x = j.getLocation() % dunLength; // to translate into grid x direction
//                    int y = j.getLocation() / dunLength; // to translate into grid y direction
//                    int type = j.getEncounterType(); // getting the encounter type of the POI to display
//                    writer.println("    <text x=\"" + x * SCALE + "\" y=\"" + (y + 1) * SCALE + "\" style=\"text-anchor: start\">" + type + "</text>");
//                }
//            }
//        }
//    }
//
//    /**
//     * This method iterates over the placed Door array and writes instructions to the SVG file to render the doors
//     *
//     * @param chambers  ArrayList of chambers to display
//     */
//    private void addDoors (ArrayList<Chamber> chambers){
//
//        for (Chamber c : chambers){
//            if (!(c.getDoors().isEmpty())) { // while the doors list is not empty (in the event a chamber is not placed)
//                for (Door d : c.getDoors()) {
//                    int x = d.getLocation() % dunLength; // to translate into grid x direction
//                    int y = d.getLocation() / dunLength; // to translate into grid y direction
//                    String color = "white";
//                    if (d.isSecretDoor()) color = "red";
//                    if (d.getDirection().equals("west")) {
//                        writer.println("    <line x1=\"" + x * SCALE + "\"  y1=\"" + y * SCALE + "\" x2=\"" + x * SCALE +
//                                "\"   y2=\"" + (y + 1) * SCALE + "\" style=\"stroke:"+color+";stroke-width:3\"/>");
//                    }
//                    if (d.getDirection().equals("north")) {
//                        writer.println("    <line x1=\"" + x * SCALE + "\"  y1=\"" + y * SCALE + "\" x2=\"" + (x + 1) * SCALE +
//                                "\"   y2=\"" + y * SCALE + "\" style=\"stroke:"+color+";stroke-width:3\"/>");
//                    }
//                    if (d.getDirection().equals("east")) {
//                        writer.println("    <line x1=\"" + (x + 1) * SCALE + "\"  y1=\"" + y * SCALE + "\" x2=\"" + (x + 1) * SCALE +
//                                "\"   y2=\"" + (y + 1) * SCALE + "\" style=\"stroke:"+color+";stroke-width:3\"/>");
//                    }
//                    if (d.getDirection().equals("south")) {
//                        writer.println("    <line x1=\"" + x * SCALE + "\"  y1=\"" + (y + 1) * SCALE + "\" x2=\"" + (x + 1) * SCALE +
//                                "\"   y2=\"" + (y + 1) * SCALE + "\" style=\"stroke:"+color+";stroke-width:3\"/>");
//                    }
//                }
//            }
//        }
//    }
//
//    /**
//     * This method iterates over the placed Boss array and writes instructions to the SVG file to render the Bosses
//     *
//     * @param chambers  ArrayList of chambers to display
//     */
//    private void addBoss(ArrayList<Chamber> chambers){
//
//        for (Chamber i : chambers){
//            if (!(i.getBossList().isEmpty())) { // while the boss list is not empty
//                for (Boss b : i.getBossList()) {
//                    int x = b.getLocation() % dunLength; // to translate into grid x direction
//                    int y = b.getLocation() / dunLength; // to translate into grid y direction
//                    int type = b.getEncounterType(); // getting the encounter type of the Boss to display
//                    writer.println("    <text x=\"" + x * SCALE + "\" y=\"" + y * SCALE + "\" style=\"text-anchor: middle\"> BOSS </text>");
//                    writer.println("    <text x=\"" + x * SCALE + "\" y=\"" + (y + 1) * SCALE + "\" style=\"text-anchor: middle\">" + type + "</text>");
//                    writer.println("    <text x=\"" + x * SCALE + "\" y=\"" + (y + 2) * SCALE + "\" style=\"text-anchor: middle\"> BATTLE!!! </text>");
//                }
//            }
//        }
//    }
}
