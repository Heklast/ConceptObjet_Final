package game;

import game.beings.Color;
import game.beings.LivingBeing;
import java.util.Optional;


public class Square {
    private final int x;
    private final int y;
    private final Color owner;
    private final SquareTypes type;
    private LivingBeing occupedBy;
   
    
    public Square(int posX,int posY,Color owner){
        this.x=posX;
        this.y=posY;
        this.type=SquareTypes.GRASS;
        this.owner= owner;
    }

    public Color getOwner() {
        return owner;
    }

    public SquareTypes getType() {
        return type;
    }
    
    public void display(){
        Character caractere = Optional.ofNullable(this.owner)
                                .filter(o -> !o.toString().isEmpty())
                                .map(o -> o.toString().charAt(0))
                                .orElse('o');

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
    
    
    
}
