package Game;

import Sokoban.Main;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Timer extends GameObject{
     private final int FONT_HEIGHT;
    //time in milliseconds
    private final long START;
    private long elapsedSeconds;
    
    private long hours;
    private long mins;
    private long secs;
    
    public Timer(int x, int y, ID id, int height) {
        super(x, y, id);
        START = System.currentTimeMillis();
        FONT_HEIGHT = height;
    }

    @Override
    public void tick() {
        elapsedSeconds = (System.currentTimeMillis()-START)/1000;
        hours = elapsedSeconds/3600;
        mins = elapsedSeconds/60%60;
        secs = elapsedSeconds%60;
    }

    @Override
    public void render(Graphics g) {
       String label = "time " + ((hours == 0) ? String.format("%d:%02d", mins, secs) : String.format("%d:%02d:%02d", hours, mins, secs));
       g.setColor(Color.WHITE);
       g.setFont(new Font("Monospaced", Font.BOLD, FONT_HEIGHT));
       int width = g.getFontMetrics().stringWidth(label);
       x = Main.game.getPanelWidth()-width-FONT_HEIGHT;
       g.drawString(label, x , y);
    }

    public long getElapsedSeconds() {
        return elapsedSeconds;
    }

    
    
}
