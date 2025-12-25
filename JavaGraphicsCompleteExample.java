// 导入Swing GUI包 - 用于创建窗口和组件
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.ArrayList;
import javax.swing.*;

// 主类：Java图形程序综合示例
public class JavaGraphicsCompleteExample {
    // main方法：程序入口点
     public static void main(String[] args) {
        // 使用事件分派线程创建GUI - Swing要求所有GUI操作都在此线程中执行
        EventQueue.invokeLater(() -> {
            // 创建主窗口对象
            JFrame mainFrame = new JFrame("Java图形程序综合示例");
            // 设置窗口关闭行为 - 关闭窗口时退出程序
            mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            // 创建综合面板对象
            IntegratedPanel panel = new IntegratedPanel();
            // 将面板添加到窗口的内容面板
            mainFrame.add(panel);
            // 调整窗口大小以适应组件
            mainFrame.pack();
            // 将窗口居中显示
            mainFrame.setLocationRelativeTo(null);
            // 显示窗口
            mainFrame.setVisible(true);
        });
    }
}

// 自定义图形类：包含图形、颜色和填充属性
class ShapeWithColor {
    // 私有成员：图形对象
    private Shape shape;
    // 私有成员：图形颜色
    private Color color;
    // 私有成员：是否填充标志
    private boolean filled;
    
    // 构造方法：初始化图形、颜色和填充属性
    public ShapeWithColor(Shape shape, Color color, boolean filled) {
        this.shape = shape;
        this.color = color;
        this.filled = filled;
    }
    
    // 获取图形对象方法
    public Shape getShape() {
        return shape;
    }  
    
    // 获取图形颜色方法
    public Color getColor() {
        return color;
    }
    
    // 获取是否填充方法
    public boolean isFilled() {
        return filled;
    }
}

// 综合面板类：继承JPanel，实现ActionListener和MouseListener接口
class IntegratedPanel extends JPanel implements ActionListener, MouseListener, MouseMotionListener {
    // 常量：窗口默认宽度
    private static final int WIDTH = 800;
    // 常量：窗口默认高度
    private static final int HEIGHT = 600;
    
    // 图形绘制相关变量
    private ArrayList<ShapeWithColor> shapes;  // 存储所有绘制的图形（使用自定义类）
    private Color currentColor;                // 当前绘图颜色
    private String currentShapeType;           // 当前绘制图形类型
    private Point2D startPoint;                // 绘制起点
    private Point2D endPoint;                  // 绘制终点
    private boolean isDrawing;                 // 是否正在绘图标志
    
    // UI组件
    private JButton rectButton;                // 矩形按钮
    private JButton circleButton;              // 圆形按钮
    private JButton lineButton;                // 直线按钮
    private JButton clearButton;               // 清除按钮
    private JComboBox<String> colorCombo;      // 颜色选择下拉框
    private JLabel statusLabel;                // 状态标签
    private JCheckBox fillCheckBox;            // 填充复选框
    
    // 构造方法：初始化面板
    public IntegratedPanel() {
        // 设置面板的首选大小
        setPreferredSize(new Dimension(WIDTH, HEIGHT));
        // 设置面板布局为BorderLayout
        setLayout(new BorderLayout());
        // 设置面板背景色为浅灰色
        setBackground(new Color(245, 245, 245));
        
        // 初始化图形列表
        shapes = new ArrayList<>();
        // 设置默认绘图颜色为黑色
        currentColor = Color.BLACK;
        // 设置默认图形类型为矩形
        currentShapeType = "矩形";
        // 初始化绘图标志为false
        isDrawing = false;
        
        // 创建控制面板
        createControlPanel();
        // 创建状态面板
        createStatusPanel();
        
        // 添加鼠标监听器到面板
        addMouseListener(this);
        addMouseMotionListener(this);
        // 设置面板可获得焦点（用于接收键盘事件）
        setFocusable(true);
    }
    
