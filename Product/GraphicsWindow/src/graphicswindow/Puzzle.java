/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphicswindow;

import java.awt.*;
import static java.awt.BorderLayout.CENTER;
import static java.awt.BorderLayout.EAST;
import static java.awt.BorderLayout.NORTH;
import static java.awt.BorderLayout.SOUTH;
import static java.awt.BorderLayout.WEST;
import static java.awt.Font.ROMAN_BASELINE;
import java.awt.GridLayout.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;
import java.util.*;
import static javax.swing.JFrame.EXIT_ON_CLOSE;
import javax.swing.border.LineBorder;

/**
 *
 * @author Joseph Ip
 */
public class Puzzle implements ActionListener {

    JFrame display;
    JTextField[] text;
    JButton[] crossword;
    JButton submit, message, message2;
    JPanel visual, answer;
    JTextArea area;
    String[] across, down, response;
    int height, width;
    boolean done;

    public Puzzle() {
        done = false;
        setScreen(400, 750, 0);
        display.setLayout(new BorderLayout());
        setWord();
        setText();
        setTextArea();
        setPicture();
        colourCode();
        display.setLocationRelativeTo(null);
        display.setUndecorated(true);
        display.setVisible(true);
    }

    private void colourCode() {
        label(0, 1);
        stringlength(1, 9, 1, Color.WHITE);
        label(22, 2);
        stringlength(23, 6, 1, Color.WHITE);
        label(48, 3);
        stringlength(49, 6, 1, Color.WHITE);
        label(69, 4);
        stringlength(70, 5, 1, Color.WHITE);
        label(95, 5);
        stringlength(88, 6, 1, Color.WHITE);
        label(17, 6);
        stringlength(1, 3, 11, Color.WHITE);
        label(34, 7);
        stringlength(28, 7, 11, Color.WHITE);
    }

    private void stringlength(int pos, int length, int direction, Color col) {
        for (int i = pos; i < pos + 1 + ((length - 1) * direction); i += direction) {
            crossword[i].setText(" ");
            crossword[i].setBackground(col);
        }
    }

    private boolean check() {
        boolean correct = true;
        Color col = Color.WHITE;
        colourCode();
        if (response[0].toLowerCase().compareTo(across[0]) != 0) {
            correction(0);
            correct = false;
        } else {
            stringlength(1, 9, 1, col);
        }
        if (response[1].toLowerCase().compareTo(across[1]) != 0) {
            correction(1);
            correct = false;
        } else {
            stringlength(23, 6, 1, col);
        }
        if (response[2].toLowerCase().compareTo(across[2]) != 0) {
            correction(2);
            correct = false;
        } else {
            stringlength(49, 6, 1, col);
        }
        if (response[3].toLowerCase().compareTo(across[3]) != 0) {
            correction(3);
            correct = false;
        } else {
            stringlength(70, 5, 1, col);
        }
        if (response[4].toLowerCase().compareTo(across[4]) != 0) {
            correction(4);
            correct = false;
        } else {
            stringlength(88, 6, 1, col);
        }
        if (response[5].toLowerCase().compareTo(down[0]) != 0) {
            correction(5);
            correct = false;
        } else {
            stringlength(1, 3, 11, col);
        }
        if (response[6].toLowerCase().compareTo(down[1]) != 0) {
            correction(6);
            correct = false;
        } else {
            stringlength(28, 7, 11, col);
        }
        individualCheck();
        if (correct){
            //done=true;
        }
        return correct;
    }

    private void individualCheck() {
        if ((response[0].length() == 9 && response[0].toLowerCase().compareTo("supernova") == 0)
                || (response[5].length() == 3 && response[5].toLowerCase().compareTo("sun") == 0)) {
            crossword[1].setBackground(Color.white);
        }

        if ((response[5].length() == 3 && response[5].toLowerCase().compareTo("sun") == 0)
                || (response[1].length() == 6 && response[1].toLowerCase().compareTo("nebula") == 0)) {
            crossword[23].setBackground(Color.white);
        }

        if ((response[1].length() == 6 && response[1].toLowerCase().compareTo("nebula") == 0)
                || (response[6].length() == 7 && response[6].toLowerCase().compareTo("anomaly") == 0)) {
            crossword[28].setBackground(Color.white);
        }

        if ((response[2].length() == 6 && response[2].toLowerCase().compareTo("cosmic") == 0)
                || (response[6].length() == 7 && response[6].toLowerCase().compareTo("anomaly") == 0)) {
            crossword[50].setBackground(Color.white);
        }

        if ((response[3].length() == 5 && response[3].toLowerCase().compareTo("dwarf") == 0)
                || (response[6].length() == 7 && response[6].toLowerCase().compareTo("anomaly") == 0)) {
            crossword[72].setBackground(Color.white);
        }

        if ((response[4].length() == 7 && response[4].toLowerCase().compareTo("gravity") == 0)
                || (response[6].length() == 7 && response[6].toLowerCase().compareTo("anomaly") == 0)) {
            crossword[94].setBackground(Color.white);
        }
    }

    private void label(int i, int num) {
        crossword[i].setBackground(Color.yellow);
        crossword[i].setBorder(new LineBorder(Color.darkGray));
        if (num == 5) {
            crossword[i].setText(num + "←");
        } else if (num == 6) {
            crossword[i].setText(2 + "↓");
        } else if (num == 7) {
            crossword[i].setText(1 + "↑");
        } else {
            crossword[i].setText(num + "→");
        }
        crossword[i].setEnabled(false);
    }

