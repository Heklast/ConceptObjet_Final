package game.events;

import game.Map;
import game.beings.LivingBeing;
import java.util.List;

public class Virus extends RandomEvent {

    public static final double ENERGY_THRESHOLD = 0.3;

    private static int instanceCount = 0;

    public Virus() {
        super("Virus");
        instanceCount++;
    }

    public static int getInstanceCount() {
        return instanceCount;
    }

    @Override
    public void apply(Map map, List<LivingBeing> characters) {
        for (LivingBeing being : characters) {
            if (isVulnerable(map, being)) {
                being.die();
            }
        }
    }

    private boolean isVulnerable(Map map, LivingBeing being) {
        return !being.isDead()
                && !map.isInOwnSafeZone(being)
                && being.getEnergyPoints() < ENERGY_THRESHOLD * being.getMaxEnergyPoints();
    }
}
