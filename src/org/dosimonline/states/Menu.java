package org.dosimonline.states;
import it.randomtower.engine.World;
import java.util.Random;
import org.dosimonline.Button;
import org.dosimonline.DosimOnline;
import org.dosimonline.settings.SettingsManager;
import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Menu extends World {
	private Image logo;
	private Image hakotel;
	private Music music;
	private DisplayMode dm = Display.getDesktopDisplayMode();
	private short heartX = 0;
	private Random random = new Random();
	private int heartY = random.nextInt(dm.getHeight() - 20) + 10;
	private Button startButton, creditsButton, exitButton, settingsButton;
	private Image heart;

	public Menu(int id, GameContainer gc) {
		super(id, gc);
	}

	@Override
	public void init(GameContainer gc, StateBasedGame game) throws SlickException {
		SettingsManager.getInstance().apply(gc); // Load the settings and apply them
		
		gc.setDefaultFont(DosimOnline.font);

		logo = new Image("org/dosimonline/res/logo.png");
		hakotel = new Image("org/dosimonline/res/hakotel.png").getScaledCopy(
			  gc.getWidth(), gc.getHeight());

		startButton = new Button(20, gc.getHeight() / 2 - 20, "Start");

		creditsButton = new Button(20, gc.getHeight() / 2 + 20, "Credits");

		settingsButton = new Button(20, gc.getHeight() / 2 + 60, "Settings");

		exitButton = new Button(20, gc.getHeight() / 2 + 100, "Exit :(");

		heart = new Image("org/dosimonline/res/heart.png");
		music = new Music("org/dosimonline/res/audio/Makche-Alleviation.ogg");
		music.loop(1, 0.5f);
		gc.setShowFPS(false);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {

		g.drawImage(hakotel, 0, 0);
		g.drawImage(heart, heartX, heartY);
		g.drawImage(logo, gc.getWidth() - logo.getWidth() - 10, gc.getHeight()
			  / 2 - logo.getHeight() / 2);

		startButton.render(g);
		creditsButton.render(g);
		settingsButton.render(g);
		exitButton.render(g);
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int i)
		  throws SlickException {
		// Traveling heart's stuff
		if (heartX < gc.getWidth()) {
			heartX++;
		} else {
			heartY = random.nextInt(gc.getHeight() - 50) + 10;
			heartX = -50;
		}
		heart.rotate(0.1f);

		startButton.update(gc.getInput(), gc);
		creditsButton.update(gc.getInput(), gc);
		settingsButton.update(gc.getInput(), gc);
		exitButton.update(gc.getInput(), gc);

		if (startButton.activated())
			sbg.enterState(2);
		else if (creditsButton.activated())
			sbg.enterState(3);
		else if (settingsButton.activated())
			sbg.enterState(4);

		if (exitButton.activated() || gc.getInput().isKeyPressed(Input.KEY_ESCAPE)) {
			gc.exit();
		}
	}
}
