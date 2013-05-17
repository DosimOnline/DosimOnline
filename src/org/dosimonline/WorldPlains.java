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
	private Image backButton, backButtonHover;
	private DisplayMode dm = Display.getDesktopDisplayMode();
	private Random random = new Random();
	private short spawnNazi;
	public static float naziMoveSpeed = 4;
	private short helpDisplayTime = 300;
	public static float gravity = 12;
	private Structure building = new Structure();
	private int spawnFSM = 1;

	public WorldPlains(int id, GameContainer gc)
	{
		super(id, gc);
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException
	{
		super.init(gc, sbg);
		backButton = new Image("org/dosimonline/res/buttons/back.png");
		backButtonHover = new Image("org/dosimonline/res/buttons/backActive.png");

		// We call it "tile" instead of "block" because we don't want too many
		// Minecraft easter eggs.
		for (short x = -1; x < 60; x++)
		{
			add(new TileGrass(x * 128, 464));
			for (byte y = 1; y < 4; y++)
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
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException
	{
		super.render(gc, sbg, g);
		int mouseX = gc.getInput().getMouseX();
		int mouseY = gc.getInput().getMouseY();
		
		g.setBackground(backgroundColor);
		g.drawString("SCORE: " + dos.score, 20, 20);
		g.drawString("NAZIS' SPEED: " + naziMoveSpeed, 20, 35);
		for (int a = 0; a < dos.life; a++)
		{
			g.drawImage(new Image("org/dosimonline/res/heart.png"), 20 + a * 32, 55);
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
		
		if (!(mouseX > 20 && mouseX < 20 + backButton.getWidth() && mouseY > dm.getHeight() - 20 - backButton.getHeight() && mouseY < dm.getHeight() - 20 + backButton.getHeight()))
		{
			g.drawImage(backButton, 20, dm.getHeight() - 20 - backButton.getHeight());
		} else
		{
			g.drawImage(backButtonHover, 20, dm.getHeight() - 20 - backButton.getHeight());
			if (gc.getInput().isMouseButtonDown(0))
			{
				sbg.enterState(1);
			}
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException
	{
		super.update(gc, sbg, i);

		if (gc.getInput().isMousePressed(Input.MOUSE_LEFT_BUTTON) && dos.attackAllowed == 0)
		{
			float mouseX = (float) gc.getInput().getMouseX();
			float mouseY = (float) gc.getInput().getMouseY();
			add(new EntityFireball(dos.x, dos.y, mouseX, mouseY, false));
			dos.attackAllowed = 100;
		}
		if (gc.getInput().isMousePressed(Input.MOUSE_RIGHT_BUTTON) && dos.attackAllowed == 0)
		{
			add(new EntityFireball(dos.x, dos.y, dos.x, dos.y, true));
			dos.attackAllowed = 100;
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
			add(dos);
			dos.x = 1920;
			dos.y = 0;
			dos.score = 0;
			dos.life = 5;
			setCameraOn(dos);
		}


		if (helpDisplayTime > 0)
		{
			helpDisplayTime--;
		}
		
		if (gc.getInput().isKeyPressed(Input.KEY_ESCAPE))
		{
			sbg.enterState(1);
		}
	}
}