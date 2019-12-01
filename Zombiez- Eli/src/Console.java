import java.awt.Color;
import java.awt.Font;
import java.awt.MouseInfo;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.Icon;
import javax.swing.JLabel;

public class Console extends Game {
	// instantiates a version of the game
	public static Console S;
	// main character
	public static Player mainC;
	// instantiates enum objects that represent each type of state
	public static gameState gS = gameState.uninitiated;
	private static buttonState bS = buttonState.uninitiated;
	private static buttonChangingState bCS = buttonChangingState.notChanging;
	private static inGameState iGS = inGameState.uninitiated;
	private static gunType gT = gunType.pistol;
	// event listener booleans
	public static boolean moveUp = false;
	public static boolean moveDown = false;
	public static boolean moveRight = false;
	public static boolean moveLeft = false;
	public static boolean leftClicked = false;
	public static boolean rightClicked = false;
	// keys corresponding to game functionality
	private static char nextPressed;
	private static char upKey = 'W';
	private static char downKey = 'S';
	private static char leftKey = 'A';
	private static char rightKey = 'D';
	private static char readyUp = 'R';
	private static char pause = 27;
	// array lists for important functionality
	public static ArrayList<Zombie> zombieList = new ArrayList<Zombie>();
	public static ArrayList<Bullet> bulletList = new ArrayList<Bullet>();
	public static ArrayList<Character> lettersTyped = new ArrayList<Character>();
	// boolean control values; self explanatory
	private static boolean menuVisible = true;
	private boolean constructedHUD = false;
	private boolean mainCAdded = false;
	private String ammoText;
	// game tracking values
	public static int kills = 0;// ammount of kill that game
	// amount of each ammo Type
	private static int ammo1 = 0;// semi auto
	private static int ammo2 = 0;// sniper
	private static int ammo3 = 0;// bad mg ( low tier mg)
	private static int ammo4 = 0;// good mg ( high tier mg)
	private static int ammo5 = 0;// super wep
	private static int wave = 1;// wave
	private static int bTimer = 0;// timer between each bullet
	private static int waveControl = 0;// control variable for spawning
	private static boolean specialWaveSpawned = false;// control boolean for
														// special waves
	// easily accessible strings corresponding to their respective labels
	private static String hTPTEXT = "PRESS R FOR NEXT ROUND, USE 1-6 TO CHANGE WEAPONS";
	private static String creditText = "my name is Elie, and I made dis";
	private static String settingText = "(unimplemented) change your controls";
	private static String left = " Left: " + leftKey;
	private static String right = " Right: " + rightKey;
	private static String up = " Up: " + upKey;
	private static String down = " Down: " + downKey;
	// statically visible
	private static GUI startButton, settings, hTP, credits, exit;
	// dynamically visible buttons
	private static GUI playPromptYes, playPromptNo;
	// dynamic buttons
	private static GUI leftButton, rightButton, upButton, downButton;
	// dynamically visible labels
	private static RectangleLabel hTPMENU, settingsMenu, creditsMenu, homeMenu,
			yesNoMenu, background;
	// ingame objects that improve the asthetic of the game
	private static RectangleLabel zSpawn1, zSpawn2, zSpawn3, zSpawn4;
	private static HUD healthDis, ammoDis;
	// end of game confirmation bubble
	private static GUI okButton;
	private static RectangleLabel lostMenu;
	// background of main menu
	private static Icon MAINICON = null;// icon

	// constructs and adds all objects
	// note: this method only calls other methods and is exclusively for
	// explicit game design
	private void constructAllItems() {
		constructMenuObjects();
		constructKeyListener();
		constructMouseListener();
		addMenuObjects();
	}

