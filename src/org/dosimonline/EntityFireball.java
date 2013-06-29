package org.dosimonline;

import it.randomtower.engine.entity.Entity;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

public class EntityFireball extends Entity
{
	private Vector2f direction;
	private int shallIDie = 3500; // milliseconds
	private float speed = 1200; // px/s
	private EntityDos shootingDos;

	public EntityFireball(float x, float y, float targetX, float targetY, EntityDos shootingDos)
			throws SlickException
	{
		super(x, y);
		
		Image fireball = new Image("org/dosimonline/res/fireball.png");
		setGraphic(fireball);
		setHitBox(0, 0, fireball.getWidth(), fireball.getHeight());
		addType("Fireball");
		
		this.shootingDos = shootingDos;

		if (x == targetX && y == targetY) // Mine (doesn't move)
			direction = new Vector2f();
		else
			direction = new Vector2f(targetX - x - this.currentImage.getWidth()
					/ 2, targetY - y - this.currentImage.getWidth() / 2);
		direction.normalise();
	}
	
	public EntityDos getShootingDos()
	{
		return this.shootingDos;
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException
	{
		super.update(gc, delta);

		x += direction.getX() * speed * (delta / 1000.0f);
		y += direction.getY() * speed * (delta / 1000.0f);

		if (collide("Solid", x, y) != null)
		{
			this.destroy();
		}

		if (shallIDie > 0)
			shallIDie -= delta;
		else
			this.destroy();
	}

	@Override
	public void destroy()
	{
		super.destroy();
	}
}
