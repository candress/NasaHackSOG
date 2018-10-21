/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package game;

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
public class Game implements ActionListener {

    JFrame title;
    JButton start,quit;
    JLabel titlePic;
    JPanel titlePan;
    BufferedImage tPic;
    
    public Game()
    {
        titleScreen();
    }
    private void titleScreen()
    {
        title = new JFrame ("Cosmic Coders");
        title.setDefaultCloseOperation(EXIT_ON_CLOSE);
        title.setSize(600,400);
        title.setResizable(false);
        titlePic = new JLabel();
        titlePic.setSize(600,400);
        titlePan = new JPanel();
        titlePan.setLayout (new GridLayout (1,2));
        start = new JButton ("Start");
        quit = new JButton("Quit");
        start.addActionListener(this);
        quit.addActionListener(this);
        title.setLayout(new BorderLayout());
        try{
        tPic = ImageIO.read(new File("title.jpg"));
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
    public static void main(String[] args) {
       Game g = new Game();
    }
    public void actionPerformed (ActionEvent e)
    {
        if (e.getSource()==start)
        {
            title.dispose();
        }
        else if (e.getSource()==quit)
        {
            System.exit(0);
        }
        
    }
    
}
