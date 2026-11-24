package game.beings;

/** Role of a trooper; each role gives 2 points in one skill and 1 in the others. */
public enum Roles {
    WARRIOR, WIZARD, RANGER;

    /** WARRIOR = strong, RANGER = fast, WIZARD = wise (memory). */
    public Skills getSkills() {
        switch (this) {
            case WARRIOR: return Skills.strong();
            case RANGER:  return Skills.fast();
            default:      return Skills.wise();
        }
    }
}
