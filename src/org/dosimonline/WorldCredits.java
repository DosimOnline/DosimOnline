package org.dosimonline;

import it.randomtower.engine.World;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class WorldCredits extends World
{
	private Button backButton;
	private Image heart;
	private Image hakotel;

	public WorldCredits(int id, GameContainer gc) throws SlickException
	{
		super(id, gc);
		
		heart = new Image("org/dosimonline/res/heart.png");

		backButton = new Button(40, DosimOnline.dm.getHeight() - 40, new Image(
				"org/dosimonline/res/buttons/back.png"), new Image(
				"org/dosimonline/res/buttons/backActive.png"));
		
		hakotel = new Image("org/dosimonline/res/hakotel.png").getScaledCopy(
				DosimOnline.dm.getWidth(), DosimOnline.dm.getHeight());
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
			throws SlickException
	{
		super.update(gc, sbg, delta);

		backButton.update(gc.getInput());
		
		if (backButton.activated()
				|| gc.getInput().isKeyPressed(Input.KEY_ESCAPE))
			sbg.enterState(1);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
			throws SlickException
	{
		super.render(gc, sbg, g);
		
		g.drawImage(hakotel, 0, 0);
		WorldPlains.drawCenteredString(g,
				"Programming: Shpitzick, Itay Rabin, yashax",
				DosimOnline.dm.getWidth() / 2, 40);
		
		WorldPlains.drawCenteredString(g, "Graphics: Tomer Ginzburg",
				DosimOnline.dm.getWidth() / 2, 60);
		WorldPlains
				.drawCenteredString(
						g,
						"Testers: Michael Puniansky, Martin Korotkov, StaveMan",
						DosimOnline.dm.getWidth() / 2, 80);
		WorldPlains.drawCenteredString(g,
				"Music: Makche (Alleviation is played at the background)",
				DosimOnline.dm.getWidth() / 2, 100);
		
		// Draw hearts at the bottom of the screen
		for (int x = 0; x < DosimOnline.dm.getWidth(); x += heart.getWidth())
		{
			g.drawImage(heart, x,
					DosimOnline.dm.getHeight() - heart.getHeight());
		}

		backButton.render(g);
	}
}
