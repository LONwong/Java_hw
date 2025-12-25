package exp3;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.InputEvent;
import java.awt.event.KeyEvent;
import javax.swing.*;

public class SmileApp extends JFrame {

    private FacePanel facePanel;

    public SmileApp() {
        setTitle("Smile");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(400, 450);
        setLayout(new BorderLayout());

        // 1. 初始化顶部按钮面板
        JPanel buttonPanel = new JPanel();
        JButton btnSmile = new JButton("Smile");
        JButton btnBigSmile = new JButton("BigSmile");

        buttonPanel.add(btnSmile);
        buttonPanel.add(btnBigSmile);
        add(buttonPanel, BorderLayout.NORTH);

        // 2. 初始化中间的绘图面板
        facePanel = new FacePanel();
        add(facePanel, BorderLayout.CENTER);

        // --- 功能实现 A: 按钮点击事件 ---
        btnSmile.addActionListener(e -> updateSmile(false));
        btnBigSmile.addActionListener(e -> updateSmile(true));

        // --- 功能实现 B: 键盘快捷键 (CTRL+B 和 CTRL+L) ---
        // 获取面板的 InputMap (当窗口处于焦点时有效)
        InputMap inputMap = ((JPanel)getContentPane()).getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW);
        ActionMap actionMap = ((JPanel)getContentPane()).getActionMap();

        // 绑定 CTRL+B -> BigSmile
        KeyStroke ctrlB = KeyStroke.getKeyStroke(KeyEvent.VK_B, InputEvent.CTRL_DOWN_MASK);
        inputMap.put(ctrlB, "doBigSmile");
        actionMap.put("doBigSmile", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateSmile(true); // 触发大笑
                // 可选：视觉上模拟按钮点击
                btnBigSmile.doClick(); 
            }
        });

        // 绑定 CTRL+L -> Smile
        KeyStroke ctrlL = KeyStroke.getKeyStroke(KeyEvent.VK_L, InputEvent.CTRL_DOWN_MASK);
        inputMap.put(ctrlL, "doSmile");
        actionMap.put("doSmile", new AbstractAction() {
            @Override
            public void actionPerformed(ActionEvent e) {
                updateSmile(false); // 触发微笑
                btnSmile.doClick();
            }
        });

        setLocationRelativeTo(null); // 居中显示
        setVisible(true);
    }

    // 辅助方法：更新状态并重绘
    private void updateSmile(boolean isBig) {
        facePanel.setBigSmile(isBig);
        facePanel.repaint();
    }

    public static void main(String[] args) {
        // 在事件调度线程中启动 GUI
        SwingUtilities.invokeLater(SmileApp::new);
    }
}

/**
 * 自定义面板，用于绘制笑脸
 * --- 功能实现 C: 窗体大小变化时，通过 paintComponent 动态计算尺寸 ---
 */
class FacePanel extends JPanel {
    private boolean isBigSmile = false; // 默认为 false (普通微笑)

    public void setBigSmile(boolean bigSmile) {
        this.isBigSmile = bigSmile;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        
        // 开启抗锯齿，让画出来的圆更平滑
        Graphics2D g2d = (Graphics2D) g;
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // 获取当前面板的宽和高
        int w = getWidth();
        int h = getHeight();

        // 计算脸的大小：取宽高中较小值的 60% 作为直径
        int diameter = Math.min(w, h) * 6 / 10;
        int x = (w - diameter) / 2;
        int y = (h - diameter) / 2 - 20; // 稍微向上偏移一点，给文字留空间

        // 1. 画脸 (黄色背景)
        g2d.setColor(Color.YELLOW);
        g2d.fillOval(x, y, diameter, diameter);

        // 2. 画眼睛 (黑色)
        g2d.setColor(Color.BLACK);
        int eyeWidth = diameter / 8;
        int eyeHeight = diameter / 8;
        int leftEyeX = x + diameter / 3 - eyeWidth / 2;
        int rightEyeX = x + 2 * diameter / 3 - eyeWidth / 2;
        int eyeY = y + diameter / 3;
        
        g2d.fillOval(leftEyeX, eyeY, eyeWidth, eyeHeight);
        g2d.fillOval(rightEyeX, eyeY, eyeWidth, eyeHeight);

        // 3. 画嘴巴
        int mouthWidth = diameter * 6 / 10;
        int mouthHeight = diameter * 5 / 10; 
        int mouthX = x + (diameter - mouthWidth) / 2;
        int mouthY = y + diameter / 3;

        g2d.setStroke(new BasicStroke(2));

        if (isBigSmile) {
            // --- 修正部分：使用 Path2D 绘制月牙形状 ---
            // 只要稍微调整两个控制点的 Y 坐标，就能改变月牙的胖瘦
            java.awt.geom.Path2D path = new java.awt.geom.Path2D.Double();
            
            // 计算嘴角的水平基准线 (drawArc 的圆弧也是以高度的一半为轴心的)
            double startY = mouthY + mouthHeight / 1.5;
            double leftX = mouthX;
            double rightX = mouthX + mouthWidth;
            double centerX = mouthX + mouthWidth / 2.0;

            // 1. 移动到左嘴角
            path.moveTo(leftX, startY);
            path.quadTo(centerX, mouthY + mouthHeight * 1.1, rightX, startY);
            path.quadTo(centerX, mouthY + mouthHeight * 1.5, leftX, startY);

            path.closePath();
            g2d.fill(path);
            
        } else {
            // Smile: 普通微笑保持不变，是一个线条
            g2d.drawArc(mouthX, mouthY, mouthWidth, mouthHeight, 0, -180);
        }

        // 4. 画文字 (随大小缩放)
        String text = isBigSmile ? "BigSmiling..." : "Smiling...";
        
        // 动态设置字体大小 (约为直径的 15%)
        int fontSize = Math.max(12, diameter / 6); 
        Font font = new Font("SansSerif", Font.ITALIC | Font.BOLD, fontSize);
        g2d.setFont(font);
        g2d.setColor(Color.BLUE);

        // 计算文字居中位置
        FontMetrics fm = g2d.getFontMetrics();
        int textWidth = fm.stringWidth(text);
        int textX = (w - textWidth) / 2;
        int textY = y + diameter + fm.getAscent() + 20; // 在脸的下方

        g2d.drawString(text, textX, textY);
    }
}