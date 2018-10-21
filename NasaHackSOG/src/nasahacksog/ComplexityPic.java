/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nasahacksog;

import javax.swing.*;
import java.awt.*;
import static java.awt.BorderLayout.CENTER;
import static java.awt.BorderLayout.EAST;
import static java.awt.BorderLayout.NORTH;
import static java.awt.BorderLayout.SOUTH;
import static java.awt.BorderLayout.WEST;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.*;
import javax.imageio.ImageIO;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

/**
 *
 * @author Joseph Ip
 */
public class ComplexityPic implements ActionListener {

    JFrame display;
    JLabel pic1, pic2;
    JPanel buttons,descript,endScreen;
    JButton buttonA, buttonB,finish;
    JTextField instruct;
    BufferedImage bf1, bf2;
    int height,width;

    public ComplexityPic() {
        pic1 = new JLabel();
        pic2 = new JLabel();
        setImage();
        setScreen(450,600,0);
        setButton();
        setDescription();
        display.setVisible(true);
    }
    private void setDescription()
    {
        descript = new JPanel();
        instruct = new JTextField("Which maze will take less steps to reach the exit?");
        instruct.setEditable(false);
        descript.add(instruct);
        display.add(descript,NORTH);
    }
    private void setButton()
    {
        buttons = new JPanel();
        buttonA = new JButton ("A");
        buttonB = new JButton ("B");
        buttonA.addActionListener (this);
        buttonB.addActionListener (this);
        buttons.add (buttonA);
        buttons.add(buttonB);
        
        display.add(buttons,SOUTH);
        
    }
    private void setScreen(int height, int width, int fullScreen) {
        display = new JFrame("pictures");
        display.setDefaultCloseOperation(EXIT_ON_CLOSE);
        display.setLayout(new BorderLayout());
        if (fullScreen ==0)
        {
            this.height = height;
            this.width = width;
        }
        else{
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            height = (int) screenSize.getHeight();
            width = (int)(screenSize.getWidth());
        }
        display.setSize(width,height);
        display.setResizable(false);
        display.add(pic1,WEST);
        display.add(pic2,EAST);
        display.setVisible(true);

    }

    private void setImage() {
        bf1 = null;
        bf2 = null;
        try {
            bf1 = ImageIO.read(new File("Maze1.jpg"));
            bf2 = ImageIO.read(new File("Maze2.jpg"));
            pic1.setIcon(new ImageIcon(bf1));
            pic2.setIcon(new ImageIcon(bf2));
        } catch (Exception e) {
            System.out.println("ERROR");
        }

    }
    private void correct()
    {
        display.setVisible(false);
        finish = new JButton("YOU CHOSE THE CORRECT ANSWER. (Press any button to continue");
        remove();
        finish.addActionListener(this);
        display.setLayout(new BorderLayout());
        display.add(finish,CENTER);
        display.setVisible(true);
    }
    private void remove()
    {
        display.remove(pic1);
        display.remove(pic2);
        display.remove(buttonA);
        display.remove(buttonB);
        display.remove(instruct);
        display.remove(buttons);
        display.remove(descript);
    }
    private void incorrect()
    {
        finish = new JButton("Maze A was the correct answer. Good luck in the next one");
        remove();
        finish.addActionListener(this);
        display.setLayout(new BorderLayout());
        display.add(finish,CENTER);
    }
    @Override
    public void actionPerformed (ActionEvent e)
    {
        if (e.getSource()==buttonA)
        {
            correct();
        }
        else if (e.getSource()==buttonB)
        {
            incorrect();
        }
        else if (e.getSource()==finish){
            display.dispose();
        }
    }
}
