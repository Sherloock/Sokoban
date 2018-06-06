package Gui;

import Sokoban.Main;
import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Credits {
        private static JPanel panel;

    public Credits() {
        initPanel();
        initText();
        initBack();
    }

    private void initPanel() {
        Main.frame.setSize(800, 600);
        panel = new JPanel();
        panel.setBackground(Color.black);
        panel.setLayout(null); // absoulute positioning
        panel.setOpaque(true);
        Main.frame.WINDOW.add(panel);
        Main.frame.refresh();
    }

    private void initBack() {
        int width = 300, height = 60;

        JButton back = new JButton("Back!");
        back.setBounds((Main.frame.WINDOW.getWidth() - width) / 2, 500, width, height);
        back.setBackground(Color.white);
        back.setForeground(Color.black);
        back.setFont(new Font("Courier", Font.BOLD, (int) (height * 0.4)));
        back.setFocusPainted(false);
        back.setMultiClickThreshhold(50);
        back.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                Main.frame.replacePanel(panel, Main.menu.getPanel());
                Main.frame.setSize(400,600);
            }
        });
        panel.add(back);
    }

    private void initText() {
        String folder = "documents", filename = "credits";
        ArrayList<String> text = Sokoban.TxtHandler.readToArrayList(folder + "/" + filename + ".txt");
        int columns = 3;
        int startHeight = 30;
        int height = 450/(text.size()/columns);
        for (int i = 0; i < columns; i++) {
            for (int j = 0; j < text.size()/columns; j++) {
                JLabel temp = new JLabel(text.get(i*text.size()/columns+j),  SwingConstants.CENTER);
                temp.setFont(new Font("SansSerif", Font.BOLD, height-5));
                temp.setBounds(800/columns*i,
                        startHeight + height*j,
                        Main.frame.WINDOW.getWidth()/columns,
                        height);
                temp.setForeground(Color.white);
                temp.setBackground(Color.BLACK);
                panel.add(temp);
            }
        }
        
    }

    public static JPanel getPanel() {
        return panel;
    }
}
