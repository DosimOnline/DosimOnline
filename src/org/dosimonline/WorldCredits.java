package org.dosimonline;
import it.randomtower.engine.World;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class WorldCredits extends World
{
	private Image heart;
	private Image backButton, backButtonHover;
	private DisplayMode dm = Display.getDesktopDisplayMode();

	public WorldCredits(int id, GameContainer gc) throws SlickException
	{
		super(id, gc);
		heart = new Image("org/dosimonline/res/heart.png");
		backButton = new Image("org/dosimonline/res/buttons/back.png");
		backButtonHover = new Image("org/dosimonline/res/buttons/backActive.png");
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException
	{
		super.update(gc, sbg, delta);

		if (gc.getInput().isKeyPressed(Input.KEY_ESCAPE))
		{
			sbg.enterState(1);
		}
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException
	{
		super.render(gc, sbg, g);
		int mouseX = gc.getInput().getMouseX();
		int mouseY = gc.getInput().getMouseY();

		g.setBackground(Color.black);
		g.drawString("Programming: Shptzick, Itay Rabin, Yashax3", 40, 40);
		g.drawString("Graphics: Tomer Ginzburg", 40, 60);
		g.drawString("Beta (Or shall I say Indev?) testers: Michael Puniansky, Martin Korotkov", 40, 80);
		g.drawString("Music: Makche (Alleviation is played at the background)", 40, 100);
		for (int x = 0; x < DosimOnline.dm.getWidth(); x += heart.getWidth())
		{
			g.drawImage(heart, x, DosimOnline.dm.getHeight() - heart.getHeight());
		}

		if (!(mouseX > 20 && mouseX < 20 + backButton.getWidth() && mouseY > dm.getHeight() - 40 - backButton.getHeight() && mouseY < dm.getHeight() - 40 + backButton.getHeight()))
		{
			g.drawImage(backButton, 40, dm.getHeight() - 40 - backButton.getHeight());
		} else
		{
			g.drawImage(backButtonHover, 40, dm.getHeight() - 40 - backButton.getHeight());
			if (gc.getInput().isMouseButtonDown(0))
			{
				sbg.enterState(1);
			}
		}
	}
}
