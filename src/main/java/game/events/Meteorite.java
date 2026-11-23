package game.events;

import game.Map;
import game.beings.LivingBeing;
import java.util.List;

public class Meteorite extends RandomEvent {

    public static final int NUMBER_OF_ROCKS = 5;

    private static int instanceCount = 0;

    public Meteorite() {
        super("Meteorite");
        instanceCount++;
    }

    public static int getInstanceCount() {
        return instanceCount;
    }

    @Override
    public void apply(Map map, List<LivingBeing> characters) {
        throw new UnsupportedOperationException("Meteorite effect is not implemented yet");
    }
}