	// constructs GUI objects
	private void constructGUI() {
		startButton = new GUI(0, 0, width / 4, height / 15, "PLAY",
				GUI.XAlignment.right, GUI.YAlignment.bottom);

		settings = new GUI(width / 2, 0, width / 4, height / 15, "SETTINGS",
				GUI.XAlignment.center, GUI.YAlignment.bottom);

		hTP = new GUI(width / 4, 0, width / 4, height / 15, "HOW TO PLAY",
				GUI.XAlignment.center, GUI.YAlignment.bottom);

		credits = new GUI(3 * width / 4, 0, width / 8, height / 15, "CREDITS",
				GUI.XAlignment.center, GUI.YAlignment.bottom);

		exit = new GUI(7 * width / 8, 0, width / 8, height / 15, "QUIT",
				GUI.XAlignment.left, GUI.YAlignment.bottom);

		// dynamically visible
		playPromptYes = new GUI(width / 4, height / 2, width / 4, height / 8,
				"YES", GUI.XAlignment.center, GUI.YAlignment.center);
		playPromptYes.setVisible(false);

		playPromptNo = new GUI(width / 2, height / 2, width / 4, height / 8,
				"NO", GUI.XAlignment.center, GUI.YAlignment.center);
		playPromptNo.setVisible(false);
		// dynamically visible labels

		background = new RectangleLabel(0, 0, width, height, color3);

		// dynamic components
		yesNoMenu = new RectangleLabel(width / 4, 3 * height / 8, width / 2,
				height / 8, color1);
		yesNoMenu.setText("Are You Sure");
		yesNoMenu.setBackground(color2);
		yesNoMenu.setFont(new Font("ARIAL", Font.PLAIN, bFont));
		yesNoMenu.setHorizontalAlignment(JLabel.CENTER);
		yesNoMenu.setVerticalAlignment(JLabel.CENTER);
		yesNoMenu.setVisible(false);

		hTPMENU = new RectangleLabel(width / 2 - width / 4, height / 15,
				width / 2, height - height / 15, color1);
		hTPMENU.setText(hTPTEXT);
		hTPMENU.setBackground(color2);
		hTPMENU.setFont(new Font("ARIAL", Font.PLAIN, bFont));
		hTPMENU.setHorizontalAlignment(JLabel.LEFT);
		hTPMENU.setVerticalAlignment(JLabel.TOP);
		hTPMENU.setVisible(false);

		creditsMenu = new RectangleLabel(width / 2 - width / 4, height / 15,
				width / 2, height - height / 15, color1);
		creditsMenu.setText(creditText);
		creditsMenu.setBackground(color2);
		creditsMenu.setFont(new Font("ARIAL", Font.PLAIN, bFont));
		creditsMenu.setHorizontalAlignment(JLabel.LEFT);
		creditsMenu.setVerticalAlignment(JLabel.TOP);
		creditsMenu.setVisible(false);

		settingsMenu = new RectangleLabel(width / 2 - width / 4, height / 15,
				width / 2, height - height / 15, color1);
		settingsMenu.setText(settingText);
		settingsMenu.setBackground(color2);
		settingsMenu.setFont(new Font("ARIAL", Font.PLAIN, bFont));
		settingsMenu.setHorizontalAlignment(JLabel.CENTER);
		settingsMenu.setVerticalAlignment(JLabel.TOP);
		settingsMenu.setVisible(false);
		homeMenu = new RectangleLabel(width, height / 15, width, height
				- height / 15, color1);
		homeMenu.setIcon(MAINICON);

		homeMenu.setBackground(color2);
		homeMenu.setHorizontalAlignment(JLabel.CENTER);
		homeMenu.setVerticalAlignment(JLabel.CENTER);
		homeMenu.setVisible(false);
	}

	// constructs buttons that will be visible dynamically
	private void constructDynamicButtons() {
		leftButton = new GUI(width / 2 - 3 * width / 16, height / 2 - height
				/ 8, width / 8, height / 8, left, GUI.XAlignment.center,
				GUI.YAlignment.center);

		rightButton = new GUI(width / 2 + width / 16, height / 2 - height / 8,
				width / 8, height / 8, right, GUI.XAlignment.center,
				GUI.YAlignment.center);

		upButton = new GUI(width / 2 - width / 16, height / 2 - height / 4,
				width / 8, height / 8, up, GUI.XAlignment.center,
				GUI.YAlignment.center);

		downButton = new GUI(width / 2 - width / 16, height / 2 - height / 8,
				width / 8, height / 8, down, GUI.XAlignment.center,
				GUI.YAlignment.center);
		upButton.setBGColor(color4);
		downButton.setBGColor(color4);
		rightButton.setBGColor(color4);
		leftButton.setBGColor(color4);

		upButton.setVisible(false);
		downButton.setVisible(false);
		rightButton.setVisible(false);
		leftButton.setVisible(false);
	}

	// constructs menuObjects
	// note, this is a wrapper method used for game explicitness
	private void constructMenuObjects() {
		constructDynamicButtons();
		constructGUI();
	}

	// adds menuObjects
	private void addMenuObjects() {
		add(startButton);
		add(hTP);
		add(settings);
		add(credits);
		add(exit);
		add(playPromptYes);
		add(playPromptNo);
		add(yesNoMenu);
		add(hTPMENU);
		add(creditsMenu);
		add(upButton);
		add(downButton);
		add(rightButton);
		add(leftButton);
		add(settingsMenu);
		add(homeMenu);
		// bottom layer
		add(background);
	}

