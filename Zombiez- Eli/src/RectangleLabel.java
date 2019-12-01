import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JLabel;

//simply jlabel with a condensed constructor
public class RectangleLabel extends JLabel {
	// constructor
	public RectangleLabel(int x, int y, int width, int height, Color color) {
		this.setBounds(x, y, width, height);
		this.setBGColor(color);
		this.setOpaque(true);
		this.setFocusable(false);
	}

	// change colors
	public void setBGColor(Color color) {
		this.setBackground(color);
	}

	public void setImage(ImageIcon icon) {
		this.setIcon(icon);
	}

}
