package graphicswindow;

/**
 *
 * @author Tennyson Demchuk
 * @date 10.21.2018
 */
public class TravelToEvent implements Event {

    @Override
    public void action(FrameHarness fh) {
        fh.displayDest();
    }
    
}
