package view;

/**
 * GUI view class that displays the Plants vs Zombies game interface
 * @author Deveza, Jerry King 
 * @author Rivera, Dustine Gian
 * @version 2.0
 */
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class GameView extends JFrame {
    // 2D array of buttons representing the game board tiles
    private final JButton[][] tiles;
    // Label showing current sun count
    private final JLabel sunLabel;
    // Label for displaying messages to the player
    private final JLabel messageLabel;
    // Label for displaying the game timer
    private final JLabel timerLabel;
    // Button to select Sunflower plant
    private final JButton sunflowerButton;
    // Button to select Peashooter plant
    private final JButton peashooterButton;
    // Button to start the game
    private final JButton startButton;
    // Button to claim dropped sun
    private final JButton claimSunButton;
    // Label to show unclaimed sun
    private final JLabel unclaimedSunLabel;
    // Listener for user actions (controller)
    private final GameViewListener listener;
    // Timer for game ticks (1 second interval)
    private Timer gameTimer;
    // Time left in seconds
    private int timeLeft = 180; // 3 minutes game timer

    /**
     * Constructs the game view, sets up the GUI layout and event listeners.
     * @param rows Number of rows in the board
     * @param cols Number of columns in the board
     * @param listener Controller to handle user actions
     */
    public GameView(int rows, int cols, GameViewListener listener) {
        this.listener = listener;
        this.tiles = new JButton[rows][cols];

        setTitle("Plants vs Zombies - GUI Version"); // Window title
        setSize(1000, 700); // Window size
        setDefaultCloseOperation(EXIT_ON_CLOSE); // Close app on exit
        setLayout(new BorderLayout()); // Use BorderLayout

        // Menu Bar setup
        JMenuBar menuBar = new JMenuBar();
        JMenu gameMenu = new JMenu("Game");
        JMenuItem exitItem = new JMenuItem("Exit");
        exitItem.addActionListener(e -> System.exit(0)); // Exit app
        gameMenu.add(exitItem);
        menuBar.add(gameMenu);
        setJMenuBar(menuBar);

        // Top Panel - Info (sun, message, timer, start button)
        JPanel topPanel = new JPanel(new GridLayout(1, 4));
        sunLabel = new JLabel("Sun: 0"); // Initial sun
        messageLabel = new JLabel("Welcome!"); // Initial message
        timerLabel = new JLabel("Time: 180s"); // Initial timer
        startButton = new JButton("Start Game");
        startButton.addActionListener(e -> startGame()); // Start game on click
        topPanel.add(sunLabel);
        topPanel.add(messageLabel);
        topPanel.add(timerLabel);
        topPanel.add(startButton);
        add(topPanel, BorderLayout.NORTH);

        // Center Panel - Game Board Grid
        JPanel gridPanel = new JPanel(new GridLayout(rows, cols));
        for (int r = 0; r < rows; r++) {
            for (int c = 0; c < cols; c++) {
                JButton tileButton = new JButton(); // Each tile is a button
                tileButton.setPreferredSize(new Dimension(80, 80)); // Size of tile
                final int row = r, col = c;
                tileButton.addActionListener(e -> listener.onTileClicked(row, col)); // Notify controller on click
                tiles[r][c] = tileButton;
                gridPanel.add(tileButton);
            }
        }
        add(gridPanel, BorderLayout.CENTER);

        // Bottom Panel - Plant Selection and Sun Claim
        unclaimedSunLabel = new JLabel("Unclaimed Sun: 0");
        claimSunButton = new JButton("Claim Sun");
        claimSunButton.setEnabled(false);
        claimSunButton.addActionListener(e -> listener.onClaimSun());
        JPanel bottomPanel = new JPanel();
        sunflowerButton = new JButton("Sunflower");
        sunflowerButton.addActionListener(e -> listener.onPlantSelected("Sunflower")); // Select Sunflower
        peashooterButton = new JButton("Peashooter");
        peashooterButton.addActionListener(e -> listener.onPlantSelected("Peashooter")); // Select Peashooter
        bottomPanel.add(new JLabel("Select a plant: "));
        bottomPanel.add(sunflowerButton);
        bottomPanel.add(peashooterButton);
        // Add unclaimed sun label and claim button to bottom panel
        bottomPanel.add(unclaimedSunLabel);
        bottomPanel.add(claimSunButton);
        add(bottomPanel, BorderLayout.SOUTH);

        setLocationRelativeTo(null); // Center window
        setVisible(true); // Show window
    }

    /**
     * Starts the game: begins the game timer and sun generation timer.
     */
    public void startGame() {
        // Remove the start button from the top panel
        startButton.setVisible(false);
        startTimer(); // Start main game timer
        // Zombie spawning is handled by Board.update()
    }

    /**
     * Starts the main game timer (1 second per tick).
     * Each tick updates the timer and notifies the controller.
     */
    private void startTimer() {
        if (gameTimer != null && gameTimer.isRunning()) {
            gameTimer.stop(); // Stop previous timer if running
        }
        gameTimer = new Timer(1000, new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                timeLeft--; // Decrement time
                timerLabel.setText("Time: " + timeLeft + "s"); // Update timer label
                if (timeLeft <= 0) {
                    gameTimer.stop();
                    setMessage("Time's up! Game Over!");
                }
                listener.onStartGame(); // Notify controller for each tick
            }
        });
        gameTimer.start();
    }

    // Zombie spawning is now handled by Board.update() with proper intervals:
    // 30-80s: every 10 seconds
    // 81-140s: every 5 seconds  
    // 141-170s: every 3 seconds
    // 171-180s: wave of zombies

    /**
     * Updates the sun count display.
     * @param amount The current sun amount
     */
    public void setSun(int amount) {
        sunLabel.setText("Sun: " + amount);
    }

    /**
     * Sets a message to display to the player.
     * @param message The message to show
     */
    public void setMessage(String message) {
        messageLabel.setText(message);
    }

    /**
     * Updates the unclaimed sun display and claim button state.
     * @param amount The current unclaimed sun amount
     */
    public void setUnclaimedSun(int amount) {
        unclaimedSunLabel.setText("Unclaimed Sun: " + amount);
        claimSunButton.setEnabled(amount > 0);
    }

    /**
     * Clears all zombie markers from the board tiles.
     */
    public void clearZombies() {
        for (int r = 0; r < tiles.length; r++) {
            for (int c = 0; c < tiles[0].length; c++) {
                String text = tiles[r][c].getText();
                if (text.contains("Z")) {
                    text = text.replaceAll("Z\\d*", ""); // Remove zombie marker
                    tiles[r][c].setText(text.trim());
                }
            }
        }
    }

    /**
     * Updates a specific tile with a marker (plant or zombie).
     * @param row Row index
     * @param col Column index
     * @param marker Marker string ("S", "P", "Z#", or "")
     */
    public void updateTile(int row, int col, String marker) {
        String text = tiles[row][col].getText();
        // Always remove all S, P, and Z# first
        text = text.replaceAll("[SP]", "");
        text = text.replaceAll("Z\\d*", "");
        text = text.trim();
        if (marker.startsWith("Z")) {
            // Only add zombie marker
            text = (text + " " + marker).trim();
        } else if (marker.equals("")) {
            // Clear tile
            // text is already cleaned
        } else {
            // Plant marker (S or P)
            text = (marker + " " + text).trim();
        }
        tiles[row][col].setText(text);
    }

    /**
     * Stops all running timers (game and sun timers).
     */
    public void stopAllTimers() {
        if (gameTimer != null) gameTimer.stop();
    }

    /**
     * Clears the message label after a delay.
     * @param ms Milliseconds to wait before clearing
     */
    public void clearMessageAfterDelay(int ms) {
        Timer clearMsgTimer = new Timer(ms, e -> setMessage(""));
        clearMsgTimer.setRepeats(false);
        clearMsgTimer.start();
    }

    /**
     * Gets the time left in the game.
     * @return Seconds remaining
     */
    public int getTimeLeft() {
        return timeLeft;
    }

    public String getTileText(int row, int col) {
        return tiles[row][col].getText();
    }
} 
