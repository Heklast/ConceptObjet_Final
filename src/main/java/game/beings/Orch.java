package game.beings;

public class Orch extends BadBeing {

    // Number of Orch instances created (masters included)
    private static int count = 0;

    public Orch(int initialY, int initialX) {
        super(initialY, initialX, Color.ORANGE);
        count++;
    }

    public static int getCount() { return count; }

    @Override
    public boolean isSameRace(LivingBeing other) {
        return other instanceof Orch;
    }
}
