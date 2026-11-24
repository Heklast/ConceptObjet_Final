package game;

public enum Direction {
    N(0),NE(1),E(2),SE(3),S(4),SO(5),O(6),NO(7);
    
    private final int value;
    
    private Direction(int value){
        this.value=value;
    }
    
    public Direction getDirectionOfNumber(int number){
        switch(number){
            case 0: return Direction.N;
            case 1: return Direction.NE;
            case 2: return Direction.E;
            case 3: return Direction.SE;
            case 4: return Direction.S;
            case 5: return Direction.SO;
            case 6: return Direction.O;
            case 7: return Direction.NO;
        }
        return this;
    }
    
    public int getValue(){
        return this.value;
    }
    
    /***
    Give the direction after rotation
    * @param modifier 
    * @return direction
    */
    public Direction getModifiedValue(int modifier){
        return  getDirectionOfNumber(Math.floorMod(this.value+modifier, 8));
    }

    /** Horizontal step of this direction (-1, 0 or 1). x grows to the East. */
    public int getDx(){
        switch(this){
            case NE: case E: case SE: return 1;
            case SO: case O: case NO: return -1;
            default: return 0;
        }
    }

    /** Vertical step of this direction (-1, 0 or 1). y grows to the South (row index). */
    public int getDy(){
        switch(this){
            case SE: case S: case SO: return 1;
            case NO: case N: case NE: return -1;
            default: return 0;
        }
    }

    /** Direction matching a step (dx, dy); only the sign of each is used. */
    public static Direction fromSteps(int dx, int dy){
        dx = Integer.signum(dx);
        dy = Integer.signum(dy);
        for(Direction d : values()){
            if(d.getDx()==dx && d.getDy()==dy && (dx!=0 || dy!=0)) return d;
        }
        return N;
    }
}
