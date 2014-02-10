package org.dosimonline.entities;

import it.randomtower.engine.entity.Entity;
import java.util.Random;
import org.dosimonline.states.Play;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

public class FlyingSpaghettiMonster extends Entity {
	private final int MAX_SPEED = 50;
	private final int CHANGE_DIRECTION_TIME = 7000; // 7sec
	private final int ATTACK_DELAY = 7000;
	private final int BE_RED_TIME = 200; // 0.1 sec. I know I'm a terrible name
	// picker.
	private Vector2f direction;
	private Color hurtColor = new Color(255, 0, 0, 100);
	private Image image;
	private float moveSpeed = 50;
	private Random random = new Random();
	private int changeDirectionTimeout = 0;
	private int attackTimeout = 2000;
	private int redTimeout = 0;
	private int life = 3;

	public FlyingSpaghettiMonster(float x, float y) throws SlickException {
		super(x, y);
		image = new Image("org/dosimonline/res/sprites/fsm.png");
		setGraphic(image);
		setHitBox(0, 0, image.getWidth(), image.getHeight());
		addType("Pasta");

		direction = new Vector2f();
		newVelocity();
	}

	private void shootOnDos() throws SlickException {
		for (Entity entity : world.getEntities())
			if (entity instanceof Dos)
				if (attackTimeout <= 0 && entity.x > x - 500
					  && entity.x < x + 500 && entity.y > y - 500
					  && entity.y < y + 500) {
					world.add(new Meatball(x, y, entity.x, entity.y));
					attackTimeout = ATTACK_DELAY;
					return;
				}
	}

	@Override
	public void update(GameContainer container, int delta)
		  throws SlickException {
		super.update(container, delta);
		changeDirectionTimeout -= delta;
		attackTimeout -= delta;

		shootOnDos();

		if (redTimeout > 0)
			redTimeout -= delta;

		if (changeDirectionTimeout <= 0) {
			newVelocity();
			changeDirectionTimeout = CHANGE_DIRECTION_TIME;
		}

		x += direction.getX() * moveSpeed * (delta / 1000.0f);
		y += direction.getY() * moveSpeed * (delta / 1000.0f);

		Dos someDos = (Dos) collide("Dos", x, y);
		if (someDos != null)
			someDos.life = 0; // No one can touch the FSM and stay alive!
		Entity starOfDavidColl = collide("Star of David", x, y);
		if (starOfDavidColl != null) // Star of David hit the FSM
		{
			starOfDavidColl.destroy();
			redTimeout = BE_RED_TIME;
			life--;

			newVelocity(); // change direction and speed

			if (life == 0) {
				((StarOfDavid) starOfDavidColl).getShootingDos().score += 10;
				this.destroy();
			}
		}

		if (x < Play.LEFT_BORDER
			  || x + this.currentImage.getWidth() > Play.RIGHT_BORDER)
			direction.x *= -1;

		if (y < Play.UPPER_BORDER
			  || y + this.currentImage.getHeight() > Play.LOWER_BORDER)
			direction.y *= -1;
	}

	@Override
	public void render(GameContainer container, Graphics g)
		  throws SlickException {
		super.render(container, g);
		if (redTimeout > 0)
			g.drawImage(image, x, y, hurtColor);
	}

	// return value: random n, min <= n <= max
	private static int randomBetween(Random random, int min, int max) {
		if (min <= max)
			return random.nextInt(max + 1 - min) + min;
		return random.nextInt(min - max + 1) + max;
	}

	private void newVelocity() {
		moveSpeed = randomBetween(random, MAX_SPEED / 2, MAX_SPEED);

		// randomize one direction out of eight
		do {
			direction.x = randomBetween(random, -1, 1);
			direction.y = randomBetween(random, -1, 1);
		} while (direction.x == 0 && direction.y == 0); // We want to move!

		direction.normalise();
	}

	@Override
	public void destroy() {
		super.destroy();
	}
}
