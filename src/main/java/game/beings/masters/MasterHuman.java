package game.beings.masters;

import game.beings.Human;

public final class MasterHuman extends Human implements Master {

    private static final MasterHuman INSTANCE = new MasterHuman();

    private MasterHuman() {
        super(-1, -1);
    }

    public static MasterHuman getInstance() {
        return INSTANCE;
    }

    @Override
    public void move() {
        // The Human Master stays in its team's safe zone.
    }
}