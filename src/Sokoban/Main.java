package Sokoban;

import Gui.*;
import Game.*;

public class Main {            
    
    public static final int LEVEL_COUNT = 7656;
    public static Window frame;
    
    
    
    //main menu 
    public static Menu menu;
    
    // select level
     public static SelectLevel selectLevel;
    // new game
     public static Game game;
    //load game
    
    //highscores
    //settings
    public static int skin = 0;

    //text panels
    public static About about;
    public static Credits credits;
    public static Controll controll;

    
    public static void main(String[] args) {
        frame = new Window(1,1,"Sokoban");
        frame.setSize(400, 600);
        menu = new Menu();
        Main.frame.WINDOW.add(menu.getPanel());
        Main.frame.refresh();
    }    
}
