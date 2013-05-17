package org.dosimonline;
import it.randomtower.engine.World;
import java.util.Random;
import org.lwjgl.input.Mouse;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class WorldMenu extends World
{
	private Image logo;
	private Image hakotel;
	private Music music;
	private DisplayMode dm = Display.getDesktopDisplayMode();
	private short heartX = 0;
	private int br = 224, bg = 224, bb = 224; //Bacground red, green and blue.
	private Random random = new Random();
	private int heartY = random.nextInt(dm.getHeight() - 20) + 10;
	private Image startButton, startButtonHover;
	private Image creditsButton, creditsButtonHover;
	private Image exitButton, exitButtonHover;
	private Image settingsButton, settingsButtonHover;
	private Image heart;

	public WorldMenu(int id, GameContainer gc)
	{
		super(id, gc);
	}

	@Override
	public void init(GameContainer gc, StateBasedGame game)
	 throws SlickException
	{
		logo = new Image("org/dosimonline/res/logo.png");
		hakotel = new Image("org/dosimonline/res/hakotel.png").getScaledCopy(dm.getWidth(), dm.getHeight());

		startButton = new Image("org/dosimonline/res/buttons/start.png");
		startButtonHover = new Image("org/dosimonline/res/buttons/startActive.png");

		creditsButton = new Image("org/dosimonline/res/buttons/credits.png");
		creditsButtonHover = new Image("org/dosimonline/res/buttons/creditsActive.png");

		exitButton = new Image("org/dosimonline/res/buttons/exit.png");
		exitButtonHover = new Image("org/dosimonline/res/buttons/exitActive.png");
		
		settingsButton = new Image("org/dosimonline/res/buttons/settings.png");
		settingsButtonHover = new Image("org/dosimonline/res/buttons/settingsActive.png");

		heart = new Image("org/dosimonline/res/heart.png");
		music = new Music("org/dosimonline/res/audio/Makche-Alleviation.ogg");
		music.loop(1, 0.5f);
		gc.setShowFPS(false);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException
	{
		g.drawImage(hakotel, 0, 0);
		int mouseX = gc.getInput().getMouseX();
		int mouseY = gc.getInput().getMouseY();
		g.drawImage(heart, heartX, heartY);
		g.drawImage(logo, dm.getWidth() - logo.getWidth() - 10, dm.getHeight() / 2 - logo.getHeight() / 2);

		//Buttons stuff
		if (!(mouseX > 20 && mouseX < 20 + startButton.getWidth() && mouseY > dm.getHeight() / 2 - 20 && mouseY < dm.getHeight() / 2 - 20 + startButton.getHeight()))
		{
			g.drawImage(startButton, 20, dm.getHeight() / 2 - 20);
		} else
		{
			g.drawImage(startButtonHover, 20, dm.getHeight() / 2 - 20);
			if (gc.getInput().isMouseButtonDown(0))
			{
				sbg.enterState(2);
			}
		}
		if (!(mouseX > 20 && mouseX < 20 + creditsButton.getWidth() && mouseY > dm.getHeight() / 2 + 20 && mouseY < dm.getHeight() / 2 + 20 + startButton.getHeight()))
		{
			g.drawImage(creditsButton, 20, dm.getHeight() / 2 + 20);
		} else
		{
			g.drawImage(creditsButtonHover, 20, dm.getHeight() / 2 + 20);
			if (gc.getInput().isMouseButtonDown(0))
			{
				sbg.enterState(3);
			}
		}
		if (!(mouseX > 20 && mouseX < 20 + settingsButton.getWidth() && mouseY > dm.getHeight() / 2 + 60 && mouseY < dm.getHeight() / 2 + 60 + startButton.getHeight()))
		{
			g.drawImage(settingsButton, 20, dm.getHeight() / 2 + 60);
		} else
		{
			g.drawImage(settingsButtonHover, 20, dm.getHeight() / 2 + 60);
			if (gc.getInput().isMouseButtonDown(0))
			{
				sbg.enterState(4);
			}
		}
		if (!(mouseX > 20 && mouseX < 20 + exitButton.getWidth() && mouseY > dm.getHeight() / 2 + 100 && mouseY < dm.getHeight() / 2 + 100 + startButton.getHeight()))
		{
			g.drawImage(exitButton, 20, dm.getHeight() / 2 + 100);
		} else
		{
			g.drawImage(exitButtonHover, 20, dm.getHeight() / 2 + 100);
			if (gc.getInput().isMouseButtonDown(0))
			{
				gc.destroy();
			}
		}
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException
	{
		//Traveling heart's stuff
		if (heartX < dm.getWidth())
		{
			heartX++;
		} else
		{
			heartY = random.nextInt(dm.getHeight() - 50) + 10;
			heartX = -50;
		}
		heart.rotate(0.1f);

		if (gc.getInput().isKeyPressed(Input.KEY_ESCAPE))
		{
			gc.exit();
		}
	}
}
