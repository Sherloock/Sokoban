package Game;


public enum FieldType{ 
    EMPTY(' '), //outside
    FIELD('_'),
    TARGET('.'),
    PLAYER('@'),
    WALL('#'),
    BOX('$'),
    BOX_ON_TARGET('*'),
    PLAYER_ON_TARGET('+');  

    static FieldType getByChar(char c) {
        for (FieldType field : FieldType.values()) {
           if(field.getChar() == c){
               return field;
           }
        }
        return null;
    }
    
    private final char c;

    FieldType(final char text) {
        this.c = text;
    }
    
    public char getChar() {
        return c;
    }
}
