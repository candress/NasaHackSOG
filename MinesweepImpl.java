/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nasahacksog;

import java.awt.*;
import static java.awt.Font.ROMAN_BASELINE;
import java.awt.GridLayout.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.util.*;
import static javax.swing.JFrame.EXIT_ON_CLOSE;

/**
 *
 * @author Joseph Ip
 */
public class MinesweepImpl implements ActionListener {

    //  private double x, y, z;
    private JFrame display;
    private int height, width;
    private boolean done, dead;
    private int tiles, bomb;
    private int[][] map;
    private JButton[] mineField;
    private JButton lose, win;
    private JPanel game, instruction;
    private BorderLayout border;

    public MinesweepImpl() {
        //  setReward(x, y, z);
        setScreen(500, 500, 1);
        tiles = 50;
        bomb = 0;
        dead = false;
        done = false;
        border = new BorderLayout();
        border.setHgap(5);
        display.setLayout(border);
        randomGen(20);
        displayButtons();
        displayInstruction();
        display.setVisible(true);
    }

    private void displayInstruction() {
        instruction = new JPanel();
        Font timeRoman = new Font("Roman", ROMAN_BASELINE, 24);
        JTextField text = new JTextField("Minesweeper: Click on a tile to see if a bomb is present. There's a total of " + bomb + " bombs.");
        //   text.setPreferredSize(new Dimension(100,100));
        //  instruction.setPreferredSize(new Dimension(100,100));
        text.setEditable(false);
        text.setFont(timeRoman);
        instruction.setFont(timeRoman);
        instruction.add(text);

        display.add(instruction, BorderLayout.NORTH);
    }

    public boolean getDead() {
        return dead;
    }

    private void randomGen(int numBomb) {
        map = new int[10][5];
        int counter = 0;
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 5; j++) {
                int tmp = (int) (Math.random() * 10);
                if (counter < numBomb && tmp == 1 && (map[i][j] != -1)) {
                    counter++;
                    bomb++;
                    map[i][j] = -1;
                    setValue(i, j);
                }
            }
        }
    }

    private void setValue(int i, int j) {
        if (i < 9 && map[i + 1][j] != -1) {
            map[i + 1][j] = (map[i + 1][j] + 1);
        }
        if (i < 9 && j < 4 && map[i + 1][j + 1] != -1) {
            map[i + 1][j + 1] = (map[i + 1][j + 1] + 1);
        }
        if (i < 9 && j > 0 && map[i + 1][j - 1] != -1) {
            map[i + 1][j - 1] = (map[i + 1][j - 1] + 1);
        }
        if (i > 0 && map[i - 1][j] != -1) {
            map[i - 1][j] = (map[i - 1][j] + 1);
        }
        if (i > 0 && j < 4 && map[i - 1][j + 1] != -1) {
            map[i - 1][j + 1] = (map[i - 1][j + 1] + 1);
        }
        if (i > 0 && j > 0 && map[i - 1][j - 1] != -1) {
            map[i - 1][j - 1] = (map[i - 1][j - 1] + 1);
        }
        if (j < 4 && map[i][j + 1] != -1) {
            map[i][j + 1] = (map[i][j + 1] + 1);
        }
        if (j > 0 && map[i][j - 1] != -1) {
            map[i][j - 1] = (map[i][j - 1] + 1);
        }
    }

    private void displaymap() {
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 10; j++) {
                System.out.print(map[j][i] + "\t");
            }
            System.out.println();
        }
    }

    private void displayButtons() {
        GridLayout grid = new GridLayout(10, 5);
        mineField = new JButton[50];
        game = new JPanel(grid);
        for (int i = 0; i < 50; i++) {
            mineField[i] = new JButton("");

            game.add(mineField[i]);

            mineField[i].addActionListener(this);
        }
        display.add(game);
    }

    public void setScreen(int height, int width, int fullscreen) {
        display = new JFrame("display");
        display.setDefaultCloseOperation(EXIT_ON_CLOSE);
        if (fullscreen == 0) {
            this.height = height;
            this.width = width;
        } else {
            Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
            height = (int) screenSize.getHeight();
            width = (int) screenSize.getWidth();
        }
        display.setSize(width, height);
        display.setResizable(false);

    }

//    public void setReward(double x, double y, double z) {
//        this.x = x;
//        this.y = y;
//        this.z = z;
//    }
//
//    public double getX() {
//        return x;
//    }
//
//    public double getY() {
//        return y;
//    }
//
//    public double getZ() {
//        return z;
//    }
//
//    public void setX(double x) {
//        this.x = x;
//    }
//
//    public void setY(double y) {
//        this.y = y;
//    }
//
//    public void setZ(double z) {
//        this.z = z;
//    }
    public boolean complete() {
        return done;
    }

    private void tryAgain() {
        display.setVisible(false);
        for (int i = 0; i < 50; i++) {
            display.remove(mineField[i]);
        }
        display.remove(game);
        display.remove(instruction);
        lose = new JButton("YOU HIT A BOMB. TRY AGAIN!");
        display.add(lose);
        display.setVisible(true);
        lose.addActionListener(this);

    }

    private void victory() {
        display.setVisible(false);
        for (int i = 0; i < 50; i++) {
            display.remove(mineField[i]);
        }
        display.remove(game);
        display.remove(instruction);
        win = new JButton("Victory!");
        display.add(win);
        display.setVisible(true);
        win.addActionListener(this);

    }

    private void openNearby(int i, int j) {
        
        if (i > 0 && map[i - 1][j] == 0) {
            emptyTile(i - 1, j);
        } 
        if (i < 9 && map[i + 1][j] == 0) {
            emptyTile(i + 1, j);
        } 
        if (j > 0 && map[i][j - 1] == 0) {
            emptyTile(i, j - 1);
        }
        if (j < 4 && map[i][j + 1] == 0) {
            emptyTile(i, j + 1);
        } 

    }

    private void emptyTile(int i, int j) {
        mineField[i * 5 + j].setText(" ");
        tiles--;
        map [i][j]=10;
        mineField[i * 5 + j].setEnabled(false);
        openNearby(i, j);
    }

    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == lose) {
            dead = true;
            display.dispose();
        } else if (e.getSource() == win) {
            done = true;
            display.dispose();
        }
        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 5; j++) {
                if (e.getSource() == mineField[i * 5 + j]) {
                    if (map[i][j] == -1) {

                        tryAgain();
                    } else if (map[i][j] == 0) {
                        openNearby(i, j);
                        mineField[i * 5 + j].setText(" ");
                    } else {
                        mineField[i * 5 + j].setText(" " + map[i][j]);
                    }
                    tiles--;
                    mineField[i * 5 + j].setEnabled(false);
                    if (tiles - bomb == 0) {

                        victory();
                    }
                    break;
                }
            }
        }
    }
}
//CROSSWORD anonomly
