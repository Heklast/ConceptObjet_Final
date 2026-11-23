package game.events;

import game.Map;
import game.RandomGenerator;
import game.Square;
import game.beings.LivingBeing;
import java.util.ArrayList;
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
        List<Square> candidates = findLandingSquares(map);
        int rocksToPlace = Math.min(NUMBER_OF_ROCKS, candidates.size());
        for (int i = 0; i < rocksToPlace; i++) {
            int index = RandomGenerator.nextInt(0, candidates.size() - 1);
            candidates.remove(index).placeRock();
        }
    }

    private List<Square> findLandingSquares(Map map) {
        List<Square> landingSquares = new ArrayList<>();
        for (int y = 0; y < map.getSizeY(); y++) {
            for (int x = 0; x < map.getSizeX(); x++) {
                Square square = map.getSquare(x, y);
                if (square.isFree() && !square.isSafeZone()) {
                    landingSquares.add(square);
                }
            }
        }
        return landingSquares;
    }
}
