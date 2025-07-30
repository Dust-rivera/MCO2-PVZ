package view;

/**
 * This class represents the main GUI of the PvZ game
 * 
 * @author Deveza, Jerry King
 * @author Rivera, Dustine Gian
 * @version 21.0
 */
import java.awt.Image;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Graphics;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;

public class GameView extends JFrame {

    private JLabel backGround;
    private JLabel shopLabel;
    private JLabel sunCount;

    private JLabel sunflowerPack;
    private JLabel peashooterPack;
    private JLabel cherryPack;
    private JLabel wallnutPack;

    private JLabel shovel;

    private CardLayout cardLayout;
    private JPanel container;

    private JButton lvl1;
    private JButton lvl2;
    private JButton lvl3;

    private JLabel mow1;
    private JLabel mow2;
    private JLabel mow3;
    private JLabel mow4;
    private JLabel mow5;

    public JPanel[][] gridCells;

    private JPanel board;

    private JLayeredPane progress;
    private JProgressBar progressBar;

    private JLayeredPane layers;

    private ImageIcon normalZWalk;
    private ImageIcon coneZWalk;
    private ImageIcon bucketZWalk;

    private ImageIcon normalZEat;
    private ImageIcon coneZEat;
    private ImageIcon bucketZEat;

    private ImageIcon peashooterGif;
    private ImageIcon sunflowerGif;
    private ImageIcon cherrybombGif;
    private ImageIcon wallnutpic;

    private ImageIcon pea;
    private ImageIcon peax;

    private ImageIcon sun;

