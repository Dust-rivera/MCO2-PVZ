package view;

import java.awt.Image;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.io.File;
import java.io.IOException;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

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

    JLabel shovel = new JLabel();

    CardLayout cardLayout = new CardLayout();
    JPanel container = new JPanel(cardLayout);
    JButton lvl1 = new JButton("Level 1");
    JButton lvl2 = new JButton("Level 2");
    JButton lvl3 = new JButton("Level 3");

    public JPanel[][] gridCells;

    Font customFont;

    JPanel board;

    JLayeredPane progress = new JLayeredPane();
    JProgressBar progressBar = new JProgressBar();

    // JLabel shopLabel = new JLabel();
    // JLabel shopLabel = new JLabel();

    JLayeredPane layers = new JLayeredPane();

    public GameView() {

        ImageIcon menuBg = new ImageIcon("view\\assets\\PvZMenu.jpg");
        JPanel menu = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(menuBg.getImage(), 0, 0, getWidth(), getHeight(), this);
            }
        };
        menu.setLayout(null);

        lvl1.setBounds(200, 300, 100, 50);
        lvl2.setForeground(Color.GRAY);
        lvl2.setBounds(400, 300, 100, 50);
        lvl2.setForeground(Color.GRAY);
        lvl3.setBounds(600, 300, 100, 50);
        lvl3.setForeground(Color.GRAY);

        menu.add(lvl1);
        menu.add(lvl2);
        menu.add(lvl3);

        container.add(menu, "Menu");

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
        // ImageIcon bg = new ImageIcon(
        // "view\\assets\\lvl1.png"); // test

        // backGround.setIcon(bg);
        ImageIcon shop = new ImageIcon("view\\assets\\shop border.png");
        ImageIcon sunflowerPk = new ImageIcon(
                "view\\assets\\SunflowerPack.png");
        ImageIcon peashooterPk = new ImageIcon(
                "view\\assets\\PeashooterPack.png");
        ImageIcon cherryPk = new ImageIcon("view\\assets\\CherryPack.png");

        JPanel lose = new JPanel();
        ImageIcon losePic = new ImageIcon("view\\assets\\PvZ1ZombiesWon.png");
        JLabel loseLabel = new JLabel(losePic);
        loseLabel.setBounds(30, 400, losePic.getIconWidth(), losePic.getIconHeight());
        lose.setBackground(Color.BLACK);
        lose.add(loseLabel);

        container.add(lose, "Lose");

        JPanel win = new JPanel();
        ImageIcon winPic = new ImageIcon("view\\assets\\win.png");
        JLabel winLabel = new JLabel(winPic);
        win.setBackground(Color.BLACK);
        win.add(winLabel);

        container.add(win, "Win");

        ImageIcon shovelPic = new ImageIcon("view\\assets\\Shovel2.png");
        shovel.setIcon(shovelPic);
        shovel.setBounds(shop.getIconWidth() + 5, 10, shovelPic.getIconWidth(), shovelPic.getIconHeight());

        progress.setBounds(881 - 300, 10, 250, 30);
        progressBar.setBounds(0, 0, 250, 30);
        progressBar.setMinimum(0);
        progressBar.setMaximum(180);
        progressBar.setValue(180);
        progressBar.setForeground(Color.decode("#6AA84F"));
        progressBar.setBorder(BorderFactory.createLineBorder(Color.BLACK, 3));

        ImageIcon progressFlag1 = new ImageIcon("view\\assets\\Progress2.png");
        JLabel flag1 = new JLabel(progressFlag1);
        flag1.setBounds(200, 2, progressFlag1.getIconWidth(), progressFlag1.getIconHeight());
        ImageIcon progressFlag2 = new ImageIcon("view\\assets\\Progress2.png");
        JLabel flag2 = new JLabel(progressFlag2);
        flag2.setBounds(130, 2, progressFlag1.getIconWidth(), progressFlag1.getIconHeight());
        ImageIcon progressFlag3 = new ImageIcon("view\\assets\\Progress2.png");
        JLabel flag3 = new JLabel(progressFlag3);
        flag3.setBounds(50, 2, progressFlag1.getIconWidth(), progressFlag1.getIconHeight());

        progress.add(flag1, Integer.valueOf(5));
        progress.add(flag2, Integer.valueOf(5));
        progress.add(flag3, Integer.valueOf(5));

        progress.add(progressBar, Integer.valueOf(1));

        layers.add(progress, Integer.valueOf(5));

        // board = new JPanel(new GridLayout(r, c));
        // board = new JPanel(new GridLayout(r, c));
        // board.setBounds(110, 100, 700, 450);
        // board.setBounds(110, 280, 700, 90);

        // board.setOpaque(false);

        // for (int row = 0; row < r; row++) {
        // for (int col = 0; col < c; col++) {
        // gridCells[row][col] = new JPanel();
        // gridCells[row][col].setOpaque(false);
        // gridCells[row][col].setBorder(BorderFactory.createLineBorder(Color.RED)); //
        // So background is visible
        // board.add(gridCells[row][col]);
        // }
        // }

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

        layers.setBounds(0, 0, 881, 600);

        sunflowerPack.setIcon(sunflowerPk);
        sunflowerPack.setBounds(73, 5, sunflowerPk.getIconWidth(), sunflowerPk.getIconHeight());
        peashooterPack.setIcon(peashooterPk);
        peashooterPack.setBounds(123, 5, peashooterPk.getIconWidth(), peashooterPk.getIconHeight());
        cherryPack.setIcon(cherryPk);
        cherryPack.setBounds(173, 5, cherryPk.getIconWidth(), cherryPk.getIconHeight());

        // ShopListener listener = new ShopListener(peashooterPack, cherryPack,
        // backGround);
        // sunflowerPack.addMouseListener(listener);
        // peashooterPack.addMouseListener(listener);
        // cherryPack.addMouseListener(listener);

        // backGround.setIcon(bg);
        backGround.setBounds(0, 0, 881, 600);

        shopLabel.setIcon(shop);
        shopLabel.setBounds(5, 0, shop.getIconWidth(), shop.getIconHeight());

        // sunflowerPack.setOpaque(true);

        layers.add(backGround, Integer.valueOf(0));
        layers.add(shopLabel, Integer.valueOf(1));
        layers.add(sunflowerPack, Integer.valueOf(2));
        layers.add(peashooterPack, Integer.valueOf(2));
        layers.add(cherryPack, Integer.valueOf(2));
        layers.add(sunCount, Integer.valueOf(2));
        layers.add(shovel, Integer.valueOf(2));
        // layers.add(board, Integer.valueOf(3));

        container.add(layers, "Game");

        cardLayout.show(container, "Menu");
        // cardLayout.show(container, "Lose");

        this.add(container);
        this.setIconImage(icon.getImage());
        this.setTitle("Plants vs. Zombies");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(881, 600);
        // this.setLayout(null);
        this.setLocationRelativeTo(null);
        this.setSize(881, 600);
        this.setVisible(true);

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

    public void addZombie(JLabel zombie) {
        layers.add(zombie, Integer.valueOf(5));
        layers.repaint();
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

    public JProgressBar getProgressBar() {
        return progressBar;
    }

    public JLabel getShovel() {
        return shovel;
    }

    public JButton getLvl1() {
        return lvl1;
    }

    public JButton getLvl2() {
        return lvl2;
    }

    public JButton getLvl3() {
        return lvl3;
    }

    public CardLayout getCardLayout() {
        return cardLayout;
    }

    public JPanel getContainerP() {
        return container;
    }

    public JLabel getBackGround() {
        return backGround;
    }

    public JPanel getBoard() {
        return board;
    }

    public void setBoard(JPanel board) {
        this.board = board;
    }

    public void setGridCells(JPanel[][] gridCells) {
        this.gridCells = gridCells;
    }

    public static void main(String[] args) {
        new GameView();
    }
}