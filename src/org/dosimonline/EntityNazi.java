package org.dosimonline;

import it.randomtower.engine.entity.Entity;
import java.util.Random;
import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class EntityNazi extends Entity
{
	private SpriteSheet naziSheet;
	private Animation naziWalkLeft;
	private Animation naziWalkRight;
	private float gravity = WorldPlains.gravity;
	private int isAfterSpawn = 600;
	private Random random = new Random();
	private int shallAddLife = random.nextInt(30);
	private float dosX, dosY;
	private EntityDos dos;
	
	public static float moveSpeed = 4.0f;

	public EntityNazi(float x, float y, EntityDos dos) throws SlickException
	{
		super(x, y);
		this.dos = dos;
		
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

		moveSpeed += 0.00001f;

		// Gravity.
		if (collide("Solid", x, y + gravity) == null
				&& collide("Ladder", x, y) == null)
			y += gravity;

		// Dos chasing.
		if (isAfterSpawn == 0)
		{
			if (collide("Ladder", x, y) != null)
			{
				if (dosY - 110 > y)
				{
					y += gravity;
					if (dosX > x)
						x -= moveSpeed;
					else
						x += moveSpeed;
				}
				if (dosY - 30 < y)
				{
					y -= gravity;
					if (dosX > x)
						x -= moveSpeed;
					else
						x += moveSpeed;
				}
			}
			if (dosX > x && collide("Solid", x + moveSpeed, y) == null)
				x += moveSpeed;
			if (dosX < x && collide("Solid", x - moveSpeed, y) == null)
				x -= moveSpeed;
		}

		// Scoring.
		if (collide("Dos", x, y) != null)
		{
			this.destroy();
			dos.life--;
		}
		if (collide("Fireball", x, y) != null)
		{
			this.destroy();
			dos.score += 1;
			if (shallAddLife == 0)
			{
				dos.life++;
			}
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
			isAfterSpawn--;
		}
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException
	{
		super.render(gc, g);
		if (dosX > x && isAfterSpawn == 0 && collide("Ladder", x, y) == null)
		{
			g.drawAnimation(naziWalkRight, x, y);
		}
		else if (dosX < x && isAfterSpawn == 0
				&& collide("Ladder", x, y) == null)
		{
			g.drawAnimation(naziWalkLeft, x, y);
		}
		else if (dosX > x)
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