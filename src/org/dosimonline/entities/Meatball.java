package org.dosimonline.entities;

import it.randomtower.engine.entity.Entity;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

public class Meatball extends Entity {
	private Vector2f direction;
	private int shallDie = 6666; // ms
	private float speed = 800f; // px/s

	public Meatball(float x, float y, float targetX, float targetY)
		  throws SlickException {
		super(x, y);
		Image image = new Image("org/dosimonline/res/meatball.png");
		setGraphic(image);
		setHitBox(0, 0, image.getWidth(), image.getHeight());

		direction = new Vector2f(targetX - x, targetY - y);
		direction.normalise();
	}

	@Override
	public void update(GameContainer container, int delta)
		  throws SlickException {
		super.update(container, delta);

		x += direction.getX() * speed * (delta / 1000.0f);
		y += direction.getY() * speed * (delta / 1000.0f);

		Dos someDos = (Dos) collide("Dos", x, y);
		if (someDos != null) {
			someDos.life--;
			this.destroy();
		}

		shallDie -= delta;
		if (shallDie <= 0)
			this.destroy();
	}

	@Override
	public void destroy() {
		super.destroy();
	}
}
