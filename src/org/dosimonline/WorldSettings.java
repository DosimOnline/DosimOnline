package org.dosimonline;

import it.randomtower.engine.World;
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
				DosimOnline.dm.getWidth(), DosimOnline.dm.getHeight());
		
		settings = SettingsManager.getInstance();
		
		back = new Button(40, DosimOnline.dm.getHeight() - 40, new Image(
				"org/dosimonline/res/buttons/back.png"), new Image(
				"org/dosimonline/res/buttons/backActive.png"));
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException
	{
		super.render(gc, sbg, g);

		g.drawImage(hakotel, 0, 0);

		settings.render(g);
		
		back.render(g);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException
	{
		super.update(gc, sbg, delta);
		
		back.update(gc.getInput());
		
		settings.update(gc);
		
		if (back.activated() || gc.getInput().isKeyPressed(Input.KEY_ESCAPE))
		{
			settings.apply(gc);
			settings.writeSettings();
			sbg.enterState(1);
		}
	}
}
