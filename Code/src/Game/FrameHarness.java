package Game;

import java.util.ArrayList;

/**
 * @author Tennyson Demchuk & Cameron Andress
 * @date 10.20.2018
 */
public class FrameHarness {
    
    // Global Variables
    boolean running;
    boolean scrolling; // whether the current window is scrolling around a background image
    GraphicsWindow w; // Graphics Window
    Picture background; // current background
    Picture hub;    // hubworld
    private ArrayList<Location> locations = new ArrayList<>(); // destinations for zoom
    Picture ho; // hubble overlay
    
    public FrameHarness()
    {
        // Local Variables
        Event[] e;
        
        running = true;
        
        w = new GraphicsWindow("GameTest",600,400,0,true);

        // Load supported events
        e = loadEvents();
        w.importEvents(e);

        // setup window to display the hub world

        // display starting page
        // if user clicks start, break out of loop and start program
        // else, if user clicks close, end program

        // initialize locations array above with all the locations and their pictures
            // location 1 gets:
                // Background = Veil Nebula 1,500 to 2,100 light-years
                // anomaly = TBD;
                // zoomOut = Veil Zoom out;
                // message = TBD;
            // location 2 gets:
                // Background = Lagoon Nebula 4,000 light-years
                // anomaly = TBD;
                // zoomOut = Lagoon Zoom out;
                // message = TBD;
            // location 3 gets:
                // Background = Crab Nebula 6,500 light-years
                // anomaly = TBD;
                // zoomOut = Crab Zoom out;
                // message = TBD;
            // location 4 gets:
                // Background = 11 000 light-years
                // anomaly = TBD;
                // message = TBD;




        setupHub();
        displayHub();
        // display intro text box, disable controls (except for next button or enter)

        run(e);

        // enable controls, allow user to pan, find coordinates

        // enter button displays warp, then sends user to level 1 (when on correct spot). display level 1 background (see location object in array)

        // user can use mouse to find anomaly

        // user can click anomaly

        // new window appear with blown up version of anomaly

        // window has button to process image (de-blurifies)

        // message window appears stating info will be entered.

        // display zoom back

        // User plays minigame 1 (mazes) new window, wins, allowed to find spot and zoom to next spot.

        // repeat this until last stage with minigame 2 (crossword) and 3 (minesweeper)

        // last stage we find out there was an explosion and the species no longer exists.

        // The end




    }



    public void displayHub()
    {
        scrolling = true;
        background = hub;
        w.clear();
        w.addOverlay(ho, (w.width/2)-(ho.width/2), 280, true); // set hubble overlay
        w.overlay(true); // display overlays
        w.centreCursor(true); // force mouse cursor to be centred in the screen
    }
    
    public void displayDest(String bkgrd)
    {
        scrolling = false;
        background = new Picture(bkgrd); // set background image for this zoom destination
        w.clear();
        w.centreCursor(false);
    }
    
    public void setupHub()
    {
        hub = new Picture("Home.jpg");
        
        Picture test = new Picture("tobey.jpeg");
        test.scale(150, 150);
        test.addEvent(Event.DECENTRALIZE_MOUSE_EVENT);
        hub.addOverlay(test, 20, 20, false);
        
        //setup hubble telescope overlay
        ho = new Picture("Hubble Telescope 2 - full.jpg"); //hubble overlay
        ho.scale(300, 160);
    }
    
    public void setupDest()
    {
        
    }
    
    public void run(Event[] ev)
    {
        long lastTime = System.currentTimeMillis();
        double diff = 1000.0/60.0; //could be int or long for integer values
        int eventcode;
        
        while (running)
        {
            
            if (System.currentTimeMillis()>=(lastTime+diff) && w!=null)
            {
                try {
                    if (scrolling) w.setSub(background); //if (w.l || w.r || w.u || w.d) 
                    else w.set(background);
                    w.update();
                    eventcode = w.getEventCode();
                    if (eventcode>0 && ev!=null)
                    {
                        ev[eventcode-1].action(this);   // carry out specific event
                        w.clearEventCode();             // clear eventcode once event has been carried out
                    }
                }
                catch (Exception e){}
            }
        }
    }
    
    private Event[] loadEvents()
    {
        Event[] e = new Event[2];
        e[0] = new CloseGameEvent();
        e[1] = new DecentralizeMouseEvent();
        return e;
    }
    
    public static void main(String[] args)
    {
        FrameHarness fh = new FrameHarness();
    }
}