package capstat.infrastructure.eventbus;

/**
 * To register for notification events on the event bus, a client must
 * implement this interface.
 * @author hjorthjort
 */
public interface NotifyEventListener {
    /**
     * A method that will be called by a notifier to indicate an event has
     * taken place.
     * @param event the key of the event that occured
     */
    void notifyEvent(String event);
}
