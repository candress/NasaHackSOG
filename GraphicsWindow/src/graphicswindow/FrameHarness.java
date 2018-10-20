package graphicswindow;

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
        Picture sub; //subset of hub image being shown
        
        running = true;
        gameWindow = new GraphicsWindow("GameTest",500,500,0);
        w = gameWindow;
        hub = new Picture("hubworld_test.jpg","hub");
        //sub = new Picture(hub.);
        
        hub.scale(gameWindow.width,gameWindow.height);
        gameWindow.setImg(hub);
        
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
                w.update();
            }
        }
    }
    
    public static void main(String[] args)
    {
        FrameHarness fh = new FrameHarness();
    }
}