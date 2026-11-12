package game.beings;

public enum Color {
    GREEN("Green","\u001B[42m"),
    PURPLE("Purple","\u001B[45m"),
    ORANGE("Orange","\u001B[103m"),
    BLUE("Blue","\u001B[44m"),
    RESET("","\u001B[0m");
    
    private final String code;
    private final String name;
    
    Color(String name,String code){
        this.name=name;
        this.code=code;
    }
    
    public String getCode(){
        return this.code;
    }

    @Override
    public String toString() {
        return this.name;
    }
    
    
}
