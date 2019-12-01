import java.awt.Color;
import java.awt.Font;
import javax.swing.JLabel;

public class HUD extends JLabel {
	// Changes the direction in which the bar scales
	public enum directionalScale {
		horizontal, vertical, noScale
	};

	// fields that help functionality of the objects within this class
	private int maxV;
	private directionalScale dScale;
	private int originalWidth, originalHeight;

	// constructor
	public HUD(int x, int y, int width, int height, String s, int maxV,
			directionalScale dS, Color bG) {
		this.dScale = dS;
		this.maxV = maxV;
		this.originalHeight = height;
		this.originalWidth = width;
		this.setBounds(x, y, width, height);
		this.setFocusable(false);
		this.setText(s);
		this.setFont(new Font("ARIAL", Font.PLAIN, Game.bFont));
		this.setForeground(Color.BLACK);
		this.setBackground(bG);
		this.setOpaque(true);
		this.setHorizontalAlignment(JLabel.CENTER);
	}

	// called everytime the value gets updated
	public void act(int value) {

		switch (dScale) {
		case horizontal:
			double scale = (double) ((0.0 + value) / maxV);
			int hOffset = (int) (originalWidth * scale);
			this.setBounds(getX() + (getWidth() - hOffset) / 2, getY(),
					hOffset, getHeight());
			break;
		case vertical:
			double scale1 = (double) (value / maxV);
			int vOffset = (int) (originalHeight * scale1);
			this.setBounds(getX(), getY() + (getHeight() - vOffset) / 2,
					getWidth(), vOffset);
			break;
		case noScale:
			break;
		}
	}
}
