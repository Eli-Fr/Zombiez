import java.awt.Color;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import javax.swing.JComponent;

//no changes
public abstract class GameObject extends JComponent {
	private Color c = Color.white;

	public void setSize(int width, int height) {
		super.setSize(width, height);
	}

	public int getX() {
		return getLocation().x;
	}

	public int getY() {
		return getLocation().y;
	}

	public void setX(int x) {
		super.setLocation(x, getLocation().y);
	}

	public void setY(int y) {
		super.setLocation(getLocation().x, y);
	}

	public void setColor(Color c) {
		this.c = c;
	}

	public void paint(Graphics g) {
		Rectangle r = getBounds();
		g.setColor(c);
		g.fillRect(0, 0, (int) r.getWidth(), (int) r.getHeight());
	}

	public boolean collides(GameObject o) {
		return getBounds().intersects(o.getBounds());
	}

	public abstract void act();
}
