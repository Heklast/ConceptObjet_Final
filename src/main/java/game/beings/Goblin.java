package game.beings;

public class Goblin extends BadBeing {

    // Number of Goblin instances created (masters included)
    private static int count = 0;

    public Goblin(int initialY, int initialX) {
        super(initialY, initialX, Color.GREEN);
        count++;
    }

    public static int getCount() { return count; }

    @Override
    public boolean isSameRace(LivingBeing other) {
        return other instanceof Goblin;
    }
}