	// self explanatory
	public void constructKeyListener() {
		this.addKeyListener(new KeyListener() {

			public void keyTyped(KeyEvent e) {
			}

			public void keyPressed(KeyEvent e) {
				char pressed = Character.toUpperCase(e.getKeyChar());
				nextPressed = pressed;
				if (pressed == Character.toUpperCase(upKey)) {
					moveUp = true;
				}
				if (pressed == Character.toUpperCase(downKey)) {
					moveDown = true;
				}
				if (pressed == Character.toUpperCase(leftKey)) {
					moveLeft = true;
				}
				if (pressed == Character.toUpperCase(rightKey)) {
					moveRight = true;
				}
				if (pressed == Character.toUpperCase(readyUp)) {
					if (iGS == inGameState.roundEnd) {
						// randomly gives ammo to user
						int a = (int) (Math.random() * 20);
						switch (a) {
						case 0:
						case 1:
						case 2:
						case 3:
						case 4:
						case 5:
							ammo1 += 25;
							break;
						case 6:
						case 7:
						case 8:
							ammo2 += 5;
							break;
						case 9:
						case 10:
						case 11:
						case 12:
							ammo3 += 50;
							break;
						case 13:
						case 14:
						case 15:
						case 16:
						case 17:
						case 18:
							ammo4 += 50;
							break;
						case 19:
							ammo1 += 100;
							ammo2 += 10;
							ammo3 += 100;
							ammo4 += 100;
							ammo5 += 5;
							break;
						}
						ammoText = ("DEFAULT SEMI AUTOMATIC: " + ammo1
								+ " SNIPER: " + ammo2 + " BADMG: " + ammo3
								+ " GOODMG: " + ammo4 + " SUPER WEAPON: " + ammo5);
						ammoText = "<html><center>AMMO</center><br/>"
								+ ammoText + "</html>";
						ammoDis.setText(ammoText);
						iGS = inGameState.roundStart;
					}
				}
				// change weapons
				if (gS == gameState.unpaused) {
					switch (pressed) {
					case '1':
						ammoText = ("<b>DEFAULT</b> " + "SEMI AUTOMATIC: "
								+ ammo1 + " SNIPER: " + ammo2 + " BADMG: "
								+ ammo3 + " GOODMG: " + ammo4
								+ " SUPER WEAPON: " + ammo5);
						ammoText = "<html><center>AMMO</center><br/>"
								+ ammoText + "</html>";
						ammoDis.setText(ammoText);
						gT = gunType.pistol;
						break;
					case '2':
						if (ammo1 <= 0) {
						} else {
							ammoText = ("DEFAULT " + "<b>SEMI AUTOMATIC: "
									+ ammo1 + "</b> SNIPER: " + ammo2
									+ " BADMG: " + ammo3 + " GOODMG: " + ammo4
									+ " SUPER WEAPON: " + ammo5);
							ammoText = "<html><center>AMMO</center><br/>"
									+ ammoText + "</html>";
							ammoDis.setText(ammoText);
							gT = gunType.semiAutomatic;
						}
						break;
					case '3':
						if (ammo2 <= 0) {
						} else {
							ammoText = ("DEFAULT " + "SEMI AUTOMATIC: " + ammo1
									+ " <b>SNIPER: " + ammo2 + "</b> BADMG: "
									+ ammo3 + " GOODMG: " + ammo4
									+ " SUPER WEAPON: " + ammo5);
							ammoText = "<html><center>AMMO</center><br/>"
									+ ammoText + "</html>";
							ammoDis.setText(ammoText);
							gT = gunType.Sniper;
						}
						break;
					case '4':
						if (ammo3 <= 0) {
						} else {
							ammoText = ("DEFAULT " + "SEMI AUTOMATIC: " + ammo1
									+ " SNIPER: " + ammo2 + "<b> BADMG: "
									+ ammo3 + " </b>GOODMG: " + ammo4
									+ " SUPER WEAPON: " + ammo5);
							ammoText = "<html><center>AMMO</center><br/>"
									+ ammoText + "</html>";
							ammoDis.setText(ammoText);
							gT = gunType.lTMG;
						}
						break;
					case '5':
						if (ammo4 <= 0) {
						} else {
							ammoText = ("DEFAULT " + "SEMI AUTOMATIC: " + ammo1
									+ " SNIPER: " + ammo2 + " BADMG: " + ammo3
									+ " <b>GOODMG: " + ammo4
									+ " </b>SUPER WEAPON: " + ammo5);
							ammoText = "<html><center>AMMO</center><br/>"
									+ ammoText + "</html>";
							ammoDis.setText(ammoText);
							gT = gunType.hTMG;
						}
						break;
					case '6':
						if (ammo5 <= 0) {
						} else {
							ammoText = ("DEFAULT " + "SEMI AUTOMATIC: " + ammo1
									+ " SNIPER: " + ammo2 + " BADMG: " + ammo3
									+ " GOODMG: " + ammo4
									+ " <b>SUPER WEAPON: " + ammo5 + "</b>");
							ammoText = "<html><center>AMMO</center><br/>"
									+ ammoText + "</html>";
							ammoDis.setText(ammoText);
							gT = gunType.ulti;
						}
						break;
					}
				}// response to esc key pressed
				if ((int) pressed == (int) pause) {
					switch (gS) {
					case uninitiated:
						bS = buttonState.quitP;
						prompt();
						break;
					case paused:

						break;
					case unpaused:
						gS = gameState.paused;
						bS = buttonState.standard;
						break;
					case finished:
						break;
					}
				}

			}

			public void keyReleased(KeyEvent e) {
				char released = Character.toUpperCase(e.getKeyChar());
				if (released == Character.toUpperCase(upKey)) {
					moveUp = false;
				}
				if (released == Character.toUpperCase(downKey)) {
					moveDown = false;
				}
				if (released == Character.toUpperCase(leftKey)) {
					moveLeft = false;
				}
				if (released == Character.toUpperCase(rightKey)) {
					moveRight = false;
				}
			}
		});

	}

