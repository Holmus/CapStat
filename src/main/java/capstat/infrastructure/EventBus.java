package capstat.infrastructure;

import java.awt.*;

/**
 * A simple event bus for managing publishers and subscribers. The class is a
 * Singleton.
 * @author hjorthjort
 */
public class EventBus {

    private EventBus() {

    }

    public static EventBus getInstance() {
        return null;
    }

    public void addNotifyEventListener(final NotifyEventListener listener, final String event) {

    }

    public void removeNotifyEventListener(final NotifyEventListener listener, final String event) {

    }

    public void notify(final String event) {

    }

    public void addDataEventListener(final DataEventListener listener, final
    String event) {

    }

    public void removeDataEventListener(final DataEventListener listener, final String event) {

    }

    public void dataNotify(final String event, final Object data) {

    }
}
