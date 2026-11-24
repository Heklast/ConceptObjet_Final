package game.beings;

public class Elf extends GoodBeing {

    // Number of Elf instances created (masters included)
    private static int count = 0;

    public Elf(int initialY, int initialX) {
        super(initialY, initialX);
        count++;
    }

    public static int getCount() { return count; }

    @Override
    public boolean isSameRace(LivingBeing other) {
        return other instanceof Elf;
    }
}
