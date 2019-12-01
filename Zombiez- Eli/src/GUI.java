import java.awt.Color;
import java.awt.Font;
import javax.swing.JButton;
import javax.swing.JLabel;
//Jbutton without the huge list of parameters
//parameters compressed into one super long constructor

public class GUI extends JButton {
	// enums to define alignment of text
	public enum XAlignment {
		left, right, center
	};

	public enum YAlignment {
		top, bottom, center
	};

	// constructor
	public GUI(int x, int y, int width, int height, String s, XAlignment xA,
			YAlignment yA) {
		super(s);
		this.setBounds(x, y, width, height);
		this.setFGColor(Game.color1);
		this.setBGColor(Game.color2);
		this.setFont(new Font("ARIAL", Font.PLAIN, Game.bFont));
		this.setOpaque(true);
		this.setFocusPainted(false);
		this.setFocusable(false);
		switch (xA) {
		case left:
			this.setHorizontalAlignment(JLabel.LEFT);
			break;
		case center:
			this.setHorizontalAlignment(JLabel.CENTER);
			break;
		case right:
			this.setHorizontalAlignment(JLabel.RIGHT);
			break;
		default:
			this.setHorizontalAlignment(JLabel.CENTER);

		}
		switch (yA) {
		case top:
			this.setVerticalAlignment(JLabel.TOP);
			break;
		case center:
			this.setVerticalAlignment(JLabel.CENTER);
			break;
		case bottom:
			this.setVerticalAlignment(JLabel.BOTTOM);
			break;
		default:
			this.setVerticalAlignment(JLabel.CENTER);

		}

	}

	// methods to change specific colors of the object
	public void setBGColor(Color color) {
		this.setBackground(color);
	}

	public void setFGColor(Color color) {
		this.setForeground(color);
	}

}
