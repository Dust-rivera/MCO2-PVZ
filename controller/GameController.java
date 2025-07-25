package controller;

import javax.swing.Timer;
import java.awt.Rectangle;
import java.awt.Component;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.ImageIcon;
import java.awt.Image;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;

//import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import model.Board;
import model.Peashooter;
import model.User;
import view.GameView;
import view.ShopListener;
import view.SunClickListener;
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

    User user = new User();

    Board board = new Board(user, 5, 9);

    GameView view = new GameView();

    public GameController() {
        view.updateSunCounter(user.getSunCount());
        startSunDrop();
        System.out.println(view.getHeight());

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
            image = new ImageIcon("view\\assets\\Sunflower.png");
            plantSelect = 0;
        } else if (source == peashooter && this.getUser().getSunCount() >= 100) {
            image = new ImageIcon("view\\assets\\peashooter.png");
            plantSelect = 1;
        } else if (source == cherry && this.getUser().getSunCount() >= 150) {
            image = new ImageIcon("view\\assets\\cherrybomb.png");
            plantSelect = 2;
        }

        if (image != null) {
            // Resize image
            Image img = image.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            image = new ImageIcon(img);

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
                    }else if(plantSelect == 1){
                        Peashooter p = new Peashooter(indices[0], indices[1]);
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
            this.placePlant(indices[0], indices[1], p);
            board.getTile(indices[0], indices[1]).toS();
            view.updateSunCounter(user.getSunCount());
            JLabel plant = new JLabel(image);
            cell.add(plant);
            cell.revalidate();
            cell.repaint();
        } else {
            p = null;
        }
    }

    public void placePlant(int row, int col, Plant plant) {
        if (!board.getBoard()[row][col].isPlantOccupied()) {
            board.getBoard()[row][col].setPlant(plant);
            user.buyPlant(plant.getCost());
            //this.setMessage("Placed plant at (" + (row + 1) + ", " + (col + 1) + ")");
        } else {
            //this.setMessage("Tile (" + (row + 1) + ", " + (col + 1) + ") is already occupied.");
        }
    }

    public void generateSunSunflower(){
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

}
