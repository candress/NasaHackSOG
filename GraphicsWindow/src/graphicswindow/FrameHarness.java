package graphicswindow;

/**
 * @author Tennyson Demchuk
 * @date 10.20.2018
 */
public class FrameHarness {
    
    // Global Variables
    boolean running;
    boolean scrolling; // whether the current window is scrolling around a background image
    GraphicsWindow w; // Graphics Window
    Picture background; // current background
    Picture hub;    // hubworld
    Picture[] dest; // destinations for zoom
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
        setupHub();
        displayHub();
        
        run(e);
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
    
    public void displayDest()
    {
        scrolling = false;
        //background = ...; // set background image for this zoom destination
        w.clear();
        w.centreCursor(false);
    }
    
    public void setupHub()
    {
        hub = new Picture("hubworld_test2.jpg","hub");
        
        Picture test = new Picture("tobey.jpeg","tobey");
        test.scale(150, 150);
        test.addEvent(Event.DECENTRALIZE_MOUSE_EVENT);
        hub.addOverlay(test, 20, 20, false);
        
        //setup hubble telescope overlay
        ho = new Picture("HubbleOverlay_Full.jpg","ho"); //hubble overlay
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