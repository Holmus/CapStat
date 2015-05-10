package capstat.infrastructure;

/**
 * @author hjorthjort
 */
public interface DataEventListener {
    void dataNotify(String event, Object data);
}