	// self explanatory
	public void constructMouseListener() {
		this.addMouseListener(new MouseListener() {

			public void mouseClicked(MouseEvent e) {

			}

			public void mouseEntered(MouseEvent e) {

			}

			public void mouseExited(MouseEvent e) {

			}

			public void mousePressed(MouseEvent e) {
				int pressed = e.getButton();
				switch (pressed) {
				case 1:
					leftClicked = true;
					break;
				case 2:
					break;
				case 3:
					rightClicked = true;
					break;
				}

			}

			public void mouseReleased(MouseEvent e) {
				int released = e.getButton();
				switch (released) {
				case 1:
					leftClicked = false;
					break;
				case 2:
					break;
				case 3:
					rightClicked = false;
					break;
				}

			}
		});

	}

	// constructs GameObjects
	private void constructGameObjects() {
		// Construct Character
		mainC = new Player(SSH, SSH);
		add(mainC);
		mainCAdded = true;
		// constructs and adds spawners (note spawners do not handle zombie
		// spawning, they are asthetic
		zSpawn1 = new RectangleLabel(0, 0, SSH * 2, SSH * 2, Color.YELLOW);
		zSpawn1.setVisible(true);
		zSpawn2 = new RectangleLabel(0, height - SSH * 2, SSH * 2, SSH * 2,
				Color.YELLOW);
		zSpawn2.setVisible(true);
		zSpawn3 = new RectangleLabel(width - SSH * 2, 0, SSH * 2, SSH * 2,
				Color.YELLOW);
		zSpawn3.setVisible(true);
		zSpawn4 = new RectangleLabel(width - SSH * 2, height - SSH * 2,
				SSH * 2, SSH * 2, Color.YELLOW);
		zSpawn4.setVisible(true);
		add(zSpawn1);
		add(zSpawn2);
		add(zSpawn3);
		add(zSpawn4);

	}

	// executes the game, handles all aspects of the games functionality
	private void game() {
		updateHUD();
		checkHealth();
		// in game state that regulates when and where to spawn zombies and give
		// ammo
		switch (iGS) {
		case uninitiated:
			constructGameObjects();
			iGS = inGameState.roundStart;
			break;
		case roundStart:

			shootBullet();
			if (wave % 3 == 0) {
				iGS = inGameState.specialWave;
			} else {
				iGS = inGameState.normalWave;
			}
			break;
		case normalWave:
			shootBullet();
			if (waveControl < wave) {
				byte a = (byte) (1.0 + (Math.random() * 4));
				byte b = (byte) (1.0 + (Math.random() * 10));
				zombieList.add(new Zombie(a, b));
				this.add(zombieList.get(zombieList.size() - 1));
				waveControl++;
			}
			if (zombieList.isEmpty()) {
				iGS = inGameState.roundEnd;
				waveControl = 0;
				wave++;
			}
			break;
		case specialWave:
			shootBullet();
			if (!specialWaveSpawned) {
				zombieList.add(new Zombie((byte) 1, (byte) 11));
				this.add(zombieList.get(zombieList.size() - 1));
				zombieList.add(new Zombie((byte) 2, (byte) 11));
				this.add(zombieList.get(zombieList.size() - 1));
				zombieList.add(new Zombie((byte) 3, (byte) 11));
				this.add(zombieList.get(zombieList.size() - 1));
				zombieList.add(new Zombie((byte) 4, (byte) 11));
				this.add(zombieList.get(zombieList.size() - 1));
				specialWaveSpawned = true;
			}
			if (zombieList.isEmpty()) {
				iGS = inGameState.roundEnd;
				wave++;
				specialWaveSpawned = false;
			}
			break;
		case roundEnd:
			shootBullet();
			mainC.setHealth(100);
			break;
		}

	}

