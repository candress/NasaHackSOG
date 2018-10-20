package graphicswindow;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * @author Tennyson Demchuk
 * @date 10.20.2018
 */
public class FrameHarness {
    
    // Global Variables
    boolean running;
    GraphicsWindow w; // Reference to current Graphics Window
    Picture hub;
    
    public FrameHarness()
    {
        // Local Variables
        GraphicsWindow gameWindow;
        
        running = true;
        gameWindow = new GraphicsWindow("GameTest",600,400,0);
        w = gameWindow;
        hub = new Picture("hubworld_test.jpg","hub");
        //System.out.println(hub.width + " " + hub.height);
        w.setSub(hub); // display subset of larger hubworld image
        
        run();
    }

    public void run()
    {
        long lastTime = System.currentTimeMillis();
        double diff = 1000.0/60.0; //could be int or long for integer values
        
        while (running)
        {
            if (System.currentTimeMillis()>=(lastTime+diff))
            {
                try {
                    if (w.l || w.r || w.u || w.d) w.setSub(hub);
                    w.update();
                }
                catch (Exception e){}
            }
        }
    }
    
    public static void main(String[] args)
    {
        FrameHarness fh = new FrameHarness();
    }
}