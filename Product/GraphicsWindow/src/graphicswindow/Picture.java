package graphicswindow;

import java.awt.image.*;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 *
 * @author Tennyson Demchuk
 */
public class Picture {
    
    // Image Data
    String title;
    int[] pixels;
    int eventMap[];
    BufferedImage img;
    int width;
    int height;
    
    // loads an image from a path
    public Picture(String path)//, String title)
    {
        //this.title = title;
        //try to read image from path
        try {
            img = ImageIO.read(this.getClass().getResource("/images/"+path)); 
            width = img.getWidth();
            height = img.getHeight();
            pixels = new int[width*height];
            eventMap = new int[pixels.length];
            updatePxls();
            System.out.println("/images/"+path+": "+"Load Successful");
        }
        //catches import error
        catch (IOException e) {System.out.println("Image Load Error");}
    }
    
    // creates a new blank image 
    public Picture(int width, int height)
    {
        //this.title = title;
        this.width=width;
        this.height=height;
        img = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
        pixels = new int[width*height];
        eventMap = new int[pixels.length];
    }
    
    // updates pixel array based on image data
    public void updatePxls()
    {
        int index;
        for(int x=0;x<width;x++)
        {
            for (int y=0;y<height;y++)
            {
                index = x+(y*width);
                //System.out.println(index + ", " + img.getWidth() + ", " + img.getHeight());
                pixels[index] = img.getRGB(x, y);
            }
        }
    }
    
    //updates image based on pixel array data
    public void updateImg()
    {
        int index;
        for(int x=0;x<width;x++)
        {
            for (int y=0;y<height;y++)
            {
                index = x+(y*width);
                img.setRGB(x, y, pixels[index]);
            }
        }
    }
    
    /**
     * Nearest neighbour algorithm to scale image to target dimensions [NOTE: event map is overwritten]
     * @param width Target width
     * @param height Target height
     */
    public void scale(int width, int height)
    {
        if (img==null)System.out.println("Null Image");
        BufferedImage temp = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        double rx,ry;
        int x,y;
        
        for (int h=0;h<height;h++)
        {
            ry = h/(double)height;
            y = (int)(ry*this.height);
            for (int w=0;w<width;w++)
            {
                rx = w/(double)width;
                x = (int)(rx*this.width);
                
                temp.setRGB(w,h,img.getRGB(x,y));
                //System.out.println(rh + ", " + y);
            }
        }
        img = temp;
        this.width = width;
        this.height = height;
        pixels = new int[this.width*this.height];
        eventMap = new int[pixels.length];
        updatePxls();
    }

    // ties entire event map to single event code
    public void addEvent(int eventcode)
    {
        //System.out.println("px: " + pixels.length + ", ev: " + eventMap.length);
        for (int i=0;i<pixels.length;i++)eventMap[i]=eventcode;
    }
    
    /**
     * Ties a block of pixels within the picture to a specific event [NOTE: will overwrite existing data] 
     * @param eventcode The specific event that this block is tied to
     * @param x0 The top left x coordinate of the block
     * @param y0 The top left y coordinate of the block
     * @param w The width of the block
     * @param h The height of the block
     */
    public void addEvent(int eventcode, int x0, int y0, int w, int h)
    {
        int ax,ay;
        int i; // array index
        
        //loop through block of eventcode
        for (int y=0;y<h;y++)
        {
            ay = (y0+y);
            if (ay>=this.height)break;
            for (int x=0;x<w;x++)
            {
               ax = (x0+x);
               if(ax>=this.width)break;
               i = ax+(ay*this.width);
               eventMap[i]=eventcode;
            }
        }
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
                        pixels[di] = p.pixels[oi];
                        eventMap[di] = p.eventMap[oi]; // write to event map
                    } 
                }
                else {
                    pixels[di] = p.pixels[oi];
                    eventMap[di] = p.eventMap[oi]; // write to event map
                }
            }
        }
    }
}