    /**
     * This creates the main GUI of the game using JLayeredPane
     */
    public GameView() {

        backGround = new JLabel();
        shopLabel = new JLabel();
        sunCount = new JLabel();

        sunflowerPack = new JLabel();
        peashooterPack = new JLabel();
        cherryPack = new JLabel();
        wallnutPack = new JLabel();

        shovel = new JLabel();

        cardLayout = new CardLayout();
        container = new JPanel(cardLayout);

        lvl1 = new JButton("Level 1");
        lvl2 = new JButton("Level 2");
        lvl3 = new JButton("Level 3");

        mow1 = new JLabel();
        mow2 = new JLabel();
        mow3 = new JLabel();
        mow4 = new JLabel();
        mow5 = new JLabel();

        progress = new JLayeredPane();
        progressBar = new JProgressBar();

        layers = new JLayeredPane();

        normalZWalk = new ImageIcon("view\\gifs\\Zombie80.gif");
        coneZWalk = new ImageIcon("view\\gifs\\ConeheadZombie.gif");
        bucketZWalk = new ImageIcon("view\\gifs\\BucketheadZombie.gif");
        normalZEat = new ImageIcon("view\\gifs\\NormalZombieEating.gif");
        coneZEat = new ImageIcon("view\\gifs\\ConeZombieEating.gif");
        bucketZEat = new ImageIcon("view\\gifs\\Bucketheadeating.gif");

        peashooterGif = new ImageIcon("view\\gifs\\Peashooter.gif");
        sunflowerGif = new ImageIcon("view\\gifs\\Sunflower.gif");
        cherrybombGif = new ImageIcon("view\\gifs\\CherryExplode.gif");
        wallnutpic = new ImageIcon("view\\assets\\wallnut.png");

        pea = new ImageIcon("view\\assets\\Pea_p.png");
        peax = new ImageIcon("view\\assets\\peax.png");

        sun = new ImageIcon("view\\assets\\Sun.png");

        layers.setLayout(null);
        layers.setOpaque(false);

        board = new JPanel();
        board.setLayout(null);
        board.setBackground(new Color(0, 0, 0, 0));

        this.setBoard(board);

        gridCells = new JPanel[5][9];
        for (int i = 0; i < gridCells.length; i++) {
            for (int j = 0; j < gridCells[i].length; j++) {
                gridCells[i][j] = new JPanel();
                gridCells[i][j].setBounds(j * 100, i * 100, 100, 100);
                gridCells[i][j].setOpaque(false);
                board.add(gridCells[i][j]);
            }
        }

        this.setGridCells(gridCells);

        JLabel mow1 = new JLabel();
        JLabel mow2 = new JLabel();
        JLabel mow3 = new JLabel();
        JLabel mow4 = new JLabel();
        JLabel mow5 = new JLabel();

        this.getLayers().add(board, Integer.valueOf(3));
        this.getLayers().setOpaque(false);

        ImageIcon mow = new ImageIcon("view\\assets\\Lawn_Mower.png");
        mow1.setIcon(mow);
        mow1.setBounds(50, 110, mow.getIconWidth(), mow.getIconHeight());
        layers.add(mow1, Integer.valueOf(4));

        mow2.setIcon(mow);
        mow2.setBounds(50, 210, mow.getIconWidth(), mow.getIconHeight());
        layers.add(mow2, Integer.valueOf(4));

        mow3.setIcon(mow);
        mow3.setBounds(50, 295, mow.getIconWidth(), mow.getIconHeight());
        layers.add(mow3, Integer.valueOf(4));

        mow4.setIcon(mow);
        mow4.setBounds(50, 390, mow.getIconWidth(), mow.getIconHeight());
        layers.add(mow4, Integer.valueOf(4));

        mow5.setIcon(mow);
        mow5.setBounds(50, 490, mow.getIconWidth(), mow.getIconHeight());
        layers.add(mow5, Integer.valueOf(4));

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

        ImageIcon icon = new ImageIcon("view\\assets\\logo.png");
        ImageIcon shop = new ImageIcon("view\\assets\\shop border.png");
        ImageIcon sunflowerPk = new ImageIcon(
                "view\\assets\\SunflowerPack.png");
        ImageIcon peashooterPk = new ImageIcon(
                "view\\assets\\PeashooterPack.png");
        ImageIcon cherryPk = new ImageIcon("view\\assets\\CherryPack.png");
        ImageIcon wallnutPk = new ImageIcon("view\\assets\\WallnutPack.png");

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

        sunCount.setBounds(25, 50, 29, 29);

        Image image = sunflowerPk.getImage();
        Image newImg = image.getScaledInstance(50, 65, Image.SCALE_SMOOTH);
        sunflowerPk = new ImageIcon(newImg);

        image = peashooterPk.getImage();
        newImg = image.getScaledInstance(50, 65, Image.SCALE_SMOOTH);
        peashooterPk = new ImageIcon(newImg);

        image = cherryPk.getImage();
        newImg = image.getScaledInstance(50, 65, Image.SCALE_SMOOTH);
        cherryPk = new ImageIcon(newImg);

        image = wallnutPk.getImage();
        newImg = image.getScaledInstance(50, 65, Image.SCALE_SMOOTH);
        wallnutPk = new ImageIcon(newImg);

        layers.setBounds(0, 0, 881, 600);

        sunflowerPack.setIcon(sunflowerPk);
        sunflowerPack.setBounds(73, 5, sunflowerPk.getIconWidth(), sunflowerPk.getIconHeight());
        peashooterPack.setIcon(peashooterPk);
        peashooterPack.setBounds(123, 5, peashooterPk.getIconWidth(), peashooterPk.getIconHeight());
        cherryPack.setIcon(cherryPk);
        cherryPack.setBounds(173, 5, cherryPk.getIconWidth(), cherryPk.getIconHeight());
        wallnutPack.setIcon(wallnutPk);
        wallnutPack.setBounds(223, 5, wallnutPk.getIconWidth(), wallnutPk.getIconHeight());

        backGround.setBounds(0, 0, 881, 600);

        shopLabel.setIcon(shop);
        shopLabel.setBounds(5, 0, shop.getIconWidth(), shop.getIconHeight());

        layers.add(backGround, Integer.valueOf(0));
        layers.add(shopLabel, Integer.valueOf(1));
        layers.add(sunflowerPack, Integer.valueOf(2));
        layers.add(peashooterPack, Integer.valueOf(2));
        layers.add(cherryPack, Integer.valueOf(2));
        layers.add(wallnutPack, Integer.valueOf(2));
        layers.add(sunCount, Integer.valueOf(2));
        layers.add(shovel, Integer.valueOf(2));

        container.add(layers, "Game");
        cardLayout.show(container, "Menu");

        this.add(container);
        this.setIconImage(icon.getImage());
        this.setTitle("Plants vs. Zombies");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.setSize(881, 600);
        this.setLocationRelativeTo(null);
        this.setSize(881, 600);
        this.setVisible(true);

    }

