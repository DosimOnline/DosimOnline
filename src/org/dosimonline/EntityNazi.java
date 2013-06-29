package org.dosimonline;

import it.randomtower.engine.entity.Entity;

import java.util.Random;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Vector2f;

public class EntityNazi extends Entity
{
	private SpriteSheet naziSheet;
	private Animation naziWalkLeft;
	private Animation naziWalkRight;

	private EntityDos dos;

	private float gravity = WorldPlains.gravity;
	private int isAfterSpawn = 10000; // 10 seconds
	private int shallAddLife;

	private static final float NAZI_INITIAL_SPEED = 66.66f;
	private static float moveSpeed = NAZI_INITIAL_SPEED;

	public EntityNazi(float x, float y) throws SlickException
	{
		super(x, y);

		dos = null;
		shallAddLife = new Random().nextInt(30);

		naziSheet = new SpriteSheet("org/dosimonline/res/sprites/nazi.png", 20,
				55);
		naziWalkLeft = new Animation();
		naziWalkLeft.setAutoUpdate(true);
		naziWalkLeft.addFrame(
				naziSheet.getSprite(0, 0).getFlippedCopy(true, false), 150);
		naziWalkLeft.addFrame(
				naziSheet.getSprite(1, 0).getFlippedCopy(true, false), 150);

		naziWalkRight = new Animation();
		naziWalkRight.setAutoUpdate(true);
		naziWalkRight.addFrame(naziSheet.getSprite(0, 0), 150);
		naziWalkRight.addFrame(naziSheet.getSprite(1, 0), 150);

		addType("Anti Semitic");
		setHitBox(0, 20, 20, 35);
	}

	private void findDosToChase()
	{
		EntityDos closestDos = null;
		float closestDosSquaredDis = 0;

		for (Entity entity : world.getEntities())
		{
			if (entity instanceof EntityDos)
			{
				float distanceSquared = new Vector2f(x, y)
						.distanceSquared(new Vector2f(entity.x, entity.y));
				if (distanceSquared > closestDosSquaredDis)
				{
					closestDosSquaredDis = distanceSquared;
					closestDos = (EntityDos) entity;
				}
			}
		}

		this.dos = closestDos;
	}

	@Override
	public void destroy()
	{
		super.destroy();
	}

	@Override
	public void update(GameContainer container, int delta)
			throws SlickException
	{
		super.update(container, delta);

		if (dos == null || dos.active == false)
		{
			findDosToChase();
			if (dos == null)
				this.destroy();
		}

		moveSpeed += 0.000166f;

		float currentSpeed = moveSpeed * (delta / 1000.0f);
		// Gravity.
		if (collide("Solid", x, y + gravity) == null
				&& collide("Ladder", x, y) == null)
			y += gravity;

		// Dos chasing.
		if (isAfterSpawn == 0)
		{
			if (collide("Ladder", x, y) != null)
			{
				if (dos.y - 110 > y)
				{
					y += gravity;
					if (dos.x > x)
						x -= currentSpeed;
					else
						x += currentSpeed;
				}
				if (dos.y - 30 < y)
				{
					y -= gravity;
					if (dos.x > x)
						x -= currentSpeed;
					else
						x += currentSpeed;
				}
			}
			if (dos.x > x && collide("Solid", x + currentSpeed, y) == null)
				x += currentSpeed;
			if (dos.x < x && collide("Solid", x - currentSpeed, y) == null)
				x -= currentSpeed;
		}

		// Scoring.
		EntityDos someDos = (EntityDos) collide("Dos", x, y);
		if (someDos != null)
		{
			someDos.life--;
			this.destroy();
		}

		EntityFireball someFireball = (EntityFireball) collide("Fireball", x, y);
		if (someFireball != null)
		{
			someFireball.getShootingDos().score += 1;
			if (shallAddLife == 0)
			{
				someFireball.getShootingDos().life++;
			}
			this.destroy();
		}

		// Soft landing.
		if (collide("Solid", x, y + 36) != null
				&& collide("Solid", x, y + 1) == null)
		{
			y++;
		}

		// Releasing from spawn limitations
		if (isAfterSpawn > 0)
		{
			isAfterSpawn -= delta;
		}
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException
	{
		super.render(gc, g);

		if (dos.x > x && isAfterSpawn == 0 && collide("Ladder", x, y) == null)
		{
			g.drawAnimation(naziWalkRight, x, y);
		}
		else if (dos.x < x && isAfterSpawn == 0
				&& collide("Ladder", x, y) == null)
		{
			g.drawAnimation(naziWalkLeft, x, y);
		}
		else if (dos.x > x)
		{
			g.drawImage(naziSheet.getSprite(1, 0), x, y);
		}
		else
		{
			g.drawImage(naziSheet.getSprite(1, 0).getFlippedCopy(true, false),
					x, y);
		}

	}
}