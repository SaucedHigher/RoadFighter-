package view;

import javax.swing.*;
import java.awt.*;

public class Options extends JPanel {
    public Options() {
        setLayout(new BorderLayout());

        JLabel controlsLabel = new JLabel("Controls: W - Up, A - Left, S - Down, D - Right, R - Kick, F - Punch, C - Throw Item, X - Crouch");
        controlsLabel.setHorizontalAlignment(SwingConstants.CENTER);

        add(controlsLabel, BorderLayout.CENTER);
    }
}
