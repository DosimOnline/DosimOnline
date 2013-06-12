package org.dosimonline;

import it.randomtower.engine.World;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class WorldSettings extends World
{
	private Image hakotel;

	private Button fpsPlus, fpsMinus, musicPlus, musicMinus, back;
	private int fps;
	float musicVolume;

	private DisplayMode dm = Display.getDesktopDisplayMode();

	public WorldSettings(int id, GameContainer gc)
	{
		super(id, gc);
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg)
			throws SlickException
	{
		super.init(gc, sbg);
		hakotel = new Image("org/dosimonline/res/hakotel.png").getScaledCopy(
				dm.getWidth(), dm.getHeight());

		Image plus = new Image("org/dosimonline/res/buttons/plus.png");
		Image plusActive = new Image(
				"org/dosimonline/res/buttons/plusActive.png");
		Image minus = new Image("org/dosimonline/res/buttons/minus.png");
		Image minusActive = new Image(
				"org/dosimonline/res/buttons/minusActive.png");

		fpsPlus = new Button(dm.getWidth() / 2 + 50, 42, plus, plusActive);
		fpsMinus = new Button(dm.getWidth() / 2 - 175, 42, minus, minusActive);
		musicPlus = new Button(dm.getWidth() / 2 + 50, 100, plus, plusActive);
		musicMinus = new Button(dm.getWidth() / 2 - 175, 100, minus,
				minusActive);

		back = new Button(40, dm.getHeight() - 40, new Image(
				"org/dosimonline/res/buttons/back.png"), new Image(
				"org/dosimonline/res/buttons/backActive.png"));

		fps = 80;
		musicVolume = gc.getMusicVolume();
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException
	{
		super.render(gc, sbg, g);

		g.drawImage(hakotel, 0, 0);

		g.drawString("Target FPS: " + fps, dm.getWidth() / 2 - 100, 50);
		g.drawString("Music volume: " + Math.round(gc.getMusicVolume() * 10),
				dm.getWidth() / 2 - 110, 108);

		fpsPlus.render(g);
		fpsMinus.render(g);
		musicPlus.render(g);
		musicMinus.render(g);
		back.render(g);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException
	{
		super.update(gc, sbg, delta);

		float oldVolume = musicVolume;

		fpsPlus.update(gc.getInput());
		fpsMinus.update(gc.getInput());
		musicPlus.update(gc.getInput());
		musicMinus.update(gc.getInput());
		back.update(gc.getInput());

		if (fpsPlus.activated())
			fps += 5;
		else if (fpsMinus.activated())
			fps -= 5;
		else if (musicPlus.activated())
			musicVolume += 0.1f;
		else if (musicMinus.activated())
			musicVolume -= 0.1f;

		if (musicVolume != oldVolume)
			gc.setMusicVolume(musicVolume);

		if (gc.getInput().isKeyPressed(Input.KEY_M))
			gc.setMusicVolume(0);
		if (back.activated() || gc.getInput().isKeyPressed(Input.KEY_ESCAPE))
		{
			sbg.enterState(1);
			gc.setTargetFrameRate(fps);
		}
	}
}
