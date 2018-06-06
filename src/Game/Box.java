package Game;

import Sokoban.Main;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class Box extends GameObject{
    private static BufferedImage tempBoxes32 = null;
    private static Image images[] = null;
    private static int size;
    
    //move box y1x1
    private static int x1, xDir, y1,yDir;   
    
    private Image image; 
    
    private int boxX,boxY;
    private boolean isMoving = false;

    public Box(int x, int y, ID id, Board board, int boxX, int boxY) {
        super(x, y, id);
        this.boxX = boxX;
        this.boxY = boxY;
        
        if(tempBoxes32 == null){
            try {
            tempBoxes32 = ImageIO.read(new File("pictures/5map32.png"));
            } catch (IOException ex) {
                Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        
        images = new Image[2];
        for (int i = 0; i <= 1; i++) {
            images[i] = tempBoxes32.getSubimage((3+i)*32, 0, 32, 32).getScaledInstance(size,size, Image.SCALE_DEFAULT);
        }
        this.image = images[board.getBoard()[boxY][boxX] == FieldType.BOX ? 0 : 1];
    }
    
    public static void moveBox(int x1, int y1,int xDir,int yDir){
        Box.x1 = x1;
        Box.y1 = y1;
        Box.xDir = xDir;
        Box.yDir = yDir;
    }

    @Override
    public void tick() {
        this.image = images[Main.game.getBoard().getBoard()[boxY][boxX] == FieldType.BOX ? 0 : 1];
        if((Box.x1 == boxX && Box.y1 == boxY) || isMoving){
            if(!isMoving){
                boxX += Box.xDir; boxY += Box.yDir;
                isMoving = true;
            }
            int targetX = Main.game.getStartWidth()  + boxX*size;
            int targetY = Main.game.getStartHeight() + boxY*size;
            
            int distanceX = targetX -x;
            int distanceY = targetY -y;
            
            //if closer than speed
            if(Math.abs(distanceX) < Main.game.getPlayer().getSpeed() && Math.abs(distanceY) < Main.game.getPlayer().getSpeed()){
                x = targetX; y = targetY;
                Box.x1 = 0; Box.y1 = 0;
                Box.xDir = 0; Box.yDir = 0;
                isMoving = false;
                Main.game.setBoxesInTheRightPosition(true);
            } else{
                x += xDir*Main.game.getPlayer().getSpeed();
                y += yDir*Main.game.getPlayer().getSpeed();
            }
        }
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(image, x, y, image.getWidth(null), image.getHeight(null), null);
        Toolkit.getDefaultToolkit().sync();
    }
    public static void setSize(int fieldSize) {
        size = fieldSize;
    }
}
