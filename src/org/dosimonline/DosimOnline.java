package org.dosimonline;

import java.io.IOException;
import org.lwjgl.LWJGLException;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class DosimOnline extends StateBasedGame
{
	public static String gamename = "Dosim Online";
	public static DisplayMode dm;

	public DosimOnline(String gamename)
	{
		super(gamename);
	}

	@Override
	public void initStatesList(GameContainer gc) throws SlickException
	{
		addState(new WorldMenu(1, gc));
		addState(new WorldPlains(2, gc));
		addState(new WorldCredits(3, gc));
		addState(new WorldSettings(4, gc));
		enterState(1);
	}

	public static void main(String[] args) throws IOException, LWJGLException
	{
		dm = Display.getDesktopDisplayMode();
		Display.setResizable(true);

		try
		{
			AppGameContainer appgc = new AppGameContainer(new DosimOnline(
					gamename));
			appgc.setDisplayMode(dm.getWidth(), dm.getHeight(), true);
			appgc.setFullscreen(true);
			appgc.setTargetFrameRate(80);
			appgc.start();
		}
		catch (SlickException e)
		{
			e.printStackTrace();
		}
	}
}