	// handles shooting bullets
	private void shootBullet() {
		// when left click is pressed, game decides what bullet to make based on
		// current guntype( gun state)
		if (leftClicked) {
			switch (gT) {
			case pistol:
				Console.bulletList.add(new Bullet(1));
				this.add(bulletList.get(bulletList.size() - 1));
				leftClicked = false;
				break;
			case semiAutomatic:
				if (ammo1 <= 0) {
					ammoText = ("<b>DEFAULT</b> " + "SEMI AUTOMATIC: " + ammo1
							+ " SNIPER: " + ammo2 + " BADMG: " + ammo3
							+ " GOODMG: " + ammo4 + " SUPER WEAPON: " + ammo5);
					ammoText = "<html><center>AMMO</center><br/>" + ammoText
							+ "</html>";
					ammoDis.setText(ammoText);
					gT = gunType.pistol;
				} else {
					Console.bulletList.add(new Bullet(2));
					this.add(bulletList.get(bulletList.size() - 1));
					ammo1--;
					ammoText = ("DEFAULT " + "<b>SEMI AUTOMATIC: " + ammo1
							+ "</b> SNIPER: " + ammo2 + " BADMG: " + ammo3
							+ " GOODMG: " + ammo4 + " SUPER WEAPON: " + ammo5);
					ammoText = "<html><center>AMMO</center><br/>" + ammoText
							+ "</html>";
					ammoDis.setText(ammoText);
					leftClicked = false;
				}
				break;
			case Sniper:
				if (ammo2 <= 0) {
					ammoText = ("<b>DEFAULT</b> " + "SEMI AUTOMATIC: " + ammo1
							+ " SNIPER: " + ammo2 + " BADMG: " + ammo3
							+ " GOODMG: " + ammo4 + " SUPER WEAPON: " + ammo5);
					ammoText = "<html><center>AMMO</center><br/>" + ammoText
							+ "</html>";
					ammoDis.setText(ammoText);
					gT = gunType.pistol;
				} else {
					Console.bulletList.add(new Bullet(2));
					this.add(bulletList.get(bulletList.size() - 1));
					ammo2--;
					ammoText = ("DEFAULT " + "SEMI AUTOMATIC: " + ammo1
							+ " <b>SNIPER: " + ammo2 + "</b> BADMG: " + ammo3
							+ " GOODMG: " + ammo4 + " SUPER WEAPON: " + ammo5);
					ammoText = "<html><center>AMMO</center><br/>" + ammoText
							+ "</html>";
					ammoDis.setText(ammoText);
					leftClicked = false;
				}
				break;
			case lTMG:
				if (ammo3 <= 0) {
					ammoText = ("<b>DEFAULT</b> " + "SEMI AUTOMATIC: " + ammo1
							+ " SNIPER: " + ammo2 + " BADMG: " + ammo3
							+ " GOODMG: " + ammo4 + " SUPER WEAPON: " + ammo5);
					ammoText = "<html><center>AMMO</center><br/>" + ammoText
							+ "</html>";
					ammoDis.setText(ammoText);
					gT = gunType.pistol;
				} else {
					if (bTimer == 20) {
						Console.bulletList.add(new Bullet(4));
						this.add(bulletList.get(bulletList.size() - 1));
						bTimer = 0;
						ammo3--;
						ammoText = ("DEFAULT " + "SEMI AUTOMATIC: " + ammo1
								+ " SNIPER: " + ammo2 + " <b>BADMG: " + ammo3
								+ " </b>GOODMG: " + ammo4 + " SUPER WEAPON: " + ammo5);
						ammoText = "<html><center>AMMO</center><br/>"
								+ ammoText + "</html>";
						ammoDis.setText(ammoText);
					} else {
						bTimer++;
					}
				}
				break;
			case hTMG:
				if (ammo4 <= 0) {
					ammoText = ("<b>DEFAULT</b> " + "SEMI AUTOMATIC: " + ammo1
							+ " SNIPER: " + ammo2 + " BADMG: " + ammo3
							+ " GOODMG: " + ammo4 + " SUPER WEAPON: " + ammo5);
					ammoText = "<html><center>AMMO</center><br/>" + ammoText
							+ "</html>";
					ammoDis.setText(ammoText);
					gT = gunType.pistol;
				} else {
					if (bTimer == 10) {
						Console.bulletList.add(new Bullet(5));
						this.add(bulletList.get(bulletList.size() - 1));
						bTimer = 0;
						ammo4--;
						ammoText = ("DEFAULT " + "SEMI AUTOMATIC: " + ammo1
								+ " SNIPER: " + ammo2 + "BADMG: " + ammo3
								+ " <b>GOODMG: " + ammo4
								+ " </b>SUPER WEAPON: " + ammo5);
						ammoText = "<html><center>AMMO</center><br/>"
								+ ammoText + "</html>";
						ammoDis.setText(ammoText);
					} else {
						bTimer++;
					}
				}
				break;
			case ulti:
				if (ammo5 <= 0) {
					ammoText = ("<b>DEFAULT</b> " + "SEMI AUTOMATIC: " + ammo1
							+ " SNIPER: " + ammo2 + " BADMG: " + ammo3
							+ " GOODMG: " + ammo4 + " SUPER WEAPON: " + ammo5);
					ammoText = "<html><center>AMMO</center><br/>" + ammoText
							+ "</html>";
					ammoDis.setText(ammoText);
					gT = gunType.pistol;
				} else {
					ammo5--;
					ammoText = ("DEFAULT " + "SEMI AUTOMATIC: " + ammo1
							+ " SNIPER: " + ammo2 + " BADMG: " + ammo3
							+ " GOODMG: " + ammo4 + " <b>SUPER WEAPON: "
							+ ammo5 + "</b>");
					ammoText = "<html><center>AMMO</center><br/>" + ammoText
							+ "</html>";
					ammoDis.setText(ammoText);
					Console.bulletList.add(new Bullet(6));
					this.add(bulletList.get(bulletList.size() - 1));
					leftClicked = false;
				}
				break;
			default:
				break;
			}
		} else {
			switch (gT) {
			case pistol:
				break;
			case semiAutomatic:
				break;
			case Sniper:
				break;
			case lTMG:
				bTimer = 20;
				break;
			case hTMG:
				bTimer = 10;
				break;
			case ulti:
				break;
			default:
				break;
			}
		}

	}

