package org.dosimonline;

import org.dosimonline.states.*;
import java.awt.Font;
import java.io.IOException;
import org.lwjgl.opengl.*;
import org.lwjgl.LWJGLException;
import org.newdawn.slick.*;
import org.newdawn.slick.state.StateBasedGame;

public class DosimOnline extends StateBasedGame {
	public static String gamename = "Dosim Online";
	public static String version = "0.9";
	public static DisplayMode dm;
	private Font awtFont, mlgfont;
	public static TrueTypeFont font;
	//private static final Font mlgfont = new Font("Verdana", Font.BOLD, 50);
	//public static UnicodeFont MLGFont = new UnicodeFont(mlgfont, mlgfont.getSize(), false, false);
	public static TrueTypeFont MLGFont;

	public DosimOnline(String gamename) {
		super(gamename);
	}

	@Override
	public void initStatesList(GameContainer gc) throws SlickException {
		awtFont = new Font("Times New Roman", Font.PLAIN, 26);
		mlgfont = new Font("Times New Roman", Font.BOLD, 50);
		font = new TrueTypeFont(awtFont, true);
		MLGFont = new TrueTypeFont(mlgfont, true);
		addState(new Menu(1, gc));
		addState(new Play(2, gc));
		addState(new About(3, gc));
		addState(new Settings(4, gc));
		addState(new EnterName(5, gc));
		addState(new Interaction(6, gc));
		enterState(1);
	}

	public static void main(String[] args) throws IOException, LWJGLException {
		dm = Display.getDesktopDisplayMode();
		Display.setResizable(true);

		try {
			AppGameContainer appgc = new AppGameContainer(new DosimOnline(
				  gamename));
			appgc.setDisplayMode(dm.getWidth(), dm.getHeight(), true);
			appgc.start();
		} catch (SlickException e) {
			e.printStackTrace();
		}
	}
}
