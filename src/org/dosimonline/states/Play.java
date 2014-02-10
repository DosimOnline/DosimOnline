package org.dosimonline.states;
import it.randomtower.engine.World;
import it.randomtower.engine.entity.Entity;
import java.util.Random;
import org.dosimonline.Button;
import org.dosimonline.Debug;
import org.dosimonline.DosimOnline;
import org.dosimonline.NotificationManager;
import org.dosimonline.Structure;
import org.dosimonline.entities.Dos;
import org.dosimonline.entities.FlyingSpaghettiMonster;
import org.dosimonline.entities.Nazi;
import org.dosimonline.entities.Rabbi;
import org.dosimonline.tiles.Dirt;
import org.dosimonline.tiles.Grass;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.StateBasedGame;

public class Play extends World {
	private final Color backgroundColor = new Color(117, 202, 255);
	public static Dos dos;
	private Button backButton;
	private Image heart;
	private final Random random = new Random();
	private float spawnNazi;
	private final Structure building = new Structure();
	private int helpDisplayTime = 5000;
	private float spawnFSM;
	private NotificationManager notifyManager;
	public static final float UPPER_BORDER = -5500f;
	public static final float LOWER_BORDER = 464f;
	public static final float RIGHT_BORDER = 7680f;
	public static final float LEFT_BORDER = -128f;
	// time in ms:
	private float spawnNaziDelay = 5000;
	private float spawnFSMDelay = 60000;
	private static int nazisKilled;

	public Play(int id, GameContainer gc) {
		super(id, gc);
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException {
		super.init(gc, sbg);

		Image cursor = new Image("org/dosimonline/res/scope.png");

		gc.setMouseCursor(cursor, cursor.getWidth() / 2, cursor.getHeight() / 2);

		backButton = new Button(20, gc.getHeight() - 20
			  - DosimOnline.font.getHeight("Back"), "Back");

		heart = new Image("org/dosimonline/res/heart.png");
		notifyManager = NotificationManager.getInstance();
		notifyManager.init(gc, sbg);
		initialize();
	}

	private void initialize() throws SlickException {
		clear();
		spawnNaziDelay = 5000;
		spawnFSMDelay = 60000;

		/*
		 * We call it "tile" instead of "block" because 
		 * we don't want too many Minecraft easter eggs.
		 */
		for (int x = -1; x < 60; x++) {
			add(new Grass(x * 128, 464));
			for (int y = 1; y < 4; y++)
				add(new Dirt(x * 128, 464 + 128 * y));
		}
		for (int a = 0, x = 650; a < 9; a++, x += 700) {
			int numOfFloors = random.nextInt(4);
			building.add(this, x, 80, numOfFloors);
		}

		spawnFSM = 0;
		dos = new Dos(1920, 0);
		add(dos);
		setCameraOn(dos);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		super.render(gc, sbg, g);
		g.setBackground(backgroundColor);
		g.drawString("SCORE: " + dos.score, 20, 20);

		for (int i = 0; i < dos.life; i++)
			g.drawImage(heart, 20 + i * 32, 50);

		Debug.render(g);

		if (helpDisplayTime > 0)
			drawCenteredString("WAD to move, Left mouse to shoot, Right Click to interact", g,
				  gc.getWidth() / 2, gc.getHeight() / 2 - 100);

		if (dos.life <= 0) {
			drawCenteredString("LOL! U DIED!", g,
				  gc.getWidth() / 2, gc.getHeight() / 2);

			drawCenteredString("Hit \"R\" to restart", g,
				  gc.getWidth() / 2, gc.getHeight() / 2 + 40);
		}

		backButton.render(g);
		notifyManager.render(g);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		super.update(gc, sbg, delta);
		Input input = gc.getInput();
		spawnNaziDelay -= 0.01 * delta;
		spawnFSMDelay -= 0.1 * delta;
		notifyManager.update(gc, delta);

		int numOfNazis = 0;
		for (Entity e : getEntities())
			if (e instanceof Nazi)
				numOfNazis++;
		Debug.show("Number of nazis: " + numOfNazis);
		Debug.show("Nazis Killed: " + nazisKilled);

		int numOfFSMs = 0;
		for (Entity e : getEntities())
			if (e instanceof FlyingSpaghettiMonster)
				numOfFSMs++;
		Debug.show("Number of Flying Spaghetti Monsters: " + numOfFSMs);

		Debug.show("Mouse X= " + input.getMouseX());
		Debug.show("Mouse Y= " + input.getMouseY());

		// Keep the camera inside world bounds
		if (-camera.x < LEFT_BORDER)
			camera.x = -LEFT_BORDER;
		if (-camera.x + gc.getWidth() > RIGHT_BORDER)
			camera.x = -(RIGHT_BORDER - gc.getWidth());

		// Shoot
		if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON)) {
			dos.shoot(input.getMouseX() - camera.x,
				  input.getMouseY() - camera.y);
		}

		// Interact
		Entity tempRabbi = dos.collide("Rabbi", dos.x, dos.y);
		Rabbi rabbi = (Rabbi) tempRabbi;
		if (input.isMousePressed(Input.MOUSE_RIGHT_BUTTON) && rabbi != null) {
			notifyManager.add("Talked to " + rabbi.getName());
			Interaction.setMassage("Hey! Why won't you give a little charity? We will give you some stuff for it!");
			sbg.enterState(6);
		}

		if (spawnNazi > 0)
			spawnNazi -= delta;
		else if (numOfNazis <= 20) {
			int naziX = random.nextInt(4100) + 600;
			add(new Nazi(naziX, -1100));
			spawnNazi = spawnNaziDelay;
		}

		if (spawnFSM > 0)
			spawnFSM -= delta;
		else if (numOfFSMs <= 10) {
			add(new FlyingSpaghettiMonster(1920, -500));
			spawnFSM = spawnFSMDelay;
		}

		if (dos.life <= 0 && input.isKeyPressed(Input.KEY_R))
			this.initialize();

		if (helpDisplayTime > 0)
			helpDisplayTime -= delta;

		backButton.update(input);
		if (backButton.activated() || input.isKeyPressed(Input.KEY_ESCAPE))
			sbg.enterState(1);

		Debug.show("FPS: " + gc.getFPS());
		Debug.show("spawnNaziDelay: " + spawnNaziDelay);
		Debug.show("spawnFSMDelay: " + spawnFSMDelay);

		if (input.isKeyPressed(Input.KEY_0))
			dos.score += 100;
		if (input.isKeyPressed(Input.KEY_9))
			spawnNaziDelay -= 100;
		if (input.isKeyPressed(Input.KEY_8))
			spawnFSMDelay -= 1000;
	}

	// Draws a string that its center is x,y
	public static void drawCenteredString(String s, Graphics g, int x, int y) {
		TrueTypeFont font = DosimOnline.font;
		Color color = new Color(0, 0, 0, 160);
		g.setColor(color);
		g.fillRoundRect(x - font.getWidth(s) / 2 - 10, y - font.getHeight() / 2 - 2, font.getWidth(s) + 20, font.getHeight() + 4, 5);
		font.drawString(x - font.getWidth(s) / 2, y - font.getHeight() / 2, s);
	}

	public static void raiseNazisKilled(int num) {
		nazisKilled += num;
	}

	public static int getNumOfNazisKilled() {
		return nazisKilled;
	}
}