    /**
     * This sets the board variable with the passed JPanel
     * 
     * @param board a JPanel in grid layout representing the tiles for the plants
     */
    public void setBoard(JPanel board) {
        this.board = board;
    }

    /**
     * This sets the gridCells with a 2D array of JPanels representing each tile
     * 
     * @param gridCells a 2D array containing JPanels
     */
    public void setGridCells(JPanel[][] gridCells) {
        this.gridCells = gridCells;
    }

    /**
     * This gets the JLabel containing the suncount
     * 
     * @return a JLabel containing the suncount
     */
    public JLabel getSunCount() {
        return sunCount;
    }

    /**
     * This gets the JLabel containing the sunflowerPack
     * 
     * @return a JLabel containing the sunflowerPack
     */
    public JLabel getSunflowerPack() {
        return sunflowerPack;
    }

    /**
     * This gets the JLabel containing the peashooterPack
     * 
     * @return a JLabel containing the peashooterPack
     */
    public JLabel getPeashooterPack() {
        return peashooterPack;
    }

    /**
     * This gets the JLabel containing the cherryPack
     * 
     * @return a JLabel containing the cherryPack
     */
    public JLabel getCherryPack() {
        return cherryPack;
    }

    /**
     * This gets the JLabel containing the wallnutpack
     * 
     * @return a JLabel containing the wallnutpack
     */
    public JLabel getWallnutPack() {
        return wallnutPack;
    }

    /**
     * This gets the JLayeredPane containing the layers of the display
     * 
     * @return a JLayeredPane containing layers variable
     */
    public JLayeredPane getLayers() {
        return layers;
    }

    /**
     * This gets the JPanel 2D array containing the JPanels representing a tile
     * 
     * @return a JPanel 2D array containing the gridCells variable
     */
    public JPanel[][] getGridCells() {
        return gridCells;
    }

    /**
     * This gets the JProgressBar containing the progressbar of the game
     * 
     * @return a JProgressBar containing the progressBar variable
     */
    public JProgressBar getProgressBar() {
        return progressBar;
    }

    /**
     * This gets the JLabel containing the shovel of the game
     * 
     * @return a JLabel containing the shovel variable
     */
    public JLabel getShovel() {
        return shovel;
    }

    /**
     * This gets the JButton of the first button of the game main menu
     * 
     * @return a JButton containing lvl1 variable
     */
    public JButton getLvl1() {
        return lvl1;
    }

    /**
     * This gets the JButton of the second button of the game main menu
     * 
     * @return a JButton containing lvl2 variable
     */
    public JButton getLvl2() {
        return lvl2;
    }

    /**
     * This gets the JButton of the third button of the game main menu
     * 
     * @return a JButton containing lvl3 variable
     */
    public JButton getLvl3() {
        return lvl3;
    }

    /**
     * This gets the CardLayout of the different scenes of the game
     * 
     * @return a CardLayout containing the cardLayout variable
     */
    public CardLayout getCardLayout() {
        return cardLayout;
    }

    /**
     * This gets the JPanel containing cardLayout
     * 
     * @return a JPanel containing the container variable
     */
    public JPanel getContainerP() {
        return container;
    }

    /**
     * This gets the JLabel containing the game's background image
     * 
     * @return a JLabel for the game's background
     */
    public JLabel getBackGround() {
        return backGround;
    }

    /**
     * This gets the JPanel of the board where plants are placed
     * 
     * @return a JPanel containing the board variable
     */
    public JPanel getBoard() {
        return board;
    }

    /**
     * This gets the JLabel for the first lawn mower
     * 
     * @return a JLabel representing the first lawn mower
     */
    public JLabel getMow1() {
        return mow1;
    }

