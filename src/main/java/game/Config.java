package game;

/** Central place for the simulation constants (energy calibration etc.). */
public final class Config {
    private Config() {}

    public static final int BASE_MAX_EP = 100;
    /** Extra max EP per strength level above 1 (ratio of BASE_MAX_EP). */
    public static final double STRENGTH_EP_BONUS = 0.10;
    public static final int EP_PER_TILE = 2;
    public static final double LOW_EP_RATIO = 0.20;
    public static final int RECHARGE_PER_STEP = 10;
    /** A being that recharged in its SafeZone leaves again once it reaches this ratio. */
    public static final double LEAVE_SAFEZONE_RATIO = 0.80;
    /** Messages a being can carry per memory level. */
    public static final int MESSAGES_PER_MEMORY = 5;

    /** Max messages moved in one friendly exchange / one won fight. */
    public static final int MAX_MESSAGES_PER_ENCOUNTER = 3;
    /** Extra EP lost in a fight (the loser pays more). Friendly meetings are free. */
    public static final int FIGHT_EP_COST_WINNER = 1;
    public static final int FIGHT_EP_COST_LOSER = 3;
}
