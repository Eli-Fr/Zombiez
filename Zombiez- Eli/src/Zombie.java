import java.awt.Color;

public class Zombie extends GameObject {
	// self explanatory fields
	private double xDis, yDis;
	private double xPos, yPos;
	private double directDis;
	private int sH = 10;
	private int health;
	private int speed;
	private int originalSpeed;
	private int damage;
	private boolean timing;
	private int timer;

	// constructor
	// zombies are constructed randomly so game has a random personality to it
	public Zombie(byte spawn, byte type) {
		// type of zombie
		// fast low health, medium, medium health, slow high health
		// boss(1 in every 5 rounds 1 will spawn from each spawner
		switch (type) {
		case 1:
		case 2:
		case 3:
		case 4: // medium
		case 5:
			this.speed = 6;
			this.setHealth(2 * sH);
			this.setDamage(21);
			this.setSize(3 * Game.SSH / 4, 3 * Game.SSH / 4);
			break;
		case 6:
		case 7:// fast
		case 8:
			this.speed = 10;
			this.setHealth(sH);
			this.setDamage(34);
			this.setSize(5 * Game.SSH / 3, 5 * Game.SSH / 3);
			break;
		case 9:// slow
		case 10:
			this.speed = 4;
			this.setHealth(3 * sH);
			this.setDamage(51);
			this.setSize(Game.SSH, Game.SSH);
			break;

		case 11:// boss
			this.speed = 4;
			this.setHealth(5 * sH);
			this.setDamage(99);
			this.setSize(3 * Game.SSH / 2, 3 * Game.SSH / 2);
			break;
		}
		// location of spawn
		switch (spawn) {
		case 1:
			this.setX(0);
			this.setY(0);
			break;
		case 2:
			this.setX(Game.width - this.getWidth());
			this.setY(0);
			break;
		case 3:
			this.setX(0);
			this.setY(Game.height - this.getHeight());
			break;
		case 4:
			this.setX(Game.width - this.getWidth());
			this.setY(Game.height - this.getHeight());
			break;
		}
		this.originalSpeed = this.speed;
		this.setColor(Color.red);

	}

	// called every tick
	public void act() {
		if (Console.gS == Console.gameState.unpaused) {
			timer();
			path();
			checkCollision();

		}
	}

	// auto pathing for the ZOMBIE
	// pathing simply constructs a similar triangle of hypotenus 1 and
	// multiplies the x and y displacement by speed to achieve a direct
	// displacement of distance: speed
	private void path() {
		// sets the distances from zombie to mainC to be from the center of both
		// the objects
		this.xDis = (Console.mainC.getX() + Console.mainC.getWidth() / 2)
				- (this.getX() + this.getWidth() / 2);
		this.yDis = (Console.mainC.getY() + Console.mainC.getHeight() / 2)
				- (this.getY() + this.getHeight() / 2);
		// using Pythagorean theorem, calculates the exact decimal distance from
		// both objects
		this.directDis = Math.sqrt(Math.pow(xDis, 2) + Math.pow(yDis, 2));

		if (this.collides(Console.mainC)) {
			if (timing) {
				moveAway(speed);
			} else {
				moveAway(5);
				slowDown();
				Console.mainC
						.setHealth(Console.mainC.getHealth() - getDamage());
			}

		} else {
			this.xPos = this.getX() + ((xDis / directDis) * speed);
			this.setX((int) xPos);
			this.yPos = this.getY() + ((yDis / directDis) * speed);
			this.setY((int) yPos);
		}
	}

	// timer that is activated based on game conditions
	private void timer() {
		if (timing) {
			timer++;
			if (timer == 200) {
				this.speed = this.originalSpeed;
				timing = false;
			}
		} else {
		}

	}

	// moves away from pathed to object
	// does the opposite of path, displace distance * speed units away from the
	// main c
	public void moveAway(int distance) {
		this.xPos = this.getX() - (xDis / directDis) * distance * speed;
		this.setX((int) xPos);
		this.yPos = this.getY() - (yDis / directDis) * distance * speed;
		this.setY((int) yPos);
	}

	// starts timer, timer ends automatically
	private void startTimer() {
		this.timing = true;
		this.timer = 0;
	}

	// slows down the zombie for an appropriate time period
	private void slowDown() {
		this.speed /= 2;
		startTimer();

	}

	// checks collision with zombies and also main character, based on what it
	// is, it will react accordingly
	private void checkCollision() {
		if (this.getX() < 0) {
			this.setX(0);
		} else if (this.getX() > Game.width - this.getWidth()) {
			this.setX(Game.width - this.getWidth());
		}
		if (this.getY() < 0) {
			this.setY(0);
		} else if (this.getY() > Game.height - this.getHeight()) {
			this.setY(Game.height - this.getHeight());
		}
		for (int i = 0; i <= Console.zombieList.size() - 1; i++) {
			Zombie a = Console.zombieList.get(i);
			if (this.collides(a) && !this.equals(a)) {
				if (this.directDis < a.directDis) {
				} else if (this.getHeight() > a.getHeight()) {
				} else {
					this.xPos = this.getX() - (xDis / directDis) * 2 * speed;
					this.setX((int) xPos);
					this.yPos = this.getY() - (yDis / directDis) * 2 * speed;
					this.setY((int) yPos);
				}
			}
		}

	}

	public int getHealth() {
		return health;
	}

	public void setHealth(int health) {
		this.health = health;
	}

	public int getDamage() {
		return damage;
	}

	public void setDamage(int damage) {
		this.damage = damage;
	}

}
