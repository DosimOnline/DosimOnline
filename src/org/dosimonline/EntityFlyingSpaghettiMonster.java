package org.dosimonline;

import it.randomtower.engine.entity.Entity;

import java.util.Random;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

public class EntityFlyingSpaghettiMonster extends Entity
{
	private final int MAX_SPEED = 50;
	private final int CHANGE_DIRECTION_TIME = 7000; // 7sec
	private final int ATTACK_DELAY = 7000;

	private Vector2f direction;
	private float speed = 50;

	private Random random = new Random();
	private int shallChangeDirection;
	private int shallAttack;
	private int life = 3;

	public EntityFlyingSpaghettiMonster(float x, float y)
			throws SlickException
	{
		super(x, y);
		Image image = new Image("org/dosimonline/res/sprites/fsm.png");
		setGraphic(image);
		setHitBox(0, 0, image.getWidth(), image.getHeight());
		addType("Pasta");
		shallChangeDirection = 0;
		shallAttack = 2000;

		direction = new Vector2f();
		newVelocity();
	}

	private void shootOnDos() throws SlickException
	{
		for (Entity entity : world.getEntities())
		{
			if (entity instanceof EntityDos)
			{
				if (shallAttack <= 0 && entity.x > x - 500
						&& entity.x < x + 500 && entity.y > y - 500
						&& entity.y < y + 500)
				{
					world.add(new EntityMeatball(x, y, entity.x, entity.y));
					shallAttack = ATTACK_DELAY;
					return;
				}
			}
		}
	}

	@Override
	public void update(GameContainer container, int delta)
			throws SlickException
	{
		super.update(container, delta);
		shallChangeDirection -= delta;
		shallAttack -= delta;

		shootOnDos();
		
		if (shallChangeDirection <= 0)
		{
			newVelocity();
			shallChangeDirection = CHANGE_DIRECTION_TIME;
		}

		x += direction.getX() * speed * (delta / 1000.0f);
		y += direction.getY() * speed * (delta / 1000.0f);

		EntityDos someDos = (EntityDos) collide("Dos", x, y);
		if (someDos != null)
			someDos.life = 0; // No one can touch the FSM and stay alive!

		Entity fireballColl = collide("Fireball", x, y);
		if (fireballColl != null) // Fireball hit the FSM
		{
			fireballColl.destroy();
			--life;
			
			newVelocity(); // change direction and speed
			
			if (life == 0)
			{
				((EntityFireball) fireballColl).getShootingDos().score += 10;
				this.destroy();
			}
		}

		if (x < WorldPlains.LEFT_BORDER || x + this.currentImage.getWidth() > WorldPlains.RIGHT_BORDER)
			direction.x *= -1;
		
		if (y < WorldPlains.UPPER_BORDER || y + this.currentImage.getHeight() > WorldPlains.LOWER_BORDER)
			direction.y *= -1;
	}

	// return value: random n, min <= n <= max
	private static int randomBetween(Random random, int min, int max)
	{
		if (min <= max)
			return random.nextInt(max + 1 - min) + min;
		return random.nextInt(min - max + 1) + max;
	}

	private void newVelocity()
	{
		speed = randomBetween(random, MAX_SPEED / 2, MAX_SPEED);

		// randomize one direction out of eight
		do
		{
			direction.x = randomBetween(random, -1, 1);
			direction.y = randomBetween(random, -1, 1);
		}
		while (direction.x == 0 && direction.y == 0); // We want to move!

		direction.normalise();
	}

	@Override
	public void destroy()
	{
		super.destroy();
	}
}
