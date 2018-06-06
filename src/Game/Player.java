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

public class Player extends GameObject {

    private Image image[][][] = new Image[7][4][3];
    private int size;

    // animation
    private int imageX = 1, imageY = 0;
    private boolean increaseImageXindex = true;
    private int skin;
    private int speed;

    public Player(int x, int y, ID id, int size, int skin) {
        super(x, y, id);
        this.size = size;
        this.skin = skin;
        this.speed = 6;
        //init skins
        try {
            BufferedImage allSkin = ImageIO.read(new File("pictures/player.png"));
            for (int skins = 0; skins < 7; skins++) {
                int startX = skins % 4 * 32 * 3;
                int startY = skins <= 3 ? 0 : 4 * 32;
                
                for (int height = 0; height < 4; height++) {
                    for (int width = 0; width < 3; width++) {
                        BufferedImage subImage = allSkin.getSubimage(startX + width * 32, startY + height * 32, 32, 32);
                        this.image[skins][height][width] = subImage.getScaledInstance(size, size, Image.SCALE_DEFAULT);
                    }
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void tick() {
        //player SHOULD stand here
        int playerX = Main.game.getStartWidth() + size * Main.game.getBoard().getPlayerX();
        int playerY = Main.game.getStartHeight() + size * Main.game.getBoard().getPlayerY();

        int distanceX = playerX - x;
        int distanceY = playerY - y;

        //if closer than speed
        if (Math.abs(distanceX) < speed && Math.abs(distanceY) < speed) {
            x = playerX;
            y = playerY;
            imageX = 1;
            Main.game.setPlayerInTheRightPosition(true);
        } else {
            x += sign(distanceX) * speed;
            y += sign(distanceY) * speed;
            if (imageX == 2) {
                increaseImageXindex = false;
            }
            if (imageX == 0) {
                increaseImageXindex = true;
            }
            imageX += increaseImageXindex ? 1 : -1;
        }
    }

    private int sign(int number) {
        if (number == 0) {
            return 0;
        }
        return number > 0 ? 1 : -1;
    }

    public void render(Graphics g) {
        g.drawImage(this.image[skin][imageY][imageX], x, y, size, size, null);
        Toolkit.getDefaultToolkit().sync();
    }

    public void setImageY(Move.DIR dir, boolean undo) {
        switch (dir) {
            case DOWN:
                imageY = !undo ? 0 : 3;
                break;
            case UP:
                imageY = !undo ? 3 : 0;
                break;
            case LEFT:
                imageY = !undo ? 1 : 2;
                break;
            case RIGHT:
                imageY = !undo ? 2 : 1;
                break;
        }
    }

    public void changeSkin(){
        if(skin == 6){
            skin = 0;
        }else{
            skin++;
        }
    }
        public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = Main.game.clamp(speed, 1, size);
        System.out.println("speed: "+ this.speed);
    }
    
}
