/**
 * The game Controller that bridges the model and the GUI,
 * also handles all the logic
 * @author Deveza, Jerry King 
 * @author Rivera, Dustine Gian
 * @version 27.1
 */
package controller;

import java.awt.Rectangle;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Image;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.Timer;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import javax.swing.SwingWorker;
import java.io.File;
import model.*;
import view.*;

import java.util.ArrayList;
import java.util.Random;

public class GameController {

    private JLabel sunflower;
    private JLabel peashooter;
    private JLabel cherry;
    private JLabel wallnut;
    private JLabel shovel;

    private JLabel drag;
    private int offsetX, offsetY;

    private int zombieType;
    private ImageIcon image;
    private Random random;
    private ArrayList<Zombie> zombieList;
    private ArrayList<JLabel> zombieLabels;

    private int plantSelect;
    private int gameTime;
    private int waveNum;

    private Clip gameMusic;

    private Timer gameTimer;
    private ArrayList<Timer> zombieWalkTimers;

    private JButton lvl1;
    private JButton lvl2;
    private JButton lvl3;

    private int boardWidth;
    private int boardHeight;
    private int boardRow;
    private int boardCol;

    private int sunflowerCD;
    private int peashooterCD;
    private int cherrybombCD;
    private int wallnutCD;

    private User user;

    private Board board;

    private GameView view;

    /**
     * Constructs a new GameController, initializes the menu
     */
    public GameController() {

        random = new Random();
        plantSelect = -1;
        gameTime = 180;
        zombieWalkTimers = new ArrayList<>();
        boardWidth = 700;
        sunflowerCD = 0;
        peashooterCD = 0;
        cherrybombCD = 0;
        wallnutCD = 0;
        user = new User();
        view = new GameView();

        menu();

        view.addMouseMotionListener(new MouseMotionListener() {

            @Override
            public void mouseMoved(MouseEvent e) {
                System.out.println("Mouse Position: X=" + e.getX() + ", Y=" + e.getY());
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                System.out.println("Dragging at X=" + e.getX() + ", Y=" + e.getY());
            }
        });
    }

    /**
     * Sets up the main menu buttons and their listeners
     */
    private void menu() {

        lvl1 = view.getLvl1();
        lvl2 = view.getLvl2();
        lvl3 = view.getLvl3();

        MenuListener menuListener = new MenuListener(this);
        lvl1.addActionListener(menuListener);
        lvl2.addActionListener(menuListener);
        lvl3.addActionListener(menuListener);
    }

    /**
     * Handles level selection based on the button pressed
     * 
     * @param e the ActionEvent triggered by a level button
     */
    public void lvlSelect(ActionEvent e) {
        Object source = e.getSource();

        if (source == lvl1) {

            board = new Board(1, 9);
            zombieType = 1;

            setupMap(90, 1, 9, 280);

            view.getBackGround().setIcon(new ImageIcon("view\\assets\\lvl1.png"));
            view.getLayers().remove(cherry);
            view.getLayers().remove(wallnut);
            view.getLayers().remove(view.getMow1());
            view.getLayers().remove(view.getMow2());
            view.getLayers().remove(view.getMow4());
            view.getLayers().remove(view.getMow5());

            waveNum = 5;

        } else if (source == lvl2) {

            board = new Board(3, 9);
            zombieType = 2;

            setupMap(270, 3, 9, 190);
            view.getBackGround().setIcon(new ImageIcon("view\\assets\\lvl2.png"));
            view.getLayers().remove(wallnut);
            view.getLayers().remove(view.getMow1());
            view.getLayers().remove(view.getMow5());
            waveNum = 7;

        } else if (source == lvl3) {

            board = new Board(5, 9);
            zombieType = 3;

            setupMap(450, 5, 9, 100);
            view.getBackGround().setIcon(new ImageIcon("view\\assets\\lvl3.png"));
            waveNum = 9;
        }
    }

    /**
     * Sets up the game board and initializes the map for the selected level
     * 
     * @param h the board height
     * @param r the number of rows
     * @param c the number of columns
     * @param y the y-position of the board relative to the frame
     */
    private void setupMap(int h, int r, int c, int y) {
        boardHeight = h;
        boardRow = r;
        boardCol = c;

        view.getCardLayout().show(view.getContainerP(), "Game");
        playBgSound("view\\audio\\Background.wav");
        view.updateSunCounter(user.getSunCount());
        startSunDrop();

        this.zombieList = new ArrayList<>();
        this.zombieLabels = new ArrayList<>();

        progress();
        shop();
        shovel();

        view.setBoard(new JPanel(new GridLayout(r, c)));
        view.getBoard().setOpaque(false);
        view.getBoard().setBounds(110, y, boardWidth, h);
        view.setGridCells(new JPanel[r][c]);
        for (int row = 0; row < r; row++) {
            for (int col = 0; col < c; col++) {
                view.getGridCells()[row][col] = new JPanel();
                view.getGridCells()[row][col].setOpaque(false);
                view.getBoard().add(view.getGridCells()[row][col]);
            }
        }

        view.getLayers().add(view.getBoard(), Integer.valueOf(3));
        view.getLayers().repaint();
    }

