import java.awt.*;
import javax.swing.*;

public class HW5 extends JPanel {
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        g.setColor(Color.YELLOW);
        g.fillOval(50, 50, 200, 200);

        g.setColor(Color.BLACK);
        g.fillOval(100, 100, 30, 30);
        g.fillOval(170, 100, 30, 30);

        g.setColor(Color.BLACK);
        g.drawArc(100, 140, 100, 50, 0, -180);

        g.setColor(Color.BLUE);
        g.drawString("Smiling...", 120, 280); 
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Smile");
        HW5 panel = new HW5();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(300, 400);
        frame.add(panel);
        frame.setVisible(true);
    }
}
