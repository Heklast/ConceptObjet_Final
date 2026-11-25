package game.beings.masters;

import game.beings.Elf;

public final class MasterElf extends Elf implements Master {

    private static final MasterElf INSTANCE = new MasterElf();

    private MasterElf() {
        super(-1, -1);
    }

    public static MasterElf getInstance() {
        return INSTANCE;
    }

    @Override
    public void move() {
        // The Elf Master stays in its team's safe zone.
    }
}