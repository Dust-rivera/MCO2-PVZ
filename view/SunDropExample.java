package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class SunDropExample extends JFrame {
    private JLayeredPane layers;
    private JLabel sunCounterLabel;
    private int sunCount = 0;
    private Random random = new Random();

    public SunDropExample() {
        setTitle("Sun Drop Example");
        setSize(800, 600);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(null);

        layers = new JLayeredPane();
        layers.setBounds(0, 0, 800, 600);
        add(layers);

        sunCounterLabel = new JLabel("0");
        sunCounterLabel.setFont(new Font("Arial", Font.BOLD, 24));
        sunCounterLabel.setBounds(20, 20, 100, 40);
        layers.add(sunCounterLabel, Integer.valueOf(10));

        // Spawn a sun every 5 seconds
        Timer spawnTimer = new Timer(5000, e -> spawnSun());
        spawnTimer.start();

        setVisible(true);
    }

    private void spawnSun() {
        ImageIcon sunIcon = new ImageIcon("C:\\Users\\river\\Desktop\\MCO2-PVZ-main BRANCH\\view\\assets\\Sun_PvZ2.png"); // your sun image
        Image scaled = sunIcon.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);
        sunIcon = new ImageIcon(scaled);

        JLabel sunLabel = new JLabel(sunIcon);
        int x = random.nextInt(700); // random x position
        sunLabel.setBounds(x, 0, 70, 70);
        layers.add(sunLabel, Integer.valueOf(5));
        layers.repaint();

        // Click listener for sun
        sunLabel.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                layers.remove(sunLabel);
                layers.repaint();
                sunCount += 25;
                sunCounterLabel.setText(String.valueOf(sunCount));
            }
        });

        // Animate falling sun
        Timer fallTimer = new Timer(30, new ActionListener() {
            int y = 0;
            int maxY = 400 + random.nextInt(100); // stops randomly in the field
            @Override
            public void actionPerformed(ActionEvent e) {
                y += 5;
                if (y >= maxY) {
                    ((Timer)e.getSource()).stop();
                }
                sunLabel.setBounds(x, y, 70, 70);
            }
        });
        fallTimer.start();
    }

    public static void main(String[] args) {
        new SunDropExample();
    }
}
