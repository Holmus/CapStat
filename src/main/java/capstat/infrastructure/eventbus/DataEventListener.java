package capstat.infrastructure.eventbus;

/**
 * @author hjorthjort
 *
 */
public interface DataEventListener {
    void dataNotify(String event, Object data);
}
