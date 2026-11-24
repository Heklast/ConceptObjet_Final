package game.beings;

public abstract class BadBeing extends LivingBeing {

    public BadBeing(int initialY, int initialX) {
        super(initialY, initialX);
    }

    @Override
    public boolean isAlly(LivingBeing other) {
        return other instanceof BadBeing;
    }
}
