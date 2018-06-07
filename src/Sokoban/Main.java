package Sokoban;

import Gui.*;
import Game.*;
import java.util.ArrayList;

public class Main {

    public static final int LEVEL_COUNT = 7656;
    public static Window frame;

    public static Menu menu;
    public static SelectLevel selectLevel;
    public static Game game;
    //load game

    public static ArrayList<Highscore> highscores = new ArrayList<>();

    //settings
    public static int skin = 0;

    //text panels
    public static About about;
    public static Credits credits;
    public static Controll controll;

    public static void main(String[] args) {
        try{
            readHighscores();
        }catch(Exception e){
            System.err.println("FATAL ERROR: " + e.getMessage() + "\ncant read highscores.txt");
        }
        frame = new Window(1, 1, "Sokoban");
        frame.setSize(400, 600);
        menu = new Menu();
        Main.frame.WINDOW.add(menu.getPanel());
        Main.frame.refresh();
    }

    private static void readHighscores() {
        ArrayList<String> hs = TxtHandler.readToArrayList("documents/highscores.txt");
        int line = 0;
        while (line < hs.size()) {
            highscores.add(new Highscore(
                Integer.parseInt(hs.get(line++)), // id
                hs.get(line++),                   // name
                Integer.parseInt(hs.get(line++)), // move c
                Integer.parseInt(hs.get(line++)), // push c
                Integer.parseInt(hs.get(line++)), // time 
                hs.get(line++)                    // sol
            ));
        } 
    }
    
    public static Highscore getHighscoreByID(int id){
        for (int i = 0; i < highscores.size(); i++) {
            if(highscores.get(i).getId() == id){
                return highscores.get(i);
            }
        }
        return null;
    }
    public static void setNewHighscoreByID(int id, Highscore newHighscore){
        for (int i = 0; i < highscores.size(); i++) {
            if(highscores.get(i).getId() == id){
                highscores.set(i, newHighscore);
                return;
            }
        }
       highscores.add(newHighscore);
    }
   
}
