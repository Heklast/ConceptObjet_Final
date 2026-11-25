package game.beings.masters;

import game.beings.Goblin;

public final class MasterGoblin extends Goblin implements Master {

    private static final MasterGoblin INSTANCE = new MasterGoblin();

    private MasterGoblin() {
        super(-1, -1);
    }

    public static MasterGoblin getInstance() {
        return INSTANCE;
    }

    @Override
    public void move() {
        // The Goblin Master stays in its team's safe zone.
    }
}