	// constructs HUD component
	private void constructHUD() {
		healthDis = new HUD(2 * Game.SSH, 0, Game.width - 4 * Game.SSH,
				Game.SSH, null, 100, HUD.directionalScale.horizontal, Color.red);
		healthDis.setVisible(true);
		ammoDis = new HUD(2 * SSH, Game.height - 2 * Game.SSH, Game.width - 4
				* SSH, 2 * Game.SSH, null, 0, HUD.directionalScale.noScale,
				Color.ORANGE);
		ammoDis.setVisible(true);
		addHUD();

	}

	// adds all HUD components
	private void addHUD() {
		add(healthDis);
		add(ammoDis);

	}

	// updates HUD information every tick
	private void updateHUD() {
		if (!constructedHUD) {
			constructHUD();
			this.ammoText = ("<b>DEFAULT</b> " + "SEMI AUTOMATIC: " + ammo1
					+ " SNIPER: " + ammo2 + " BADMG: " + ammo3 + " GOODMG: "
					+ ammo4 + " SUPER WEAPON: " + ammo5);
			this.ammoText = "<html><center>AMMO</center><br/>" + this.ammoText
					+ "</html>";
			ammoDis.setText(ammoText);
			constructedHUD = true;
			gT = gunType.pistol;
		} else {
			healthDis.setText("" + mainC.getHealth());
			healthDis.act(mainC.getHealth());

		}
	}

	// listens to button presses during game pauses

