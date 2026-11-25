package game.beings.masters;

import game.beings.Orch;

public final class MasterOrch extends Orch implements Master {

    private static final MasterOrch INSTANCE = new MasterOrch();

    private MasterOrch() {
        super(-1, -1);
    }

    public static MasterOrch getInstance() {
        return INSTANCE;
    }

    @Override
    public void move() {
        // The Orch Master stays in its team's safe zone.
    }
}