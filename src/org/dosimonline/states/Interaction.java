package org.dosimonline.states;
import it.randomtower.engine.World;
import org.dosimonline.Button;
import org.dosimonline.DosimOnline;
import org.dosimonline.entities.Dos;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class Interaction extends World {
	private Button backButton, buyHP, decreaseReloadTime;
	private Image hakotel;
	private static String message;

	public Interaction(int id, GameContainer gc) throws SlickException {
		super(id, gc);
		backButton = new Button(40, DosimOnline.dm.getHeight() - 40, "Back");
		buyHP = new Button(gc.getWidth() / 2 - DosimOnline.font.getWidth("Get more health for 50 points!") / 2, 150, "Get more health for 50 points!");
		decreaseReloadTime = new Button(gc.getWidth() / 2 - DosimOnline.font.getWidth("Decrease reload time for 100 points!") / 2, 220, "Decrease reload time for 100 points!");

		hakotel = new Image("org/dosimonline/res/hakotel.png").getScaledCopy(
			  DosimOnline.dm.getWidth(), DosimOnline.dm.getHeight());
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		super.update(gc, sbg, delta);
		backButton.update(gc.getInput());
		buyHP.update(gc.getInput());
		decreaseReloadTime.update(gc.getInput());

		if (buyHP.activated())
			if (Play.dos.score >= 50) {
				Play.dos.life++;
				Play.dos.score -= 50;
			} else
				setMassage("Are you trying to fool me?");
		if (decreaseReloadTime.activated()) {
			if (Play.dos.score > 100) {
				Play.dos.ATTACK_DELAY -= 350;
				Play.dos.score -= 100;
			} else
				setMassage("Are you trying to fool me?");
		}
		if (backButton.activated()
			  || gc.getInput().isKeyPressed(Input.KEY_ESCAPE))
			sbg.enterState(2);
	}

	@Override
	public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException {
		super.render(gc, sbg, g);
		hakotel.draw();
		backButton.render(g);
		buyHP.render(g);
		decreaseReloadTime.render(g);

		Play.drawCenteredString("Woah, you have " + Play.dos.score + " points! Who are you, Yitzhak Tshuva?", g, gc.getWidth() / 2, 30);
		Play.drawCenteredString(message, g, gc.getWidth() / 2, 100);
	}

	public static void setMassage(String message) {
		Interaction.message = message;
	}
}
