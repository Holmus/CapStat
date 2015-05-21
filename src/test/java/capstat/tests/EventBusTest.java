package capstat.tests;

import capstat.infrastructure.DataEventListener;
import capstat.infrastructure.EventBus;
import capstat.infrastructure.NotifyEventListener;
import org.junit.After;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * @author hjorthjort
 */
public class EventBusTest {

    private EventBus bus;
    private boolean hasBeenNotified;
    private NotifyEventListener notifyListener;
    private DataEventListener dataListener;
    private Object dataSendObject, dataReceiveObject;
    private final String NOTIFICATION_STRING = "Test";

    @Before
    public void init() {
        //Get eventb bus
        bus = EventBus.getInstance();
        //Create a listener that will change the value of the boolean
        // hasBeenNotified
        notifyListener = new NotifyEventListener() {
            @Override
            public void notifyEvent(final String event) {
                hasBeenNotified();
            }
        };
        //Create a listener that will change value of the boolean
        // hasBeenNotified and set value for dataReceiveObject
        dataListener = new DataEventListener() {
            @Override
            public void dataNotify(final String event, final Object data) {
                hasBeenNotified(data);
            }
        };
    }

    @Before
    public void reset() {
        hasBeenNotified = false;
        dataSendObject = new Object();
        dataReceiveObject = null;
    }

    @After
    public void removeListeners() {
        bus.removeNotifyEventListener(NOTIFICATION_STRING, notifyListener);
        bus.removeDataEventListener(NOTIFICATION_STRING, dataListener);
    }

    //Tests

    @Test
    public void notifyListenerTest() {
        bus.addNotifyEventListener(NOTIFICATION_STRING, notifyListener);
        bus.notify(NOTIFICATION_STRING);
        assertTrue(hasBeenNotified);
    }

    @Test
    public void removeNotifyListenerTest() {
        bus.addNotifyEventListener(NOTIFICATION_STRING, notifyListener);
        bus.removeNotifyEventListener(NOTIFICATION_STRING, notifyListener);
        bus.notify(NOTIFICATION_STRING);
        assertFalse(hasBeenNotified);
    }

    @Test
    public void dataListenerTest() {
        bus.addDataEventListener(NOTIFICATION_STRING, dataListener);
        bus.dataNotify(NOTIFICATION_STRING, dataSendObject);
        assertTrue(hasBeenNotified);
        assertTrue(dataSendObject == dataReceiveObject);
    }

    @Test
    public void removeDataListenerTest() {
        bus.addDataEventListener(NOTIFICATION_STRING, dataListener);
        bus.removeDataEventListener(NOTIFICATION_STRING, dataListener);
        bus.dataNotify(NOTIFICATION_STRING, dataSendObject);
        assertFalse(hasBeenNotified);
        assertFalse(dataSendObject == dataReceiveObject);
    }

    private void hasBeenNotified() {
        hasBeenNotified = true;
    }

    private void hasBeenNotified(final Object dataObject) {
        hasBeenNotified();
        dataReceiveObject = dataObject;
    }

}
