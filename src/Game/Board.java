package Game;

import Game.Move.TYPE;
import java.util.ArrayList;

public class Board {

    private FieldType[][] fields;

    private final int ID;
    
    //player position
    private int playerX, playerY;

    //size
    private int width, height;
 
    public Board(int ID) {
        this.ID = ID;
        initBoard();
    }
    
    //temp
    private boolean checked[][];

    private void initBoard() {
        String path = "levels/", filename = "original_levels";
        ArrayList<String> tempBoard = Sokoban.TxtHandler.readToArrayList(path + filename + ".txt", String.valueOf(ID));
        
        System.out.println(tempBoard.size());
        for (int i = 0; i < tempBoard.size(); i++) {
            System.out.println(tempBoard.get(i));
        }
        
        for (int y = 0; y < tempBoard.size(); y++) {
            width = Math.max(tempBoard.get(y).length(), width);
        }
        
        height = tempBoard.size();
        this.fields = new FieldType[height][width];
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                this.fields[y][x] = FieldType.EMPTY;
            }
        }
        
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < tempBoard.get(y).length(); x++) {
                this.fields[y][x] = FieldType.getByChar(tempBoard.get(y).charAt(x));
                if (this.fields[y][x] == FieldType.PLAYER || this.fields[y][x] == FieldType.PLAYER_ON_TARGET) {
                    playerX = x;
                    playerY = y;
                }
            }
        }
        
        checked = new boolean[height][width]; //default false
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                // wall is checked so recursion cant go outside
                checked[y][x] = (this.fields[y][x] == FieldType.WALL);
            }
        }
        setInsideFields(playerY, playerX);
    }

    //recursive inside check
    private void setInsideFields(int y, int x) {
        checked[y][x] = true;
        if (fields[y][x] == FieldType.EMPTY) {
            fields[y][x] = FieldType.FIELD;
        }
        int neighbours[][] = {
            {  0,  1},
            {  0, -1},
            {  1,  0},
            { -1,  0},
        };
        for (int i = 0; i < neighbours.length; i++) {
            if (!checked[y + neighbours[i][0]][x + neighbours[i][1]]) {
                setInsideFields(y + neighbours[i][0], x + neighbours[i][1]);
            }
        }
    }

    public void move(Move move) {
        // do nothing
        if(move.getType() == TYPE.STAY){
            return;
        }
        
        // directions 
        final int x = move.getX(), y = move.getY();
        
        //players position and field front of the player
        FieldType pp   = getFieldComparedToPlayer(x, y, +0);
        FieldType ppp1 = getFieldComparedToPlayer(x, y, +1);
         
        // set ppp1 to player
        fields[playerY + y][playerX + x] = (ppp1 == FieldType.FIELD || ppp1 == FieldType.BOX) ? FieldType.PLAYER : FieldType.PLAYER_ON_TARGET;
        
        
        if(move.getType() == TYPE.MOVE){
            // set players position
            fields[playerY][playerX] = (pp == FieldType.PLAYER) ? FieldType.FIELD : FieldType.TARGET;
        }
        
        if(move.getType() == TYPE.PUSH){
            // set players position
            fields[playerY][playerX] = (pp == FieldType.PLAYER) ? FieldType.FIELD : FieldType.TARGET;
            
            // set box new position
            fields[playerY+ 2*y][playerX+ 2*x] = (getFieldComparedToPlayer(x, y, +2) == FieldType.FIELD) ? FieldType.BOX : FieldType.BOX_ON_TARGET;
            Box.moveBox(playerX+x, playerY+y, x, y);
        }
        
        if(move.getType() == TYPE.PULL){
            // remove box from the field behind the player
            fields[playerY-y][playerX-x] = (getFieldComparedToPlayer(x, y, -1) == FieldType.BOX) ? FieldType.FIELD : FieldType.TARGET;
            
            //player "pulls" the box to his previous position
            fields[playerY][playerX] = (pp == FieldType.PLAYER) ? FieldType.BOX : FieldType.BOX_ON_TARGET;
            Box.moveBox(playerX-x, playerY-y, x, y);
        }

        //set new player position    
        playerX += x;
        playerY += y;
    }
        
    private FieldType getFieldComparedToPlayer(int x, int y, int distance){
        return fields[playerY + y*distance][playerX + x*distance];
    }
    
    public Move.TYPE initType(int x, int y){
        FieldType ppp1 = fields[playerY + y][playerX + x];
        FieldType ppp2; 

        if (ppp1 == FieldType.WALL || ppp1 == FieldType.EMPTY) {
            return TYPE.STAY;
        }
        
        if (ppp1 == FieldType.FIELD || ppp1 == FieldType.TARGET) {
            return TYPE.MOVE;
        }

        ppp2 = fields[playerY + y*2][playerX + x*2];
        if ((ppp1 == FieldType.BOX || ppp1 == FieldType.BOX_ON_TARGET) && (ppp2 == FieldType.FIELD || ppp2 == FieldType.TARGET)) {
            return TYPE.PUSH;
        }

        else{ 
            return TYPE.STAY;
        }
    }

    public boolean isOver() {
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                if (fields[y][x] == FieldType.BOX || fields[y][x] == FieldType.TARGET) {
                    return false;
                }
            }
        }
        return true;
    }
    
    @Override
    public String toString() {
        String map = "level: " + ID + "\n";
        for (int y = 0; y < this.fields.length; y++) {
            for (int x = 0; x < this.fields[y].length; x++) {
                map += fields[y][x].getChar();
            }
            map += "\n";
        }
        return map;
    }

    public int getID() {
        return ID;
    }

    public int getPlayerX() {
        return playerX;
    }

    public int getPlayerY() {
        return playerY;
    }

    public FieldType[][] getBoard() {
        return fields;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }
}
