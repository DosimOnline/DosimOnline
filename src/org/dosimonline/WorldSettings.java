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
	private Button back;

	private SettingsManager settings;
	
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
		
		settings = SettingsManager.getInstance();
		
		back = new Button(40, dm.getHeight() - 40, new Image(
				"org/dosimonline/res/buttons/back.png"), new Image(
				"org/dosimonline/res/buttons/backActive.png"));
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException
	{
		super.render(gc, sbg, g);

		g.drawImage(hakotel, 0, 0);

		Setting[] settingsArr = settings.getSettings();
		for (int i = 0; i < settingsArr.length; i++)
			settingsArr[i].render(g);

		back.render(g);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException
	{
		super.update(gc, sbg, delta);
		
		back.update(gc.getInput());
		
		Setting[] settingsArr = settings.getSettings();
		for (int i = 0; i < settingsArr.length; i++)
			settingsArr[i].update(gc);
		
		if (back.activated() || gc.getInput().isKeyPressed(Input.KEY_ESCAPE))
		{
			settings.apply(gc);
			settings.writeSettings();
			sbg.enterState(1);
		}
	}
}
