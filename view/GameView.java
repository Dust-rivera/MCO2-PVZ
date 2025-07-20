package view;

/**
 * GUI view class that displays the Plants vs Zombies game interface
 * @author Deveza, Jerry King 
 * @author Rivera, Dustine Gian
 * @version 1.0
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameView extends JFrame {
    private final JButton[][] tiles;
    private final JLabel sunLabel;
    private final JLabel messageLabel;
    private final JLabel timerLabel;
    private final JButton sunflowerButton;
    private final JButton peashooterButton;
    private final JButton startButton;
    private final GameViewListener listener;
    private Timer gameTimer;
    private Timer sunTimer;
    private int timeLeft = 180; // 3 minutes game timer

    public GameView(int rows, int cols, GameViewListener listener) {
        this.listener = listener;
        this.tiles = new JButton[rows][cols];

        setTitle("Plants vs Zombies - GUI Version");
        setSize(1000, 700);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Menu Bar
        JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Game");
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0));
        gameMenu.add(exitItem);
        menuBar.add(gameMenu);
        setJMenuBar(menuBar);

        // Top Panel - Info
        JPanel topPanel = new JPanel(new GridLayout(1, 4));
        sunLabel = new JLabel("Sun: 0");
        messageLabel = new JLabel("Welcome!");
        timerLabel = new JLabel("Time: 180s");
        startButton = new JButton("Start Game");
        startButton.addActionListener(e -> startGame());

        topPanel.add(sunLabel);
        topPanel.add(messageLabel);
        topPanel.add(timerLabel);
        topPanel.add(startButton);
        add(topPanel, BorderLayout.NORTH);

        // Center Panel - Game Board Grid
        JPanel gridPanel = new JPanel(new GridLayout(rows, cols));
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                JButton tileButton = new JButton();
                tileButton.setPreferredSize(new Dimension(80, 80));
                final int row = r, col = c;
                tileButton.addActionListener(e -> listener.onTileClicked(row, col));
                tiles[r][c] = tileButton;
                gridPanel.add(tileButton);
            }
        }
        add(gridPanel, BorderLayout.CENTER);

        // Bottom Panel - Plant Selection
        JPanel bottomPanel = new JPanel();
        sunflowerButton = new JButton("Sunflower");
        sunflowerButton.addActionListener(e -> listener.onPlantSelected("Sunflower"));
        peashooterButton = new JButton("Peashooter");
        peashooterButton.addActionListener(e -> listener.onPlantSelected("Peashooter"));

        bottomPanel.add(new JLabel("Select a plant: "));
        bottomPanel.add(sunflowerButton);
        bottomPanel.add(peashooterButton);
        add(bottomPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(null);
        setVisible(true);
    }

    public void startGame() {
        startTimer();
        startSunGeneration();
        // Zombie spawning is now handled by Board.update() with proper intervals
    }

    private void startTimer() {
        if (gameTimer != null && gameTimer.isRunning()) {
            gameTimer.stop();
        }
        gameTimer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                timeLeft--;
                timerLabel.setText("Time: " + timeLeft + "s");
                if (timeLeft <= 0) {
                    gameTimer.stop();
                    setMessage("Time's up! Game Over!");
                }
                listener.onStartGame(); // Game tick logic each second
            }
        });
        gameTimer.start();
    }

    private void startSunGeneration() {
        if (sunTimer != null && sunTimer.isRunning()) {
            sunTimer.stop();
        }
        sunTimer = new Timer(10000, new ActionListener() { // every 10 seconds
            public void actionPerformed(ActionEvent e) {
                listener.onPlantSelected("_generateSun"); // signal controller to add sun
            }
        });
        sunTimer.start();
    }

    // Zombie spawning is now handled by Board.update() with proper intervals:
    // 30-80s: every 10 seconds
    // 81-140s: every 5 seconds  
    // 141-170s: every 3 seconds
    // 171-180s: wave of zombies

    public void setSun(int amount) {
        sunLabel.setText("Sun: " + amount);
    }

    public void setMessage(String message) {
        messageLabel.setText(message);
    }

    public void clearZombies() {
        for (int r = 0; r < tiles.length; r++) {
            for (int c = 0; c < tiles[0].length; c++) {
                String text = tiles[r][c].getText();
                if (text.contains("Z")) {
                    text = text.replaceAll("Z\\d*", "");
                    tiles[r][c].setText(text.trim());
                }
            }
        }
    }

    public void updateTile(int row, int col, String marker) {
        String text = tiles[row][col].getText();
        if (marker.startsWith("Z")) {
            // Add or update zombie count
            text = text.replaceAll("Z\\d*", "");
            text = (text + " " + marker).trim();
        } else if (marker.equals("")) {
            text = text.replaceAll("Z\\d*", "");
            text = text.replaceAll("[SP]", "");
        } else {
            // Plant marker
            text = text.replaceAll("[SP]", "");
            text = (marker + text).trim();
        }
        tiles[row][col].setText(text);
    }

    public void stopAllTimers() {
        if (gameTimer != null) gameTimer.stop();
        if (sunTimer != null) sunTimer.stop();
    }

    public void clearMessageAfterDelay(int ms) {
        Timer clearMsgTimer = new Timer(ms, e -> setMessage(""));
        clearMsgTimer.setRepeats(false);
        clearMsgTimer.start();
    }

    public int getTimeLeft() {
        return timeLeft;
    }
} 
