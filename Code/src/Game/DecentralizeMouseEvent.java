package Game;

/**
 *
 * @author Owner
 */
public class DecentralizeMouseEvent implements Event {

    @Override
    public void action(FrameHarness fh) {
        fh.w.centreCursor(false);
    }
    
}
