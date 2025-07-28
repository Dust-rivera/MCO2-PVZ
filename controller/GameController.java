package controller;

import javax.swing.Timer;

import java.awt.Color;

import java.awt.Rectangle;
import java.awt.Component;
import java.awt.GridLayout;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.BorderFactory;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JButton;

import java.awt.Image;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

//import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.io.File;
import java.io.IOException;

import model.Board;
import model.BucketheadZombie;
import model.CherryBomb;
import model.ConeheadZombie;
import model.FlagZombie;
import model.NormalZombie;
import model.Peashooter;
import model.User;
import model.Wallnut;
import model.Zombie;
import view.GameView;
import view.MenuListener;
import view.ShopListener;
import view.ShovelListener;
import view.SunClickListener;

import java.util.ArrayList;
import java.util.Random;

import model.Sunflower;
import model.Plant;

public class GameController {

    JLabel sunflower;
    JLabel peashooter;
    JLabel cherry;
    JLabel wallnut;
    JLabel shovel;
    private JLabel drag = new JLabel();
    private int offsetX, offsetY;
    ImageIcon image;
    Point initialCLick;
    int plantSelect;
    int gameTime = 180;

    private Random random = new Random();
    private ArrayList<Zombie> zombieList;
    private ArrayList<JLabel> zombieLabels;

    int waveNum;

    Clip gameMusic;

    Timer gameTimer;
    ArrayList<Timer> zombieWalkTimers = new ArrayList<>();

    JButton lvl1;
    JButton lvl2;
    JButton lvl3;

    int boardWidth = 700;
    int boardHeight;
    int boardRow;
    int boardCol;

    Boolean mow1 = false;
    Boolean mow2 = false;
    Boolean mow3 = false;
    Boolean mow4 = false;
    Boolean mow5 = false;

    int sunflowerCD = 0;
    int peashooterCD = 0;
    int cherrybombCD = 0;
    int wallnutCD = 0;

    User user = new User();

    Board board = new Board(user, 5, 9);

    GameView view = new GameView();

    public GameController() {

        menu();
        // board.getSunflowerPack();

        view.addMouseMotionListener(new MouseMotionListener() {

            @Override
            public void mouseMoved(MouseEvent e) {
                System.out.println("Mouse Position: X=" + e.getX() + ", Y=" + e.getY());
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                // Optional: Track dragging too
                System.out.println("Dragging at X=" + e.getX() + ", Y=" + e.getY());
            }
        });
    }

    private void menu() {

        System.out.println(view.getLvl1());

        lvl1 = view.getLvl1();
        lvl2 = view.getLvl2();
        lvl3 = view.getLvl3();

        MenuListener menuListener = new MenuListener(this);
        lvl1.addActionListener(menuListener);
        lvl2.addActionListener(menuListener);
        lvl3.addActionListener(menuListener);
    }

    public void lvlSelect(ActionEvent e) {
        Object source = e.getSource();

        if (source == lvl1) {

            setupMap(90, 1, 9, 280);
            view.getBackGround().setIcon(new ImageIcon("view\\assets\\lvl1.png"));
            view.getLayers().remove(cherry);
            view.setCherryPack(null);
            view.getLayers().remove(wallnut);
            view.setWallnutPack(null);

            // System.out.println(view.getHeight());
            waveNum = 5;

        } else if (source == lvl2) {

            // startSpawningZombies();
            setupMap(270, 3, 9, 190);
            view.getBackGround().setIcon(new ImageIcon("view\\assets\\lvl2.png"));
            view.getLayers().remove(wallnut);
            view.setWallnutPack(null);
            // System.out.println(view.getHeight());
            waveNum = 7;

        } else if (source == lvl3) {

            // startSpawningZombies();
            setupMap(450, 5, 9, 100);
            view.getBackGround().setIcon(new ImageIcon("view\\assets\\lvl3.png"));
            // System.out.println(view.getHeight());
            waveNum = 9;
        }
    }

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
        // startSpawningZombies();

