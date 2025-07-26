package controller;

import javax.swing.Timer;
import java.awt.Rectangle;
import java.awt.Component;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
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
import model.CherryBomb;
import model.NormalZombie;
import model.Peashooter;
import model.User;
import model.Zombie;
import view.GameView;
import view.ShopListener;
import view.SunClickListener;

import java.util.ArrayList;
import java.util.Random;

import model.Sunflower;
import model.Plant;

public class GameController {

    JLabel sunflower;
    JLabel peashooter;
    JLabel cherry;
    private JLabel drag = new JLabel();
    private int offsetX, offsetY;
    ImageIcon image;
    Point initialCLick;
    int plantSelect;
    int gameTime = 180;

    User user = new User();

    Board board = new Board(user, 5, 9);

    GameView view = new GameView();

    public GameController() {
        playSound("view\\audio\\Background.wav");
        view.updateSunCounter(user.getSunCount());
        startSunDrop();
        startSpawningZombies();
        this.zombieList = new ArrayList<>();
        System.out.println(view.getHeight());

        progress();

        shop();

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

    private void progress() {

        Timer timer = new Timer(1000, e -> decreaseTime());
        timer.start();
    }

    private void decreaseTime() {
        gameTime--;
        view.getProgressBar().setValue(gameTime);
        //view.getLayers().repaint();
    }

    private void shop() {

        sunflower = view.getSunflowerPack();
        peashooter = view.getPeashooterPack();
        cherry = view.getCherryPack();

        ShopListener shopListener = new ShopListener(this);
        sunflower.addMouseListener(shopListener);
        sunflower.addMouseMotionListener(shopListener);
        peashooter.addMouseListener(shopListener);
        peashooter.addMouseMotionListener(shopListener);
        cherry.addMouseListener(shopListener);
        cherry.addMouseMotionListener(shopListener);
    }

    public void shopPressed(MouseEvent e) {

        Object source = e.getSource();
        ImageIcon image = null;

        if (source == sunflower && this.getUser().getSunCount() >= 50) {
            image = new ImageIcon("view\\gifs\\Sunflower.gif");
            plantSelect = 0;
        } else if (source == peashooter && this.getUser().getSunCount() >= 100) {
            image = new ImageIcon("view\\gifs\\Peashooter.gif");
            plantSelect = 1;
        } else if (source == cherry && this.getUser().getSunCount() >= 150) {
            image = new ImageIcon("view\\assets\\Cherry3.png");
            plantSelect = 2;
        }

        if (image != null) {
            // Resize image
            // Image img = image.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            // image = new ImageIcon(img);

            // Create new draggable JLabel
            drag = new JLabel(image);

            // Get mouse position relative to layered pane
            Point point = SwingUtilities.convertPoint((Component) e.getSource(), e.getPoint(), view.getLayers());

            drag.setBounds(point.x - image.getIconWidth() / 2,
                    point.y - image.getIconHeight() / 2,
                    image.getIconWidth(), image.getIconHeight());

            view.getLayers().add(drag, Integer.valueOf(2));
            view.getLayers().repaint();
            offsetX = point.x - drag.getX();
            offsetY = point.y - drag.getY();
            this.image = image; // used in mouse released
        }
    }

    public void shopReleased(MouseEvent e) {
        if (drag != null) {
            JPanel grid = null;
            for (int i = 0; i < view.getLayers().getComponentCount(); i++) {
                if (view.getLayers().getComponent(i) instanceof JPanel
                        && view.getLayers().getComponent(i).getBounds().width == 700
                        && view.getLayers().getComponent(i).getBounds().height == 450) { // this is the board
                    grid = (JPanel) view.getLayers().getComponent(i);
                    break;
                }
            }
            if (board != null && this.getView() != null) {
                Point mousePoint = SwingUtilities.convertPoint(e.getComponent(), e.getPoint(), grid);
                int[] indices = new int[2];
                JPanel cell = getCellAtPoint(mousePoint, indices);
                System.out.println(indices[0]);
                System.out.println(indices[1]);
                if (cell != null || !board.getTile(indices[0], indices[1]).isPlantOccupied()) {
                    if (plantSelect == 0) {
                        Sunflower p = new Sunflower(indices[0], indices[1]);
                        placePlant(indices, p, cell);
                    } else if (plantSelect == 1) {
                        Peashooter p = new Peashooter(indices[0], indices[1]);
                        placePlant(indices, p, cell);
                    } else if (plantSelect == 2) {
                        CherryBomb p = new CherryBomb(indices[0], indices[1]);
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

            view.updateSunCounter(user.getSunCount());
            board.getBoard()[indices[0]][indices[1]].setPlant(p);
            user.buyPlant(p.getCost());
            JLabel plant = new JLabel(image);
            cell.add(plant);
            cell.revalidate();
            cell.repaint();
            board.getTile(indices[0], indices[1]).toS();

            plantUpdate(p, cell);

        } else {
            p = null;
        }
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
        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 9; j++) {
                if (view.getGridCells()[i][j] == cell) {
                    row = i;
                    break;
                }
            }
        }

        if (row != -1 && isZombieInSameRow(row, cell.getX())) {
            shootPea(cell);
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
        if (y < 120)
            return 0;
        else if (y < 210)
            return 1;
        else if (y < 300)
            return 2;
        else if (y < 390)
            return 3;
        else
            return 4;
    }

    private void shootPea(JPanel cell) {
        ImageIcon peaIcon = new ImageIcon("view\\assets\\Pea_p.png");
        JLabel peaLabel = new JLabel(peaIcon);

        // Convert cell position to layered pane coordinates
        Point point = SwingUtilities.convertPoint(cell.getParent(), cell.getLocation(), view.getLayers());
        int startX = point.x + cell.getWidth() - 10;
        int startY = point.y + (cell.getHeight() - 20) / 2;

        peaLabel.setBounds(startX, startY, 20, 20);
        view.getLayers().add(peaLabel, Integer.valueOf(3));

        Timer peaTimer = new Timer(20, new ActionListener() {
            int x = startX;

            @Override
            public void actionPerformed(ActionEvent e) {
                x += 5;
                if (x > view.getWidth()) {
                    ((Timer) e.getSource()).stop();
                    view.getLayers().remove(peaLabel);
                    view.getLayers().repaint();
                } else {
                    peaLabel.setBounds(x, startY, 20, 20);
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
        for (int row = 0; row < 5; row++) {
            for (int col = 0; col < 9; col++) {
                JPanel[][] grid = this.getView().getGridCells();
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

    public void shopDrag(MouseEvent e) {
        if (drag != null) {
            Point p = SwingUtilities.convertPoint(e.getComponent(), e.getPoint(), view.getLayers());
            drag.setLocation(p.x - offsetX, p.y - offsetY);
        }
    }

    public Board getBoard() {
        return board;
    }

    public GameView getView() {
        return view;
    }

    public User getUser() {
        return user;
    }

    // private JLayeredPane layers;
    // private static int zombieCount;
    private Random random = new Random();
    private ArrayList<Zombie> zombieList;

    private void startSpawningZombies() {
        Timer startTimer = new Timer(1000, e -> spawnZombie());
        Timer Brains = new Timer(10000, e -> playWavFile("view\\audio\\Groan_brains1.wav"));
        startTimer.start();
        Brains.start();
    }

    public void spawnZombie() {
        ImageIcon zombieIcon = new ImageIcon("view\\gifs\\Zombie80.gif");
        // Image scaled = zombieIcon.getImage();//.getScaledInstance(70, 70,
        // Image.SCALE_SMOOTH);
        // zombieIcon = new ImageIcon(scaled);

        JLabel zombieLabel = new JLabel(zombieIcon);
        int yCoordinate;
        switch (random.nextInt(5)) {
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

        int startX = 800;
        zombieLabel.setBounds(startX, yCoordinate, zombieIcon.getIconWidth(), zombieIcon.getIconHeight());
        view.addZombie(zombieLabel);
        zombieList.add(new NormalZombie());

        Timer zombieWalking = new Timer(30, new ActionListener() {

            int x = startX;

            @Override
            public void actionPerformed(ActionEvent e) {

                x -= .2;
                if (x < -100) {
                    ((Timer) e.getSource()).stop();
                }
                zombieLabel.setBounds(x, yCoordinate, zombieIcon.getIconWidth(), zombieIcon.getIconHeight());

                int boardX = 110;
                int boardY = 100;
                int tileWidth = 77;
                int tileHeight = 90;
                int zombieX = (int) x - boardX;
                int zombieY = yCoordinate + 100 - boardY;
                int col = zombieX / tileWidth;
                int row = zombieY / tileHeight;
                if (row >= 0 && row < 5 && col >= 0 && col < 9) {
                    int displayCol = col + 1;
                    int displayRow = row + 1;
                    // System.out.println("Zombie is on tile: (" + displayCol + "," + displayRow +
                    // ")");
                }
            }
        });
        zombieWalking.start();
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

    public void playSound(String filePath) {
        try {
            Clip clip = AudioSystem.getClip();
            clip.open(AudioSystem.getAudioInputStream(new File(filePath)));
            clip.start();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
