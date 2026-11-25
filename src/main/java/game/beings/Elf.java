package game.beings;
import game.Direction;

public class Elf extends GoodBeing {

    // Number of Elf instances created (masters included)
    private static int count = 0;

    public Elf(int initialY, int initialX) {
        super(initialY, initialX, Color.PURPLE);
        count++;
    }

    public static int getCount() { return count; }

    @Override
    protected boolean canMoveInDirection(Direction direction) {
        return direction.isDiagonal();
    }
    
    @Override
    public boolean isSameRace(LivingBeing other) {
        return other instanceof Elf;
    }
}
