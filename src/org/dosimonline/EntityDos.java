package org.dosimonline;
import it.randomtower.engine.entity.Entity;
import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class EntityDos extends Entity
{
	private Animation dosWalkLeft;
	private Animation dosWalkRight;
	private Image dosStanding;
	private Image dosJumping;
	private boolean jumpAllowed = true;
	private short jump;
	public static byte direction = 1; // 1 is left, 2 is right.
	private float gravity = WorldPlains.gravity;
	private float moveSpeed = 6;
	public int life;
	public int score;
	public short attackAllowed = 0;

	public EntityDos(float x, float y) throws SlickException
	{
		super(x, y);
		SpriteSheet dosSheet = new SpriteSheet("org/dosimonline/res/sprites/dos.png", 20, 36);
		dosWalkLeft = new Animation();
		dosWalkLeft.setAutoUpdate(true);
		dosWalkLeft.addFrame(dosSheet.getSprite(0, 0).getFlippedCopy(true, false), 150);
		dosWalkLeft.addFrame(dosSheet.getSprite(1, 0).getFlippedCopy(true, false), 150);

		life = 5;

		dosWalkRight = new Animation();
		dosWalkRight.setAutoUpdate(true);
		dosWalkRight.addFrame(dosSheet.getSprite(0, 0), 150);
		dosWalkRight.addFrame(dosSheet.getSprite(1, 0), 150);
		dosStanding = dosSheet.getSprite(0, 0);
		dosJumping = dosSheet.getSprite(2, 0);
		define("right", Input.KEY_D);
		define("left", Input.KEY_A);
		define("up", Input.KEY_W);
		define("q", Input.KEY_Q);
		setHitBox(0, 0, dosStanding.getWidth(), dosStanding.getHeight());
		addType("Dos");
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException
	{
		super.render(gc, g);

		if ((check("left") || check("right")) && direction == 2 && collide("Solid", x, y + gravity) != null)
		{
			g.drawAnimation(dosWalkRight, x, y);
		} else if ((check("left") || check("right")) && direction == 1 && collide("Solid", x, y + gravity) != null)
		{
			g.drawAnimation(dosWalkLeft, x, y);
		} else if (direction == 1 && collide("Solid", x, y + gravity) != null)
		{
			g.drawImage(dosStanding.getFlippedCopy(true, false), x, y);
		} else if (direction == 2 && collide("Solid", x, y + gravity) != null)
		{
			g.drawImage(dosStanding, x, y);
		} else if (direction == 1)
		{
			g.drawImage(dosJumping.getFlippedCopy(true, false), x, y);
		} else if (direction == 2)
		{
			g.drawImage(dosJumping, x, y);
		}
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException
	{
		super.update(gc, delta);

		//Dos's direction (Only graphical).
		if (gc.getInput().getMouseX() > DosimOnline.dm.getWidth() / 2)
		{
			direction = 2;
		} else
		{
			direction = 1;
		}

		//Movement.
		if (check("right") && collide("Solid", x + moveSpeed, y) == null)
		{
			x += moveSpeed;
		} else if (check("left") && collide("Solid", x - moveSpeed, y) == null)
		{
			x -= moveSpeed;
		}

		//Jumping.
		if (jumpAllowed && check("up"))
		{
			jumpAllowed = false;
			if (collide("Solid", x, y) == null)
			{
				jump = 30;
			}
		}
		if (jump > 15)	//This part smoothes the jump.
		{
			jump--;
			if (collide("Solid", x, y) == null)
			{
				y -= gravity * 2;
			}
		} else if (jump > 5)
		{
			jump--;
			if (collide("Solid", x, y) == null)
			{
				y -= gravity * 1.5;
			}
		} else if (jump > 0)
		{
			jump--;
			if (collide("Solid", x, y) == null)
			{
				y -= gravity * 1.15;
			}
		}				//No more jump smoothing!
		if (collide("Solid", x, y) != null)
		{
			jump = 0;
		}

		// Gravity
		if (collide("Solid", x, y + gravity) == null)
		{
			y += gravity;
			jumpAllowed = false;
		} else
		{
			jumpAllowed = true;
		}
		
		//Preventing from exiting world bounds.
		if (this.x <= 600)
		{
			x += moveSpeed;
		}
		if (this.x >= 7000)
		{
			x -= moveSpeed;
		}
		
		//Death.
		if (life == 0)
		{
			this.destroy();
		}
		
		//Preventing from the dos to launch when touching a ladder and jumping.
		if (collide("Ladder", x, y) != null && jump > 0)
		{
			jump = 0;
		}
		
		//Climbing.
		if (collide("Ladder", x, y) != null)
		{
			if (check("up"))
			{
				y -= gravity * 2;
			}
		}
		
		//Soft landing.
		if (collide("Solid", x, y + 36) != null && collide("Solid", x, y + 1) == null)
		{
			y++;
		}
		
		//Poopee.
		if (attackAllowed > 0)
		{
			attackAllowed--;
		}
	}
}