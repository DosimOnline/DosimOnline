package org.dosimonline.entities;
import it.randomtower.engine.entity.Entity;
import java.util.Random;
import org.dosimonline.states.EnterName;
import org.dosimonline.states.Interaction;
import org.dosimonline.states.Play;
import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Rabbi extends Entity {
	private static final float GRAVITY = 8;
	private float movementSpeed = 1;
	private final Animation walkLeft, walkRight;
	private String direction = "Left";
	private int rabbiWidth, rabbiHeight;

	public Rabbi(float x, float y) throws SlickException {
		super(x, y);
		SpriteSheet spriteSheet = new SpriteSheet(
			  "org/dosimonline/res/sprites/rabbi.png", 30, 54);
		walkLeft = new Animation();
		walkLeft.setAutoUpdate(true);
		walkLeft.addFrame(spriteSheet.getSprite(0, 0), 150);
		walkLeft.addFrame(spriteSheet.getSprite(1, 0), 150);

		walkRight = new Animation();
		walkRight.setAutoUpdate(true);
		walkRight.addFrame(spriteSheet.getSprite(0, 0).getFlippedCopy(true, false), 150);
		walkRight.addFrame(spriteSheet.getSprite(1, 0).getFlippedCopy(true, false), 150);

		// There is no actual reason for picking walkLeft over walkRight; like many things here, it was chosen randomly.
		rabbiWidth = walkLeft.getCurrentFrame().getWidth();
		rabbiHeight = walkLeft.getHeight();

		setHitBox(0, 0, rabbiWidth, rabbiHeight);
		depth = 1;
		addType("Rabbi");
		name = generateName();
	}
	
	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		super.update(gc, delta);
		
		if (direction.equals("Left"))
			x += movementSpeed;
		else
			x -= movementSpeed;

		// Change direction!
		if (collide("Syn", x + rabbiWidth, y) == null || collide("Syn", x - rabbiWidth, y) == null)
			direction = direction.equals("Left") ? "Right" : "Left";

		// Gravity.
		if (collide("Solid", x, y + GRAVITY) == null
			  // We don't want him to fall off the ladder, aren't we?
			  && collide("Syn", x, y + 56) != null) // Don't ask why did I put 56 there. It works so it's ok.
			y += GRAVITY;

		// Soft landing.
		if (collide("Solid", x, y + GRAVITY) != null && collide("Solid", x, y + 1) == null)
			y++;
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		super.render(gc, g);
		Play.drawCenteredString(name, g, (int) x + rabbiWidth / 2, (int) y - rabbiWidth / 2);

		Entity dos = collide("Dos", x, y);
		if (dos == null) {
			movementSpeed = 1;
			if (direction.equals("Left"))
				g.drawAnimation(walkLeft, x, y);
			else
				g.drawAnimation(walkRight, x, y);
		} else {
			movementSpeed = 0;
			if (dos.x > x)
				g.drawImage(walkLeft.getImage(0), x, y);
			else
				g.drawImage(walkRight.getImage(0), x, y);
			Play.drawCenteredString("Hello, " + EnterName.getName() + "! Right Click me if you know what's good for you!", g, (int) x, (int) y - rabbiWidth * 2);
		}
	}
	
	public String getName() {
		return name;
	}

	private String generateName() {
		String names[] = {
			"SHIM'ON BEN-MITZVA",
			"SWAGGODOS",
			"AMNON MELAPHEPHON",
			"OVADIA YOLOSEF",
			"URBANODOS",
			"WINDRABBI",
			"TZVIKKA SWAGGZ",
			"YO MITZVA",
			"YOSSI DOSSI",
			"POMPARABBI",
			"RENTGEN RABBI",
			"CT RABBI",
		};

		Random random = new Random();
		return names[random.nextInt(names.length)];
	}
}
