package Gui;

import Game.Game;
import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.GraphicsEnvironment;
import java.awt.Rectangle;
import java.awt.Toolkit;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class Window extends Canvas {

    public JFrame WINDOW;

    //monitor resulition
    public int maxWidth;
    public int maxHeight;
    
    //WINDOW resolution
    private int width;
    private int height;

    public Window(int width, int height, String label) {
        this.width = width;
        this.height = height;
        WINDOW = new JFrame(label);

        WINDOW.setPreferredSize(new Dimension(width, height));
        WINDOW.setMaximumSize(new Dimension(width, height));
        WINDOW.setMinimumSize(new Dimension(width, height));

        getScreenSize();
        setWindowCentered();

        WINDOW.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        WINDOW.setResizable(false);
        WINDOW.setLocationRelativeTo(null);
        
        WINDOW.setVisible(true);
    }

    public void replacePanel(JPanel remove, JPanel add) {
        WINDOW.remove(remove);
        WINDOW.add(add);
        this.refresh();
    }

    void replacePanel(JPanel panel, Game game) {
        WINDOW.remove(panel);
        WINDOW.add(game);
        this.refresh();
    }
    public void replacePanel(Game game, JPanel panel) {
        WINDOW.remove(game);
        WINDOW.add(panel);
        this.refresh();    }
    
    public void refresh() {
        WINDOW.validate();
        WINDOW.repaint();
    }

    public void setWindowCentered() {
        WINDOW.setLocation(maxWidth / 2 - WINDOW.getSize().width / 2, maxHeight / 2 - WINDOW.getSize().height / 2);
    }

    
    private void getScreenSize() {
        Rectangle dim = GraphicsEnvironment.getLocalGraphicsEnvironment().getMaximumWindowBounds();
        maxWidth = dim.width;
        maxHeight = dim.height;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setSize(int width, int height) {
        if(this.height != height || this.width != width){
            this.width = width;
            this.height = height;
            WINDOW.setSize(width, height);
            setWindowCentered();
        }
    }
}
