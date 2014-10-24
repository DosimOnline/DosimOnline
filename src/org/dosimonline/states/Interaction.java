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

public class Interaction extends World {
	private Button backButton, buyHP, decreaseReloadTime, increaseMovementSpeed, increaseClimbSpeed, increaseJumpSpeed;
	private Image hakotel;
	private static String message;

	public Interaction(int id, GameContainer gc) throws SlickException {
		super(id, gc);
		backButton = new Button(40, DosimOnline.dm.getHeight() - 40, "Back");
		buyHP = new Button(gc.getWidth() / 2 - DosimOnline.font.getWidth("Get more health for 50 points!") / 2, 150, "Get more health for 50 Jew Gold!");
		decreaseReloadTime = new Button(gc.getWidth() / 2 - DosimOnline.font.getWidth("Decrease reload time for 30 Jew Gold!") / 2, 220, "Decrease reload time for 25 Jew Gold!");
		increaseMovementSpeed = new Button(gc.getWidth() / 2 - DosimOnline.font.getWidth("Increase your movement speed for 70 Jew Gold!") / 2, 290, "Increase your movement speed for 70 Jew Gold!");
		increaseClimbSpeed = new Button(gc.getWidth() / 2 - DosimOnline.font.getWidth("Climb faster for 60 Jew Gold!") / 2, 360, "Climb faster for 60 Jew Gold!");
		increaseJumpSpeed = new Button(gc.getWidth() / 2 - DosimOnline.font.getWidth("Jump higher for 40 Jew Gold!") / 2, 430, "Jump higher for 40 Jew Gold!");

		hakotel = new Image("org/dosimonline/res/hakotel.png").getScaledCopy(
			  DosimOnline.dm.getWidth(), DosimOnline.dm.getHeight());
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		super.update(gc, sbg, delta);
		backButton.update(gc.getInput());
		buyHP.update(gc.getInput());
		decreaseReloadTime.update(gc.getInput());
		increaseMovementSpeed.update(gc.getInput());
		increaseJumpSpeed.update(gc.getInput());
		increaseClimbSpeed.update(gc.getInput());

		if (buyHP.activated())
			if (Play.dos.score >= 50) {
				Play.dos.life++;
				Play.dos.score -= 50;
			} else
				setMassage("Are you trying to fool me?");
		if (decreaseReloadTime.activated())
			if (Play.dos.score > 30) {
				Play.dos.ATTACK_DELAY -= 125;
				Play.dos.score -= 30;
			} else
				setMassage("Are you trying to fool me?");
		if (increaseMovementSpeed.activated())
			if (Play.dos.score > 70) {
				Play.dos.moveSpeed += 100;
				Play.dos.score -= 70;
			} else
				setMassage("Are you trying to fool me?");
		if (increaseClimbSpeed.activated())
			if (Play.dos.score > 60) {
				Play.dos.climbSpeed -= 100;
				Play.dos.score -= 60;
			} else
				setMassage("Are you trying to fool me?");
		if (increaseJumpSpeed.activated())
			if (Play.dos.score > 40) {
				Play.dos.jumpSpeed += 100;
				Play.dos.score -= 40;
			} else
				setMassage("Are you trying to fool me?");
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
		increaseClimbSpeed.render(g);
		increaseJumpSpeed.render(g);
		increaseMovementSpeed.render(g);

		Play.drawCenteredString("Woah, you have " + Play.dos.score + " Jew Gold! Who are you, Yitzhak Tshuva?", g, gc.getWidth() / 2, 30);
		Play.drawCenteredString(message, g, gc.getWidth() / 2, 100);
	}

	public static void setMassage(String message) {
		Interaction.message = message;
	}
}
