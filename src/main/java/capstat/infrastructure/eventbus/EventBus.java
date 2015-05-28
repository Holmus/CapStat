package capstat.infrastructure.eventbus;

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

    /**
     * Unregisters the specified listener from events with the specified
     * String. If the listener is not registered before, this method has no
     * effect
     * @param event the string that the listener will no longer get notified by
     * @param listener the listener to be unregistered
     */
    public void removeNotifyEventListener(final String event, final NotifyEventListener listener) {

        Set<NotifyEventListener> set = this.notifyListenersMap.get(event);
        if (set != null) {
            set.remove(listener);
            if (set.isEmpty()) this.notifyListenersMap.remove(event);
        }
    }

	public void removeNotifyEventListenersByEvent(final String event) {
		System.out.println(dataListenersMap.toString());
		if (this.notifyListenersMap != null) {
			this.notifyListenersMap.remove(event);
		}
	}

    public void notify(final String event) {
        Set<NotifyEventListener> set = this.notifyListenersMap.get(event);
        if (set != null) {
            for (NotifyEventListener listener : set) {
                listener.notifyEvent(event);
            }
        }
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

    /**
     * Unregisters the specified listener from events with the specified
     * String. If the listener is not registered before, this method has no
     * effect
     * @param event the string that the listener will no longer get notified by
     * @param listener the listener to be unregistered
     */
    public void removeDataEventListener(final String event, final DataEventListener listener) {

        Set<DataEventListener> set = this.dataListenersMap.get(event);
        if (set != null) {
            set.remove(listener);
            if (set.isEmpty()) this.dataListenersMap.remove(event);
        }

    }

    public void dataNotify(final String event, final Object data) {
        Set<DataEventListener> set = this.dataListenersMap.get(event);
        if (set != null) {
            for (DataEventListener listener : set) {
                listener.dataNotify(event, data);
            }
        }
    }
}
