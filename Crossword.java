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
public interface Crossword {
    
       public boolean complete();
       public void setReward (double x, double y, double z);
       public double getX ();
       public double getY();
       public double getZ();       
}
