package Game;

import Sokoban.Main;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Counter extends GameObject{
    private final int FONT_HEIGHT;
    private int count;
    private String label;
    
    public Counter(int x, int y, ID id, String label, int height) {
        super(x, y, id);
        this.count = 0;
        this.label = label;
        FONT_HEIGHT = height;
    }

    @Override
    public void tick() {
        
    }

    @Override
    public void render(Graphics g) {
       String label = this.label + " " + this.count;
       g.setColor(Color.WHITE);
       g.setFont(new Font("Monospaced", Font.BOLD, FONT_HEIGHT));
       int width = g.getFontMetrics().stringWidth(label);
       g.drawString(label, x , y);
       Main.game.getPushCount().setX(x+width+FONT_HEIGHT);
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }
    
    public int increase() {
        return count++;
    }

    public void decrease() {
        this.count--;
    }
    
}
