package event;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;
import javax.swing.*;
public class SketchTest {
	public static void main(String[] args){
		// Create GUI on the Event Dispatch Thread
		SwingUtilities.invokeLater(() -> {
			SketchFrame f = new SketchFrame();
			f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			f.setVisible(true);
		});
	}
}

class SketchFrame extends JFrame{
	public SketchFrame(){
		this.setTitle("Sketch Frame");
		// Use preferred size from panel and pack frame
		SketchPanel panel = new SketchPanel();
		this.add(panel, BorderLayout.CENTER);

		JPanel top = new JPanel();
		JButton btnSmile = new JButton("Smile");
		JButton btnBig = new JButton("BigSmile");
		top.add(btnSmile);
		top.add(btnBig);
		this.add(top, BorderLayout.NORTH);

		btnSmile.addActionListener(e -> panel.setSmileMode(SketchPanel.SMILE));
		btnBig.addActionListener(e -> panel.setSmileMode(SketchPanel.BIG_SMILE));

		this.pack();
	}
	
	public static final int DEFAULT_WIDTH = 400;
	public static final int DEFAULT_HEIGHT = 300;

}

class SketchPanel extends JPanel{
	public SketchPanel(){
		pl = new ArrayList<Line2D>();
		last = new Point2D.Double(100, 100);
		// original keyboard-driven movement
		KeyHandler h = new KeyHandler();
		this.addKeyListener(h);
		this.setFocusable(true);

		// Key bindings for Ctrl+B and Ctrl+L to switch smiles
		InputMap im = this.getInputMap(WHEN_IN_FOCUSED_WINDOW);
		ActionMap am = this.getActionMap();
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_B, KeyEvent.CTRL_DOWN_MASK), "bigSmile");
		im.put(KeyStroke.getKeyStroke(KeyEvent.VK_L, KeyEvent.CTRL_DOWN_MASK), "smile");
		am.put("bigSmile", new AbstractAction(){ public void actionPerformed(ActionEvent e){ setSmileMode(BIG_SMILE); } });
		am.put("smile", new AbstractAction(){ public void actionPerformed(ActionEvent e){ setSmileMode(SMILE); } });

		// Mouse click sets the starting point for the lines
		this.addMouseListener(new MouseAdapter(){
			@Override
			public void mousePressed(MouseEvent e){
				last = new Point2D.Double(e.getX(), e.getY());
				requestFocusInWindow();
				repaint();
			}
		});
	}
	public void paintComponent(Graphics g){
		Graphics2D g2 = (Graphics2D)g;
		super.paintComponent(g2);
		// draw stored lines
		g2.setColor(Color.BLACK);
		for(Line2D l:pl){
			g2.draw(l);
		}

		// scalable smile drawing
		int w = getWidth();
		int h = getHeight();
		int size = Math.min(w, h);
		if(smileMode != NONE){
			double cx = w / 2.0;
			double cy = h / 2.0 - size * 0.05;
			double radius = (smileMode == SMILE) ? size * 0.35 : size * 0.45;

			Ellipse2D face = new Ellipse2D.Double(cx - radius, cy - radius, radius * 2, radius * 2);
			g2.setColor(Color.YELLOW);
			g2.fill(face);
			g2.setColor(Color.BLACK);
			g2.setStroke(new BasicStroke(2f));
			g2.draw(face);

			double eyeR = radius * 0.14;
			double eyeY = cy - radius * 0.25;
			double eyeOffset = radius * 0.45;
			Ellipse2D leftEye = new Ellipse2D.Double(cx - eyeOffset - eyeR, eyeY - eyeR, eyeR * 2, eyeR * 2);
			Ellipse2D rightEye = new Ellipse2D.Double(cx + eyeOffset - eyeR, eyeY - eyeR, eyeR * 2, eyeR * 2);
			g2.fill(leftEye);
			g2.fill(rightEye);

			if(smileMode == SMILE){
				g2.setStroke(new BasicStroke(Math.max(2f, (float)(radius * 0.06))));
				Arc2D mouth = new Arc2D.Double(cx - radius * 0.6, cy - radius * 0.1, radius * 1.2, radius * 1.2, 200, 140, Arc2D.OPEN);
				g2.draw(mouth);
			} else {
				g2.setColor(Color.BLACK);
				g2.setStroke(new BasicStroke(Math.max(6f, (float)(radius * 0.25f))));
				Arc2D mouth = new Arc2D.Double(cx - radius * 0.6, cy - radius * 0.05, radius * 1.2, radius * 1.2, 200, 140, Arc2D.OPEN);
				g2.draw(mouth);
			}

			String txt = (smileMode == SMILE) ? "Smiling..." : "BigSmiling...";
			g2.setColor(Color.BLUE);
			int fontSize = Math.max(12, (int)(size * 0.06));
			g2.setFont(new Font("Serif", Font.ITALIC, fontSize));
			FontMetrics fm = g2.getFontMetrics();
			int tx = (int)(cx - fm.stringWidth(txt) / 2.0);
			int ty = (int)(cy + radius + fm.getAscent() + 10);
			g2.drawString(txt, tx, ty);
		}
	
	}
	public void add(int dx, int dy){
		
		Point2D end = new Point2D.Double(last.getX()+dx,last.getY()+dy);
		Line2D l = new Line2D.Double(last,end);
		pl.add(l);
		
		last = end;
		
		repaint();		
	}
	
	private class KeyHandler implements KeyListener{
	 public void keyPressed(KeyEvent ke){
		 System.out.println("key pressed");
		 int c = ke.getKeyCode();
		 int d;
		 if(ke.isShiftDown())
			 d = LARGE_INCRE;
		 else
			 d = SMALL_INCRE;
		 
		 if(c == KeyEvent.VK_LEFT) add(-d,0);
		 else if(c == KeyEvent.VK_RIGHT) add(d,0);
		 else if(c == KeyEvent.VK_UP)	add(0,-d);
		 else if(c == KeyEvent.VK_DOWN) add(0,d);
	 }
	 
	 public void keyReleased(KeyEvent ke){
		 System.out.println("key released");
		 
	 }
	 public void keyTyped(KeyEvent ke){
		 System.out.println("key typed");
		 char ch = ke.getKeyChar();
		 int d;
		 if(Character.isUpperCase(ch)){
			 d = LARGE_INCRE;
			 ch = Character.toLowerCase(ch);
		 }
		 else
			 d = SMALL_INCRE;
		 
		 if( ch == 'h') add(-d,0);
		 else if (ch == 'l') add(d,0);
		 else if(ch == 'k')  add(0,-d);
		 else if(ch == 'j')  add(0,d);
		 
	 }
	}
	
	public static final int SMALL_INCRE = 1;
	public static final int LARGE_INCRE = 5;
	private ArrayList<Line2D> pl;
	private Point2D last;
	// smile drawing modes
	public static final int NONE = 0;
	public static final int SMILE = 1;
	public static final int BIG_SMILE = 2;
	private int smileMode = SMILE;

	public void setSmileMode(int mode){
		this.smileMode = mode;
		repaint();
	}
}
