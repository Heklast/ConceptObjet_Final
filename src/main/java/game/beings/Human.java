package game.beings;

import game.Direction;

public class Human extends GoodBeing {

    // Number of Human instances created (masters included)
    private static int count = 0;

    public Human(int initialY, int initialX) {
        super(initialY, initialX, Color.BLUE);
        count++;
    }

    public static int getCount() { return count; }

    @Override
    protected boolean canMoveInDirection(Direction direction) {
        return !direction.isDiagonal();
    }

    @Override
    public boolean isSameRace(LivingBeing other) {
        return other instanceof Human;
    }
}
