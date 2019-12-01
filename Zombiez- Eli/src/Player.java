import java.awt.Color;

import javax.swing.JComponent;

public class Player extends GameObject {
	// self explanatory fields
	private int speed = 5;
	private int health = 100;

	// constructor
	public Player(int width, int height) {
		this.setSize(width, height);
		this.setColor(Color.GREEN);
		this.setX(Game.centerX - width / 2);
		this.setY(Game.centerY - height / 2);
	}

	// acts method, called every tick
	public void act() {
		if (Console.gS == Console.gameState.unpaused) {
			movementListener();
		}
	}

	// listens for movement, reacts to boundary collision
	private void movementListener() {
		if (Console.moveUp) {
			moveUp();
		}
		if (Console.moveDown) {
			moveDown();
		}
		if (Console.moveLeft) {
			moveLeft();
		}
		if (Console.moveRight) {
			moveRight();
		}
		if (this.getX() < 2 * Game.SSH) {
			this.setX(2 * Game.SSH);
		} else if (this.getX() > Game.width - this.getWidth() - 2 * Game.SSH) {
			this.setX(Game.width - this.getWidth() - 2 * Game.SSH);
		}
		if (this.getY() < 2 * Game.SSH) {
			this.setY(2 * Game.SSH);
		} else if (this.getY() > Game.height - this.getHeight() - 2 * Game.SSH) {
			this.setY(Game.height - this.getHeight() - 2 * Game.SSH);
		}
	}

	// movement
	private void moveUp() {
		this.setY(this.getY() - this.speed);
	}

	private void moveDown() {
		this.setY(this.getY() + this.speed);
	}

	private void moveLeft() {
		this.setX(this.getX() - this.speed);
	}

	private void moveRight() {
		this.setX(this.getX() + this.speed);
	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

}
