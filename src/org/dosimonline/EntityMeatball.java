package org.dosimonline;

import it.randomtower.engine.entity.Entity;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

public class EntityMeatball extends Entity
{
	private Vector2f direction;
	private int shallDie = 400;
	private EntityDos dos; // Dirty hack for accessing dos.life . the collision stuff should be done in EntityDos
	private int speed = 20;

	public EntityMeatball(float x, float y, float targetX, float targetY, EntityDos dos)
			throws SlickException
	{
		super(x, y);
		Image image = new Image("org/dosimonline/res/meatball.png");
		setGraphic(image);
		setHitBox(0, 0, image.getWidth(), image.getHeight());
		
		direction = new Vector2f(targetX - x, targetY - y);
		direction.normalise();
		
		this.dos = dos;
	}

	@Override
	public void update(GameContainer container, int delta)
			throws SlickException
	{
		super.update(container, delta);

		x += direction.getX() * speed;
		y += direction.getY() * speed;

		if (collide("Dos", x, y) != null)
		{
			dos.life--;
			this.destroy();
		}

		if (shallDie <= 0)
			this.destroy();
	}

	@Override
	public void destroy()
	{
		super.destroy();
	}
}
