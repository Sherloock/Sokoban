package Game;

import Sokoban.Main;
import java.util.ArrayList;

public class Highscore {

    private static final String TEMPNAME = "B";
    //folder
    private static String folder = "highscores";

    private String fullPath;
    
    private int id;
    private String name;
   
    private int moveCount, pushCount;
    private int time;
    private String solution;

    //generate
    public Highscore(int id, String name, int moveCount, int pushCount, int time, String solution) {
        fullPath = folder + "/" + id + ".txt";
        this.id = id;
        this.moveCount = moveCount;
        this.pushCount = pushCount;
        this.time = time;
        this.solution = solution;
        this.name = TEMPNAME;
    }

    // read
    public Highscore(int id) {
        fullPath = folder + "/" + id + ".txt";
        this.id = id;

        try {
            ArrayList<String> list = Sokoban.TxtHandler.readToArrayList(fullPath);
            this.name = list.get(0);
            this.moveCount = Integer.parseInt(list.get(1));
            this.pushCount = Integer.parseInt(list.get(2));
            this.time = Integer.parseInt(list.get(3));
            this.solution = list.get(4);
        } catch (Exception ex) {
            System.err.println("Level " + id + " not solved yet!");
            this.name = "";
            this.moveCount = Integer.MAX_VALUE;
            this.pushCount = Integer.MAX_VALUE;
            this.time = Integer.MAX_VALUE;
            this.solution = null;
        }
    }

    public void display() {
        if (solution != null) {
            int moveC = 0;
            while (moveC < solution.length() - 1) {
                if (Main.game.isReadyForInput()) {
                    Main.game.move(solution.charAt(moveC++), false);
                } 
                else {
                    try {Thread.sleep(1);}
                    catch(Exception e){System.err.println("Thread.sleep(1)");}
                }
            }
        }
    }

    public boolean isBetter(Highscore other) {
        if (this.moveCount == other.moveCount) {
            if (this.pushCount == other.pushCount) {
                if (this.time == other.time) {
                    return false;
                } else {
                    return this.time > other.time;
                }
            } else {
                return this.pushCount > other.pushCount;
            }
        } else {
            return this.moveCount > other.moveCount;
        }
    }

    @Override
    public String toString() {
        return id+ "\n" + name + "\n" + moveCount + "\n" + pushCount + "\n" + time + "\n" + solution + "\n";
    }

    public int getId() {
        return id;
    }
    
}