    private void setBorder(int i) {
        crossword[i].setOpaque(true);
        crossword[i].setBorder(new LineBorder(Color.BLACK));
        crossword[i].setBackground(Color.BLACK);
    }

    public void setTextArea() {
        area = new JTextArea(1, 1);
        area.setLineWrap(true);
        area.insert("Across 1: A star that shines brightly before it slowly fades away. Also known as a \nnew bright star."
                + "\nAcross 2: A cloud of gas and dust in \nouter space. Usually seen as a bright \npatch or a dark shadow against other \nlight pataches."
                + "\nAcross 3: The universe or cosmos in \nspace"
                + "\nAcross 4: A type of a planet that isn't \nconsidered a \"true\" planet or a \nnatural satelite."
                + "\nAcross 5: A force that allows things to \nstay on Earth."
                + "\nDown 1: The star that Earth orbits \naround."
                + "\nDown 2: Deviation or departure from the normal or common order,form, or rule.", 0);
        area.setEditable(false);
        display.add(area, CENTER);

    }

    private void fillInBlank() {
        response = new String[7];
        int counter = 0;
        for (int i = 2; i < 15; i += 2) {
            response[counter] = text[i].getText();
            counter++;
        }
        fillSpace(response[0], 1, 9, 1);
        fillSpace(response[1], 23, 6, 1);
        fillSpace(response[2], 49, 6, 1);
        fillSpace(response[3], 70, 5, 1);
        fillSpace(response[4], 88, 7, 1);
        fillSpace(response[5], 1, 3, 11);
        fillSpace(response[6], 28, 7, 11);
    }

    private void fillSpace(String text, int pos, int maxlength, int direction) {
        int length;

        if (text.length() > maxlength) {
            length = maxlength;
        } else {
            length = text.length();
        }
        int counter = 0;
        for (int i = pos; i < pos + 1 + ((length - 1) * direction); i += direction) {
            crossword[i].setText("" + text.charAt(counter));
            counter++;
        }
    }

    private void setText() {
        text = new JTextField[15];
        answer = new JPanel(new GridLayout(8, 1));
        for (int i = 0; i < 15; i++) {
            text[i] = new JTextField(2);
            if (i == 1) {
                submit = new JButton("Submit");
                answer.add(submit);
            }
            answer.add(text[i]);
        }
        for (int i = 1; i < 15; i = i + 2) {
            if (i < 10) {
                text[i].setText(((i / 2) + 1) + " Across");
            } else {
                text[i].setText((((i - 11) / 2) + 1) + " Down");
            }
            text[i].setEditable(false);
        }
        text[0].setText("");
        submit.addActionListener(this);
        text[0].setEditable(false);
        display.add(answer, EAST);
    }

    public void setPicture() {
        Font timeRoman = new Font("Roman", ROMAN_BASELINE, 20);
        crossword = new JButton[99];
        visual = new JPanel(new GridLayout(9, 11));
        for (int i = 0; i < 99; i++) {
            crossword[i] = new JButton();
            crossword[i].setFont(timeRoman);
            setBorder(i);
            visual.add(crossword[i]);
            crossword[i].setEnabled(false);
        }

        display.add(visual, WEST);
    }

    public void setScreen(int height, int width, int fullScreen) {
        display = new JFrame("CrossWord");
        display.setDefaultCloseOperation(EXIT_ON_CLOSE);
        if (fullScreen == 0) {
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

    private void setWord() {
        across = new String[]{"supernova", "nebula", "cosmic", "dwarf", "gravity"};
        down = new String[]{"sun", "anomaly"};
    }

    public boolean getDone() {
        return done;
    }

    public void victory() {
        message = new JButton("Success!!!");
        message2 = new JButton("Click anywhere to continue");
        remove();
        message.addActionListener(this);
        message2.addActionListener(this);
        display.setLayout(new BorderLayout());
        display.add(message, CENTER);
        display.add(message2, SOUTH);
        
    }

    public void remove() {
        display.setVisible(false);
        for (int i = 0; i < 99; i++) {
            display.remove(crossword[i]);
        }
        for (int i = 0; i < 15; i++) {
            display.remove(text[i]);
        }
        display.remove(submit);
        display.remove(area);
        display.remove(visual);
        display.remove(answer);
        display.setVisible(true);
    }

    private void correction(int i) {
        switch (i) {
            case 0: {
                stringlength(1, 9, 1, Color.RED);
                break;
            }
            case 1: {
                stringlength(23, 6, 1, Color.RED);
                break;
            }
            case 2: {
                stringlength(49, 6, 1, Color.RED);
                break;
            }
            case 3: {
                stringlength(70, 5, 1, Color.RED);
                break;
            }
            case 4: {
                stringlength(88, 6, 1, Color.RED);
                break;
            }
            case 5: {
                stringlength(1, 3, 11, Color.RED);
                break;
            }
            case 6: {
                stringlength(28, 7, 11, Color.RED);
                break;
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submit) {
            fillInBlank();
            if (check()) {
                victory();
            } else {
                fillInBlank();
            }
        }
        if (e.getSource() == message) {
            display.dispose();
            done=true;
        }
        if (e.getSource() == message2) {
            display.dispose();
            done=true;
        }
    }
}
