package view;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.image.BufferedImage;

import view.GamePanel;
public class MapSelection extends JPanel {
    public MapSelection() {
        setLayout(new GridLayout(3, 1));

        JButton map1Button = new JButton("MAP 1");
        JButton map2Button = new JButton("MAP 2");
        JLabel map2UnavailableLabel = new JLabel("Sorry, only MAP 1 is available", SwingConstants.CENTER);

        add(map1Button);
        add(map2Button);
        add(map2UnavailableLabel);

        }
    }
}


