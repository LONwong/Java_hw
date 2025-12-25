package exp4;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Path2D;
import javax.swing.*;


public class MenuTest extends JFrame {

    enum FaceColor { YELLOW, PINK }
    enum FaceSize  { SMALL, BIG }
    enum SmileType { SMILE, BIGSMILE }

    private FaceColor faceColor = FaceColor.YELLOW;
    private FaceSize faceSize = FaceSize.SMALL;
    private SmileType smileType = SmileType.SMILE;

    private final FacePanel facePanel = new FacePanel();
    private final JPopupMenu popup = new JPopupMenu();

    // 用于同步“Smile 菜单”和“弹出菜单”
    private JRadioButtonMenuItem menuSmile, menuBigSmile;
    private JRadioButtonMenuItem popSmile, popBigSmile;

    public MenuTest() {
        super("MenuTest");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(520, 420);
        setLocationRelativeTo(null);

        setLayout(new BorderLayout());
        add(facePanel, BorderLayout.CENTER);

        setJMenuBar(buildMenuBar());
        buildPopupMenu();
        installPopupTrigger();

        setVisible(true);
    }

    // ===================== 菜单栏 =====================
    private JMenuBar buildMenuBar() {
        JMenuBar bar = new JMenuBar();

        // ---- Face ----
        JMenu face = new JMenu("Face");

        JMenu color = new JMenu("Color");
        ButtonGroup cg = new ButtonGroup();
        JRadioButtonMenuItem yellow = new JRadioButtonMenuItem("Yellow", true);
        JRadioButtonMenuItem pink = new JRadioButtonMenuItem("Pink");
        cg.add(yellow);
        cg.add(pink);
        yellow.addActionListener(e -> { faceColor = FaceColor.YELLOW; facePanel.repaint(); });
        pink.addActionListener(e -> { faceColor = FaceColor.PINK; facePanel.repaint(); });
        color.add(yellow);
        color.add(pink);

        JMenu size = new JMenu("Size");
        ButtonGroup sg = new ButtonGroup();
        JRadioButtonMenuItem small = new JRadioButtonMenuItem("Small", true);
        JRadioButtonMenuItem big = new JRadioButtonMenuItem("Big");
        sg.add(small);
        sg.add(big);
        small.addActionListener(e -> { faceSize = FaceSize.SMALL; facePanel.repaint(); });
        big.addActionListener(e -> { faceSize = FaceSize.BIG; facePanel.repaint(); });
        size.add(small);
        size.add(big);

        JMenuItem exit = new JMenuItem("Exit");
        exit.addActionListener(e -> System.exit(0));

        face.add(color);
        face.add(size);
        face.addSeparator();
        face.add(exit);

        // ---- Smile ----
        JMenu smile = new JMenu("Smile");
        ButtonGroup mg = new ButtonGroup();
        menuSmile = new JRadioButtonMenuItem("Smile", true);
        menuBigSmile = new JRadioButtonMenuItem("BigSmile");
        mg.add(menuSmile);
        mg.add(menuBigSmile);

        menuSmile.addActionListener(e -> setSmileType(SmileType.SMILE));
        menuBigSmile.addActionListener(e -> setSmileType(SmileType.BIGSMILE));
        smile.add(menuSmile);
        smile.add(menuBigSmile);

        // ---- Help ----
        JMenu help = new JMenu("Help");

        JMenuItem index = new JMenuItem("Index");
        index.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_I,
                Toolkit.getDefaultToolkit().getMenuShortcutKeyMaskEx()));
        index.addActionListener(e -> JOptionPane.showMessageDialog(
                this,
                "Index:\n" +
                        "Face -> Color: Yellow / Pink\n" +
                        "Face -> Size: Small / Big\n" +
                        "Smile -> Smile / BigSmile\n" +
                        "Right click: Popup menu (Smile / BigSmile)",
                "Index",
                JOptionPane.INFORMATION_MESSAGE
        ));

        JMenuItem about = new JMenuItem("About");
        about.addActionListener(e -> JOptionPane.showMessageDialog(
                this,
                "MenuTest - Experiment 4",
                "About",
                JOptionPane.INFORMATION_MESSAGE
        ));

        help.add(index);
        help.add(about);

        bar.add(face);
        bar.add(smile);
        bar.add(help);

        return bar;
    }

    // ===================== 弹出菜单（只要 Smile / BigSmile）=====================
    private void buildPopupMenu() {
        ButtonGroup pg = new ButtonGroup();
        popSmile = new JRadioButtonMenuItem("Smile", true);
        popBigSmile = new JRadioButtonMenuItem("BigSmile");
        pg.add(popSmile);
        pg.add(popBigSmile);

        popSmile.addActionListener(e -> setSmileType(SmileType.SMILE));
        popBigSmile.addActionListener(e -> setSmileType(SmileType.BIGSMILE));

        popup.add(popSmile);
        popup.add(popBigSmile);
    }

    private void installPopupTrigger() {
        facePanel.addMouseListener(new MouseAdapter() {
            private void showIfPopup(MouseEvent e) {
                if (e.isPopupTrigger()) popup.show(e.getComponent(), e.getX(), e.getY());
            }
            @Override public void mousePressed(MouseEvent e) { showIfPopup(e); }
            @Override public void mouseReleased(MouseEvent e) { showIfPopup(e); }
        });
    }

    private void setSmileType(SmileType type) {
        smileType = type;

        // 同步菜单
        if (menuSmile != null && menuBigSmile != null) {
            menuSmile.setSelected(type == SmileType.SMILE);
            menuBigSmile.setSelected(type == SmileType.BIGSMILE);
        }
        // 同步弹出菜单
        if (popSmile != null && popBigSmile != null) {
            popSmile.setSelected(type == SmileType.SMILE);
            popBigSmile.setSelected(type == SmileType.BIGSMILE);
        }

        facePanel.repaint();
    }

    // ===================== 画脸面板（参考你 SmileApp 的嘴巴画法）=====================
    class FacePanel extends JPanel {
        @Override
        protected void paintComponent(Graphics g) {
            super.paintComponent(g);

            Graphics2D g2d = (Graphics2D) g.create();
            g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

            int w = getWidth();
            int h = getHeight();

            // 直径：随窗口变化
            double sizeFactor = (faceSize == FaceSize.BIG) ? 0.75 : 0.60; // Big 比 Small 大
            int diameter = (int) (Math.min(w, h) * sizeFactor);

            int x = (w - diameter) / 2;
            int y = (h - diameter) / 2 - 20; // 上移给文字留空间

            // 脸色
            Color fc = (faceColor == FaceColor.YELLOW)
                    ? new Color(255, 235, 0)
                    : new Color(255, 170, 190);

            // 1) face
            g2d.setColor(fc);
            g2d.fillOval(x, y, diameter, diameter);

            // 2) eyes（黑色圆点）
            g2d.setColor(Color.BLACK);
            int eyeSize = Math.max(8, diameter / 8);
            int leftEyeX = x + diameter / 3 - eyeSize / 2;
            int rightEyeX = x + 2 * diameter / 3 - eyeSize / 2;
            int eyeY = y + diameter / 3;
            g2d.fillOval(leftEyeX, eyeY, eyeSize, eyeSize);
            g2d.fillOval(rightEyeX, eyeY, eyeSize, eyeSize);

            // 3) mouth
            int mouthW = diameter * 6 / 10;
            int mouthH = diameter * 5 / 10;
            int mouthX = x + (diameter - mouthW) / 2;
            int mouthY = y + diameter / 3;

            if (smileType == SmileType.SMILE) {
                // 普通笑：细弧线
                g2d.setStroke(new BasicStroke(Math.max(2f, diameter / 180f),
                        BasicStroke.CAP_ROUND, BasicStroke.JOIN_ROUND));
                g2d.drawArc(mouthX, mouthY, mouthW, mouthH, 0, -180);

            } else {
                Path2D path = new Path2D.Double();

                double leftX = mouthX;
                double rightX = mouthX + mouthW;

                // 月牙笑
                double baseY = mouthY + mouthH * 0.5;
                double bottomCtrlY = mouthY + mouthH * 1.2;
                double topCtrlY = mouthY + mouthH * 1.5;

                double centerX = mouthX + mouthW / 2.0;

                path.moveTo(leftX, baseY);
                // 下弧线
                path.quadTo(centerX, bottomCtrlY, rightX, baseY);
                // 上弧线
                path.quadTo(centerX, topCtrlY, leftX, baseY);

                path.closePath();

                g2d.setColor(Color.BLACK);
                g2d.fill(path);
            }

            // 4) text（蓝色斜体，随大小缩放）
            String text = (smileType == SmileType.SMILE) ? "Smiling..." : "BigSmiling...";
            int fontSize = Math.max(14, diameter / 6);
            Font font = new Font("Serif", Font.ITALIC | Font.BOLD, fontSize);
            g2d.setFont(font);
            g2d.setColor(new Color(30, 60, 200));

            FontMetrics fm = g2d.getFontMetrics();
            int textW = fm.stringWidth(text);
            int textX = (w - textW) / 2;
            int textY = y + diameter + fm.getAscent() + 20;
            g2d.drawString(text, textX, textY);

            g2d.dispose();
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
            catch (Exception ignored) {}
            new MenuTest();
        });
    }
}
