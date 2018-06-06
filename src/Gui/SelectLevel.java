package Gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import Sokoban.*;
import Game.*;

public class SelectLevel {

    private static JPanel panel;
    private static JTextField id; 
    public SelectLevel() {
        initPanel();
        initMessage();
        initTextField();
        initButtons();
    }

    private void initPanel() { 
        panel = new JPanel();
        panel.setBackground(Color.black);
        panel.setLayout(null); // absoulute positioning
        panel.setOpaque(true);
        Main.frame.WINDOW.add(panel);
        Main.frame.refresh();
    }

    private void initMessage() {
        int height = 30;
        String text[] = {
            "You can select level by ID or random!",
            Main.LEVEL_COUNT + " levels available!",
            "Please select level:",};
        for (int i = 0; i < text.length; i++) {
            JLabel message = new JLabel(text[i], SwingConstants.CENTER);
            message.setFont(new Font("Courier", Font.BOLD, height-5));
            message.setBounds(0, 30+i*(height+10), Main.frame.WINDOW.getWidth(), height);
            message.setForeground(Color.white);
            message.setBackground(Color.BLACK);
            panel.add(message);
        }

    }

    private void initTextField() {
        int height = 30;
        id = new JTextField(4);
        id.setText("1");
        id.setFont(new Font("Courier", Font.BOLD, height-5));
        id.setBorder(BorderFactory.createEmptyBorder());
        id.setForeground(Color.black);
        id.setBackground(Color.white);
        id.setHorizontalAlignment(JTextField.CENTER);
        
         int width = id.getPreferredSize().width;
        id.setBounds((Main.frame.WINDOW.getWidth()-width)/2, 150, width, height);
        panel.add(id);
    }

    private void initButtons() {
        int width = 250, height = 60;
        String options[] = {
            "New game!",
            "Back",
        };
        int space = (Main.frame.WINDOW.getWidth()-(options.length*width))/ (options.length + 1);
        JButton buttons[] = new JButton[options.length];

        for (int x = 0; x < buttons.length; x++) {
            buttons[x] = new JButton(options[x]);
            buttons[x].setBounds((x+1)*space + x*width , 400-30-height-space, width, height);
            buttons[x].setBackground(Color.white);
            buttons[x].setForeground(Color.black);
            buttons[x].setFont(new Font("Courier", Font.BOLD, (int) (height * 0.6)));
            buttons[x].setFocusPainted(false);
            buttons[x].setMultiClickThreshhold(50);
            
            final int z = x;
            buttons[x].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    switch(z){
                        case 0: // New Game
                            final int level = Integer.valueOf(id.getText());
                            if(level > 0 && level <= Main.LEVEL_COUNT){
                                Main.game = new Game(level);
                                Main.frame.replacePanel(panel,  Main.game );
                                Main.game.start();
                            }
                            break;
                            
                        case 1: // BACK
                            Main.frame.setSize(400,600);
                            Main.frame.replacePanel(panel, Main.menu.getPanel());
                            break;
                    }
                }
            });
            panel.add(buttons[x]);
        }
        
        JButton random = new JButton("getRandom");
        random.setBounds(((Main.frame.WINDOW.getWidth()-width)/2)+ 200, 150, random.getPreferredSize().width, 30);
        random.setForeground(Color.black);
        random.setBackground(Color.white);
        random.setFont(new Font("Courier", Font.BOLD, (int) (height * 0.4)));
        random.setFocusPainted(false);
        random.setMultiClickThreshhold(50);
        random.setBounds(((Main.frame.WINDOW.getWidth()-width)/2)+ 250, 150, random.getPreferredSize().width, 30);
        random.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                id.setText(getRandomLevel()+"");
            }
        });
        panel.add(random);
    }
    private int getRandomLevel(){
        return (int)(Math.random()*(Main.LEVEL_COUNT)+1);
    }
    
    public static JPanel getPanel() {
        return panel;
    }

}
