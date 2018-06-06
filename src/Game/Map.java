package Game;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class Map extends GameObject{
    private Image image;

    public Map(int x, int y, ID id, int fieldsize, Board board) {
        super(x, y, id);
     
        //read file
        BufferedImage tempFields32 = null;
        try {
            tempFields32 = ImageIO.read(new File("pictures/5map32.png"));
        } catch (IOException ex) {
            Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        // create field array
        // 0 = Field, 1 = Target, 2 = Wall 
        BufferedImage fields32[] = new BufferedImage[3];
        for (int i = 0; i < fields32.length; i++) {
            fields32[i] = tempFields32.getSubimage(i*32, 0, 32, 32);
        }
        
        //create map32
        BufferedImage image32 = new BufferedImage(board.getWidth()*32, board.getHeight()*32, BufferedImage.TYPE_INT_ARGB);
        for (int boardY = 0; boardY < board.getHeight(); boardY++) {
            for (int boardX = 0; boardX < board.getWidth(); boardX++) {
                if(board.getBoard()[boardY][boardX] != FieldType.EMPTY){
                    image32.createGraphics().drawImage(fields32[getOrder(board.getBoard()[boardY][boardX])], boardX*32, boardY*32, null);
                }
            }
        }
        
        //resize size map32
        this.image = image32.getScaledInstance(image32.getWidth()*fieldsize/32, image32.getHeight()*fieldsize/32, Image.SCALE_DEFAULT);   
    }
    private int getOrder(FieldType type) {
        // 0 = Field, 1 = Target, 2 = Wall 
        switch (type) {
            case FIELD:  
            case PLAYER: 
            case BOX: 
                return 0;
                
            case TARGET: 
            case PLAYER_ON_TARGET: 
            case BOX_ON_TARGET:
                return 1;
            
            case WALL: 
                return 2;
        }
        return -1; // error
    }
    
    @Override
    public void tick() {
        // map does not change
    }

    @Override
    public void render(Graphics g) {
        g.drawImage(image, x, y, image.getWidth(null), image.getHeight(null), null);
        Toolkit.getDefaultToolkit().sync();
    }
}
