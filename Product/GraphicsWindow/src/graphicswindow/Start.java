package graphicswindow;

import java.awt.event.ActionListener;
import javax.swing.*;
import java.io.*;
import java.util.*;
import java.awt.*;
import static java.awt.BorderLayout.SOUTH;
import java.awt.event.ActionEvent;
import java.awt.image.BufferedImage;
import javax.imageio.ImageIO;
import static javax.swing.GroupLayout.Alignment.CENTER;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
/**
 *
 * @author Joseph Ip
 */
public class Start implements ActionListener {

    JFrame title;
    JButton start,quit;
    JLabel titlePic;
    JPanel titlePan;
    BufferedImage tPic;
    boolean done;
    
    public Start()
    {
        done = false;
        titleScreen();
    }
    private void titleScreen()
    {
        title = new JFrame();
        title.setDefaultCloseOperation(EXIT_ON_CLOSE);
        title.setUndecorated(true);
        title.setLocationRelativeTo(null);
        title.setSize(600,400);
        title.setResizable(false);
        titlePic = new JLabel();
        titlePic.setSize(600,600);
        titlePan = new JPanel();
        titlePan.setLayout (new GridLayout (1,2));
        start = new JButton ("Start");
        quit = new JButton("Quit");
        start.addActionListener(this);
        quit.addActionListener(this);
        title.setLayout(new BorderLayout());
        try{
        tPic = ImageIO.read(new File("Title.jpg"));
        }catch(Exception e){
            System.out.println ("An error occured. Please try again later.");
        }
        titlePic.setIcon(new ImageIcon(tPic));
        titlePan.add(start);
        titlePan.add(quit);
        title.add(titlePic);
        title.add(titlePan,SOUTH);
        title.setVisible(true);
    }

    public void actionPerformed (ActionEvent e)
    {
        if (e.getSource()==start)
        {
            title.dispose();
            done = true;
        }
        else if (e.getSource()==quit)
        {
            System.exit(0);
        }
        
    }
    
}