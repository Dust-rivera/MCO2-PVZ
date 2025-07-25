package view;

import java.awt.Image;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.GridLayout;
import java.io.File;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;

// import javafx.scene.effect.Light.Point;
// import javafx.scene.shape.Rectangle;

//import javafx.scene.text.Font;

public class GameView extends JFrame {

    JLabel backGround = new JLabel();
    JLabel shopLabel = new JLabel();
    JLabel sunCount = new JLabel();

    JLabel sunflowerPack = new JLabel();
    JLabel peashooterPack = new JLabel();
    JLabel cherryPack = new JLabel();

    public JPanel[][] gridCells = new JPanel[5][9];

    Font customFont;

    JPanel board;

    // ArrayList<JLabel> suns = new ArrayList<>();

    // JLabel shopLabel = new JLabel();
    // JLabel shopLabel = new JLabel();

    JLayeredPane layers = new JLayeredPane();

    public GameView() {

        try {
            customFont = Font
                    .createFont(Font.TRUETYPE_FONT,
                            new File("view\\assets\\Chalkboard.ttc"))
                    .deriveFont(18f);
            sunCount.setFont(customFont);
        } catch (FontFormatException e) {
        } catch (IOException e) {
        }

        ImageIcon icon = new ImageIcon("view\\assets\\logo.png");
        ImageIcon bg = new ImageIcon(
                "view\\assets\\PC Computer - Plants vs Zombies - Day.png");
        ImageIcon shop = new ImageIcon("view\\assets\\shop border.png");
        ImageIcon sunflowerPk = new ImageIcon(
                "view\\assets\\SunflowerPack.png");
        ImageIcon peashooterPk = new ImageIcon(
                "view\\assets\\PeashooterPack.png");
        ImageIcon cherryPk = new ImageIcon("view\\assets\\CherryPack.png");

        board = new JPanel(new GridLayout(5, 9, 5, 7));
        board.setBounds(110, 100, 700, 450);
        board.setBackground(Color.GREEN);
        // board.setVisi(true);
        board.setOpaque(false);

        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 9; col++) {
                gridCells[row][col] = new JPanel();
                gridCells[row][col].setOpaque(false);
                gridCells[row][col].setBorder(BorderFactory.createLineBorder(Color.RED)); // So background is visible
                board.add(gridCells[row][col]);
            }
        }

        sunCount.setBounds(25, 50, 29, 29);
        // sunCount.setOpaque(true);

        Image image = sunflowerPk.getImage();
        Image newImg = image.getScaledInstance(50, 65, Image.SCALE_SMOOTH);
        sunflowerPk = new ImageIcon(newImg);

        image = peashooterPk.getImage();
        newImg = image.getScaledInstance(50, 65, Image.SCALE_SMOOTH);
        peashooterPk = new ImageIcon(newImg);

        image = cherryPk.getImage();
        newImg = image.getScaledInstance(50, 65, Image.SCALE_SMOOTH);
        cherryPk = new ImageIcon(newImg);

        layers.setBounds(0, 0, bg.getIconWidth(), bg.getIconHeight());

        sunflowerPack.setIcon(sunflowerPk);
        sunflowerPack.setBounds(73, 5, sunflowerPk.getIconWidth(), sunflowerPk.getIconHeight());
        peashooterPack.setIcon(peashooterPk);
        peashooterPack.setBounds(123, 5, peashooterPk.getIconWidth(), peashooterPk.getIconHeight());
        cherryPack.setIcon(cherryPk);
        cherryPack.setBounds(173, 5, cherryPk.getIconWidth(), cherryPk.getIconHeight());

        // ShopListener listener = new ShopListener(peashooterPack, cherryPack, backGround);
        // sunflowerPack.addMouseListener(listener);
        // peashooterPack.addMouseListener(listener);
        // cherryPack.addMouseListener(listener);

        backGround.setIcon(bg);
        backGround.setBounds(0, 0, bg.getIconWidth(), bg.getIconHeight());

        shopLabel.setIcon(shop);
        shopLabel.setBounds(5, 0, shop.getIconWidth(), shop.getIconHeight());

        sunflowerPack.setOpaque(true);

        layers.add(backGround, Integer.valueOf(0));
        layers.add(shopLabel, Integer.valueOf(1));
        layers.add(sunflowerPack, Integer.valueOf(2));
        layers.add(peashooterPack, Integer.valueOf(2));
        layers.add(cherryPack, Integer.valueOf(2));
        layers.add(sunCount, Integer.valueOf(2));
        layers.add(board, Integer.valueOf(3));

        this.setIconImage(icon.getImage());
        this.setTitle("Plants vs. Zombies");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(bg.getIconWidth(), bg.getIconHeight());
        this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setVisible(true);
        this.add(layers);
    }

    // public void drawSunCount(Graphics2D g){
    // g.setFont(customFont);
    // g.setColor(Color.BLACK);
    // FontMetrics metrics = g.getFontMetrics(customFont);
    // g.drawString(Integer.toString(sunCount), 4
    // -(metrics.stringWidth(Integer.toString(sunCount))/2), 4);
    // }

    public void updateSunCounter(int count) {
        sunCount.setText(String.valueOf(count));
    }

    public void addSun(JLabel sun) {
        layers.add(sun, Integer.valueOf(5));
        layers.repaint();
    }

    public void removeSun(JLabel sun) {
        layers.remove(sun);
        layers.repaint();
    }

    @Override
    public int getHeight() {
        return super.getHeight();
    }

    @Override
    public int getWidth() {
        return super.getWidth();
    }

    public JLabel getSunCount() {
        return sunCount;
    }

    public JLabel getSunflowerPack() {
        return sunflowerPack;
    }

    public JLabel getPeashooterPack() {
        return peashooterPack;
    }

    public JLabel getCherryPack() {
        return cherryPack;
    }

    public JLayeredPane getLayers() {
        return layers;
    }

    public JPanel[][] getGridCells() {
        return gridCells;
    }

    
    public static void main(String[] args) {
        new GameView();
    }
}