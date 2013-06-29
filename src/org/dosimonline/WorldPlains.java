package org.dosimonline;

import it.randomtower.engine.World;

import java.util.Random;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class WorldPlains extends World
{
	private Color backgroundColor = new Color(117, 202, 255);
	public EntityDos dos;

	private Button backButton;
	private Image heart;

	private Random random = new Random();
	private int spawnNazi;
	private int helpDisplayTime = 5000; // 5 seconds
	public static float gravity = 12; // Deprecated. still in use because the
										// nazis are in the old gravity system
	private Structure building = new Structure();
	private int spawnFSM;

	public static final float UPPER_BORDER = -5500f;
	public static final float LOWER_BORDER = 464f;
	public static final float RIGHT_BORDER = 7680f;
	public static final float LEFT_BORDER = -128f;

	// time in ms:
	private static final int SPAWN_NAZI = 5000;
	private static final int SPAWN_FSM = 60000;

	public WorldPlains(int id, GameContainer gc)
	{
		super(id, gc);
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException
	{
		super.init(gc, sbg);
		
		Image cursor = new Image("org/dosimonline/res/scope.png");

		gc.setMouseCursor(cursor, cursor.getWidth() / 2, cursor.getHeight() / 2);

		Image backButtonImage = new Image(
				"org/dosimonline/res/buttons/back.png");

		backButton = new Button(20, DosimOnline.dm.getHeight() - 20
				- backButtonImage.getHeight(), backButtonImage, new Image(
				"org/dosimonline/res/buttons/backActive.png"));

		heart = new Image("org/dosimonline/res/heart.png");

		initialize();
	}

	private void initialize() throws SlickException
	{
		this.clear();

		// We call it "tile" instead of "block" because we don't want too many
		// Minecraft easter eggs.

		for (int x = -1; x < 60; x++)
		{
			add(new TileGrass(x * 128, 464));
			for (int y = 1; y < 4; y++)
			{
				add(new TileDirt(x * 128, 464 + 128 * y));
			}
		}
		for (int a = 0, x = 650; a < 9; a++, x += 700)
		{
			int numOfFloors = random.nextInt(10) + 3;
			building.add(this, x, 80, numOfFloors);
		}

		spawnFSM = 0;
		dos = new EntityDos(1920, 0);
		add(dos);
		setCameraOn(dos);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException
	{
		super.render(gc, sbg, g);

		g.setBackground(backgroundColor);

		g.drawString("SCORE: " + dos.score, 20, 20);

		for (int i = 0; i < dos.life; i++)
		{
			g.drawImage(heart, 20 + i * 32, 50);
		}

		Debug.render(g);

		if (helpDisplayTime > 0)
		{
			drawCenteredString(
					g,
					"ADW to move, Left mouse to shoot, Right mouse to place mine",
					DosimOnline.dm.getWidth() / 2,
					DosimOnline.dm.getHeight() / 2 - 100);
		}
		if (dos.life <= 0)
		{
			drawCenteredString(g, "LOL! U DIED!",
					DosimOnline.dm.getWidth() / 2,
					DosimOnline.dm.getHeight() / 2);

			drawCenteredString(g, "Hit \"R\" to restart",
					DosimOnline.dm.getWidth() / 2,
					DosimOnline.dm.getHeight() / 2 + 20);
		}

		backButton.render(g);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException
	{
		super.update(gc, sbg, delta);

		// Keep the camera inside world bounds
		if (-camera.x < LEFT_BORDER)
			camera.x = -LEFT_BORDER;
		if (-camera.x + DosimOnline.dm.getWidth() > RIGHT_BORDER)
			camera.x = -(RIGHT_BORDER - DosimOnline.dm.getWidth());

		Input input = gc.getInput();

		// Shoot
		if (input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON))
			dos.shoot((float) input.getMouseX() - camera.x,
					(float) input.getMouseY() - camera.y);

		// Place mine
		if (input.isMouseButtonDown(Input.MOUSE_RIGHT_BUTTON))
			dos.placeMine();

		if (spawnNazi > 0)
			spawnNazi -= delta;
		else
		{
			int naziX = random.nextInt(4100) + 600;
			add(new EntityNazi(naziX, -7000));
			spawnNazi = SPAWN_NAZI;
		}

		if (spawnFSM > 0)
			spawnFSM -= delta;
		else
		{
			add(new EntityFlyingSpaghettiMonster(1920, -500));
			spawnFSM = SPAWN_FSM;
		}

		if (dos.life <= 0 && input.isKeyPressed(Input.KEY_R))
			this.initialize();

		if (helpDisplayTime > 0)
			helpDisplayTime -= delta;

		backButton.update(input);

		if (backButton.activated() || input.isKeyPressed(Input.KEY_ESCAPE))
		{
			sbg.enterState(1);
		}

		Debug.show("FPS: " + gc.getFPS());
	}

	// Draws a string that its center is x,y
	public static void drawCenteredString(Graphics g, String s, int x, int y)
	{
		g.drawString(s, x - g.getFont().getWidth(s) / 2, y
				- g.getFont().getHeight(s) / 2);
	}
}