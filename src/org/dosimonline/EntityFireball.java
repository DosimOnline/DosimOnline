package org.dosimonline;
import it.randomtower.engine.entity.Entity;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

public class EntityFireball extends Entity
{
	private int shallIDie = 200;
	private boolean isMine;
	private Vector2f direction;
	private int velocity = 20;

	public EntityFireball(float x, float y, float targetX, float targetY, boolean isMine) throws SlickException
	{
		super(x, y);
		setGraphic(new Image("org/dosimonline/res/fireball.png"));
		setHitBox(0, 0, 32, 32);
		addType("Fireball");
		
		this.isMine = isMine;
		DisplayMode dm = Display.getDesktopDisplayMode();
		direction = new Vector2f(targetX - dm.getWidth() / 2, targetY - dm.getHeight() / 2);
		direction.normalise();
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException
	{
		super.update(gc, delta);

		if (!isMine)
		{
			x += (int) (direction.getX() * velocity);
			y += (int) (direction.getY() * velocity);
		} else
		{
			
		}

		if (collide("Solid", x, y) != null)
		{
			destroy();
		}
		if (shallIDie > 0)
		{
			shallIDie--;
		}
		if (shallIDie == 0)
		{
			destroy();
		}
	}

	@Override
	public void destroy()
	{
		super.destroy();
	}
}
