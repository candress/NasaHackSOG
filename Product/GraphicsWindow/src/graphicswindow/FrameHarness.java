package graphicswindow;

/**
 * @author Tennyson Demchuk
 * @date 10.20.2018
 */
public class FrameHarness {
    
    // Global Variables
    boolean running;
    boolean scrolling; // whether the current window is scrolling around a background image
    int warping; //frames for warping
    GraphicsWindow w; // Graphics Window
    Picture background; // current background
    Picture hub;    // hubworld
    Picture warp;   // warp effect
    Picture warpOut;
    Picture[] dest; // destinations for zoom
    int nextdest=0;
    Picture[] msg;
    int nextmsg=0;
    Picture ho; // hubble overlay
    Picture poi;
    Picture anomaly;
    int count = 1;
    
    public FrameHarness()
    {
        // Local Variables
        Event[] e;
        Start s;

        running = true;
        s = new Start();
        while (!s.done)
        {
            try {
                Thread.sleep(50);
            }
            catch(Exception oih){}
        }
        
        w = new GraphicsWindow("GameTest",600,400,0,true);

        // Load supported events
        e = loadEvents();
        w.importEvents(e);
        
        // setup window to display the hub world
        setupHub();
        setupDest();
        loadMsg();
        displayHub();  
        run(e);
    }
    
    private void loadMsg()
    {
        msg = new Picture[5];
        msg[0] = new Picture("Encrypted.jpg");
        msg[0].scale(w.width,w.height);
        msg[1] = new Picture("1 Decrypted.jpg");
        msg[1].scale(w.width,w.height);
        msg[2] = new Picture("2 Decrypted.jpg");
        msg[2].scale(w.width,w.height);
        msg[3] = new Picture("Fully Decrypted.jpg");
        msg[3].scale(w.width,w.height);
        msg[4] = new Picture("FinalScreen.jpg");
        msg[4].scale(w.width,w.height);
    }
    
    private void setupDest()
    {
        anomaly = new Picture("POI.jpg");
        anomaly.scale(20,20);
        anomaly.addEvent(Event.MESSAGE); // create display next message event
        
        dest = new Picture[4];
        dest[0] = new Picture("Veil Nebula.jpg");
        dest[0].scale(w.width,w.height);
        dest[0].addOverlay(anomaly, 289, 152, true);
        dest[1] = new Picture("Lagoon Nebula.jpg");
        dest[1].scale(w.width,w.height);
        dest[1].addOverlay(anomaly, 418, 82, true);
        dest[2] = new Picture("CrabNebula.jpg");
        dest[2].scale(w.width,w.height);
        dest[2].addOverlay(anomaly, 398, 73, true);
        dest[3] = new Picture("11000ly.jpg");
        dest[3].scale(w.width,w.height);
        //dest[3].addOverlay(anomaly, 200, 200, true);
    }

    public void displayHub()
    {
        scrolling = true;
        if (nextmsg>1) outWarp();
        background = hub;
        warping=0; // constantly output background = no warp animation 
        w.clear();
        w.addOverlay(ho, (w.width/2)-(ho.width/2), 280, true); // set hubble overlay
        w.overlay(false); // display overlays
        w.centreCursor(true); // force mouse cursor to be centred in the screen
        if (nextmsg==0)message();
        w.overlay(true);
    }
    
    public void displayDest()
    {
        scrolling = false;
        w.overlay(false);
        warp(); // warp animation
        
        w.overlay(true);
        if (nextmsg==4) 
        {
            w.overlay(false);
            message();
        }
        background = dest[nextdest]; // set background image for this zoom destination
        
        //warping = 320;  //animate warp for 5 frames
        w.clear();
        w.centreCursor(false);
        nextdest++;
    }
    public void displayGame(){
        running=false;
        w.deactivate();
        if (count==1 && nextmsg == 1) {
                ComplexityPic p = new ComplexityPic();
                while (!p.done)
                {
                    try {
                        Thread.sleep(50);
                    }
                    catch(Exception oih){}
                }
                count++;
            }
        else if (count==2 && nextmsg==2){
            Minesweep m=new Minesweep();
            while (!m.done)
            {
                try {
                    Thread.sleep(50);
                }
                catch(Exception oih){}
            }
            count++;
        }
        else if (count==3&&nextmsg==3) {
            Puzzle p = new Puzzle();
            while (!p.done)
            {
                try {
                    Thread.sleep(50);
                }
                catch(Exception oih){}
            }
            count++;
        }
        running=true;
        w.setActive();
        message();
    }
    public void setupHub()
    {
        hub = new Picture("Home.jpg");
        warp = new Picture("Warp.jpg");
        warp.scale(w.width, w.height);
        warpOut = new Picture("VeilZoom.jpg");
        warpOut.scale(w.width, w.height);
        
        poi = new Picture("POI.jpg");//(20,20); // blank image
        poi.scale(50,50);
        poi.addEvent(Event.TRAVEL_TO_DEST);
        hub.addOverlay(poi, 528, 290, true);
        
        //setup hubble telescope overlay
        ho = new Picture("HubbleOverlay_Full.jpg"); //hubble overlay
        ho.scale(210, 160);
    }
    
    private void outWarp()
    {
        int fr = 820; // display for 320 frames
        long lastTime = System.currentTimeMillis();
        double diff = 1000.0/60.0; //could be int or long for integer values
        
        while(running) {
            if (System.currentTimeMillis()>=(lastTime+diff) && w!=null)
            {
                if (fr>0 && fr < 321) {
                    w.set(warpOut);
                    w.update();
                    fr--;
                }
                else if (fr > 320) {
                    w.set(background);
                    w.update();
                    fr--;
                }
                else break;
            }
        }
    }
    
    private void warp()
    {
        int fr = 320; // display for 320 frames
        long lastTime = System.currentTimeMillis();
        double diff = 1000.0/60.0; //could be int or long for integer values
        
        while(running) {
            if (System.currentTimeMillis()>=(lastTime+diff) && w!=null)
            {
                if (fr>0) {
                    w.set(warp);
                    w.update();
                    fr--;
                }
                else break;
            }
        }
    }
    
    public void message()
    {
        if (nextmsg<5){
            int fr = 2000; // display for 2000 frames
            long lastTime = System.currentTimeMillis();
            double diff = 1000.0/60.0; //could be int or long for integer values

            while(running) {
                if (System.currentTimeMillis()>=(lastTime+diff) && w!=null)
                {
                    if (fr>0) {
                        w.set(msg[nextmsg]);
                        w.update();
                        fr--;
                    }
                    else break;
                }
            }
            nextmsg++;
            if (nextmsg<5) displayHub();//if (nextmsg<3) displayHub();
            //else outWarp();
        }
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
                    //displayGame(count);
                }
                catch (Exception e){}
            }
        }
    }
    
    private Event[] loadEvents()
    {
        Event[] e = new Event[4];
        e[0] = new CloseGameEvent();
        e[1] = new DecentralizeMouseEvent();
        e[2] = new TravelToEvent(); 
        e[3] = new MessageEvent();
        return e;
    }
    
    public static void main(String[] args)
    {
        FrameHarness fh = new FrameHarness();
    }
}