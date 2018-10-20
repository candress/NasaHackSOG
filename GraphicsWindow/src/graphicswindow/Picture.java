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
    public Picture(String path, String title)
    {
        this.title = title;
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
    public Picture(int width, int height, String title)
    {
        this.title = title;
        this.width=width;
        this.height=height;
        img = new BufferedImage(width,height,BufferedImage.TYPE_INT_ARGB);
        pixels = new int[width*height];
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
        for (int i=0;i<pixels.length;i++)eventMap[i]=eventcode;
    }
    
    /**
     * Ties a block of pixels to a specific event 
     * @param eventcode
     * @param x The top left x coordinate of 
     * @param y
     * @param w
     * @param h 
     */
    public void addEvent(int eventcode, int x, int y, int w, int h)
    {
        
    }
}