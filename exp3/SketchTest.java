package exp3;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class SketchTest extends JFrame {

    public SketchTest() {
        setTitle("SketchTest - Mouse Line Start");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        LinePanel panel = new LinePanel();
        add(panel);

        setVisible(true);
    }

    public static void main(String[] args) {
        new SketchTest();
    }

    private static class LinePanel extends JPanel implements MouseListener {

        private int startX = 80;
        private int startY = 80;
        private int endX = 300;
        private int endY = 300;

        public LinePanel() {
            addMouseListener(this);
            setBackground(Color.WHITE);
        }

        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            g.setColor(Color.BLACK);
            g.drawLine(startX, startY, endX, endY);
        }

        @Override
        public void mouseClicked(MouseEvent e) {
            startX = e.getX();
            startY = e.getY();
            repaint();
        }

        @Override
        public void mousePressed(MouseEvent e) {}

        @Override
        public void mouseReleased(MouseEvent e) {}

        @Override
        public void mouseEntered(MouseEvent e) {}

        @Override
        public void mouseExited(MouseEvent e) {}
    }
}