	private void buttonListener() {
		// listens to buttons and make decision based on what is pressed or if
		// game is prompting
		switch (bS) {
		case uninitiated:
			if (startButton.getModel().isPressed()) {
				bS = buttonState.startP;
				prompt();
			} else if (hTP.getModel().isPressed()) {
				hideMenus();
				hTPMENU.setVisible(true);
			} else if (settings.getModel().isPressed()) {
				hideMenus();
				settingsMenu.setVisible(true);
				upButton.setVisible(true);
				downButton.setVisible(true);
				rightButton.setVisible(true);
				leftButton.setVisible(true);
			} else if (credits.getModel().isPressed()) {
				hideMenus();
				creditsMenu.setVisible(true);
			} else if (exit.getModel().isPressed()) {
				bS = buttonState.quitP;
				prompt();
			}
			break;
		case standard:
			if (startButton.getModel().isPressed()) {
				bS = buttonState.notVis;
				gS = gameState.unpaused;
			} else if (hTP.getModel().isPressed()) {
				hideMenus();
				hTPMENU.setVisible(true);
			} else if (settings.getModel().isPressed()) {
				hideMenus();
				settingsMenu.setVisible(true);
				upButton.setVisible(true);
				downButton.setVisible(true);
				rightButton.setVisible(true);
				leftButton.setVisible(true);
			} else if (credits.getModel().isPressed()) {
				hideMenus();
				creditsMenu.setVisible(true);
			} else if (exit.getModel().isPressed()) {
				bS = buttonState.quitP;
				prompt();
			}
			// } else if (upButton.getModel().isPressed()) {
			// bS = buttonState.changingButtons;
			// bCS = buttonChangingState.up;
			// } else if (downButton.getModel().isPressed()) {
			// bS = buttonState.changingButtons;
			// bCS = buttonChangingState.down;
			// } else if (rightButton.getModel().isPressed()) {
			// bS = buttonState.changingButtons;
			// bCS = buttonChangingState.right;
			// } else if (leftButton.getModel().isPressed()) {
			// bS = buttonState.changingButtons;
			// bCS = buttonChangingState.left;

			break;
		case quitP:
			// if yes is pressed return variable true
			if (playPromptYes.getModel().isPressed()) {
				yesNoMenu.setVisible(false);
				playPromptYes.setVisible(false);
				playPromptNo.setVisible(false);
				System.exit(1);
			} else if (playPromptNo.getModel().isPressed()) {
				yesNoMenu.setVisible(false);
				playPromptYes.setVisible(false);
				playPromptNo.setVisible(false);
				bS = buttonState.standard;
			}
			break;
		case startP:
			if (playPromptYes.getModel().isPressed()) {
				yesNoMenu.setVisible(false);
				playPromptYes.setVisible(false);
				playPromptNo.setVisible(false);
				startButton.setText("CONTINUE");
				bS = buttonState.notVis;
				gS = gameState.unpaused;
			} else if (playPromptNo.getModel().isPressed()) {
				yesNoMenu.setVisible(false);
				playPromptYes.setVisible(false);
				playPromptNo.setVisible(false);
				bS = buttonState.standard;
			}
			break;
		case notVis:
			;
			break;
		case changingButtons:
			// switch (bCS) {
			// case up:
			// upKey = nextPressed;
			// bS = buttonState.standard;
			// break;
			// case down:
			// downKey = nextPressed;
			// bS = buttonState.standard;
			// break;
			// case left:
			// leftKey = nextPressed;
			// bS = buttonState.standard;
			// break;
			// case right:
			// rightKey = nextPressed;
			// bS = buttonState.standard;
			// break;
			// case buy:
			// break;
			// case notChanging:
			// bS = buttonState.standard;
			// break;
			// default:
			// break;
			// }
			break;
		}
	}

	// sets all menu components to no visible
	private void hideMenus() {
		playPromptYes.setVisible(false);
		playPromptNo.setVisible(false);
		hTPMENU.setVisible(false);
		creditsMenu.setVisible(false);
		settingsMenu.setVisible(false);
		upButton.setVisible(false);
		downButton.setVisible(false);
		rightButton.setVisible(false);
		leftButton.setVisible(false);
	}

	// prompt method for buttons that require a decision
	private void prompt() {
		hideMenus();
		yesNoMenu.setVisible(true);
		playPromptYes.setVisible(true);
		playPromptNo.setVisible(true);
	}

	// check the health of all instantiated objects, deletes them when they have
	// less than 0 health
	public void checkHealth() {
		for (int i = 0; i <= Console.zombieList.size() - 1; i++) {
			Zombie a = zombieList.get(i);
			if (a.getHealth() < 0) {
				zombieList.remove(a);
				remove(a);
				kills++;
				break;
			}
		}
		if (mainCAdded && mainC.getHealth() < 0) {
			endGame();
		}
	}

