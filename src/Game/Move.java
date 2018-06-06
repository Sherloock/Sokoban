package Game;

import Sokoban.Main;

public class Move {

    public enum DIR {
        LEFT, RIGHT, UP, DOWN, STAY;
    }
    public enum TYPE {
        STAY, PUSH, MOVE, PULL,
    }
    
    private DIR direction = DIR.STAY;
    private TYPE type = TYPE.STAY;
    private final boolean UNDO;
    private int x = 0, y = 0;

    public Move(char move, boolean undo) {
        this.UNDO = undo;
        initXYDIR(move);
        initType(move);
    }

    private void initXYDIR(char move) {
        switch (move) {
            case 'u':
            case 'U':
                if (!UNDO) {
                    this.direction = DIR.UP;
                } else {
                    this.direction = DIR.DOWN;
                }
                y = -1;
                break;

            case 'd':
            case 'D':
                if (!UNDO) {
                    this.direction = DIR.DOWN;
                } else {
                    this.direction = DIR.UP;
                }
                y = +1;
                break;

            case 'r':
            case 'R':
                if (!UNDO) {
                    this.direction = DIR.RIGHT;
                } else {
                    this.direction = DIR.LEFT;
                }
                x = +1;
                break;

            case 'l':
            case 'L':
                if (!UNDO) {
                    this.direction = DIR.LEFT;
                } else {
                    this.direction = DIR.RIGHT;
                }
                x = -1;
                break;

            default:
                this.direction = DIR.STAY;
        }
        if (UNDO) {
            x *= -1;
            y *= -1;
        }
    }

    private void initType(char move) {
        if (UNDO) {
            if (this.direction == DIR.STAY) {
                type = TYPE.STAY;
            } else {
                type = Character.isLowerCase(move) ? TYPE.MOVE : TYPE.PULL;
            }
        } else {
            type = Main.game.getBoard().initType(x, y);
        }
    }

    public char toChar() {
        char move = 'X';
        switch (direction) {
            case LEFT:
                move = 'l';
                break;
            case RIGHT:
                move = 'r';
                break;
            case UP:
                move = 'u';
                break;
            case DOWN:
                move = 'd';
                break;
        }
        if (type == TYPE.PUSH) {
            move = Character.toUpperCase(move);
        }
        return move;
    }

    public void setType(TYPE type) {
        this.type = type;
    }

    public DIR getDirection() {
        return direction;
    }

    public TYPE getType() {
        return type;
    }

    public boolean isUndo() {
        return UNDO;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

}
