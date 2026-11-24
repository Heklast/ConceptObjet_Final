package game.events;

import game.GameMap;
import game.beings.LivingBeing;
import java.util.List;

public class Meteorite extends RandomEvent {

    public static final int NUMBER_OF_ROCKS = 5;

    private static int instanceCount = 0;

    private int numberOfRocksPlaced;

    public Meteorite() {
        super("Meteorite");
        instanceCount++;
    }

    public static int getInstanceCount() {
        return instanceCount;
    }

    @Override
    public void apply(GameMap map, List<LivingBeing> characters) {
        numberOfRocksPlaced = map.placeRandomRocks(NUMBER_OF_ROCKS);
    }

    @Override
    public String describeEffect() {
        if (numberOfRocksPlaced == 1) {
            return "1 rock fell";
        }
        return numberOfRocksPlaced + " rocks fell";
    }
}
