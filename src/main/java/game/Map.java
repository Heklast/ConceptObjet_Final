package game;

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
                Square square = new Square(i,j);
                squares[i][j]=square;
            }
        }
     
    }
    
    public void display(){
        System.out.println("Map:");
        for(int i=0;i<sizeY;i++){
            for(int j=0;j<sizeX;j++){
                System.out.print("o");
            }
            System.out.println("");
        }
    }
       
        
}
