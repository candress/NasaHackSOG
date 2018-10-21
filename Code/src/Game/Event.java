package Game;

/**
 * Event interface for custom events
 * @author Tennyson Demchuk
 */
public interface Event {
    final int CLOSE_GAME_EVENT = 1;
    final int DECENTRALIZE_MOUSE_EVENT = 2;
    public void action(FrameHarness fh);
}