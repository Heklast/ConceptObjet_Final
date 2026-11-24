package game.beings;

public abstract class GoodBeing extends LivingBeing {

    public GoodBeing(int initialY, int initialX, Color color) {
        super(initialY, initialX, color);
    }

    @Override
    public boolean isAlly(LivingBeing other) {
        return other instanceof GoodBeing;
    }
}
