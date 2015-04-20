package capstat.utils;

/**
 * @author hjorthjort
 */
public class Throw {

    private final static Throw HIT = new Hit();
    private final static Throw MISS = new Miss();

    private static class Hit extends Throw {}
    private static class Miss extends Throw {}

    public static Throw createHit() {
        return HIT;
    }

    public static Throw createMiss() {
        return MISS;
    }
}