    // 创建控制面板方法
    private void createControlPanel() {
        // 创建控制面板对象
        JPanel controlPanel = new JPanel();
        // 设置控制面板背景色
        controlPanel.setBackground(new Color(230, 230, 230));
        // 设置控制面板布局为FlowLayout
        controlPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        // 创建矩形按钮
        rectButton = new JButton("矩形");
        // 创建圆形按钮
        circleButton = new JButton("圆形");
        // 创建直线按钮
        lineButton = new JButton("直线");
        // 创建清除按钮
        clearButton = new JButton("清除画布");
        
        // 为所有按钮添加动作监听器（当前面板作为监听器）
        rectButton.addActionListener(this);
        circleButton.addActionListener(this);
        lineButton.addActionListener(this);
        clearButton.addActionListener(this);
        
        // 创建颜色选择下拉框
        String[] colors = {"黑色", "红色", "绿色", "蓝色", "黄色", "紫色", "橙色"};
        colorCombo = new JComboBox<>(colors);
        // 为下拉框添加动作监听器
        colorCombo.addActionListener(this);
        
        // 创建填充复选框
        fillCheckBox = new JCheckBox("填充图形");
        // 设置复选框默认选中
        fillCheckBox.setSelected(true);
        
        // 将组件添加到控制面板
        controlPanel.add(new JLabel("图形类型:"));
        controlPanel.add(rectButton);
        controlPanel.add(circleButton);
        controlPanel.add(lineButton);
        controlPanel.add(new JLabel("    颜色:"));
        controlPanel.add(colorCombo);
        controlPanel.add(fillCheckBox);
        controlPanel.add(new JLabel("    "));
        controlPanel.add(clearButton);
        
        // 将控制面板添加到主面板的北部（顶部）
        add(controlPanel, BorderLayout.NORTH);
    }
    
    // 创建状态面板方法
    private void createStatusPanel() {
        // 创建状态面板对象
        JPanel statusPanel = new JPanel();
        // 设置状态面板背景色
        statusPanel.setBackground(new Color(240, 240, 240));
        // 设置状态面板布局为FlowLayout
        statusPanel.setLayout(new FlowLayout(FlowLayout.LEFT));
        
        // 创建状态标签
        statusLabel = new JLabel("就绪 - 选择图形类型开始绘制");
        // 设置标签字体
        statusLabel.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        
        // 将标签添加到状态面板
        statusPanel.add(statusLabel);
        // 将状态面板添加到主面板的南部（底部）
        add(statusPanel, BorderLayout.SOUTH);
    }
    
    // 重写paintComponent方法：进行自定义绘制
    @Override
    protected void paintComponent(Graphics g) {
        // 调用父类方法，确保正确的绘制流程
        super.paintComponent(g);
        // 将Graphics对象转换为功能更强的Graphics2D对象
        Graphics2D g2 = (Graphics2D) g;
        
        // 启用抗锯齿渲染，使图形边缘更平滑
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, 
                           RenderingHints.VALUE_ANTIALIAS_ON);
        
        // 绘制标题和说明
        drawTitle(g2);
        
        // 绘制已保存的所有图形
        drawSavedShapes(g2);
        
