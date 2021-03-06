package graphicswindow;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.image.*;
import javax.swing.*;

/**
 *
 * @author Tennyson Demchuk
 * @date 10.19.2018
 */
public class GraphicsWindow implements KeyListener, MouseListener {

    // Frame Object
    private JFrame f;
    private boolean isActive;
    // Location Data
    int xloc, yloc;
    // Image Data
    private int[] pixels;
    private BufferedImage img;
    private int[] imgOverlay;
    boolean showOverlay;
    //custom event data
    private int[] eventMap;
    private int[] oEventMap;
    private Event[] events; // specific events to be run
    private int eventcode;  // what event is to be run
    //custom cursors
    private Cursor blankCursor;
    private Cursor selectCursor;
    private Cursor hoverCursor;
    boolean centreC;
    //Movement control
    boolean l,r,u,d;
    final double MOVESPEED = 0.5;
    // Size data
    int width, height;
    //Subimage offset coordinates
    private double xoff,yoff;
    // Class constants
    final int FULLSCREEN = 1;
    
    public GraphicsWindow(String t,int w, int h, int fs, boolean active) //frame title, min width, min height, fullscreen mode, active window
    {
        f = new JFrame();
        isActive = active;
        
        if (fs==1) {
            this.width = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getWidth();
            this.height = GraphicsEnvironment.getLocalGraphicsEnvironment().getDefaultScreenDevice().getDisplayMode().getHeight();
        }
        else {
            this.width = w;
            this.height = h;
        }
        
        img = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_RGB); // initializes the image that will be drawn to the screen
        pixels = ((DataBufferInt)img.getRaster().getDataBuffer()).getData();
        //System.out.println("width: " + width + ", height: " + height);
        //System.out.println("PIxels length: " + pixels.length);
        
        imgOverlay = new int[pixels.length]; // all blank img overlay
        showOverlay = true;
        
        eventMap = new int[pixels.length];
        oEventMap = new int[pixels.length]; //overlay events
        events = null;
        clearEventCode();
        
        //setup cursors
        setupCursors();
        
        f.setTitle(t);
        f.setSize(width, height);
        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setResizable(false);
        f.setUndecorated(true);
        f.setVisible(isActive);
        
        if (isActive) {
            f.addKeyListener(this);
            f.addMouseListener(this);
        }

        
        xloc = f.getLocation().x;
        yloc = f.getLocation().y;
        
        xoff=0;yoff=0;
        l=false;r=false;u=false;d=false;
        
        //show cursor
        cursor(true);
        centreC=false;
        
