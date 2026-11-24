package game.beings;

public abstract class BadBeing extends LivingBeing {

    public BadBeing(int initialY, int initialX, Color color) {
        super(initialY, initialX, color);
    }

    @Override
    public boolean isAlly(LivingBeing other) {
        return other instanceof BadBeing;
    }
}