	// ends the game
	private void endGame() {
		// deletes everything so it can be reconstructed
		clearBulletList();
		clearZombieList();
		removeAll();
		startButton.setVisible(false);
		settings.setVisible(false);
		hTP.setVisible(false);
		credits.setVisible(false);
		exit.setVisible(false);
		playPromptYes.setVisible(false);
		playPromptNo.setVisible(false);
		leftButton.setVisible(false);
		rightButton.setVisible(false);
		upButton.setVisible(false);
		downButton.setVisible(false);
		hTPMENU.setVisible(false);
		settingsMenu.setVisible(false);
		creditsMenu.setVisible(false);
		homeMenu.setVisible(false);
		yesNoMenu.setVisible(false);
		background.setVisible(false);
		zSpawn1.setVisible(false);
		zSpawn2.setVisible(false);
		zSpawn3.setVisible(false);
		zSpawn4.setVisible(false);
		healthDis.setVisible(false);
		ammoDis.setVisible(false);
		mainC.setVisible(false);
		menuVisible = true;
		constructedHUD = false;
		mainCAdded = false;
		bS = buttonState.uninitiated;
		gS = gameState.finished;
		iGS = inGameState.uninitiated;
		gT = gunType.pistol;
		okButton = new GUI(Game.centerX - 3 * SSW, Game.centerY + 3 * Game.SSH,
				6 * SSW, 2 * SSH, "OK", GUI.XAlignment.center,
				GUI.YAlignment.center);
		lostMenu = new RectangleLabel(Game.centerX - 3 * SSW, Game.centerY - 3
				* Game.SSH, 6 * SSW, 6 * SSH, Game.color2);
		lostMenu.setText("<html><center> you Survived: <p>" + (wave - 1)
				+ " rounds,</p> killing: <p>" + kills
				+ " zombies</p></center></html>");
		lostMenu.setHorizontalAlignment(JLabel.CENTER);
		lostMenu.setVerticalAlignment(JLabel.TOP);
		lostMenu.setFont(new Font("ARIAL", Font.PLAIN, Game.bFont));
		add(okButton);
		add(lostMenu);
		setup();
		kills = 0;
		ammo1 = 0;
		ammo2 = 0;
		ammo3 = 0;
		ammo4 = 0;
		ammo5 = 0;
		wave = 1;
		bTimer = 0;
		waveControl = 0;
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

	// clears zombie list and deletes all zombies
	private void clearZombieList() {
		for (int i = 0; i < zombieList.size(); i++) {
			zombieList.get(i).setVisible(false);
			zombieList.get(i).setDamage(0);

		}
		zombieList.clear();
	}

	// clears bullet list and deletes all bullets
	private void clearBulletList() {
		for (int i = 0; i < bulletList.size(); i++) {
			bulletList.get(i).setVisible(false);
		}
		bulletList.clear();
	}

	// updates GUI every tick
	private void updateGUI() {
		// decision based on game state
		updateStrings();
		switch (gS) {
		case uninitiated:
			;
			break;
		case paused:
			if (!menuVisible) {
				startButton.setVisible(true);
				hTP.setVisible(true);
				settings.setVisible(true);
				credits.setVisible(true);
				exit.setVisible(true);
				background.setVisible(true);
				menuVisible = true;
			}
			;
			break;
		case unpaused:
			if (menuVisible) {
				startButton.setVisible(false);
				hTP.setVisible(false);
				settings.setVisible(false);
				credits.setVisible(false);
				exit.setVisible(false);
				background.setVisible(false);
				hideMenus();
				menuVisible = false;
			}
			;
			break;
		case finished:
			;
			break;
		}

	}

	// unimplemented method (not enough time to implement)
	private void updateScreenSize(int x, int y) {
		screenSize.setSize(x, y);
		height = screenSize.height;
		width = screenSize.width;
		centerX = screenSize.width / 2;
		centerY = screenSize.height / 2;
		sFont = screenSize.height / 80;
		bFont = screenSize.height / 40;
		constructMenuObjects();
		constructGameObjects();
	}

	// updates the games mouse position
	private void updateMousePosition() {
		a = MouseInfo.getPointerInfo();
		cursorX = a.getLocation().x;
		cursorY = a.getLocation().y;
	}

	// updates strings
	private void updateStrings() {
		left = " Left: " + leftKey;
		right = " Right: " + rightKey;
		up = " Up: " + upKey;
		down = " Down: " + downKey;

	}

	// sets up all important things
	public void setup() {
		// sets screen size and updates constructs all objects
		setSize(width, height);
		constructAllItems();
	}

	// acts method, called once every 100th of a second
	public void act() {
		// updates mouse and gui every tick
		updateMousePosition();
		updateGUI();
		// decision based on game state
		switch (gS) {
		case uninitiated:
			buttonListener();
			break;
		case paused:
			buttonListener();
			break;
		case unpaused:
			game();
			break;
		case finished:
			if (okButton.getModel().isPressed()) {
				okButton.setVisible(false);
				lostMenu.setVisible(false);
				gS = gameState.uninitiated;
			}
			break;
		}
	}

	public static void main(String[] args) {
		S = new Console();
		S.setVisible(true);
		S.initComponents();
	}
}
