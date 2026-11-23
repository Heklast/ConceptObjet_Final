package game.events;

import game.Map;
import game.beings.LivingBeing;
import java.util.List;

public abstract class RandomEvent {

    private static int instanceCount = 0;

    private final String name;

    protected RandomEvent(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("name must not be empty");
        }
        this.name = name;
        instanceCount++;
    }

    public static int getInstanceCount() {
        return instanceCount;
    }

    public String getName() {
        return name;
    }

    public abstract void apply(Map map, List<LivingBeing> characters);
}
