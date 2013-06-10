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

	public EntityMeatball(float x, float y, float targetX, float targetY)
			throws SlickException
	{
		super(x, y);
		Image image = new Image("org/dosimonline/res/meatball.png");
		setGraphic(image);
		setHitBox(0, 0, image.getWidth(), image.getHeight());
		direction = new Vector2f(targetX - x, targetY - y);
	}

	@Override
	public void update(GameContainer container, int delta)
			throws SlickException
	{
		super.update(container, delta);

		x += direction.getX() / 50;
		y += direction.getY() / 50;

		if (collide("Dos", x, y) != null)
		{
			WorldPlains.dos.life--;
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
