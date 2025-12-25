package exp4;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;

public class TextDisplay extends JFrame {
    private final JTextArea ta = new JTextArea();

    private final JComboBox<String> fontBox =
            new JComboBox<>(GraphicsEnvironment.getLocalGraphicsEnvironment().getAvailableFontFamilyNames());
    private final JCheckBox bold = new JCheckBox("Bold");
    private final JCheckBox italic = new JCheckBox("Italic");

    private final JRadioButton small  = new JRadioButton("small");
    private final JRadioButton medium = new JRadioButton("Medium", true);
    private final JRadioButton large  = new JRadioButton("Large");
    private final JRadioButton xlarge = new JRadioButton("Extra large");

    public TextDisplay() {
        super("TextDisplay");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        add(buildSetFontPanel(), BorderLayout.NORTH);

        ta.setLineWrap(true);
        ta.setWrapStyleWord(true);
        add(new JScrollPane(ta), BorderLayout.CENTER);

        JPanel bottom = new JPanel(new FlowLayout(FlowLayout.CENTER, 18, 10));
        JButton insert = new JButton("Insert");
        JButton clean = new JButton("Clean");
        bottom.add(insert);
        bottom.add(clean);
        add(bottom, BorderLayout.SOUTH);

        insert.addActionListener(e -> ta.append("The quick brown fox jumps over the lazy dog.\n"));
        clean.addActionListener(e -> ta.setText(""));

        Runnable u = this::updateFont;
        fontBox.setSelectedItem("Serif");
        fontBox.addActionListener(e -> u.run());
        bold.addActionListener(e -> u.run());
        italic.addActionListener(e -> u.run());
        small.addActionListener(e -> u.run());
        medium.addActionListener(e -> u.run());
        large.addActionListener(e -> u.run());
        xlarge.addActionListener(e -> u.run());

        updateFont();

        setSize(360, 380);
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private JPanel buildSetFontPanel() {
        JPanel box = new JPanel();
        box.setLayout(new BoxLayout(box, BoxLayout.Y_AXIS));

        Color blue = new Color(70, 120, 255);
        TitledBorder tb = BorderFactory.createTitledBorder(
                BorderFactory.createLineBorder(blue),
                "SetFont",
                TitledBorder.LEFT,
                TitledBorder.TOP
        );
        box.setBorder(tb);

        // Row1: Font: + Combo
        JPanel r1 = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 4));
        fontBox.setPreferredSize(new Dimension(160, 24));
        r1.add(new JLabel("Font:"));
        r1.add(fontBox);

        // Row2: Bold + Italic
        JPanel r2 = new JPanel(new FlowLayout(FlowLayout.LEFT, 18, 0));
        r2.add(bold);
        r2.add(Box.createHorizontalStrut(60));
        r2.add(italic);

        // Row3: small/Medium/Large/Extra large
        ButtonGroup g = new ButtonGroup();
        for (JRadioButton b : new JRadioButton[]{small, medium, large, xlarge}) g.add(b);

        // margin + FlowLayout hgap
        Insets tight = new Insets(0, 0, 0, 0);
        small.setMargin(tight);
        medium.setMargin(tight);
        large.setMargin(tight);
        xlarge.setMargin(tight);

        JPanel r3 = new JPanel(new FlowLayout(FlowLayout.LEFT, 10, 0));
        r3.add(small);
        r3.add(medium);
        r3.add(large);
        r3.add(xlarge);

        box.add(r1);
        box.add(r2);
        box.add(r3);
        return box;
    }

    private void updateFont() {
        int style = (bold.isSelected() ? Font.BOLD : 0) | (italic.isSelected() ? Font.ITALIC : 0);
        int size = small.isSelected() ? 12 : medium.isSelected() ? 16 : large.isSelected() ? 20 : 24;
        ta.setFont(new Font((String) fontBox.getSelectedItem(), style, size));
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try { UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName()); }
            catch (Exception ignored) {}
            new TextDisplay();
        });
    }
}
