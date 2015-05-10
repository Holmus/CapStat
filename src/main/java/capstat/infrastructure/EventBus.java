package capstat.infrastructure;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * A simple event bus for managing publishers and subscribers. The class is a
 * Singleton.
 * @author hjorthjort
 */
public class EventBus {

    private Map<String, Set<NotifyEventListener>> notifyListenersMap;
    private Map<String, Set<DataEventListener>> dataListenersMap;
    private static EventBus instance = null;

    private EventBus() {
        this.notifyListenersMap = new HashMap<>();
        this.dataListenersMap = new HashMap<>();
    }

    public synchronized static EventBus getInstance() {
        if (instance == null) instance = new EventBus();
        return instance;
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
