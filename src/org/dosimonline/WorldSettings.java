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
	private Image plus, plusActive, minus, minusActive, back, backActive;
	private DisplayMode dm = Display.getDesktopDisplayMode();

	public WorldSettings(int id, GameContainer gc)
	{
		super(id, gc);
	}

	@Override
	public void init(GameContainer gc, StateBasedGame sbg) throws SlickException
	{
		super.init(gc, sbg);
		hakotel = new Image("org/dosimonline/res/hakotel.png").getScaledCopy(dm.getWidth(), dm.getHeight());
		plus = new Image("org/dosimonline/res/buttons/plus.png");
		plusActive = new Image("org/dosimonline/res/buttons/plusActive.png");
		minus = new Image("org/dosimonline/res/buttons/minus.png");
		minusActive = new Image("org/dosimonline/res/buttons/minusActive.png");
		back = new Image("org/dosimonline/res/buttons/back.png");
		backActive = new Image("org/dosimonline/res/buttons/backActive.png");
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException
	{
		super.render(gc, sbg, g);
		int mouseX = gc.getInput().getMouseX();
		int mouseY = gc.getInput().getMouseY();

		g.drawImage(hakotel, 0, 0);

		g.drawString("Target FPS: " + gc.getFPS(), dm.getWidth() / 2 - 100, 50);
		if (!(mouseX > dm.getWidth() / 2 - 175 && mouseX < dm.getWidth() / 2 - 175 + plus.getWidth() && mouseY > 42 && mouseY < 42 + plus.getHeight()))
		{
			g.drawImage(minus, dm.getWidth() / 2 - 175, 42);
		} else
		{
			g.drawImage(minusActive, dm.getWidth() / 2 - 175, 42);
			if (gc.getInput().isMousePressed(0))
			{
				gc.setTargetFrameRate(gc.getFPS() - 5);
			}
		}
		if (!(mouseX > dm.getWidth() / 2 + 50 && mouseX < dm.getWidth() / 2 + 50 + plus.getWidth() && mouseY > 42 && mouseY < 42 + plus.getHeight()))
		{
			g.drawImage(plus, dm.getWidth() / 2 + 50, 42);
		} else
		{
			g.drawImage(plusActive, dm.getWidth() / 2 + 50, 42);
			if (gc.getInput().isMousePressed(0))
			{
				gc.setTargetFrameRate(gc.getFPS() + 5);
			}
		}

		g.drawString("Music volume: " + Math.round(gc.getMusicVolume() * 10), dm.getWidth() / 2 - 110, 108);
		if (!(mouseX > dm.getWidth() / 2 - 175 && mouseX < dm.getWidth() / 2 - 175 + plus.getWidth() && mouseY > 100 && mouseY < 100 + plus.getHeight()))
		{
			g.drawImage(minus, dm.getWidth() / 2 - 175, 100);
		} else
		{
			g.drawImage(minusActive, dm.getWidth() / 2 - 175, 100);
			if (gc.getInput().isMousePressed(0))
			{
				gc.setMusicVolume(gc.getMusicVolume() - 0.1f);
			}
		}
		if (!(mouseX > dm.getWidth() / 2 + 50 && mouseX < dm.getWidth() / 2 + 50 + plus.getWidth() && mouseY > 100 && mouseY < 100 + plus.getHeight()))
		{
			g.drawImage(plus, dm.getWidth() / 2 + 50, 100);
		} else
		{
			g.drawImage(plusActive, dm.getWidth() / 2 + 50, 100);
			if (gc.getInput().isMousePressed(0))
			{
				gc.setMusicVolume(gc.getMusicVolume() + 0.1f);
			}
		}

		if (!(mouseX > 30 && mouseX < 30 + back.getWidth() && mouseY > dm.getHeight() - 20 - back.getHeight() && mouseY < dm.getHeight() - 20))
		{
			g.drawImage(back, 30, dm.getHeight() - 20 - back.getHeight());
		} else
		{
			g.drawImage(backActive, 30, dm.getHeight() - 20 - back.getHeight());
			if (gc.getInput().isMousePressed(0))
			{
				sbg.enterState(1);
			}
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException
	{
		super.update(gc, sbg, delta);

		if (gc.getInput().isKeyPressed(Input.KEY_M)) gc.setMusicVolume(0);
		if (gc.getInput().isKeyPressed(Input.KEY_ESCAPE)) sbg.enterState(1);
	}
}