        // 如果正在绘图，绘制当前临时图形
        drawCurrentShape(g2);
    }
    
    // 绘制标题和说明方法
    private void drawTitle(Graphics2D g2) {
        // 设置标题字体
        g2.setFont(new Font("微软雅黑", Font.BOLD, 20));
        // 设置标题颜色
        g2.setColor(new Color(50, 50, 150));
        // 绘制标题文本
        g2.drawString("Java图形程序设计综合示例", 20, 40);
        
        // 设置说明字体
        g2.setFont(new Font("微软雅黑", Font.PLAIN, 12));
        // 设置说明颜色
        g2.setColor(Color.DARK_GRAY);
        // 绘制操作说明
        g2.drawString("操作指南：", 20, 70);
        g2.drawString("1. 选择图形类型（矩形/圆形/直线）", 40, 90);
        g2.drawString("2. 选择颜色", 40, 110);
        g2.drawString("3. 在绘图区按下鼠标并拖动绘制图形", 40, 130);
        g2.drawString("4. 勾选/取消勾选'填充图形'切换填充模式", 40, 150);
        g2.drawString("5. 点击'清除画布'按钮清除所有图形", 40, 170);
    }
    
    // 绘制已保存的图形方法
    private void drawSavedShapes(Graphics2D g2) {
        // 遍历所有保存的图形
        for (ShapeWithColor coloredShape : shapes) {
            // 设置绘图颜色
            g2.setColor(coloredShape.getColor());
            
            // 判断是否填充图形
            if (coloredShape.isFilled()) {
                // 填充图形（实心）
                g2.fill(coloredShape.getShape());
            } else {
                // 绘制图形轮廓
                g2.draw(coloredShape.getShape());
            }
        }
    }
    
    // 绘制当前正在绘制的图形方法
    private void drawCurrentShape(Graphics2D g2) {
        // 如果不是正在绘图，直接返回
        if (!isDrawing || startPoint == null || endPoint == null) return;
        
        // 设置当前绘图颜色
        g2.setColor(currentColor);
        // 创建当前图形对象
        Shape currentShape = null;
        
        // 根据当前图形类型创建不同的图形
        switch (currentShapeType) {
            case "矩形":
                // 计算矩形的左上角坐标和宽高
                double rectX = Math.min(startPoint.getX(), endPoint.getX());
                double rectY = Math.min(startPoint.getY(), endPoint.getY());
                double rectWidth = Math.abs(endPoint.getX() - startPoint.getX());
                double rectHeight = Math.abs(endPoint.getY() - startPoint.getY());
                // 创建矩形对象
                currentShape = new Rectangle2D.Double(rectX, rectY, rectWidth, rectHeight);
                break;
                
            case "圆形":
                // 计算圆形的左上角坐标和直径
                double circleX = Math.min(startPoint.getX(), endPoint.getX());
                double circleY = Math.min(startPoint.getY(), endPoint.getY());
                double circleSize = Math.max(Math.abs(endPoint.getX() - startPoint.getX()), 
                                           Math.abs(endPoint.getY() - startPoint.getY()));
                // 创建圆形对象
                currentShape = new Ellipse2D.Double(circleX, circleY, circleSize, circleSize);
                break;
                
            case "直线":
                // 创建直线对象
                currentShape = new Line2D.Double(startPoint, endPoint);
                break;
        }
        
        // 如果图形不为空，绘制临时图形
        if (currentShape != null) {
            // 设置临时图形为虚线样式
            float[] dashPattern = {5, 5}; // 5像素实线，5像素空白
            g2.setStroke(new BasicStroke(2, BasicStroke.CAP_BUTT, 
                                       BasicStroke.JOIN_BEVEL, 1, dashPattern, 0));
            
            // 绘制临时图形
            g2.draw(currentShape);
            
            // 恢复实线样式
            g2.setStroke(new BasicStroke(2));
        }
    }
    
    // 实现ActionListener接口的actionPerformed方法
    @Override
    public void actionPerformed(ActionEvent e) {
        // 获取事件源对象
        Object source = e.getSource();
        
        // 处理矩形按钮点击事件
        if (source == rectButton) {
            currentShapeType = "矩形";
            updateStatus("已选择矩形工具");
        }
        // 处理圆形按钮点击事件
        else if (source == circleButton) {
            currentShapeType = "圆形";
            updateStatus("已选择圆形工具");
        }
        // 处理直线按钮点击事件
        else if (source == lineButton) {
            currentShapeType = "直线";
            updateStatus("已选择直线工具");
        }
        // 处理清除按钮点击事件
        else if (source == clearButton) {
            shapes.clear(); // 清空图形列表
            updateStatus("已清除所有图形");
            repaint(); // 重新绘制面板
        }
        // 处理颜色选择下拉框事件
        else if (source == colorCombo) {
            // 获取选中的颜色文本
            String selectedColor = (String) colorCombo.getSelectedItem();
            // 根据选择设置当前颜色
            switch (selectedColor) {
                case "黑色": currentColor = Color.BLACK; break;
                case "红色": currentColor = Color.RED; break;
                case "绿色": currentColor = Color.GREEN; break;
                case "蓝色": currentColor = Color.BLUE; break;
                case "黄色": currentColor = Color.YELLOW; break;
                case "紫色": currentColor = new Color(128, 0, 128); break;
                case "橙色": currentColor = Color.ORANGE; break;
            }
            updateStatus("当前颜色: " + selectedColor);
        }
    }
    
    // 更新状态标签方法
    private void updateStatus(String message) {
        // 更新状态标签文本
        statusLabel.setText("状态: " + message);
    }
    
    // 实现MouseListener接口的mousePressed方法
    @Override
    public void mousePressed(MouseEvent e) {
        // 记录鼠标按下时的起点
        startPoint = e.getPoint();
        // 设置绘图标志为true
        isDrawing = true;
        // 更新状态
        updateStatus("开始绘制" + currentShapeType + " - 拖动鼠标调整大小");
    }
    
    // 实现MouseListener接口的mouseReleased方法
    @Override
    public void mouseReleased(MouseEvent e) {
        // 如果不是正在绘图，直接返回
        if (!isDrawing || startPoint == null) return;
        
        // 记录鼠标释放时的终点
        endPoint = e.getPoint();
        
        // 创建最终的图形对象
        Shape finalShape = null;
        
        // 根据当前图形类型创建对应的图形对象
        switch (currentShapeType) {
            case "矩形":
                double rectX = Math.min(startPoint.getX(), endPoint.getX());
                double rectY = Math.min(startPoint.getY(), endPoint.getY());
                double rectWidth = Math.abs(endPoint.getX() - startPoint.getX());
                double rectHeight = Math.abs(endPoint.getY() - startPoint.getY());
                finalShape = new Rectangle2D.Double(rectX, rectY, rectWidth, rectHeight);
                break;
                
            case "圆形":
                double circleX = Math.min(startPoint.getX(), endPoint.getX());
                double circleY = Math.min(startPoint.getY(), endPoint.getY());
                double circleSize = Math.max(Math.abs(endPoint.getX() - startPoint.getX()), 
                                           Math.abs(endPoint.getY() - startPoint.getY()));
                finalShape = new Ellipse2D.Double(circleX, circleY, circleSize, circleSize);
                break;
                
            case "直线":
                finalShape = new Line2D.Double(startPoint, endPoint);
                break;
        }
        
        // 如果图形不为空，保存到图形列表
        if (finalShape != null) {
            // 创建带颜色和填充属性的图形对象
            ShapeWithColor coloredShape = new ShapeWithColor(
                finalShape, currentColor, fillCheckBox.isSelected()
            );
            // 添加到图形列表
            shapes.add(coloredShape);
            // 更新状态
            updateStatus("已绘制" + currentShapeType + " - 共" + shapes.size() + "个图形");
        }
        
        // 重置绘图状态
        isDrawing = false;
        startPoint = null;
        endPoint = null;
        // 重新绘制面板
        repaint();
    }
    
    // 实现MouseMotionListener接口的mouseDragged方法
    @Override
    public void mouseDragged(MouseEvent e) {
        // 如果正在绘图，更新终点并重新绘制
        if (isDrawing) {
            endPoint = e.getPoint();
            repaint();
        }
    }
    
    // 实现MouseMotionListener接口的mouseMoved方法
    @Override
    public void mouseMoved(MouseEvent e) {
        // 获取当前鼠标坐标
        int x = e.getX();
        int y = e.getY();
        // 更新状态显示鼠标位置
        updateStatus("鼠标位置: (" + x + ", " + y + ") - 选择图形类型开始绘制");
    }
    
    // 以下方法实现MouseListener接口，但暂时不需要功能，所以留空
    
    // 鼠标点击方法（空实现）
    @Override
    public void mouseClicked(MouseEvent e) {}
    
    // 鼠标进入组件方法（空实现）
    @Override
    public void mouseEntered(MouseEvent e) {}
    
    // 鼠标离开组件方法（空实现）
    @Override
    public void mouseExited(MouseEvent e) {}
}