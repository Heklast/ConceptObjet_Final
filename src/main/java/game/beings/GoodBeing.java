package game.beings;

public abstract class GoodBeing extends LivingBeing {

    public GoodBeing(int initialY, int initialX) {
        super(initialY, initialX);
    }

    @Override
    public boolean isAlly(LivingBeing other) {
        return other instanceof GoodBeing;
    }
}
