package game.beings;

public class Human extends GoodBeing{

    // Number of Human instances created (masters included)
    private static int count = 0;

    public Human(int initialY, int initialX) {
        super(initialY, initialX);
        count++;
    }

    public static int getCount() { return count; }

    @Override
    public boolean isSameRace(LivingBeing other) {
        return other instanceof Human;
    }
}
