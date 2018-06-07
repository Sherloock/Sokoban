package Game;

import Gui.SelectLevel;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import Sokoban.Main;

public class KeyInput extends KeyAdapter {

    private Handler handler;

    public KeyInput(Handler handler) {
        this.handler = handler;
    }

    public void keyPressed(KeyEvent e) {
        int key = e.getKeyCode();
        
        
        // speed ++
        if(key == KeyEvent.VK_R){
            Main.game.getPlayer().setSpeed(Main.game.getPlayer().getSpeed()+1);
        }
        // speed --
        if(key == KeyEvent.VK_E){
            Main.game.getPlayer().setSpeed(Main.game.getPlayer().getSpeed()-1);
        }
        
        // change skin
        if(key == KeyEvent.VK_Q){
            Main.game.getPlayer().changeSkin();
        }

       
        // back
        if(key == KeyEvent.VK_ESCAPE){
            Main.frame.setSize(600, 400);
            Main.frame.replacePanel(Main.game, SelectLevel.getPanel());
        }
        
        // restart
        if(key == KeyEvent.VK_HOME){
           Main.game.nextlevel(Main.game.getId());
        }
        
        //next level
        if(key == KeyEvent.VK_PAGE_UP){
           Main.game.nextlevel(Main.game.getId()+1);
        }
        
        // prev level
        if(key == KeyEvent.VK_PAGE_DOWN){
           Main.game.nextlevel(Main.game.getId()-1);
        }
        
        //replay
        if(key == KeyEvent.VK_END){
            if(Main.game.getHighscore() != null){
                Main.game.nextlevel(Main.game.getId());
                Main.game.getHighscore().display();
            }
        }
        
        //move keyinput
        if(Main.game.isReadyForInput()){
            if (key == KeyEvent.VK_SPACE) { // undo
                Main.game.move(Main.game.getSolution().getLast(), true);
            }
            
            //moves
            if (key == KeyEvent.VK_W || key == KeyEvent.VK_UP) {
                Main.game.move('u', false);
            }
            if (key == KeyEvent.VK_S || key == KeyEvent.VK_DOWN) {
                Main.game.move('d', false);
            }
            if (key == KeyEvent.VK_A || key == KeyEvent.VK_LEFT) {
                Main.game.move('l', false);
            }
            if (key == KeyEvent.VK_D || key == KeyEvent.VK_RIGHT) {
                Main.game.move('r', false);
            }
            
        }   
    }
}
