package org.dosimonline.states;
import it.randomtower.engine.World;
import org.dosimonline.Button;
import org.dosimonline.DosimOnline;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Credits extends World {
	private Button backButton;
	private Image heart;
	private Image hakotel;

	public Credits(int id, GameContainer gc) throws SlickException {
		super(id, gc);

		heart = new Image("org/dosimonline/res/heart.png");
		backButton = new Button(40, DosimOnline.dm.getHeight() - 40, "Back");
		hakotel = new Image("org/dosimonline/res/hakotel.png").getScaledCopy(
			  DosimOnline.dm.getWidth(), DosimOnline.dm.getHeight());
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta)
		  throws SlickException {
		super.update(gc, sbg, delta);

		backButton.update(gc.getInput(), gc);

		if (backButton.activated()
			  || gc.getInput().isKeyPressed(Input.KEY_ESCAPE))
			sbg.enterState(1);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g)
		  throws SlickException {
		super.render(gc, sbg, g);

		g.drawImage(hakotel, 0, 0);
		Play.drawCenteredString(g,
			  "Programming: Shpitzick, Itay Rabin, yashax",
			  DosimOnline.dm.getWidth() / 2, 40);

		Play.drawCenteredString(g,
			  "Graphics: Tomer Ginzburg, Yinon-David Zadok",
			  DosimOnline.dm.getWidth() / 2, 60);
		Play.drawCenteredString(g,
			  "Site Management: royysszz, Solainz", gc.getWidth() / 2, 80);
		Play.drawCenteredString(g,
			  "Testers: Michael Puniansky, Martin Korotkov, StaveMan",
			  DosimOnline.dm.getWidth() / 2, 100);
		Play.drawCenteredString(g,
			  "Music: Makche (Alleviation is played at the background)",
			  DosimOnline.dm.getWidth() / 2, 120);

		// Draw hearts at the bottom of the screen
		for (int x = 0; x < DosimOnline.dm.getWidth(); x += heart.getWidth()) {
			g.drawImage(heart, x,
				  DosimOnline.dm.getHeight() - heart.getHeight());
		}

		backButton.render(g);
	}
}
