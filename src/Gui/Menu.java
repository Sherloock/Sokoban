package Gui;

import Sokoban.*;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Menu {

    private static JPanel panel;
    private JButton buttons[];

    public Menu() {
        initPanel();
        initButtons();
        initHeader();
    }


    private void initPanel() {
        panel = new JPanel();
        panel.setBackground(Color.black);
        panel.setLayout(null); // absoulute positioning
    }

    private void initButtons() {
        int width = 250, height = 50;
        String options[] = {
            "New game",
            "Load game",
            "Highscores",
            "Controll",
            "About game",
            "Credits",
            "Exit"
        };
        buttons = new JButton[options.length];

        for (int x = 0; x < buttons.length; x++) {
            buttons[x] = new JButton(options[x]);
            buttons[x].setBounds((Main.frame.WINDOW.getSize().width - width) / 2, 120 + x * height * 6 / 5, width, height);
            buttons[x].setBackground(Color.white);
            buttons[x].setForeground(Color.black);
            buttons[x].setFont(new Font("Courier", Font.BOLD, (int) (height * 0.7)));
            buttons[x].setFocusPainted(false);
            buttons[x].setMultiClickThreshhold(50);
            
            final int z = x;
            buttons[x].addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    switch(z){
                        case 0: // new game
                            Main.frame.setSize(600, 400);
                            if(Main.selectLevel == null){
                                Main.frame.WINDOW.remove(panel);
                                Main.selectLevel = new SelectLevel();
                            }else{
                                Main.frame.replacePanel(panel, Main.selectLevel.getPanel());
                            }
                            break;
                            
                        case 1: // load game
                            
                            break;
                            
                        case 2: // highscores
                            
                            break;
                        case 3: // controll
                            Main.frame.setSize(800,600);
                             if(Main.controll == null){
                                Main.frame.WINDOW.remove(panel);
                                Main.controll = new Controll();
                            }else{
                                Main.frame.replacePanel(panel, Main.controll.getPanel());
                            }
                            break;
                            
                        case 4: // about game
                            Main.frame.setSize(800,600);
                            if(Main.about == null){
                                Main.frame.WINDOW.remove(panel);
                                Main.about = new About();
                            }else{
                                Main.frame.replacePanel(panel, Main.about.getPanel());
                            }
                            break;
                            
                        case 5: //credits
                            Main.frame.setSize(800,600);
                            if(Main.credits == null){
                                Main.frame.WINDOW.remove(panel);
                                Main.credits = new Credits();
                            }else{
                                
                                Main.frame.replacePanel(panel, Main.credits.getPanel());
                            }
                            
                            break;
                            
                        case 6: // exit
                            Main.frame.WINDOW.dispose();
                            System.out.println("\nHave a nice day! ♥\n");
                            System.exit(0);
                            break;
                            
                    }
                }
            });
            panel.add(buttons[x]);
        }
    }

    private void initHeader() {
        int height = 80;
        JLabel header = new JLabel("Sokoban", SwingConstants.CENTER);
        header.setFont(new Font("SansSerif", Font.BOLD, height-5));
        header.setBounds(0, 10, Main.frame.WINDOW.getWidth(), height);
        header.setForeground(Color.white);
        header.setBackground(Color.BLACK);
        panel.add(header);
    }

    public JPanel getPanel() {
        return panel;
    }
    
}
