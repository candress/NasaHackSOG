/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package nasahacksog;

/**
 *
 * @author Joseph Ip
 */
public class NasaHackSOG {

    /**
     * @param args the command line arguments
     */
    public NasaHackSOG()
    {
        testMine();
    }
    public void testMine() {
        MinesweepImpl mine = new MinesweepImpl();
        boolean dead, done;
        while (true) {
            done = mine.complete();
            dead = mine.getDead();
            if (dead) {
                mine = new MinesweepImpl();
                System.out.println(mine.getDead());
            } else if (done) {
                break;
            }
            System.out.println(mine.complete() + "\t" + mine.getDead());
        }
    }

    public static void main(String[] args) {
        WordPuzzle word = new WordPuzzle();
        //NasaHackSOG s = new NasaHackSOG();

        // TODO code application logic here
    }

}