    /**
     * Sets up the shovel and its listeners
     */
    private void shovel() {
        shovel = view.getShovel();

        ShovelListener shovelListener = new ShovelListener(this);
        shovel.addMouseListener(shovelListener);
        shovel.addMouseMotionListener(shovelListener);
    }

    /**
     * Handles the event when the shovel is pressed
     * 
     * @param e the MouseEvent triggered
     */
    public void shovelPressed(MouseEvent e) {

        ImageIcon image = null;

        image = new ImageIcon("view\\assets\\Shovel1.png");

        elementPressed(image, e);

    }

    /**
     * Handles the event when the shovel is released
     * 
     * @param e the MouseEvent triggered
     */
    public void shovelReleased(MouseEvent e) {
        if (drag != null) {
            JPanel grid = null;
            for (int i = 0; i < view.getLayers().getComponentCount(); i++) {
                if (view.getLayers().getComponent(i) instanceof JPanel
                        && view.getLayers().getComponent(i).getBounds().width == boardWidth
                        && view.getLayers().getComponent(i).getBounds().height == boardHeight) { // this is the board
                    grid = (JPanel) view.getLayers().getComponent(i);
                    break;
                }
            }
            if (board != null && view != null) {
                Point mousePoint = SwingUtilities.convertPoint(e.getComponent(), e.getPoint(), grid);
                int[] indices = new int[2];
                JPanel cell = getCellAtPoint(mousePoint, indices);
                if (cell != null && board.getTile(indices[0], indices[1]).isPlantOccupied()) {

                    removePlant(indices, cell);
                }
            }
            view.getLayers().remove(drag);
            view.getLayers().repaint();
            drag = null;
        }
    }

    /**
     * Removes a plant from the board and cell at the specified indices
     * 
     * @param indices the row and column indices
     * @param cell    the JPanel cell to remove the plant from
     */
    private void removePlant(int[] indices, JPanel cell) {

        board.getBoard()[indices[0]][indices[1]].removePlant();
        cell.removeAll();
        cell.revalidate();
        cell.repaint();

    }

    /**
     * Handles the event when a draggable element is pressed.
     * 
     * @param image the ImageIcon to drag
     * @param e     the MouseEvent triggered
     */
    public void elementPressed(ImageIcon image, MouseEvent e) {
        if (image != null) {
            
            drag = new JLabel(image);
            // Get mouse position relative to layered pane
            Point point = SwingUtilities.convertPoint((Component) e.getSource(), e.getPoint(), view.getLayers());

            drag.setBounds(point.x - image.getIconWidth() / 2,
                    point.y - image.getIconHeight() / 2,
                    image.getIconWidth(), image.getIconHeight());

            view.getLayers().add(drag, Integer.valueOf(3));
            view.getLayers().repaint();
            offsetX = point.x - drag.getX();
            offsetY = point.y - drag.getY();
            this.image = image; // used in mouse released
        }
    }

    /**
     * Starts the game timer and initializes the game progress
     */
    private void progress() {
        gameTimer = new Timer(1000, e -> decreaseTime());
        gameTimer.start();
    }

    /**
     * Decreases the game time and updates the progress bar
     */
    private void decreaseTime() {
        gameTime--;
        view.getProgressBar().setValue(gameTime);

        if (gameTime >= 100 && gameTime < 150 && gameTime % 10 == 0) {
            zombiePick();
        } else if (gameTime >= 40 && gameTime <= 100 && gameTime % 5 == 0) {
            zombiePick();
        } else if (gameTime > 10 && gameTime <= 40 && gameTime % 3 == 0) {
            zombiePick();
        } else if (gameTime < 10 && !board.getFinalWaveFlag()) {
            board.setFinalWaveFlag(true);
            spawnWave();

            if (zombieList.isEmpty()) {
                gameTime = 0;
            }
        }

        if (gameTime == 0) {
            gameWin();
        }

    }

    /**
     * Executes gamewin logic when the player wins
     */
    private void gameWin() {

        for (int i = 0; i < zombieWalkTimers.size(); i++) {
            zombieWalkTimers.get(i).stop();
        }

        gameMusic.stop();
        gameTimer.stop();

        playSound("view\\audio\\Win.wav");

        try {
            Thread.sleep(4000); // This blocks the EDT!
        } catch (InterruptedException a) {
            a.printStackTrace();
        }
        view.getCardLayout().show(view.getContainerP(), "Win");
    }

