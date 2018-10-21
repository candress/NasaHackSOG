package graphicswindow;

/**
 *
 * @author Tennyson Demchuk
 * @date 10.20.2018
 */
public class CloseGameEvent implements Event {

    @Override
    public void action(FrameHarness fh) {
        System.exit(0);
    }
    
}
