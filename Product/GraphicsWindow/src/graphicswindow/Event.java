package graphicswindow;

/**
 * Event interface for custom events
 * @author Tennyson Demchuk
 */
public interface Event {
    final int CLOSE_GAME = 1;
    final int DECENTRALIZE_MOUSE = 2;
    final int TRAVEL_TO_DEST = 3;
    final int MESSAGE = 4;
    public void action(FrameHarness fh);
}