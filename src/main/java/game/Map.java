package game;

import game.beings.Color;
import game.beings.LivingBeing;

public class Map {
    private int sizeX;
    private int sizeY;
    private Square[][] squares;
        
    public Map(int sizeX,int sizeY){
        this.sizeX = sizeX;
        this.sizeY = sizeY;
        squares = new Square[sizeY][sizeX];
        
        for(int i=0;i<sizeY;i++){
            for(int j=0;j<sizeX;j++){
                Color color = Color.RESET;
                
                if(i<3 && j<3){
                    color=Color.GREEN;
                }
                if(i>=sizeY-3 && j<3){
                    color = Color.ORANGE;
                }
                if(i<3 && j>=sizeX-3){
                    color = Color.PURPLE;
                }
                if(i>=sizeY-3 && j>=sizeX-3){
                    color = Color.BLUE;
                }
                
                Square square = new Square(j,i,color,this);
                squares[i][j]=square;
                
            }
        }
     
    }
    
    public void display(){
        System.out.println("Map:");
        for (Square[] square1 : squares) {
            for (Square square : square1) {
                square.display();
            }
            System.out.println("");
        }
    }
    
    public void moveCharacter(LivingBeing character,int newPosX,int newPosY){
        int oldX = character.getPosX();
        int oldY = character.getPosY();
        
        squares[oldY][oldX].setOccupedBy(null);
        squares[newPosY][newPosX].setOccupedBy(character);
        character.setPosition(newPosX, newPosY);
    }

    public int getSizeX() { return sizeX; }

    public int getSizeY() { return sizeY; }

    public boolean isInBounds(int x, int y) {
        return x >= 0 && x < sizeX && y >= 0 && y < sizeY;
    }

    public Square getSquare(int x, int y) {
        return squares[y][x];
    }

    /** True if the being stands on a square of its own team's SafeZone. */
    public boolean isInOwnSafeZone(LivingBeing being) {
        return squares[being.getPosY()][being.getPosX()].getOwner() == being.getColor();
    }

    /**
     * Can this being step on (x, y)? No if out of the map, occupied (other being or
     * exhausted being), a rock, or the SafeZone of another team (its outline acts as an obstacle).
     */
    public boolean isFreeFor(LivingBeing being, int x, int y) {
        if (!isInBounds(x, y)) return false;
        Square s = squares[y][x];
        if (s.getOccupedBy() != null || s.getType() == SquareTypes.ROCK) return false;
        Color owner = s.getOwner();
        return owner == Color.RESET || owner == being.getColor();
    }

    /** Direction of the closest square of the being's own SafeZone. */
    public Direction directionToSafeZone(LivingBeing being) {
        int bestDist = Integer.MAX_VALUE;
        Square best = null;
        for (Square[] row : squares) {
            for (Square s : row) {
                if (s.getOwner() != being.getColor()) continue;
                int dist = Math.max(Math.abs(s.getX() - being.getPosX()), Math.abs(s.getY() - being.getPosY()));
                if (dist < bestDist) {
                    bestDist = dist;
                    best = s;
                }
            }
        }
        if (best == null) return Direction.N;
        return Direction.fromSteps(best.getX() - being.getPosX(), best.getY() - being.getPosY());
    }
       
        
}
