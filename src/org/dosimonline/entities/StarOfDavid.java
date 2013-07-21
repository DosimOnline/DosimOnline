package org.dosimonline.entities;
import it.randomtower.engine.entity.Entity;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

public class StarOfDavid extends Entity {
	private Image image;
	private Vector2f direction;
	private int shallIDie = 3500; // milliseconds
	private float moveSpeed = 1200; // px/s
	private Dos shootingDos;

	public StarOfDavid(float x, float y, float targetX, float targetY, Dos shootingDos)
		  throws SlickException {
		super(x, y);

		image = new Image("org/dosimonline/res/starOfDavid.png");
		setGraphic(image);
		setHitBox(0, 0, image.getWidth(), image.getHeight());
		addType("Semitic Attack");
		setCentered(true);

		this.shootingDos = shootingDos;

		if (x == targetX && y == targetY) // Mine (doesn't move)
			direction = new Vector2f();
		else
			direction = new Vector2f(targetX - x, targetY - y);
		direction.normalise();
	}

	public Dos getShootingDos() {
		return this.shootingDos;
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		super.update(gc, delta);
		image.rotate(10);

		x += direction.getX() * moveSpeed * (delta / 1000.0f);
		y += direction.getY() * moveSpeed * (delta / 1000.0f);

		if (collide("Solid", x, y) != null) {
			this.destroy();
		}

		if (shallIDie > 0)
			shallIDie -= delta;
		else
			this.destroy();
	}

	@Override
	public void destroy() {
		super.destroy();
	}
}