    /**
     * Spawns a wave of zombies
     */
    private void spawnWave() {

        spawnFlag();

        for (int i = 0; i < waveNum - 1; i++) {
            zombiePick();
        }
    }

    /**
     * initializes the shop and its listeners
     */
    private void shop() {

        sunflower = view.getSunflowerPack();
        peashooter = view.getPeashooterPack();
        cherry = view.getCherryPack();
        wallnut = view.getWallnutPack();

        ShopListener shopListener = new ShopListener(this);
        sunflower.addMouseListener(shopListener);
        sunflower.addMouseMotionListener(shopListener);
        peashooter.addMouseListener(shopListener);
        peashooter.addMouseMotionListener(shopListener);
        cherry.addMouseListener(shopListener);
        cherry.addMouseMotionListener(shopListener);
        wallnut.addMouseListener(shopListener);
        wallnut.addMouseMotionListener(shopListener);
    }

    /**
     * Handles the event when a shop item (seed pack) is pressed
     * 
     * @param e the MouseEvent triggered
     */
    public void shopPressed(MouseEvent e) {

        Object source = e.getSource();
        ImageIcon image = null;

        if (source == sunflower && user.getSunCount() >= Sunflower.getCostStatic()) {
            if (sunflowerCD != 0)
                return;
            image = view.getSunflowerGif();
            plantSelect = 0;
        } else if (source == peashooter && user.getSunCount() >= Peashooter.getCostStatic()) {
            if (peashooterCD != 0)
                return;
            image = view.getPeashooterGif();
            plantSelect = 1;
        } else if (source == cherry && user.getSunCount() >= CherryBomb.getCostStatic()) {
            if (cherrybombCD != 0)
                return;
            image = view.getCherrybombGif();
            plantSelect = 2;
        } else if (source == wallnut && user.getSunCount() >= Wallnut.getCostStatic()) {
            if (wallnutCD != 0)
                return;
            image = view.getWallnutpic();
            plantSelect = 3;
        }

        elementPressed(image, e);
    }

    /**
     * Handles the event when a shop item (seed pack) is released
     * 
     * @param e the MouseEvent triggered
     */
    public void shopReleased(MouseEvent e) {
        if (drag != null) {
            JPanel grid = null;
            for (int i = 0; i < view.getLayers().getComponentCount(); i++) {
                if (view.getLayers().getComponent(i) instanceof JPanel
                        && view.getLayers().getComponent(i).getBounds().width == boardWidth
                        && view.getLayers().getComponent(i).getBounds().height == boardHeight) { // this is the board
                    grid = (JPanel) view.getLayers().getComponent(i);
                    break;
                }
            }
            if (board != null && view != null) {
                Point mousePoint = SwingUtilities.convertPoint(e.getComponent(), e.getPoint(), grid);
                int[] indices = new int[2];
                JPanel cell = getCellAtPoint(mousePoint, indices);
                if (cell != null && !board.getTile(indices[0], indices[1]).isPlantOccupied()) {
                    if (plantSelect == 0) {
                        Sunflower p = new Sunflower(indices[0], indices[1]);
                        placePlant(indices, p, cell);
                        plantSelect = -1;
                    } else if (plantSelect == 1) {
                        Peashooter p = new Peashooter(indices[0], indices[1]);
                        placePlant(indices, p, cell);
                        plantSelect = -1;
                    } else if (plantSelect == 2) {
                        CherryBomb p = new CherryBomb(indices[0], indices[1]);
                        placePlant(indices, p, cell);
                        plantSelect = -1;

                        cell.removeAll();
                        cell.revalidate();
                        cell.repaint();

                        Point cellPosCherry = SwingUtilities.convertPoint(cell.getParent(), cell.getLocation(),
                                view.getLayers());
                        JLabel cherryLabel = new JLabel(image);
                        cherryLabel.setBounds(cellPosCherry.x - 90, cellPosCherry.y - 80, 274, 227);
                        view.getLayers().add(cherryLabel, Integer.valueOf(5));
                        view.getLayers().repaint();
                        Timer timer2 = new javax.swing.Timer(280, ev -> {
                            view.getLayers().remove(cherryLabel);
                            Point cellPosPow = SwingUtilities.convertPoint(cell.getParent(), cell.getLocation(),
                                    view.getLayers());
                            ImageIcon powieIcon = new ImageIcon("view\\assets\\powie.png");
                            JLabel powieLabel = new JLabel(powieIcon);
                            powieLabel.setBounds(cellPosPow.x - 110, cellPosPow.y - 80, 274, 227);
                            view.getLayers().add(powieLabel, Integer.valueOf(10));
                            view.getLayers().repaint();
                            cherryBombExplode(indices[0], indices[1]);
                            board.getTile(indices[0], indices[1]).removePlant();
                            Timer timer = new javax.swing.Timer(500, evt -> {
                                view.getLayers().remove(powieLabel);
                                view.getLayers().repaint();
                                ((Timer) e.getSource()).stop();
                            });
                            timer.setRepeats(false);
                            timer.start();
                            ((Timer) e.getSource()).stop();
                        });
                        timer2.setRepeats(false);
                        timer2.start();
                    } else if (plantSelect == 3) {
                        Wallnut p = new Wallnut(indices[0], indices[1]);
                        placePlant(indices, p, cell);
                        plantSelect = -1;
                    }
                }
            }
            view.getLayers().remove(drag);
            view.getLayers().repaint();
            drag = null;
        }
    }