        // clears screen
        clear();
    }
    
    public int getEventCode()
    {
        return eventcode; // returns the event code
    }
    
    public void clearEventCode()
    {
        eventcode = 0;
    }
    
    public boolean isActive()
    {
        return isActive;
    }
    
    public void setActive()
    {
        if (isActive) return;
        isActive = true;
        f.setVisible(true);
        f.addKeyListener(this);
        f.addMouseListener(this);
    }
    
    public void deactivate()
    {
        if (!isActive) return;
        isActive = false;
        f.setVisible(false);
        f.removeKeyListener(this);
        f.removeMouseListener(this);
    }
    
    //clears screen to black
    public void clear()
    {
        for (int i=0;i<pixels.length;i++){
            pixels[i]=0;
            imgOverlay[i]=0;
            eventMap[i]=0;
            oEventMap[i]=0;
        }
    }
    
    // add custom events that can be run
    public void importEvents(Event[] e)
    {
        events = e;
    }
    
    //returns an updated dimension containing the frame coordinates in pixels relative to the display 
    public Dimension getLocation()
    {
        xloc = f.getLocation().x;
        yloc = f.getLocation().y;
        return new Dimension(xloc,yloc);
    }
    
    // turns mouse cursor on or off. off by default
    public void cursor(boolean on)
    {
        if (on){
            f.getContentPane().setCursor(selectCursor);
        }
        else {
            //Blank out cursor while in the game window
            f.getContentPane().setCursor(blankCursor);
        }
    }
    
    /**
     * sets whether or not the cursor is shown on the centre of the screen
     * @param on 
     */
    public void centreCursor(boolean on)
    {
        centreC=on;
        
        if (centreC) {
            try {
                Robot r = new Robot();
                r.mouseMove(f.getLocationOnScreen().x + (this.width / 2), f.getLocationOnScreen().y + (this.height / 2));
            } catch (AWTException e){}
            
        }
    }
    
    private void setupCursors()
    {
        BufferedImage blankCursorImage = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB); //blank image
        BufferedImage selectImage = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        BufferedImage hoverImage = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        
        int cSize = 5; //cursor size
        
        for (int drawx=0; drawx < cSize; drawx++) {
            
            //if the current x coordinate isnt the centre of the screen, then the height of the crosshair in that pixel column will be maxdrawy
            int maxdrawy = 1;
            //otherwise, the height will be cSize pixels
            if (drawx == cSize / 2) maxdrawy = cSize;
            //draws the crosshair to the pixel array
            for (int drawy = (int) (Math.ceil((double)cSize/2.0)-maxdrawy/2); drawy < Math.ceil((double)cSize/2.0) + maxdrawy; drawy++) {
                selectImage.setRGB(drawx,drawy,Color.GREEN.getRGB());
                hoverImage.setRGB(drawx,drawy,Color.RED.getRGB());
            }
        }
        
        blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(blankCursorImage, new Point(0, 0), "blank cursor");
        selectCursor = Toolkit.getDefaultToolkit().createCustomCursor(selectImage, new Point(0, 0), "select cursor");
        hoverCursor = Toolkit.getDefaultToolkit().createCustomCursor(hoverImage, new Point(0, 0), "hover cursor");
    }
    
    //closes JFrame
    public void close()
    {
        f.dispose();
        isActive=false;
        //System.exit(0);
    }
    
    /**
     * Sets the display image to be shown to the screen
     * @param p Picture object to display
     */
    public void set(Picture p)
    {
        if (p.pixels.length==pixels.length) 
        {
            for (int i=0;i<pixels.length;i++)
            {
                if (imgOverlay[i]!=0 && showOverlay) pixels[i]=imgOverlay[i];
                else pixels[i]=p.pixels[i];
                eventMap[i]=p.eventMap[i];//copy over eventMap
            }
            update(); // pushes image to buffer
        }
        else System.out.println("Invalid Image Size");
    }
    
    /**
     * Sets the image to be displayed as a subset [crop] of a 'Master' image
     * @param m The master image
     */
    public void setSub(Picture m)
    {
        //Local Variables
        int mi,si; // master and sub indices
        int mx,my; // coordinates in master being referenced
        
        //error checking
        if (m.pixels.length<pixels.length){
            System.out.println("Invalid Master Image Size");
            return;
        }
        
        //Update subImage offset
        if (u) {
            yoff=((yoff - MOVESPEED)+m.height)%m.height;
        }
        if (d) {
            yoff=((yoff + MOVESPEED)+m.height)%m.height;
        }
        if (l) {
            xoff=((xoff - MOVESPEED)+m.width)%m.width;
        }
        if (r) {
            xoff=((xoff + MOVESPEED)+m.width)%m.width;
        }
        
        //clear eventMap
        eventMap=new int[pixels.length];
        
        //System.out.println("xff: "  + xoff + ", yff: " + yoff + ", MV: " + MOVESPEED);

        //loop through coordinates in sub in row-major order
        for (int sy=0;sy<this.height;sy++)
        {
            // master | relative y cordinate
            my = ((int)yoff + sy)%m.height;
            for (int sx=0;sx<this.width;sx++)
            {
                // sub index
                si = sx+(sy*this.width);
                //master | relative x coordinate
                mx = ((int)xoff + sx)%m.width; 
                //master index
                mi = mx+(my*m.width);
                if (imgOverlay[si]!=0 && showOverlay) pixels[si] = imgOverlay[si];
                else pixels[si]=m.pixels[mi];
                
                if (oEventMap[si]==0) eventMap[si]=m.eventMap[mi]; // copy eventMap from image if there isnt an event from an overlay
            }
        }
        update(); // push image to buffer
    }
    
    /**
     * Adds the pixels of an image to the overlay layer of this window
     * starting at a specific point
     * @param p The image data of the overlay
     * @param x0 The x coordinate of the top left of the overlay image onscreen
     * @param y0 The y coordinate of the top left of the overlay image onscreen
     * @param greenScreen whether or not to apply green screen effect 
     */
    public void addOverlay(Picture p, int x0, int y0, boolean greenScreen)
    {
        // Local variables
        int oi, di; // indices
        int dx, dy; // coordinates of display image 
        
        for (int y=0;y<p.height;y++) //loop through rows of overlay
        {
            dy = (y0+y);
            if (dy>=this.height)break; //dont draw outside of range
            for (int x=0;x<p.width;x++) //loop through columns of overlay
            { 
                dx = (x0+x);
                if (dx>=this.width)break; // dont draw outside of range
                oi = x+(y*p.width);
                di = dx+(dy*this.width);
                if (greenScreen) {
                    if (p.pixels[oi]!=p.pixels[0]) // if the current rbg doesnt equal the overlay's green screen value, draw it
                    {
                        imgOverlay[di] = p.pixels[oi];
                        oEventMap[di] = p.eventMap[oi]; //write to overlay event map [for backup]
                        eventMap[di] = oEventMap[di]; // write to event map
                    } 
                }
                else {
                    imgOverlay[di] = p.pixels[oi];
                    oEventMap[di] = p.eventMap[oi]; //write to overlay event map [for backup]
                    eventMap[di] = oEventMap[di]; // write to event map
                }
            }
        }
    }
    
    public void overlay(boolean on)
    {
        if (on) showOverlay=true;
        else showOverlay = false;
    }
    
    private void detectEvent()
    {
        int x,y; //coordinates of cursor currently on screen
        int i;   // eventMap index
        x = MouseInfo.getPointerInfo().getLocation().x - f.getLocationOnScreen().x;
        y = MouseInfo.getPointerInfo().getLocation().y - f.getLocationOnScreen().y;
        i = x+(y*this.width);

        // check event map
        if (eventMap[i]>0)
        {
            eventcode = eventMap[i]; // set event code if there is one
        }
    }
    
    //method draws the contents of the pixel array to a buffer which is then outputted from the screen
    //This method was basically directly copied from the tutorial found here: http://www.instructables.com/id/Making-a-Basic-3D-Engine-in-Java/
    public void update() {
        if (isActive){
            centreCursor(centreC);
            // Update Graphics
            BufferStrategy buffstrat = f.getBufferStrategy(); //creates a new buffer strategy for triple buffering the output image
               if (buffstrat == null) {
                    f.createBufferStrategy(3); // Triple buffering requires 3 calls to update before image data is drawn
                    return;
               }
               Graphics g = buffstrat.getDrawGraphics();
               g.drawImage(img, 0, 0, this.width, this.height, null);
               buffstrat.show();
        }
    }
    
    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        // close Graphics Window object
        if (e.getKeyCode()==KeyEvent.VK_ESCAPE)
        {
            System.exit(0);
            close();
        }
        // Check for interactable element
        if (e.getKeyCode()==KeyEvent.VK_ENTER)
        {
            detectEvent();
        }
        // keyboard movement
        if (e.getKeyCode()==KeyEvent.VK_UP) 
        {
            u=true;
        }
        if (e.getKeyCode()==KeyEvent.VK_DOWN) 
        {
            d=true;
        }
        if (e.getKeyCode()==KeyEvent.VK_LEFT) 
        {
            l=true;
        }
        if (e.getKeyCode()==KeyEvent.VK_RIGHT) 
        {
            r=true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode()==KeyEvent.VK_UP) 
        {
            u=false;
        }
        if (e.getKeyCode()==KeyEvent.VK_DOWN) 
        {
            d=false;
        }
        if (e.getKeyCode()==KeyEvent.VK_LEFT) 
        {
            l=false;
        }
        if (e.getKeyCode()==KeyEvent.VK_RIGHT) 
        {
            r=false;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) 
    {
        detectEvent();
    }

    @Override
    public void mousePressed(MouseEvent e) {
    }

    @Override
    public void mouseReleased(MouseEvent e) {
    }

    @Override
    public void mouseEntered(MouseEvent e) {
    }

    @Override
    public void mouseExited(MouseEvent e) {
    }
}