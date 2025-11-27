import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class HW6 extends JFrame {
    private Color currentColor;
    private JButton redButton, greenButton, blueButton;
    private JPanel drawingPanel;

    public HW6() {
        setTitle("ColorTest");
        setSize(400, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        currentColor = getBackground();

        redButton = new JButton("Red");
        greenButton = new JButton("Green");
        blueButton = new JButton("Blue");

        redButton.addActionListener(e -> {
            currentColor = Color.RED;
            drawingPanel.repaint();
        });
        greenButton.addActionListener(e -> {
            currentColor = Color.GREEN;
            drawingPanel.repaint();
        });
        blueButton.addActionListener(e -> {
            currentColor = Color.BLUE;
            drawingPanel.repaint();
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(redButton);
        buttonPanel.add(greenButton);
        buttonPanel.add(blueButton);
        add(buttonPanel, BorderLayout.NORTH);

        drawingPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.setColor(currentColor);
                g.fillOval(100, 100, 200, 200);
                g.setColor(Color.BLACK);
                g.drawOval(100, 100, 200, 200);
            }
        };

        drawingPanel.setPreferredSize(new Dimension(400, 400));
        drawingPanel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));

        drawingPanel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                if (isMouseInsideCircle(e.getPoint())) {
                    drawingPanel.setCursor(Cursor.getPredefinedCursor(Cursor.CROSSHAIR_CURSOR));
                } else {
                    drawingPanel.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
                }
            }
        });

        drawingPanel.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (isMouseInsideCircle(e.getPoint())) {
                    if (currentColor == getBackground()) {
                        currentColor = Color.RED;
                    } else if (currentColor == Color.RED) {
                        currentColor = Color.GREEN;
                    } else if (currentColor == Color.GREEN) {
                        currentColor = Color.BLUE;
                    } else {
                        currentColor = getBackground();
                    }
                    drawingPanel.repaint();
                }
            }
        });

        add(drawingPanel, BorderLayout.CENTER);
    }

    private boolean isMouseInsideCircle(Point p) {
        int circleX = 100, circleY = 100, radius = 100;
        int dx = p.x - (circleX + radius);
        int dy = p.y - (circleY + radius);
        return (dx * dx + dy * dy) <= (radius * radius);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new HW6().setVisible(true);
        });
    }
}
