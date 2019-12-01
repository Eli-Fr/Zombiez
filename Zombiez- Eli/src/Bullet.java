public class Bullet extends GameObject {
	// private fields for game functionality
	private int damage;
	private int type;
	// x and y displacement per tick
	private double xDis, yDis;
	private double xPos, yPos;
	private double xDif, yDif;

	// bullet constructor, type is given in the console
	public Bullet(int type) {
		this.xPos = Console.mainC.getX() + Console.mainC.getWidth() / 2;
		this.setX((int) xPos);
		this.yPos = (Console.mainC.getY() + Console.mainC.getHeight() / 2);
		this.setY((int) yPos);
		this.xDif = ((Game.cursorX) - (this.getX() + this.getWidth() / 2));
		this.yDif = ((Game.cursorY) - (this.getY() + this.getHeight() / 2));
		double directDis = Math.sqrt(Math.pow(xDif, 2) + Math.pow(yDif, 2));
		this.xDis = (((Game.cursorX) - (this.getX() + this.getWidth() / 2)) / directDis) * 20;
		this.yDis = (((Game.cursorY) - (this.getY() + this.getHeight() / 2)) / directDis) * 20;
		this.type = type;
		switch (type) {
		case 1:// pistol
			this.setSize(5, 5);
			this.damage = 20;
			break;
		case 2:// semi auto( larger bullet )
			this.setSize(10, 10);
			this.damage = 20;
			break;
		case 3:// sniper
			this.setSize(10, 10);
			this.damage = 1000;
			break;
		case 4:// worst machine gun
			this.setSize(5, 5);
			this.damage = 10;
			break;
		case 5:// better machine gun
			this.setSize(7, 7);
			this.damage = 10;
			break;
		case 6:// mega ultimate weapon(special weapon)
				// activated by right click
			this.setSize(20, 20);
			this.damage = 1000;
			break;
		}
	}

	// note that fire does not generate the bullet but rather is used to follow
	// the FIRE-ing path
	public void act() {
		if (Console.gS == Console.gameState.unpaused) {
			fire();
			checkCollision();

		}
	}

	// gets called every turn since this needs to be initiated to move
	private void fire() {
		this.xPos += xDis;
		this.setX((int) xPos);
		this.yPos += yDis;
		this.setY((int) yPos);

	}

	// deletes this from game by looping through the console bulletlIst
	private void removeFromGame(GameObject o) {
		for (int i = 0; i <= Console.bulletList.size() - 1; i++) {
			if (o.equals(Console.bulletList.get(i))) {
				Console.bulletList.remove(i);
			}
		}
	}

	// checks if it has collided with zombie, using the arraylist in console
	private void checkCollision() {
		if (this.getX() < 0 || this.getX() > Game.width || this.getY() < 0
				|| this.getY() > Game.height) {
			removeFromGame(this);
		}
		for (int i = 0; i <= Console.zombieList.size() - 1; i++) {
			Zombie a = Console.zombieList.get(i);
			if (this.collides(Console.zombieList.get(i))) {
				a.setHealth(a.getHealth() - damage);
				switch (type) {
				case 1:
					this.damage = 0;
					this.setVisible(false);
					removeFromGame(this);
					break;
				case 2:
					this.damage = 0;
					this.setVisible(false);
					removeFromGame(this);
					break;
				case 3:

					break;
				case 4:
					this.setVisible(false);
					removeFromGame(this);
					break;
				case 5:
					this.setVisible(false);
					removeFromGame(this);
					break;
				case 6:
					a.moveAway(15);
					break;
				}
			}
		}
	}

}