    /**
     * Places a plant on the board and updates the GUI
     * 
     * @param indices the row and column indices
     * @param p       the Plant to place
     * @param cell    the JPanel cell to add the plant to
     */
    private void placePlant(int[] indices, Plant p, JPanel cell) {

        if (user.getSunCount() >= p.getCost()) {

            board.getBoard()[indices[0]][indices[1]].setPlant(p);
            user.buyPlant(p.getCost());
            playSound("view\\audio\\Plant.wav");
            JLabel plant = new JLabel(image);
            cell.add(plant);
            cell.revalidate();
            cell.repaint();
            view.updateSunCounter(user.getSunCount());
            plantUpdate(p, cell);

        } else {
            p = null;
            return;
        }

        if (p instanceof Sunflower) {
            sunflowerCD = p.getRegenTime();
            ImageIcon originalSunflowerIcon = (ImageIcon) sunflower.getIcon();
            sunflower.setIcon(getTransparentIcon(originalSunflowerIcon, 0.4f));
            Timer recharge = new Timer(500, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    sunflowerCD -= 500;
                    if (sunflowerCD <= 0) {
                        sunflower.setIcon(originalSunflowerIcon);
                        ((Timer) e.getSource()).stop();
                    }
                }
            });
            recharge.start();
        } else if (p instanceof Peashooter) {
            peashooterCD = p.getRegenTime();
            ImageIcon originaPeashooterIcon = (ImageIcon) peashooter.getIcon();
            peashooter.setIcon(getTransparentIcon(originaPeashooterIcon, 0.4f));
            Timer recharge = new Timer(500, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    peashooterCD -= 500;
                    if (peashooterCD <= 0) {
                        peashooter.setIcon(originaPeashooterIcon);
                        ((Timer) e.getSource()).stop();
                    }
                }
            });
            recharge.start();
        } else if (p instanceof CherryBomb) {
            cherrybombCD = p.getRegenTime();
            ImageIcon originalCherryIcon = (ImageIcon) cherry.getIcon();
            cherry.setIcon(getTransparentIcon(originalCherryIcon, 0.4f));
            Timer recharge = new Timer(500, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    cherrybombCD -= 500;
                    if (cherrybombCD <= 0) {
                        cherry.setIcon(originalCherryIcon);
                        ((Timer) e.getSource()).stop();
                    }
                }
            });
            recharge.start();
        } else if (p instanceof Wallnut) {
            wallnutCD = p.getRegenTime();
            ImageIcon originalWallnutIcon = (ImageIcon) wallnut.getIcon();
            wallnut.setIcon(getTransparentIcon(originalWallnutIcon, 0.4f));
            Timer recharge = new Timer(500, new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    wallnutCD -= 500;
                    if (wallnutCD <= 0) {
                        wallnut.setIcon(originalWallnutIcon);
                        ((Timer) e.getSource()).stop();
                    }
                }
            });
            recharge.start();
        }
    }

    /**
     * Returns an updated version of the given icon
     * 
     * @param icon    the original ImageIcon
     * @param opacity the opacity (0.0f to 1.0f)
     * @return a new ImageIcon with the specified opacity
     */
    private ImageIcon getTransparentIcon(ImageIcon icon, float opacity) {
        int w = icon.getIconWidth();
        int h = icon.getIconHeight();
        java.awt.image.BufferedImage image = new java.awt.image.BufferedImage(w, h,
                java.awt.image.BufferedImage.TYPE_INT_ARGB);
        java.awt.Graphics2D g = image.createGraphics();
        g.setComposite(java.awt.AlphaComposite.getInstance(java.awt.AlphaComposite.SRC_OVER, opacity));
        icon.paintIcon(null, g, 0, 0);
        g.dispose();
        return new ImageIcon(image);
    }

    /**
     * Updates the plant's behavior after being placed
     * 
     * @param p    the Plant placed
     * @param cell the JPanel cell containing the plant
     */
    private void plantUpdate(Plant p, JPanel cell) {
        if (p instanceof Sunflower) {
            Timer update = new Timer(24000, e -> generateSunSunflower(cell));
            update.start();
        } else if (p instanceof Peashooter) {
            Timer update = new Timer(1500, e -> checkRow(cell));
            update.start();
        }
    }

    /**
     * Checks if there is a zombie in the same row as the peashooter
     * 
     * @param cell the JPanel cell containing the plant
     */
    private void checkRow(JPanel cell) {
        int row = -1;
        int col = -1;

        for (int i = 0; i < boardRow; i++) {
            for (int j = 0; j < boardCol; j++) {
                if (view.getGridCells()[i][j] == cell) {
                    row = i;
                    col = j;
                    break;
                }
            }
        }

        if (row != -1 && isZombieInSameRow(row, cell.getX())) {
            shootPea(cell, row, col);
        }
    }

    /**
     * Checks if a zombie is in the same row and to the right of the plant
     * 
     * @param row    the row to check
     * @param plantX the x-position of the plant
     * @return true if a zombie is present, false otherwise
     */
    private boolean isZombieInSameRow(int row, int plantX) {

        for (JLabel zombieLabel : zombieLabels) {
            // Check if this JLabel is actually a zombie
            Rectangle zombieBounds = zombieLabel.getBounds();

            int zombieY = zombieBounds.y;
            int zombieRow = getRowFromY(zombieY);
            // Zombie is in the same row AND to the right of the plant
            if (zombieRow == row && zombieBounds.x > plantX) {
                return true;
            }
        }

        return false;
    }

    /**
     * Gets the row index from a y-coordinate
     * 
     * @param y the y-coordinate
     * @return the row index
     */
    private int getRowFromY(int y) {

        if (boardRow == 5) {
            if (y <= 120)
                return 0;
            else if (y <= 210)
                return 1;
            else if (y <= 300)
                return 2;
            else if (y <= 390)
                return 3;
            else
                return 4;
        } else if (boardRow == 3) {
            if (y <= 210)
                return 0;
            else if (y <= 300)
                return 1;
            else
                return 2;
        } else {
            return 0;
        }
    }

    /**
     * Animates and handles the logic for a peashooter shooting a pea
     * 
     * @param cell the JPanel cell containing the peashooter
     * @param row  the row index
     * @param col  the column index
     */
    private void shootPea(JPanel cell,int row, int col) {
        ImageIcon peaIcon = view.getPea();
        JLabel peaLabel = new JLabel(peaIcon);

        // Convert cell position to layered pane coordinates
        Point point = SwingUtilities.convertPoint(cell.getParent(), cell.getLocation(), view.getLayers());
        int startX = point.x + (cell.getWidth() / 2);
        int startY = point.y + (cell.getHeight() - 20) / 2;

        peaLabel.setBounds(startX, startY, 20, 20);
        view.getLayers().add(peaLabel, Integer.valueOf(4));

        final int[] rowHolder = { -1 };
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 9; j++) {
                if (view.getGridCells()[i][j] == cell) {
                    rowHolder[0] = i;
                    break;
                }
            }
        }
        if (rowHolder[0] == -1) return;
    
        // Lock onto first live zombie in row
        Zombie target = null;
        for (Zombie zombie : zombieList) {
            if (!zombie.isDead() && zombie.getYPosition() == rowHolder[0] && zombie.getXPosition() > startX) {
                target = zombie;
                break;
            }
        }
        if (target == null) return;
    
        final int rowZ = rowHolder[0];
        Zombie lockedZombie = target;

        SwingWorker<Void, Void> worker = new SwingWorker<Void, Void>() {
            @Override
            protected Void doInBackground() {
                Timer peaTimer = new Timer(20, new ActionListener() {
                    int x = startX;

                    @Override
                    public void actionPerformed(ActionEvent e) {
                        x += 5;
                        // Only attack locked zombie
                        //for (Zombie zombie : zombieList) {
                            if (!lockedZombie.isDead() && Math.abs(lockedZombie.getXPosition() - x) <= 5) {
                                if (Math.abs(lockedZombie.getXPosition()) <= startX + 100)
                                    lockedZombie.takeDamage(board.getTile(rowZ, col).getPlant().getDirDamage());
                                else
                                    lockedZombie.takeDamage(board.getTile(rowZ, col).getPlant().getDamage());
                                ImageIcon explosion = view.getPeax();
                                peaLabel.setIcon(explosion);
                                int explosionWidth = explosion.getIconWidth();
                                int explosionHeight = explosion.getIconHeight();
                                peaLabel.setBounds(x - explosionWidth / 2, startY - explosionHeight / 2, explosionWidth,
                                        explosionHeight);
                                ((Timer) e.getSource()).stop();

                                new Timer(100, evt -> {
                                    view.getLayers().remove(peaLabel);
                                    view.getLayers().repaint();
                                    ((Timer) evt.getSource()).stop();
                                }).start();

                                if (lockedZombie.isDead()) {
                                    int index = zombieList.indexOf(lockedZombie);
                                    view.getLayers().remove(zombieLabels.get(index));
                                    view.getLayers().repaint();
                                    zombieList.remove(lockedZombie);
                                    zombieLabels.remove(index);
                                    zombieWalkTimers.get(index).stop();
                                    zombieWalkTimers.remove(index);
                                }
                                return;
                            }
                        //}

                        if (x > view.getWidth()) {
                            ((Timer) e.getSource()).stop();
                            view.getLayers().remove(peaLabel);
                            view.getLayers().repaint();
                        } else {
                            peaLabel.setBounds(x, startY, 20, 20);
                            view.getLayers().repaint();
                        }
                    }
                });

                peaTimer.start();
                return null;
            }
        };
        worker.execute();

    }

    /**
     * Generates a sun from a sunflower and adds it to the game view
     * 
     * @param cell the JPanel cell containing the sunflower
     */
    private void generateSunSunflower(JPanel cell) {

        Point point = SwingUtilities.convertPoint(cell.getParent(), cell.getLocation(), view.getLayers());
        int x = point.x + (cell.getWidth() - 70) / 2;
        int y = point.y + (cell.getWidth() - 70) / 2;

        ImageIcon sunIcon = view.getSun(); // replace path
        Image scaled = sunIcon.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);
        sunIcon = new ImageIcon(scaled);

        JLabel sunLabel = new JLabel(sunIcon);
        sunLabel.setBounds(x, y, 70, 70);
        view.addSun(sunLabel);
        sunLabel.addMouseListener(new SunClickListener(sunLabel, this));

    }

    /**
     * Gets the cell at a given point and sets the indices array
     * 
     * @param p       the Point to check
     * @param indices an array to store the row and column indices
     * @return the JPanel cell at the point, or null if not found
     */
    public JPanel getCellAtPoint(Point p, int[] indices) {
        for (int row = 0; row < boardRow; row++) {
            for (int col = 0; col < boardCol; col++) {
                JPanel[][] grid = view.getGridCells();
                JPanel cell = grid[row][col];
                Rectangle bounds = cell.getBounds();
                if (bounds.contains(p)) {
                    if (indices != null && indices.length >= 2) {
                        indices[0] = row;
                        indices[1] = col;
                    }
                    return cell;
                }
            }
        }
        return null;
    }

    /**
     * Handles the event when a sun is clicked
     * 
     * @param sunLabel the JLabel representing the sun
     */
    public void sunClick(JLabel sunLabel) {
        view.removeSun(sunLabel);
        user.collectSun();
        view.updateSunCounter(user.getSunCount());
    }

    /**
     * Starts the timer for random sun drops.
     */
    private void startSunDrop() {
        Timer startTimer = new Timer(board.getSUN_GENERATE_TIME(), e -> spawnSun());
        startTimer.start();
    }

    /**
     * Spawns a sun at a random position and animates its fall.
     */
    private void spawnSun() {
        ImageIcon sunIcon = view.getSun(); // replace path
        Image scaled = sunIcon.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);
        sunIcon = new ImageIcon(scaled);

        JLabel sunLabel = new JLabel(sunIcon);
        Random random = new Random();
        int x = random.nextInt(view.getWidth() - 70);
        sunLabel.setBounds(x, 0, 70, 70);
        view.addSun(sunLabel);
        sunLabel.addMouseListener(new SunClickListener(sunLabel, this));

        Timer fallTimer = new Timer(100, new ActionListener() {
            int y = 0;
            int min = 100;
            int maxY = random.nextInt(550 - min) + min;

            @Override
            public void actionPerformed(ActionEvent e) {
                y += 5;
                if (y >= maxY) {
                    ((Timer) e.getSource()).stop();
                }
                sunLabel.setBounds(x, y, 70, 70);
            }
        });
        fallTimer.start();
    }

    /**
     * Handles dragging of images with the mouse
     * 
     * @param e the MouseEvent triggered
     */
    public void imgDrag(MouseEvent e) {
        if (drag != null) {
            Point p = SwingUtilities.convertPoint(e.getComponent(), e.getPoint(), view.getLayers());
            drag.setLocation(p.x - offsetX, p.y - offsetY);
        }
    }

    /**
     * Handles the explosion logic for the Cherry Bomb
     * 
     * @param centerRow the center row of the explosion
     * @param centerCol the center column of the explosion
     */
    private void cherryBombExplode(int centerRow, int centerCol) {
        // Calculate the bounds of the 3x3 area
        int startRow = Math.max(0, centerRow - 1);
        int endRow = Math.min(boardRow - 1, centerRow + 1);
        int startCol = Math.max(0, centerCol - 1);
        int endCol = Math.min(boardCol - 1, centerCol + 1);

        // For each zombie, check if it's in the 3x3 area and remove it
        for (int i = zombieList.size() - 1; i >= 0; i--) {
            Zombie zombie = zombieList.get(i);
            int zRow = zombie.getYPosition();
            int zCol = -1;
            // Try to estimate the zombie's column based on its X position
            int zX = (int) zombie.getXPosition();
            for (int col = 0; col < boardCol; col++) {
                JPanel cell = view.getGridCells()[zRow][col];
                Point cellPos = SwingUtilities.convertPoint(cell.getParent(), cell.getLocation(), view.getLayers());
                int cellX = cellPos.x;
                int cellWidth = cell.getWidth();
                if (zX >= cellX && zX < cellX + cellWidth) {
                    zCol = col;
                    break;
                }
            }
            if (zCol >= startCol && zCol <= endCol && zRow >= startRow && zRow <= endRow) {
                // Remove zombie label from view
                if (i < zombieLabels.size()) {
                    view.getLayers().remove(zombieLabels.get(i));
                    view.getLayers().repaint();
                    zombieLabels.remove(i);
                }
                zombieList.remove(i);
            }
        }
    }

    /**
     * Handles the logic for a zombie eating a plant
     * 
     * @param zombie    the Zombie eating
     * @param walkTimer the Timer controlling the zombie's movement
     */
    private void eatPlant(Zombie zombie, Timer walkTimer) {

        int index = zombieList.indexOf(zombie);
        int row = zombie.getYPosition();
        int zombieX = (int) zombie.getXPosition();

        for (int col = 0; col < boardCol; col++) {
            Plant plant = board.getTile(row, col).getPlant();
            if (plant != null) {
                JPanel cell = view.getGridCells()[row][col];
                Point cellPoint = SwingUtilities.convertPoint(cell.getParent(), cell.getLocation(), view.getLayers());
                int cellX = cellPoint.x;

                if (Math.abs(cellX - zombieX) <= 50) {
                    walkTimer.stop();
                    JLabel zombieLabel = zombieLabels.get(index);
                    ImageIcon prevIcon = (ImageIcon)zombieLabels.get(index).getIcon();

                    if (zombie instanceof NormalZombie)
                        zombieLabel.setIcon(view.getNormalZEat());
                    else if (zombie instanceof ConeheadZombie)
                        zombieLabel.setIcon(view.getConeZEat());
                    else if (zombie instanceof BucketheadZombie)
                        zombieLabel.setIcon(view.getBucketZEat());

                    final int capturedCol = col;
                    Timer eatTimer = new Timer(800, null);
                    eatTimer.addActionListener(evt -> {
                        plant.decreaseHealth(zombie.getDamage());

                        if (plant.isDead()) {
                            eatTimer.stop();
                            board.getTile(row, capturedCol).removePlant();
                            cell.removeAll();
                            cell.revalidate();
                            cell.repaint();

                            zombieLabel.setIcon(prevIcon);
                            walkTimer.start();
                        }
                    });
                    eatTimer.start();
                    break;
                }
            }
        }
    }

    /**
     * Spawns a Flag Zombie at the start of a wave
     */
    private void spawnFlag() {

        final ImageIcon zombieIcon = new ImageIcon("view\\gifs\\Flag1.gif");
        final Zombie zombie = new FlagZombie();

        JLabel zombieLabel = new JLabel(zombieIcon);

        spawnZombie(zombie, zombieLabel, zombieIcon);
    }

    /**
     * Randomly selects and spawns a zombie
     */
    private void zombiePick() {

        ImageIcon zombieIconT = null;
        Zombie zombieT = null;

        final ImageIcon zombieIcon;
        final Zombie zombie;

        int zom = random.nextInt(zombieType);

        switch (zom) {
            case 0:
                zombieIconT = view.getNormalZWalk();
                zombieT = new NormalZombie();
                break;
            case 1:
                zombieIconT = view.getConeZWalk();
                zombieT = new ConeheadZombie();
                break;
            case 2:
                zombieIconT = view.getBucketZWalk();
                zombieT = new BucketheadZombie();
        }
        zombie = zombieT;
        zombieIcon = zombieIconT;

        JLabel zombieLabel = new JLabel(zombieIcon);

        spawnZombie(zombie, zombieLabel, zombieIcon);
    }

    /**
     * Spawns a zombie and animates its movement
     * 
     * @param zombie      the Zombie to spawn
     * @param zombieLabel the JLabel representing the zombie
     * @param zombieIcon  the ImageIcon for the zombie
     */
    private void spawnZombie(Zombie zombie, JLabel zombieLabel, ImageIcon zombieIcon) {

        int yCoordinate;
        int row = random.nextInt(boardRow);

        if (boardRow == 5) {
            switch (row) {
                case 0:
                    yCoordinate = 75;
                    break;
                case 1:
                    yCoordinate = 170;
                    break;
                case 2:
                    yCoordinate = 260;
                    break;
                case 3:
                    yCoordinate = 350;
                    break;
                case 4:
                    yCoordinate = 440;
                    break;
                default:
                    yCoordinate = 0;
                    break;
            }
        } else if (boardRow == 3) {
            switch (row) {
                case 0:
                    yCoordinate = 170;
                    break;
                case 1:
                    yCoordinate = 260;
                    break;
                case 2:
                    yCoordinate = 350;
                    break;
                default:
                    yCoordinate = 0;
                    break;
            }
        } else {
            yCoordinate = 260;
        }

        int startX = 800;

        zombie.setXPosition(startX);
        zombie.setYPosition(row);
        zombieLabel.setBounds(startX, yCoordinate, zombieIcon.getIconWidth(), zombieIcon.getIconHeight());
        view.addZombie(zombieLabel);

        zombieLabels.add(zombieLabel);
        zombieList.add(zombie);

        Timer[] zombieWalkingRef = new Timer[1];

        Timer zombieWalking = new Timer(130, new ActionListener() {

            int x = startX;

            @Override
            public void actionPerformed(ActionEvent e) {

                x -= zombie.getSpeed() / 2;

                if (x < 150) {
                    switch (yCoordinate) {
                        case 75:
                            moveMow(view.getMow1());
                            break;
                        case 170:
                            moveMow(view.getMow2());
                            break;
                        case 260:
                            moveMow(view.getMow3());
                            break;
                        case 350:
                            moveMow(view.getMow4());
                            break;
                        case 440:
                            moveMow(view.getMow5());
                            break;
                    }
                }

                if (x == 50) {
                    ((Timer) e.getSource()).stop();
                    gameTimer.stop();

                    for (int i = 0; i < zombieWalkTimers.size(); i++) {
                        zombieWalkTimers.get(i).stop();
                    }

                    gameMusic.stop();
                    playSound("view\\audio\\Lose.wav");

                    try {
                        Thread.sleep(4000); // This blocks the EDT!
                    } catch (InterruptedException a) {
                        a.printStackTrace();
                    }
                    view.getCardLayout().show(view.getContainerP(), "Lose");

                }
                zombie.setXPosition(x);
                zombieLabel.setBounds(x, yCoordinate, zombieIcon.getIconWidth(), zombieIcon.getIconHeight());
                eatPlant(zombie, zombieWalkingRef[0]);
            }
        });
        zombieWalkTimers.add(zombieWalking);
        zombieWalkingRef[0] = zombieWalking;
        zombieWalking.start();
    }

    /**
     * Animates the movement of a lawn mower and removes zombies in its path
     * 
     * @param lMow the JLabel representing the lawn mower
     */
    private void moveMow(JLabel lMow) {

        int y = lMow.getY();
        int x = lMow.getX();
        ImageIcon mowIcon = (ImageIcon) view.getMow1().getIcon();

        ArrayList<Integer> indexes = new ArrayList<>();

        Timer mow = new Timer(10, new ActionListener() {
            int mowX = x;

            @Override
            public void actionPerformed(ActionEvent e) {
                mowX += 5;
                lMow.setBounds(mowX, y, mowIcon.getIconWidth(), mowIcon.getIconHeight());
                if (lMow.getX() == 881) {
                    indexes.sort((a, b) -> b - a); // descending
                    for (Integer index : indexes) {
                        zombieLabels.remove((int) index);
                        zombieList.remove((int) index);
                        zombieWalkTimers.remove((int) index);
                    }
                    view.getLayers().remove(lMow);
                    ((Timer) e.getSource()).stop();

                }
                for (Zombie zombie : zombieList) {
                    int index = zombieList.indexOf(zombie);
                    if (mowX - 50 < zombie.getXPosition() && mowX + 50 > zombie.getXPosition()
                            && getRowFromY(lMow.getY()) == zombie.getYPosition()) {
                        zombie.takeDamage(9999);
                        view.getLayers().remove(zombieLabels.get(index));
                        view.getLayers().repaint();
                        zombieWalkTimers.get(index).stop();
                        if (!indexes.contains(index))
                            indexes.add(index);
                    }
                }

            }
        });
        mow.start();
    }

    /**
     * Plays the background music for the game
     * 
     * @param filePath the path to the audio file
     */
    private void playBgSound(String filePath) {
        try {
            gameMusic = AudioSystem.getClip();
            gameMusic.open(AudioSystem.getAudioInputStream(new File(filePath)));
            gameMusic.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Plays a sound effect
     * 
     * @param filePath the path to the audio file
     */
    private void playSound(String filePath) {
        try {
            Clip gameSound = AudioSystem.getClip();
            gameSound.open(AudioSystem.getAudioInputStream(new File(filePath)));
            gameSound.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
