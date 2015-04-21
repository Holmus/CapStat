package capstat.utils;

/**
 * @author hjorthjort
 */
public abstract class Throw {

    private final static Throw HIT = new Hit();
    private final static Throw MISS = new Miss();

    public abstract boolean hit();

    private static class Hit extends Throw {
        @Override
        public boolean hit() {
            return true;
        }
    }
    private static class Miss extends Throw {
        @Override
        public boolean hit() {
            return false;
        }
    }

    public static Throw createHit() { return HIT; }
    public static Throw createMiss() {
        return MISS;
    }
}
