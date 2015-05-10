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

    private static EventBus bus;
    private static boolean hasBeenNotified;
    private static NotifyEventListener notifyListener;
    private static DataEventListener dataListener;
    private static Object dataSendObject, dataReceiveObject;
    private static final String NOTIFICATION_STRING = "Test";

    @BeforeClass
    public static void init() {
        //Get eventb bus
        bus = EventBus.getInstance();
        //Create a listener that will change the value of the boolean
        // hasBeenNotified
        notifyListener = new NotifyEventListener() {
            @Override
            public void notify(final String event) {
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
        bus.removeNotifyEventListener(notifyListener, NOTIFICATION_STRING);
        bus.removeDataEventListener(dataListener, NOTIFICATION_STRING);
    }

    //Tests

    @Test
    public void notifyListenerTest() {
        bus.addNotifyEventListener(notifyListener, NOTIFICATION_STRING);
        bus.notify(NOTIFICATION_STRING);
        assertTrue(hasBeenNotified);
    }

    @Test
    public void removeNotifyListenerTest() {
        bus.addNotifyEventListener(notifyListener, NOTIFICATION_STRING);
        bus.removeNotifyEventListener(notifyListener, NOTIFICATION_STRING);
        bus.notify(NOTIFICATION_STRING);
        assertFalse(hasBeenNotified);
    }

    @Test
    public void dataListenerTest() {
        bus.addDataEventListener(dataListener, NOTIFICATION_STRING);
        bus.dataNotify(NOTIFICATION_STRING, dataSendObject);
        assertTrue(hasBeenNotified);
        assertTrue(dataSendObject == dataReceiveObject);
    }

    @Test
    public void removeDataListenerTest() {
        bus.addDataEventListener(dataListener, NOTIFICATION_STRING);
        bus.removeDataEventListener(dataListener, NOTIFICATION_STRING);
        bus.dataNotify(NOTIFICATION_STRING, dataSendObject);
        assertFalse(hasBeenNotified);
        assertFalse(dataSendObject == dataReceiveObject);
    }

    private static void hasBeenNotified() {
        hasBeenNotified = true;
    }

    private static void hasBeenNotified(final Object dataObject) {
        dataReceiveObject = dataObject;
    }

}
