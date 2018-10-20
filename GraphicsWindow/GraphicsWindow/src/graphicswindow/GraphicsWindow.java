package graphicswindow;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.image.*;
import javax.swing.*;

/**
 *
 * @author Tennyson Demchuk
 * @date 10.19.2018
 */
public class GraphicsWindow implements KeyListener {

    // Frame Object
    private JFrame f;
    // Location Data
    int xloc, yloc;
    // Image Data
    private int[] pixels;
    private BufferedImage img;
    private int[] imgOverlay;
    //custom cursors
    private Cursor blankCursor;
    private Cursor selectCursor;
    private Cursor hoverCursor;
    //Movement control
    boolean l,r,u,d;
    final double MOVESPEED = 0.5;
    // Size data
    int width, height;
    //Subimage offset coordinates
    private double xoff,yoff;
    // Class constants
    final int FULLSCREEN = 1;
    
    public GraphicsWindow(String t,int w, int h, int fs) //frame title, min width, min height, fullscreen mode
    {
        f = new JFrame();
        
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
        imgOverlay = new int[pixels.length];
        
        //setup cursors
        setupCursors();
        
        f.setTitle(t);
        f.setSize(width, height);
        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setResizable(false);
        f.setUndecorated(true);
        f.setVisible(true);
        
        f.addKeyListener(this);
        
        xloc = f.getLocation().x;
        yloc = f.getLocation().y;
        
        xoff=0;yoff=0;
        l=false;r=false;u=false;d=false;
        
        //blank out cursor
        cursor(true);
        
        // clears screen
        clear();
    }
    
    //clears screen to black
    public void clear()
    {
        for (int i=0;i<pixels.length;i++){pixels[i]=0;}
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
    
    private void setupCursors()
    {
        BufferedImage blankCursorImage = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB); //blank image
        BufferedImage selectImage = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        BufferedImage hoverImage = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        for (int x=0;x<4;x++)
        {
            for (int y=0;y<4;y++)
            {
                selectImage.setRGB(x,y,Color.GREEN.getRGB());
                hoverImage.setRGB(x,y,Color.RED.getRGB());
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
        //.System.exit(0);
        this.close();
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
                //System.out.println(p[i]);
                pixels[i]=p.pixels[i];
            }
            update(); // pushes image to buffer
        }
        else System.out.println("Invalid Image Size");
    }
    
    /**
     * Sets the image to be displayed as a subset of a 'Master' image
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
                pixels[si]=m.pixels[mi];
            }
        }
        update(); // push image to buffer
    }
    
    
    
    //method draws the contents of the pixel array to a buffer which is then outputted from the screen
    //This method was basically directly copied from the tutorial found here: http://www.instructables.com/id/Making-a-Basic-3D-Engine-in-Java/
    public void update() {
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
    
    @Override
    public void keyTyped(KeyEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode()==KeyEvent.VK_ESCAPE) 
        {
            close();
        }
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
}