package org.dosimonline;

import it.randomtower.engine.World;
import java.util.Random;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
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
	public static EntityDos dos;
	public static EntityNazi nazi;
	
	private Button backButton;
	
	private DisplayMode dm = Display.getDesktopDisplayMode();
	private Random random = new Random();
	private short spawnNazi;
	public static float naziMoveSpeed = 4;
	private short helpDisplayTime = 300;
	public static float gravity = 12;
	private Structure building = new Structure();
	private int spawnFSM = 1;
	private static final int ATTACK_DELAY = 100;

	public WorldPlains(int id, GameContainer gc)
	{
		super(id, gc);
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException
	{
		super.init(gc, sbg);
		
		Image backButtonImage = new Image("org/dosimonline/res/buttons/back.png");
		
		backButton = new Button(20, dm.getHeight() - 20 - backButtonImage.getHeight(),
				backButtonImage, new Image("org/dosimonline/res/buttons/backActive.png"));

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
		for (short a = 0, x = 650; a < 9; a++, x += 700)
		{
			int numOfFloors = random.nextInt(10) + 3;
			building.add(x, this, 80, numOfFloors);
		}

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
		g.drawString("NAZIS' SPEED: " + naziMoveSpeed, 20, 35);
		for (int a = 0; a < dos.life; a++)
		{
			g.drawImage(new Image("org/dosimonline/res/heart.png"),
					20 + a * 32, 55);
		}
		g.drawString("Reload: " + dos.attackAllowed, 20, 80);
		if (helpDisplayTime > 0)
		{
			g.drawString("ASDW to move, left mouse to shoot",
					DosimOnline.dm.getWidth() / 2 - 100,
					DosimOnline.dm.getHeight() / 2 - 100);
		}
		if (dos.life <= 0)
		{
			g.drawString("LOL! U DIED!", DosimOnline.dm.getWidth() / 2 - 10,
					DosimOnline.dm.getHeight() / 2);
			g.drawString("Hit \"R\" to restart",
					DosimOnline.dm.getWidth() / 2 - 20,
					DosimOnline.dm.getHeight() / 2 + 20);
		}

		backButton.render(g);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int i)
			throws SlickException
	{
		super.update(gc, sbg, i);

		// Shoot
		if (gc.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON)
				&& dos.attackAllowed == 0)
		{
			float mouseX = (float) gc.getInput().getMouseX();
			float mouseY = (float) gc.getInput().getMouseY();
			add(new EntityFireball(dos.x, dos.y, mouseX, mouseY, false));
			dos.attackAllowed = ATTACK_DELAY;
		}
		
		// Place mine
		if (gc.getInput().isMousePressed(Input.MOUSE_RIGHT_BUTTON)
				&& dos.attackAllowed == 0)
		{
			add(new EntityFireball(dos.x, dos.y, dos.x, dos.y, true));
			dos.attackAllowed = ATTACK_DELAY;
		}

		if (spawnNazi > 0)
		{
			spawnNazi--;
		}
		if (spawnNazi == 0)
		{
			int naziX = random.nextInt(4100) + 600;
			add(new EntityNazi(naziX, -7000));
			spawnNazi = 450;
		}
		naziMoveSpeed += 0.00005;

		if (spawnFSM <= 0)
		{
			add(new EntityFlyingSpaghettiMonster(1920, -500));
			spawnFSM = 4000;
		}
		spawnFSM--;

		if (dos.life == 0 && gc.getInput().isKeyPressed(Input.KEY_R))
		{
			naziMoveSpeed = 4;
			dos = new EntityDos(1920, 0);
			add(dos);
	//		dos.x = 1920;
	//		dos.y = 0;
	//		dos.score = 0;
	//		dos.life = 5;
			setCameraOn(dos);
		}

		if (helpDisplayTime > 0)
		{
			helpDisplayTime--;
		}

		backButton.update(gc.getInput());
		
		if (backButton.activated() || gc.getInput().isKeyPressed(Input.KEY_ESCAPE))
		{
			sbg.enterState(1);
		}
	}
}