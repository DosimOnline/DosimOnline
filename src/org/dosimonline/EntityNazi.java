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
	private int isAfterSpawn = 400;
	private Random random = new Random();
	private int shallAddLife = random.nextInt(30);
	private float dosX = WorldPlains.dos.x;
	public static float moveSpeed = WorldPlains.naziMoveSpeed;

	public EntityNazi(float x, float y) throws SlickException
	{
		super(x, y);
		naziSheet = new SpriteSheet("org/dosimonline/res/sprites/nazi.png", 20, 55);
		naziWalkLeft = new Animation();
		naziWalkLeft.setAutoUpdate(true);
		naziWalkLeft.addFrame(naziSheet.getSprite(0, 0).getFlippedCopy(true, false), 150);
		naziWalkLeft.addFrame(naziSheet.getSprite(1, 0).getFlippedCopy(true, false), 150);

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
	public void update(GameContainer container, int delta) throws SlickException
	{
		super.update(container, delta);
		dosX = WorldPlains.dos.x;
		moveSpeed += 0.000001;

		//Gravity.
		if (collide("Solid", x, y + gravity) != null)
		{
			y -= gravity;
		}
		y += gravity;

		//Dos chasing.
		if (dosX > x && collide("Ladder", x, y) == null)
		{
			x += moveSpeed;
		} else if (dosX < x && collide("Ladder", x, y) == null)
		{
			x -= moveSpeed;
		}
		if (collide("Solid", x - moveSpeed, y) != null)
		{
			x += moveSpeed;
		} else if (collide("Solid", x + moveSpeed, y) != null)
		{
			x -= moveSpeed;
		}
		if (WorldPlains.dos.y < y + 20 && collide("Ladder", x, y) != null)
		{
			y -= gravity + 4;
		}
		if (WorldPlains.dos.y >= y - 20 && collide("Ladder", x, y) != null)
		{
			if (dosX > x)
			{
				x += moveSpeed;
			} else
			{
				x -= moveSpeed;
			}
		}

		//Scoring.
		if (collide("Dos", x, y) != null)
		{
			this.destroy();
			WorldPlains.dos.life -= 1;
		}
		if (collide("Fireball", x, y) != null)
		{
			this.destroy();
			WorldPlains.dos.score += 1;
			if (shallAddLife == 0)
			{
				WorldPlains.dos.life++;
			}
		}

		//Preventing the Nazi from colliding ceilings while climbing.
		if (collide("Solid", x, y - gravity) != null && collide("Solid", x, y + gravity) == null)
		{
			y += gravity;
			if (WorldPlains.dos.x > x)
			{
				x += moveSpeed;
			} else
			{
				x -= moveSpeed;
			}
		}

		//Soft landing.
		if (collide("Solid", x, y + 36) != null && collide("Solid", x, y + 1) == null)
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
			if (collide("Solid", x + moveSpeed, y) == null)
			{
				x += moveSpeed;
			}
			g.drawAnimation(naziWalkRight, x, y);
		} else if (dosX < x && isAfterSpawn == 0 && collide("Ladder", x, y) == null)
		{
			if (collide("Solid", x - moveSpeed, y) == null)
			{
				x -= moveSpeed;
			}
			g.drawAnimation(naziWalkLeft, x, y);
		} else if (dosX > x)
		{
			g.drawImage(naziSheet.getSprite(1, 0), x, y);
		} else
		{
			g.drawImage(naziSheet.getSprite(1, 0).getFlippedCopy(true, false), x, y);
		}
	}
}