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
        img = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
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
    
    public void scale(int width, int height)
    {
        if (img==null)System.out.println("Null Image");
        BufferedImage temp = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        double rw,rh;
        int x,y;
        
        for (int h=0;h<height;h++)
        {
            for (int w=0;w<width;w++)
            {
                rw = (w/(double)width); // calculate ratio for width
                rh = (h/(double)height); //ratio for height
                x = (int)(width*rw);
                y = (int)(height*rh);
                temp.setRGB(w,h,img.getRGB(x,y));
                //System.out.println(rh + ", " + y);
            }
        }
        img = temp;
        this.width = width;
        this.height = height;
        pixels = new int[this.width*this.height];
        updatePxls();
    }
}
