package view;

import java.awt.event.MouseEvent;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter; // not used
import java.awt.Component;
import java.awt.Image;
import java.awt.Point;
import java.awt.geom.Point2D; // not used
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.SwingUtilities;
import javax.swing.JPanel;

public class ShopListener extends MouseAdapter {

    private JLabel sunflower, peashooter, cherry;
    private JLabel drag = new JLabel();
    private JLayeredPane layers;
    private int offsetX, offsetY;
    ImageIcon image;
    Point initialCLick;
    private GameView gameView; // added gameView para ma-access ung board mismo

    public ShopListener(JLabel p1, JLabel p2, JLabel p3, JLayeredPane layers, GameView gameView) { // also added it as a parameter
        sunflower = p1;
        peashooter = p2;
        cherry = p3;
        this.layers = layers;
        this.gameView = gameView;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Object source = e.getSource();
        ImageIcon image = null;

        if (source == sunflower) {
            image = new ImageIcon("C:\\Users\\river\\Desktop\\MCO2-PVZ-main BRANCH\\view\\assets\\Sunflower.png");
        } else if (source == peashooter) {
            image = new ImageIcon("C:\\Users\\river\\Desktop\\MCO2-PVZ-main BRANCH\\view\\assets\\Peashooter.png");
        } else if (source == cherry) {
            image = new ImageIcon("C:\\Users\\river\\Desktop\\MCO2-PVZ-main BRANCH\\view\\assets\\Cherry.png");
        }

        if (image != null) {
            // Resize image
            Image img = image.getImage().getScaledInstance(50, 50, Image.SCALE_SMOOTH);
            image = new ImageIcon(img);

            // Create new draggable JLabel
            drag = new JLabel(image);

            // Get mouse position relative to layered pane
            Point point = SwingUtilities.convertPoint((Component) e.getSource(), e.getPoint(), layers);

            drag.setBounds(point.x - image.getIconWidth() / 2,
                    point.y - image.getIconHeight() / 2,
                    image.getIconWidth(), image.getIconHeight());

            layers.add(drag, Integer.valueOf(2));
            layers.repaint();

            offsetX = point.x - drag.getX();
            offsetY = point.y - drag.getY();

            
            this.image = image; // used in mouse released
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        if (drag != null) {
            JPanel board = null;
            for (int i = 0; i < layers.getComponentCount(); i++) {
                if (layers.getComponent(i) instanceof JPanel && layers.getComponent(i).getBounds().width == 700 
                && layers.getComponent(i).getBounds().height == 450) { // this is the board
                    board = (JPanel) layers.getComponent(i);
                    break;
                }
            }
            if (board != null && gameView != null) {
                Point mousePoint = SwingUtilities.convertPoint(e.getComponent(), e.getPoint(), board);
                int[] indices = new int[2];
                JPanel cell = gameView.getCellAtPoint(mousePoint, indices);
                if (cell != null) {
                    cell.removeAll();
                    JLabel plant = new JLabel(image);
                    cell.add(plant);
                    cell.revalidate();
                    cell.repaint();
                }
            }
            layers.remove(drag);
            layers.repaint();
            drag = null;
        }
    }

    @Override // this is used when the user is dragging the plant, magpapakita ung image na dinadrag mo siya
    public void mouseDragged(MouseEvent e) {
        if (drag != null) {
            Point p = SwingUtilities.convertPoint(e.getComponent(), e.getPoint(), layers);
            drag.setLocation(p.x - offsetX, p.y - offsetY);
        }
    }


}