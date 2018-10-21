/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graphicswindow;

/**
 *
 * @author Owner
 */
public class MessageEvent implements Event{

    @Override
    public void action(FrameHarness fh) {
        if (fh.nextmsg >= 1) fh.displayGame(); // display game
        else fh.message(); // display next message to screen
    }
    
}