    /**
     * This gets the JLabel for the second lawn mower
     * 
     * @return a JLabel representing the second lawn mower
     */
    public JLabel getMow2() {
        return mow2;
    }

    /**
     * This gets the JLabel for the third lawn mower
     * 
     * @return a JLabel representing the third lawn mower
     */
    public JLabel getMow3() {
        return mow3;
    }

    /**
     * This gets the JLabel for the fourth lawn mower
     * 
     * @return a JLabel representing the fourth lawn mower
     */
    public JLabel getMow4() {
        return mow4;
    }

    /**
     * This gets the JLabel for the fifth lawn mower
     * 
     * @return a JLabel representing the fifth lawn mower
     */
    public JLabel getMow5() {
        return mow5;
    }

    /**
     * This updates the sun counter display with the given count.
     * 
     * @param count the new sun count to display
     */
    public void updateSunCounter(int count) {
        sunCount.setText(String.valueOf(count));
    }

    /**
     * Adds a zombie JLabel to the game display.
     * 
     * @param zombie the JLabel representing the zombie
     */
    public void addZombie(JLabel zombie) {
        layers.add(zombie, Integer.valueOf(5));
        layers.repaint();
    }

    /**
     * Adds a sun JLabel to the game display.
     * 
     * @param sun the JLabel representing the sun
     */
    public void addSun(JLabel sun) {
        layers.add(sun, Integer.valueOf(5));
        layers.repaint();
    }

    /**
     * Removes a sun JLabel from the game display.
     * 
     * @param sun the JLabel representing the sun
     */
    public void removeSun(JLabel sun) {
        layers.remove(sun);
        layers.repaint();
    }

        /**
     * Gets the ImageIcon for the normal zombie walking animation.
     * @return ImageIcon for normal zombie walking
     */
    public ImageIcon getNormalZWalk() {
        return normalZWalk;
    }

    /**
     * Gets the ImageIcon for the conehead zombie walking animation.
     * @return ImageIcon for conehead zombie walking
     */
    public ImageIcon getConeZWalk() {
        return coneZWalk;
    }

    /**
     * Gets the ImageIcon for the buckethead zombie walking animation.
     * @return ImageIcon for buckethead zombie walking
     */
    public ImageIcon getBucketZWalk() {
        return bucketZWalk;
    }

    /**
     * Gets the ImageIcon for the normal zombie eating animation.
     * @return ImageIcon for normal zombie eating
     */
    public ImageIcon getNormalZEat() {
        return normalZEat;
    }

    /**
     * Gets the ImageIcon for the conehead zombie eating animation.
     * @return ImageIcon for conehead zombie eating
     */
    public ImageIcon getConeZEat() {
        return coneZEat;
    }

    /**
     * Gets the ImageIcon for the buckethead zombie eating animation.
     * @return ImageIcon for buckethead zombie eating
     */
    public ImageIcon getBucketZEat() {
        return bucketZEat;
    }

    /**
     * Gets the ImageIcon for the peashooter animation.
     * @return ImageIcon for peashooter
     */
    public ImageIcon getPeashooterGif() {
        return peashooterGif;
    }

    /**
     * Gets the ImageIcon for the sunflower animation.
     * @return ImageIcon for sunflower
     */
    public ImageIcon getSunflowerGif() {
        return sunflowerGif;
    }

    /**
     * Gets the ImageIcon for the cherry bomb animation.
     * @return ImageIcon for cherry bomb
     */
    public ImageIcon getCherrybombGif() {
        return cherrybombGif;
    }

    /**
     * Gets the ImageIcon for the wallnut image.
     * @return ImageIcon for wallnut
     */
    public ImageIcon getWallnutpic() {
        return wallnutpic;
    }

    /**
     * Gets the ImageIcon for the pea projectile.
     * @return ImageIcon for pea
     */
    public ImageIcon getPea() {
        return pea;
    }

    /**
     * Gets the ImageIcon for the peax projectile.
     * @return ImageIcon for peax
     */
    public ImageIcon getPeax() {
        return peax;
    }

    /**
     * Gets the ImageIcon for the sun.
     * @return ImageIcon for sun
     */
    public ImageIcon getSun() {
        return sun;
    }
}