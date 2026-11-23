package game;

import game.beings.Color;
import game.beings.LivingBeing;
import java.util.Optional;


public class Square {
    private final int x;
    private final int y;
    private final Color owner;
    private SquareTypes type;
    private LivingBeing occupedBy;
    private Map map;
   
    
    public Square(int posX,int posY,Color owner,Map map){
        this.x=posX;
        this.y=posY;
        this.type=SquareTypes.GRASS;
        this.owner= owner;
        this.map = map;
    }

    public Color getOwner() {
        return owner;
    }

    public SquareTypes getType() {
        return type;
    }

    public boolean isSafeZone() {
        return owner != Color.RESET;
    }

    public boolean isFree() {
        return type == SquareTypes.GRASS && occupedBy == null;
    }

    public void placeRock() {
        if (!isFree()) {
            throw new IllegalStateException("a rock can only be placed on a free grass square");
        }
        type = SquareTypes.ROCK;
    }
    
    public void display(){
        Character caractere = Optional.ofNullable(this.owner)
                                .filter(o -> !o.toString().isEmpty())
                                .map(o -> o.toString().charAt(0))
                                .orElse('o');

        if(type==SquareTypes.ROCK){
            caractere='#';
        }

        if(occupedBy!=null){
            caractere=occupedBy.getClass().getSimpleName().toLowerCase().charAt(0);
        }
        
        System.out.print(owner.getCode()+caractere+Color.RESET.getCode());
    }

    public void setOccupedBy(LivingBeing occupedBy) {
        this.occupedBy = occupedBy;
    }
    
    public LivingBeing getOccupedBy(){
        return this.occupedBy;
    }

    public Map getMap() {
        return map;
    }

    public void setMap(Map map) {
        this.map = map;
    }
    
    
    
}