        // view = new GameView(r, c);
        view.setBoard(new JPanel(new GridLayout(r, c)));
        view.getBoard().setOpaque(false);
        view.getBoard().setBounds(110, y, boardWidth, h);
        view.setGridCells(new JPanel[r][c]);
        for (int row = 0; row < r; row++) {
            for (int col = 0; col < c; col++) {
                view.getGridCells()[row][col] = new JPanel();
                view.getGridCells()[row][col].setOpaque(false);
                view.getGridCells()[row][col].setBorder(BorderFactory.createLineBorder(Color.RED)); // So background is
                                                                                                    // visible
                view.getBoard().add(view.getGridCells()[row][col]);
            }
        }

        // System.out.println(view.getBoard());
        // System.exit(0);

        view.getLayers().add(view.getBoard(), Integer.valueOf(3));
        view.getLayers().repaint();
    }

    private void shovel() {
        shovel = view.getShovel();

        ShovelListener shovelListener = new ShovelListener(this);
        shovel.addMouseListener(shovelListener);
        shovel.addMouseMotionListener(shovelListener);
    }

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

    public void shovelPressed(MouseEvent e) {

        ImageIcon image = null;

        image = new ImageIcon("view\\assets\\Shovel1.png");

        elementPressed(image, e);

    }

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
                System.out.println(indices[0]);
                System.out.println(indices[1]);
                if (cell != null && board.getTile(indices[0], indices[1]).isPlantOccupied()) {

                    removePlant(indices, cell);
                }
            }
            view.getLayers().remove(drag);
            view.getLayers().repaint();
            drag = null;
        }
    }

    private void removePlant(int[] indices, JPanel cell) {

        board.getBoard()[indices[0]][indices[1]].removePlant();
        cell.removeAll();
        cell.revalidate();
        cell.repaint();

        board.getTile(indices[0], indices[1]).toS();
        System.out.println(board.getTile(indices[0], indices[1]).isPlantOccupied());
    }

    private void progress() {
        gameTimer = new Timer(100, e -> decreaseTime());
        gameTimer.start();
    }

    private void decreaseTime() {
        gameTime--;
        view.getProgressBar().setValue(gameTime);
        // view.getLayers().repaint();

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
            // finalWaveFlag = false;
            // this.setMessage("A wave of zombies has appeared");
            // spawnWaveOfZombies();
            gameWin();
        }

        // if(gameTime == 170){
        // System.out.println("Game Finished");
        // System.exit(0);
        // }

    }

    private void gameWin() {

        for (int i = 0; i < zombieWalkTimers.size(); i++) {
            zombieWalkTimers.get(i).stop();
        }

        gameMusic.stop();

        System.out.println(gameMusic.isRunning());

        playSound("view\\audio\\Win.wav");

        try {
            Thread.sleep(4000); // This blocks the EDT!
        } catch (InterruptedException a) {
            a.printStackTrace();
        }
        view.getCardLayout().show(view.getContainerP(), "Win");
    }

    private void spawnWave() {

        spawnFlag();

        for (int i = 0; i < waveNum - 1; i++) {
            zombiePick();
        }
    }

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

    public void shopPressed(MouseEvent e) {

        Object source = e.getSource();
        ImageIcon image = null;

        if (source == sunflower && user.getSunCount() >= 50) {
            if (sunflowerCD != 0)
                return;
            image = new ImageIcon("view\\gifs\\Sunflower.gif");
            plantSelect = 0;
        } else if (source == peashooter && user.getSunCount() >= 100) {
            if (peashooterCD != 0)
                return;
            image = new ImageIcon("view\\gifs\\Peashooter.gif");
            plantSelect = 1;
        } else if (source == cherry && user.getSunCount() >= 150) {
            if (cherrybombCD != 0)
                return;
            image = new ImageIcon("view\\gifs\\CherryExplode.gif");
            plantSelect = 2;
        } else if (source == wallnut && user.getSunCount() >= 50) {
            if (wallnutCD != 0)
                return;
            image = new ImageIcon("view\\assets\\wallnut.png");
            plantSelect = 3;
        }

        elementPressed(image, e);
    }

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
                System.out.println(indices[0]);
                System.out.println(indices[1]);
                if (cell != null && !board.getTile(indices[0], indices[1]).isPlantOccupied()) {
                    if (plantSelect == 0) {
                        Sunflower p = new Sunflower(indices[0], indices[1]);
                        placePlant(indices, p, cell);
                    } else if (plantSelect == 1) {
                        Peashooter p = new Peashooter(indices[0], indices[1]);
                        placePlant(indices, p, cell);
                    } else if (plantSelect == 2) {
                        CherryBomb p = new CherryBomb(indices[0], indices[1]);
                        placePlant(indices, p, cell);
                        cell.removeAll();
                        cell.revalidate();
                        cell.repaint();

                        Point cellPosCherry = SwingUtilities.convertPoint(cell.getParent(), cell.getLocation(),
                                view.getLayers());
                        JLabel cherryLabel = new JLabel(image);
                        cherryLabel.setBounds(cellPosCherry.x - 90, cellPosCherry.y - 80, 274, 227);
                        view.getLayers().add(cherryLabel, Integer.valueOf(5));
                        view.getLayers().repaint();
                        javax.swing.Timer timer2 = new javax.swing.Timer(280, ev -> {
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
                            javax.swing.Timer timer = new javax.swing.Timer(500, evt -> {
                                view.getLayers().remove(powieLabel);
                                view.getLayers().repaint();
                            });
                            timer.setRepeats(false);
                            timer.start();
                        });
                        timer2.setRepeats(false);
                        timer2.start();
                    } else if (plantSelect == 3) {
                        Wallnut p = new Wallnut(indices[0], indices[1]);
                        placePlant(indices, p, cell);
                    }
                }
            }
            view.getLayers().remove(drag);
            view.getLayers().repaint();
            drag = null;
        }
    }

    private void placePlant(int[] indices, Plant p, JPanel cell) {
        if (user.getSunCount() >= p.getCost()) {

            board.getBoard()[indices[0]][indices[1]].setPlant(p);
            user.buyPlant(p.getCost());
            playSound("view\\audio\\Plant.wav");
            JLabel plant = new JLabel(image);
            cell.add(plant);
            cell.revalidate();
            cell.repaint();
            board.getTile(indices[0], indices[1]).toS();
            view.updateSunCounter(user.getSunCount());
            plantUpdate(p, cell);
            System.out.println(board.getTile(indices[0], indices[1]).isPlantOccupied());

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
        } else if (plantSelect == 2) {
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
        } else if (plantSelect == 3) {
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

    private void plantUpdate(Plant p, JPanel cell) {
        if (p instanceof Sunflower) {
            Timer update = new Timer(24000, e -> generateSunSunflower(cell));
            update.start();
        } else if (p instanceof Peashooter) {
            Timer update = new Timer(1500, e -> checkRow(cell));
            update.start();
        }
    }

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

        System.out.println(isZombieInSameRow(row, cell.getX()));
        System.exit(0);

        if (row != -1 && isZombieInSameRow(row, cell.getX())) {
            System.out.println("COL" + col);
            shootPea(cell, row, col);
        }
    }

    private boolean isZombieInSameRow(int row, int plantX) {

        for (Component comp : view.getLayers().getComponents()) {
            if (comp instanceof JLabel) {
                JLabel zombieLabel = (JLabel) comp;

                // Check if this JLabel is actually a zombie
                if (zombieLabel.getIcon() != null &&
                        zombieLabel.getIcon().toString().contains("Zombie")) { // crude check
                    Rectangle zombieBounds = zombieLabel.getBounds();

                    int zombieY = zombieBounds.y;
                    int zombieRow = getRowFromY(zombieY);

                    System.out.println("ROW " + zombieRow);

                    // Zombie is in the same row AND to the right of the plant
                    if (zombieRow == row && zombieBounds.x > plantX) {
                        System.out.println("ZOMBIE!!");
                        return true;
                    }
                }
            }
        }
        return false;
    }

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

    private void shootPea(JPanel cell, int row, int col) {
        ImageIcon peaIcon = new ImageIcon("view\\assets\\Pea_p.png");
        JLabel peaLabel = new JLabel(peaIcon);

        // Convert cell position to layered pane coordinates
        Point point = SwingUtilities.convertPoint(cell.getParent(), cell.getLocation(), view.getLayers());
        int startX = point.x + (cell.getWidth() / 2);
        int startY = point.y + (cell.getHeight() - 20) / 2;

        peaLabel.setBounds(startX, startY, 20, 20);
        view.getLayers().add(peaLabel, Integer.valueOf(4));

        Zombie target = null;
        for (Zombie zombie : zombieList) {

            if (!zombie.isDead() && zombie.getYPosition() == row && zombie.getXPosition() > startX) {
                target = zombie;
                break;
            }
        }
        // if (target == null) return;

        Zombie lockedZombie = target;

        Timer peaTimer = new Timer(20, new ActionListener() {
            int x = startX;

            @Override
            public void actionPerformed(ActionEvent e) {
                x += 5;

                // Only attack locked zombie
                // System.out.println("tite " + lockedZombie.isDead());
                // System.out.println("TITE " + Math.abs(lockedZombie.getXPosition() - x));

                if (!lockedZombie.isDead() && Math.abs(lockedZombie.getXPosition() - x) <= 5) {
                    if (Math.abs(lockedZombie.getXPosition()) <= startX + 100)
                        lockedZombie.takeDamage(board.getTile(row, col).getPlant().getDirDamage());
                    else
                        lockedZombie.takeDamage(board.getTile(row, col).getPlant().getDamage());
                    ImageIcon explosion = new ImageIcon("view\\assets\\peax.png");
                    peaLabel.setIcon(explosion);
                    int explosionWidth = explosion.getIconWidth();
                    int explosionHeight = explosion.getIconHeight();
                    peaLabel.setBounds(x - explosionWidth / 2, startY - explosionHeight / 2, explosionWidth,
                            explosionHeight);
                    ((Timer) e.getSource()).stop();

                    new Timer(100, evt -> {
                        view.getLayers().remove(peaLabel);
                        view.getLayers().repaint();
                    }).start();

                    if (lockedZombie.isDead()) {
                        int index = zombieList.indexOf(lockedZombie);
                        System.out.println("zombie tite " + lockedZombie);
                        System.out.println("zombie index " + zombieLabels.get(index));
                        view.getLayers().remove(zombieLabels.get(index));
                        view.getLayers().repaint();
                        zombieList.remove(lockedZombie);
                        zombieLabels.remove(index);
                        zombieWalkTimers.get(index).stop();
                        zombieWalkTimers.remove(index);
                    }
                    return;
                }

                if (x > view.getLayers().getWidth()) {
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
    }

    private void generateSunSunflower(JPanel cell) {

        Point point = SwingUtilities.convertPoint(cell.getParent(), cell.getLocation(), view.getLayers());
        int x = point.x + (cell.getWidth() - 70) / 2;
        int y = point.y + (cell.getWidth() - 70) / 2;

        ImageIcon sunIcon = new ImageIcon("view\\assets\\Sun.png"); // replace path
        Image scaled = sunIcon.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);
        sunIcon = new ImageIcon(scaled);

        JLabel sunLabel = new JLabel(sunIcon);
        sunLabel.setBounds(x, y, 70, 70);
        view.addSun(sunLabel);
        sunLabel.addMouseListener(new SunClickListener(sunLabel, this));

    }

    public JPanel getCellAtPoint(Point p, int[] indices) {
        System.out.println("Mouse point: " + p);
        for (int row = 0; row < boardRow; row++) {
            for (int col = 0; col < boardCol; col++) {
                JPanel[][] grid = view.getGridCells();
                JPanel cell = grid[row][col];
                Rectangle bounds = cell.getBounds();
                System.out.println("Cell [" + row + "][" + col + "] bounds: " + bounds);
                if (bounds.contains(p)) {
                    System.out.println("Found cell at [" + row + "][" + col + "]");
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

    public void sunClick(JLabel sunLabel) {
        view.removeSun(sunLabel);
        user.collectSun(board);
        view.updateSunCounter(user.getSunCount());
        System.out.println(user.getSunCount());
    }

    private void startSunDrop() {
        Timer startTimer = new Timer(board.getSUN_GENERATE_TIME(), e -> spawnSun());
        startTimer.start();
    }

    private void spawnSun() {
        ImageIcon sunIcon = new ImageIcon("view\\assets\\Sun.png"); // replace path
        Image scaled = sunIcon.getImage().getScaledInstance(70, 70, Image.SCALE_SMOOTH);
        sunIcon = new ImageIcon(scaled);

        JLabel sunLabel = new JLabel(sunIcon);
        Random random = new Random();
        int x = random.nextInt(view.getWidth());
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

    public void imgDrag(MouseEvent e) {
        if (drag != null) {
            Point p = SwingUtilities.convertPoint(e.getComponent(), e.getPoint(), view.getLayers());
            drag.setLocation(p.x - offsetX, p.y - offsetY);
        }
    }

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

    // public Board getBoard() {
    // return board;
    // }

    // public GameView getView() {
    // return view;
    // }

    // public User getUser() {
    // return user;
    // }

    // private JLayeredPane layers;
    // private static int zombieCount;

    /*
     * private void startSpawningZombies() {
     * Timer startTimer = new Timer(1000, e -> spawnZombie());
     * Timer Brains = new Timer(10000, e ->
     * playWavFile("view\\audio\\Groan_brains1.wav"));
     * startTimer.start();
     * Brains.start();
     * }
     */

    private void eatPlant(Zombie zombie, Timer walkTimer) {

        // System.out.println(walkTimer);

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

                    if (zombie instanceof NormalZombie)
                        zombieLabel.setIcon(new ImageIcon("view/gifs/NormalZombieEating.gif"));
                    else if (zombie instanceof ConeheadZombie)
                        zombieLabel.setIcon(new ImageIcon("view/gifs/ConeZombieEating.gif"));
                    else if (zombie instanceof BucketheadZombie)
                        zombieLabel.setIcon(new ImageIcon("view/gifs/Bucketheadeating.gif"));

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

                            zombieLabel.setIcon(new ImageIcon("view/gifs/Zombie80.gif"));
                            walkTimer.start();
                        }
                    });
                    eatTimer.start();
                    break;
                }
            }
        }
    }

    private void spawnFlag() {

        final ImageIcon zombieIcon = new ImageIcon("view\\gifs\\Flag1.gif");
        final Zombie zombie = new FlagZombie();

        JLabel zombieLabel = new JLabel(zombieIcon);

        spawnZombie(zombie, zombieLabel, zombieIcon);
    }

    private void zombiePick() {

        ImageIcon zombieIconT = null;
        Zombie zombieT = null;

        final ImageIcon zombieIcon;
        final Zombie zombie;

        int zom = random.nextInt(3);

        // switch (zom) {
        //     case 0:
                zombieIconT = new ImageIcon("view\\gifs\\Zombie80.gif");
                zombieT = new NormalZombie();
        //         break;
        //     case 1:
                // zombieIconT = new ImageIcon("view\\gifs\\Cone80.gif");
                // zombieT = new ConeheadZombie();
        //         break;
        //     case 2:
                // zombieIconT = new ImageIcon("view\\gifs\\Buckethead.gif");
                // zombieT = new BucketheadZombie();
                //break;
            /*
             * case 3:
             * yCoordinate = 350;
             * break;
             */
        // }
        zombie = zombieT;
        zombieIcon = zombieIconT;

        JLabel zombieLabel = new JLabel(zombieIcon);

        spawnZombie(zombie, zombieLabel, zombieIcon);
    }

    private void spawnZombie(Zombie zombie, JLabel zombieLabel, ImageIcon zombieIcon) {

        int yCoordinate;

        int row = random.nextInt(boardRow);
        // if (boardRow == 5) {
        // row = ; // 0 to 4
        // } else if (boardRow == 3) {
        // row = random.nextInt(3) + 1; // 1 to 3
        // } else if (boardRow == 1) {
        // row = 2; // constant
        // }

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

        // switch (row) {
        // case 0:
        // yCoordinate = 75;
        // break;
        // case 1:
        // yCoordinate = 170;
        // break;
        // case 2:
        // yCoordinate = 260;
        // break;
        // case 3:
        // yCoordinate = 350;
        // break;
        // case 4:
        // yCoordinate = 440;
        // break;
        // default:
        // yCoordinate = 0;
        // break;
        // }

        int startX = 800;

        zombie.setXPosition(startX);
        zombie.setYPosition(row);
        zombieLabel.setBounds(startX, yCoordinate, zombieIcon.getIconWidth(), zombieIcon.getIconHeight());
        view.addZombie(zombieLabel);

        zombieLabels.add(zombieLabel);
        zombieList.add(zombie);

        // System.out.println(zombieLabels.get(0));
        // System.out.println(zombieList.get(0));
        Timer[] zombieWalkingRef = new Timer[1];

        Timer zombieWalking = new Timer(150, new ActionListener() {

            int x = startX;

            @Override
            public void actionPerformed(ActionEvent e) {

                x -= zombie.getSpeed() / 2;

                if (x == 100) {
                    switch (yCoordinate) {
                        case 75:
                            mow1 = true;
                            moveMow(view.getMow1());
                            break;
                        case 170:
                            mow2 = true;
                            moveMow(view.getMow2());
                            break;
                        case 260:
                            mow3 = true;
                            moveMow(view.getMow3());
                            break;
                        case 350:
                            mow4 = true;
                            moveMow(view.getMow4());
                            break;
                        case 440:
                            mow5 = true;
                            moveMow(view.getMow5());
                            break;
                    }
                }

                if (x == 50) {
                    ((Timer) e.getSource()).stop();
                    // int index = zombieList.indexOf(zombie);
                    // System.out.println("zombie index " + zombie);
                    // System.out.println("zombie index " + zombieLabels.get(index));
                    gameTimer.stop();

                    // view.getLayers().remove(zombieLabels.get(index));
                    // zombieLabels.remove(index);
                    // zombieList.remove(zombie);
                    // view.getLayers().repaint();

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

                    // view.getCardLayout().show(view.getContainerP(), "Lose");

                    // System.out.println(JOptionPane.showConfirmDialog(null, "Zombie has entered
                    // your home", "YOU LOSE", JOptionPane.PLAIN_MESSAGE));

                    // System.exit(0);
                }
                zombie.setXPosition(x);
                zombieLabel.setBounds(x, yCoordinate, zombieIcon.getIconWidth(), zombieIcon.getIconHeight());
                eatPlant(zombie, zombieWalkingRef[0]);
                // int boardX = 110;
                // int boardY = 100;
                // int tileWidth = 77;
                // int tileHeight = 90;
                // int zombieX = (int) x - boardX;
                // int zombieY = yCoordinate + 100 - boardY;
                // int col = zombieX / tileWidth;
                // int row = zombieY / tileHeight;
                // if (row >= 0 && row < 5 && col >= 0 && col < 9) {
                // int displayCol = col + 1;
                // int displayRow = row + 1;
                // // System.out.println("Zombie is on tile: (" + displayCol + "," + displayRow
                // +
                // // ")");

            }
        });
        zombieWalkTimers.add(zombieWalking);
        zombieWalkingRef[0] = zombieWalking;
        zombieWalking.start();
    }

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

    public void playWavFile(String filePath) {
        try {
            // Get the audio file
            File audioFile = new File(filePath);
            AudioInputStream audioStream = AudioSystem.getAudioInputStream(audioFile);

            // Get audio format
            AudioFormat format = audioStream.getFormat();

            // Create data line info
            DataLine.Info info = new DataLine.Info(Clip.class, format);

            // Check if system supports the data line
            if (!AudioSystem.isLineSupported(info)) {
                System.out.println("Line not supported");
                return;
            }

            // Get and open data line
            Clip audioClip = (Clip) AudioSystem.getLine(info);
            audioClip.open(audioStream);

            // Start playing
            audioClip.start();

            // Optional: Wait for the sound to finish
            // audioClip.drain();

        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void playBgSound(String filePath) {
        try {
            gameMusic = AudioSystem.getClip();
            gameMusic.open(AudioSystem.getAudioInputStream(new File(filePath)));
            gameMusic.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void playSound(String filePath) {
        try {
            Clip gameSound = AudioSystem.getClip();
            gameSound.open(AudioSystem.getAudioInputStream(new File(filePath)));
            gameSound.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
