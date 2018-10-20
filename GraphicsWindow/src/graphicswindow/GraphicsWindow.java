package graphicswindow;

import java.awt.*;
import java.awt.image.*;
import javax.swing.*;

/**
 *
 * @author Tennyson Demchuk
 * @date 10.19.2018
 */
public class GraphicsWindow {

    // Frame Object
    private JFrame f;
    // Location Data
    int xloc, yloc;
    // Image Data
    private int[] pixels;
    private BufferedImage img;
    //custom cursors
    private Cursor blankCursor;
    private Cursor selectCursor;
    private Cursor hoverCursor;
    // Size data
    int width, height;
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
        
        //setup cursors
        setupCursors();
        
        f.setTitle(t);
        f.setSize(width, height);
        f.setLocationRelativeTo(null);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setResizable(false);
        f.setUndecorated(false);
        f.setVisible(true);
        
        xloc = f.getLocation().x;
        yloc = f.getLocation().y;
        
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
    
    // displays a new image to the gamewindow
    public void setImg(Picture p)
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
    
    //method draws the contents of the pixel array to a buffer which is then outputted from the screen
    //This method was basically directly copied from the tutorial found here: http://www.instructables.com/id/Making-a-Basic-3D-Engine-in-Java/
    public void update() {
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