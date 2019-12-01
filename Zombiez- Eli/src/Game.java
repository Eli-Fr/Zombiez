import java.awt.Color;
import java.awt.Dimension;
import java.awt.MouseInfo;
import java.awt.PointerInfo;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.Timer;

//changes to this class: 
// added removeAll method to empty gameObject array
//added important game variables that seem to make sense ( Game.width makes more sense than console.width)
public abstract class Game extends JFrame {
	private ArrayList<GameObject> objectList = new ArrayList<GameObject>();
	private Timer ticker;
	public static Dimension screenSize = Toolkit.getDefaultToolkit()
			.getScreenSize();
	public static PointerInfo a = MouseInfo.getPointerInfo();
	public static int cursorX = a.getLocation().x;
	public static int cursorY = a.getLocation().y;
	public static int height = screenSize.height;
	public static int width = screenSize.width;
	public static int centerX = screenSize.width / 2;
	public static int centerY = screenSize.height / 2;
	public static int sFont = height / 80;// small font
	public static int bFont = height / 40;// big font
	public static int SSH = height / 20; // standard Square Height
	public static int SSW = width / 20;// standard Square Width
	// use for text color
	public static Color color1 = new Color(90, 10, 50);
	// use for buttons
	public static Color color2 = new Color(90, 110, 50);
	// Background of menu
	public static Color color3 = new Color(220, 50, 50);
	// use for Background of game
	public static Color color4 = new Color(30, 120, 180);

	// enum data types that simplify alot of the boolean issues
	// (boolean 'overflow')
	protected enum gameState {
		uninitiated, paused, unpaused, finished
	};

	protected enum buttonState {
		uninitiated, standard, startP, quitP, notVis, changingButtons
	};

	protected enum inGameState {
		uninitiated, roundStart, roundEnd, normalWave, specialWave
	};

	protected enum gunType {
		pistol, semiAutomatic, Sniper, hTMG, lTMG, ulti
	}

	protected enum buttonChangingState {
		notChanging, left, right, up, down, buy
	}

	public abstract void setup();

	public abstract void act();

	public void initComponents() {
		getContentPane().setBackground(Color.black);
		setup();
		for (int i = 0; i < objectList.size(); i++) {
			GameObject o = (GameObject) objectList.get(i);
			o.repaint();
		}
		ticker.start();
	}

	public void add(GameObject o) {
		objectList.add(o);
		getContentPane().add(o);
	}

	public void remove(GameObject o) {
		o.setVisible(false);
		objectList.remove(o);
		getContentPane().remove(o);

	}

	public void removeAll() {
		for (int i = 0; i < objectList.size(); i++) {
			GameObject o = objectList.get(i);
			o.setVisible(false);
			objectList.remove(o);
			getContentPane().remove(o);
		}
	}

	public void setDelay(int delay) {
		ticker.setDelay(delay);
	}

	public void setBackground(Color c) {
		getContentPane().setBackground(c);
	}

	public Game() {
		setSize(400, 600);
		setUndecorated(true);
		getContentPane().setLayout(null);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		ticker = new Timer(10, new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				act();
				for (int i = 0; i < objectList.size(); i++) {
					GameObject o = (GameObject) objectList.get(i);
					o.act();
				}
			}
		});
	}

	public void startGame() {
		ticker.start();
	}

	public void stopGame() {
		ticker.stop();
	}

	public int getFieldWidth() {
		return getContentPane().getBounds().width;
	}

	public int getFieldHeight() {
		return getContentPane().getBounds().height;
	}

	class WinDialog extends JDialog {
		JButton ok = new JButton("OK");

		WinDialog(JFrame owner, String title) {
			super(owner, title);
			Rectangle r = owner.getBounds();
			setSize(200, 100);
			setLocation(r.x + r.width / 2 - 100, r.y + r.height / 2 - 50);
			getContentPane().add(ok);
			ok.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent e) {
					WinDialog.this.setVisible(false);
				}
			});
		}
	}
}