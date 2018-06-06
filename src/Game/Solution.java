package Game;

import Sokoban.Main;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class Solution extends GameObject {

    private final int FONT_HEIGHT;
    private String solution;

    public Solution(int x, int y, ID id, int height) {
        super(x, y, id);
        FONT_HEIGHT = height;
        solution = "";
    }

    @Override
    public void tick() {

    }

    @Override
    public void render(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Monospaced", Font.BOLD, FONT_HEIGHT));
        int width = g.getFontMetrics().stringWidth(solution);
        if (width > Main.game.getPanelWidth() - 2 * FONT_HEIGHT) {
            x = Main.game.getPanelWidth() - FONT_HEIGHT - width;
        }
        g.drawString(solution, x, y);
    }

    public String getSolution() {
        return solution;
    }

    public void append(char append) {
        this.solution += append;
    }

    public void removeLast() {
        if (solution.length() == 0) {
            return;
        } else {
            solution = solution.substring(0, solution.length() - 1);
        }
    }

    public char getLast() {
        if (this.solution.length() == 0) {
            return 'X';
        } else {
            return solution.charAt(solution.length() - 1);
        }
    }

    void append(int x, int y, boolean isPush) {
        char move;
        if (x == 0) {
            if (y == -1) {
                move = 'u';
            } else {
                move = 'd';
            }
        } else if (x == 1) {
            move = 'r';
        } else {
            move = 'l';
        }
        if (isPush) {
            move = Character.toUpperCase(move);
        }
        solution += move;
    }
    
}
