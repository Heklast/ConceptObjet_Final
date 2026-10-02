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
        return  getDirectionOfNumber((this.value+modifier)%8);
    }
}
