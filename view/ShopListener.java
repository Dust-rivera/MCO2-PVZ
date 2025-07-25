package view;

import java.awt.event.MouseEvent;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseMotionAdapter;
import java.awt.Component;
import java.awt.Image;
import java.awt.Point;
import java.awt.geom.Point2D;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JLayeredPane;
import javax.swing.SwingUtilities;

public class ShopListener extends MouseAdapter {

    private JLabel sunflower, peashooter, cherry;
    private JLabel drag = new JLabel();
    private JLayeredPane layers;
    private int offsetX, offsetY;
    ImageIcon image;
    Point initialCLick;

    public ShopListener(JLabel p1, JLabel p2, JLabel p3, JLayeredPane layers) {
        sunflower = p1;
        peashooter = p2;
        cherry = p3;
        this.layers = layers;
    }

    @Override
    public void mousePressed(MouseEvent e) {
        Object source = e.getSource();
        ImageIcon image = null;

        if (source == sunflower) {
            image = new ImageIcon("C:\\Users\\devez\\Desktop\\MCO2-PVZ-main\\view\\assets\\Sunflower.png");
        } else if (source == peashooter) {
            image = new ImageIcon("C:\\Users\\devez\\Desktop\\MCO2-PVZ-main\\view\\assets\\Peashooter.png");
        } else if (source == cherry) {
            image = new ImageIcon("C:\\Users\\devez\\Desktop\\MCO2-PVZ-main\\view\\assets\\Cherry.png");
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
            //layers.addMouseMotionListener(dragMotionListener);

            layers.addMouseMotionListener(new MouseMotionAdapter() {
                @Override
                public void mouseDragged(MouseEvent e) {
                    if (drag != null) {
                        Point p = SwingUtilities.convertPoint(e.getComponent(), e.getPoint(), layers);
                        drag.setLocation(p.x - offsetX, p.y - offsetY);
                    }
                }
            });
        }
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        // Drop the label in final position
        if (drag != null) {
            drag.setLocation(e.getXOnScreen() - offsetX, e.getYOnScreen() - offsetY);
            drag = null;
        }
    }

}