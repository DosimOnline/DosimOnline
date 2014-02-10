package org.dosimonline.entities;
import it.randomtower.engine.entity.Entity;
import org.dosimonline.Debug;
import org.dosimonline.states.EnterName;
import org.dosimonline.states.Play;
import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Dos extends Entity {
	private final Animation walkLeft;
	private final Animation walkRight;
	private final Image standingImage;
	private final Image jumpingImage;
	private boolean jumpAllowed = true;
	private final float moveSpeed = 600;
	public int lookDirection = 1; // 1 is left, 2 is right.
	public int life;
	public int score;
	private int attackAllowed = 0;
	private final float JUMP_SPEED = -500;
	private final float CLIMB_SPEED = -500;
	private float velocityY;
	private float accelerationY;
	private static final float GRAVITY = 1000;
	public int ATTACK_DELAY = 1800;
	private int dosWidth, dosHeight;

	public Dos(float x, float y) throws SlickException {
		super(x, y);
		SpriteSheet spriteSheet = new SpriteSheet(
			  "org/dosimonline/res/sprites/dos.png", 20, 36);
		walkLeft = new Animation();
		walkLeft.setAutoUpdate(true);
		walkLeft.addFrame(
			  spriteSheet.getSprite(0, 0).getFlippedCopy(true, false), 150);
		walkLeft.addFrame(
			  spriteSheet.getSprite(1, 0).getFlippedCopy(true, false), 150);

		walkRight = new Animation();
		walkRight.setAutoUpdate(true);
		walkRight.addFrame(spriteSheet.getSprite(0, 0), 150);
		walkRight.addFrame(spriteSheet.getSprite(1, 0), 150);
		standingImage = spriteSheet.getSprite(0, 0);
		jumpingImage = spriteSheet.getSprite(2, 0);
		define("right", Input.KEY_D);
		define("left", Input.KEY_A);
		define("up", Input.KEY_W);
		define("q", Input.KEY_Q);
		setHitBox(0, 0, standingImage.getWidth(), standingImage.getHeight());
		addType("Dos");

		dosWidth = walkLeft.getCurrentFrame().getWidth();
		dosHeight = walkLeft.getCurrentFrame().getHeight();

		score = 0;
		life = 5;
		velocityY = 0;
		jumpAllowed = false;
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		super.update(gc, delta);

		name = EnterName.getName();

		// Dos' direction (Graphical only).
		if (gc.getInput().getMouseX() > this.x + world.camera.x
			  + standingImage.getWidth() / 2)
			lookDirection = 2;
		else
			lookDirection = 1;

		// Movement.
		float nextRightX = x + moveSpeed * (delta / 1000.0f);
		float nextLeftX = x - moveSpeed * (delta / 1000.0f);

		if (check("right")
			  && nextRightX + standingImage.getWidth() < Play.RIGHT_BORDER
			  && collide("Solid", nextRightX, y) == null)
			x = nextRightX;
		else if (check("left") && nextLeftX > Play.LEFT_BORDER
			  && collide("Solid", nextLeftX, y) == null)
			x = nextLeftX;

		if (jumpAllowed && check("up")) // Jump
		{
			jumpAllowed = false;
			velocityY = JUMP_SPEED;
		} else // Update jumpAllowed (true if the dos is standing)

			jumpAllowed = collide("Solid", x, y + 0.5f) != null;

		if (collide("Ladder", x, y) != null) // The dos is on ladder
		{
			jumpAllowed = false;

			if (check("up")) {
				velocityY = CLIMB_SPEED;
				accelerationY = 0;
			} else {
				velocityY = -CLIMB_SPEED;
				accelerationY = 0;
			}
		} else if (jumpAllowed && check("up")) // Jump
		{
			jumpAllowed = false;
			velocityY = JUMP_SPEED;
		} else if (jumpAllowed == false) // Apply gravity

			accelerationY = GRAVITY;

		float nextY = y + velocityY * (delta / 1000.0f);

		if (collide("Solid", x, nextY) != null)
			if (velocityY > 0) // The dos is going down
			{
				jumpAllowed = true;
				while (collide("Solid", x, y + 0.5f) == null)
					y += 0.5f;

				accelerationY = 0;
				velocityY = 0;
			} else // collision with ceiling
				velocityY *= -1;
		else {
			y = nextY;
			velocityY += accelerationY * (delta / 1000.0f);
		}

		if (this.life == 0)
			this.destroy();

		//yo, lo!
		if (attackAllowed > 0)
			attackAllowed -= delta;

		Debug.show("Reload: " + attackAllowed);
		Debug.show("Jump allowed= " + jumpAllowed);
		Debug.show("X: " + x);
		Debug.show("Y: " + y);
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		super.render(gc, g);
		Play.drawCenteredString(name, g, (int) x + dosWidth / 2, (int) y - dosHeight / 2);

		if (jumpAllowed) {
			if (check("left") || check("right")) {
				if (lookDirection == 1)
					g.drawAnimation(walkLeft, x, y);
				else if (lookDirection == 2)
					g.drawAnimation(walkRight, x, y);
			} else if (lookDirection == 1)
				g.drawImage(standingImage.getFlippedCopy(true, false), x, y);
			else if (lookDirection == 2)
				g.drawImage(standingImage, x, y);
		} else if (lookDirection == 1)
			g.drawImage(jumpingImage.getFlippedCopy(true, false), x, y);
		else if (lookDirection == 2)
			g.drawImage(jumpingImage, x, y);
	}

	public boolean canAttack() {
		return this.attackAllowed <= 0;
	}

	public void shoot(float targetX, float targetY) throws SlickException {
		if (canAttack()) {
			world.add(new StarOfDavid(
				  // Spawn a Star of David at the center of the dos
				  x - 6, // Dos image width / 2 - Star of David image width / 2
				  y + 2, // Dos image height / 2 - Star of David image height / 2
				  targetX, targetY, this));
			attackAllowed = ATTACK_DELAY;
		}
	}

	public void placeMine() throws SlickException {
		if (canAttack()) {
			world.add(new StarOfDavid(x, y, x, y, this));
			attackAllowed = ATTACK_DELAY;
		}
	}
}
