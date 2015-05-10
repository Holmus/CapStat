package capstat.infrastructure;

import java.awt.*;
import java.util.HashMap;
import java.util.HashSet;
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

    /**
     * Adds a listener who will be notified when an event with the specified
     * String as key has taken place. If the listener already is registered,
     * this method has no effect.
     * @param event the string that subject will notify by and will be
     *              passed to the listeners notify method
     * @param listener the listener that should be notified
     */
    public void addNotifyEventListener(final String event, final NotifyEventListener listener) {
        if (!this.notifyListenersMap.containsKey(event)) {
            this.notifyListenersMap.put(event, new HashSet<>());
        }
        this.notifyListenersMap.get(event).add(listener);
    }

    public void removeNotifyEventListener(final String event, final NotifyEventListener listener) {

    }

    public void notify(final String event) {

    }

    /**
     * Adds a listener who will be notified when an event with the specified
     * String as key has taken place. If the listener already is registered,
     * this method has no effect.
     * @param event the string that subject will notify by and will be
     *              passed to the listeners dataNotify method
     * @param listener the listener that should be notified
     */
    public void addDataEventListener(final
                                     String event, final DataEventListener listener) {
        if (!this.dataListenersMap.containsKey(event)) {
            this.dataListenersMap.put(event, new HashSet<>());
        }
        this.dataListenersMap.get(event).add(listener);

    }

    public void removeDataEventListener(final String event, final DataEventListener listener) {

    }

    public void dataNotify(final String event, final Object data) {

    }
}
