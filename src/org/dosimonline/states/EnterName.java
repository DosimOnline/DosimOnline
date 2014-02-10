package org.dosimonline.states;
import it.randomtower.engine.World;
import java.util.ArrayList;
import org.dosimonline.Button;
import org.dosimonline.DosimOnline;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class EnterName extends World {
	private Button backButton, startButton;
	private Image hakotel;
	private ArrayList<Character> nameArray = new ArrayList<>();
	private static String name = "";

	public EnterName(int id, GameContainer gc) throws SlickException {
		super(id, gc);
		backButton = new Button(30, DosimOnline.dm.getHeight() - 40, "Back");
		startButton = new Button(gc.getWidth() - DosimOnline.font.getWidth("Let's go!") - 40, gc.getHeight() - 40, "Let's go!");
		hakotel = new Image("org/dosimonline/res/hakotel.png").getScaledCopy(
			  DosimOnline.dm.getWidth(), DosimOnline.dm.getHeight());
	}

	@Override
	public void update(GameContainer gc, StateBasedGame sbg, int delta) throws SlickException {
		super.update(gc, sbg, delta);
		backButton.update(gc.getInput());
		startButton.update(gc.getInput());

		name = "";
		for (char s : nameArray)
			name += s;

		if (pressed(Input.KEY_BACK, gc) && !nameArray.isEmpty())
			nameArray.remove(nameArray.size() - 1);

		if ((startButton.activated() || pressed(Input.KEY_ENTER, gc)) && !nameArray.isEmpty())
			sbg.enterState(2);

		if (backButton.activated()
			  || gc.getInput().isKeyPressed(Input.KEY_ESCAPE))
			sbg.enterState(1);

		if (pressed(Input.KEY_A, gc)) nameArray.add('A');
		if (pressed(Input.KEY_B, gc)) nameArray.add('B');
		if (pressed(Input.KEY_C, gc)) nameArray.add('C');
		if (pressed(Input.KEY_D, gc)) nameArray.add('D');
		if (pressed(Input.KEY_E, gc)) nameArray.add('E');
		if (pressed(Input.KEY_F, gc)) nameArray.add('F');
		if (pressed(Input.KEY_G, gc)) nameArray.add('G');
		if (pressed(Input.KEY_H, gc)) nameArray.add('H');
		if (pressed(Input.KEY_I, gc)) nameArray.add('I');
		if (pressed(Input.KEY_J, gc)) nameArray.add('J');
		if (pressed(Input.KEY_K, gc)) nameArray.add('K');
		if (pressed(Input.KEY_L, gc)) nameArray.add('L');
		if (pressed(Input.KEY_M, gc)) nameArray.add('M');
		if (pressed(Input.KEY_N, gc)) nameArray.add('N');
		if (pressed(Input.KEY_O, gc)) nameArray.add('O');
		if (pressed(Input.KEY_P, gc)) nameArray.add('P');
		if (pressed(Input.KEY_Q, gc)) nameArray.add('Q');
		if (pressed(Input.KEY_R, gc)) nameArray.add('R');
		if (pressed(Input.KEY_S, gc)) nameArray.add('S');
		if (pressed(Input.KEY_T, gc)) nameArray.add('T');
		if (pressed(Input.KEY_U, gc)) nameArray.add('U');
		if (pressed(Input.KEY_V, gc)) nameArray.add('V');
		if (pressed(Input.KEY_W, gc)) nameArray.add('W');
		if (pressed(Input.KEY_X, gc)) nameArray.add('X');
		if (pressed(Input.KEY_Y, gc)) nameArray.add('Y');
		if (pressed(Input.KEY_Z, gc)) nameArray.add('Z');
	}

	@Override
	public void render(GameContainer gc, StateBasedGame game, Graphics g) throws SlickException {
		super.render(gc, game, g);
		g.drawImage(hakotel, 0, 0);
		backButton.render(g);
		startButton.render(g);

		Play.drawCenteredString("Your name is: " + name, g, gc.getWidth() / 2, gc.getHeight() / 2);
	}

	private boolean pressed(int key, GameContainer gc) {
		return gc.getInput().isKeyPressed(key);
	}

	public static String getName() {
		return name;
	}
}
