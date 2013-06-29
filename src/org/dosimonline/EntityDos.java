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
	private float moveSpeed = 600;
	public int lookDirection = 1; // 1 is left, 2 is right.
	
	public int life;
	public int score;
	private int attackAllowed = 0;
	
	private float JUMP_SPEED = -500;
	private float CLIMB_SPEED = -500;
	
	private float velocityY;
	
	private static final float GRAVITY = 1000;
	private static final int ATTACK_DELAY = 1800;
	
	private boolean onHack;
	
	public EntityDos(float x, float y) throws SlickException
	{
		super(x, y);
		SpriteSheet dosSheet = new SpriteSheet(
				"org/dosimonline/res/sprites/dos.png", 20, 36);
		dosWalkLeft = new Animation();
		dosWalkLeft.setAutoUpdate(true);
		dosWalkLeft.addFrame(
				dosSheet.getSprite(0, 0).getFlippedCopy(true, false), 150);
		dosWalkLeft.addFrame(
				dosSheet.getSprite(1, 0).getFlippedCopy(true, false), 150);
		
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
		
		score = 0;
		life = 5;
		velocityY = 0;
		jumpAllowed = false;
	}
	
	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException
	{
		super.render(gc, g);
		
		if (jumpAllowed)
		{
			if (check("left") || check("right"))
			{
				if (lookDirection == 1)
					g.drawAnimation(dosWalkLeft, x, y);
				else if (lookDirection == 2)
					g.drawAnimation(dosWalkRight, x, y);
			}
			else
			{
				if (lookDirection == 1)
					g.drawImage(dosStanding.getFlippedCopy(true, false), x, y);
				else if (lookDirection == 2)
					g.drawImage(dosStanding, x, y);
			}
		}
		else
		{
			if (lookDirection == 1)
				g.drawImage(dosJumping.getFlippedCopy(true, false), x, y);
			else if (lookDirection == 2)
				g.drawImage(dosJumping, x, y);
		}
	}
	
	@Override
	public void update(GameContainer gc, int delta) throws SlickException
	{
		super.update(gc, delta);
		
		// Dos's direction (Only graphical).
		if (gc.getInput().getMouseX() > this.x + world.camera.x + dosStanding.getWidth() / 2)
			lookDirection = 2;
		else
			lookDirection = 1;
		
		// Movement.
		float nextRightX = x + moveSpeed * (delta / 1000.0f);
		float nextLeftX = x - moveSpeed * (delta / 1000.0f);
		
		if (check("right")
				&& nextRightX + dosStanding.getWidth() < WorldPlains.RIGHT_BORDER
				&& collide("Solid", nextRightX, y) == null)
		{
			x = nextRightX;
		}
		else if (check("left") && nextLeftX > WorldPlains.LEFT_BORDER
				&& collide("Solid", nextLeftX, y) == null)
		{
			x = nextLeftX;
		}
		
		if (jumpAllowed && check("up")) // Jump
		{
			jumpAllowed = false;
			velocityY = JUMP_SPEED;
		}
		else
		// Update jumpAllowed (true if the dos is standing)
		{
			jumpAllowed = collide("Solid", x, y + 0.5f) != null;
		}
		
		boolean collidesWithLadder = collide("Ladder", x, y) != null;
		
		if (collidesWithLadder)
		{
			if (check("up"))
			{
				velocityY = CLIMB_SPEED;
			}
			else if (onHack == false) // Go down if the dos is not close to the ground!
			{
				velocityY = -CLIMB_SPEED;
			}
		}
		
		float nextY = y + velocityY * (delta / 1000.0f);
		
		// Check for collision with the surface after the current move
		Entity surface = collide("Solid", x, nextY);
		
		if (jumpAllowed == false && surface == null) // Apply gravity
		{
			if (!collidesWithLadder)
			{
				velocityY += GRAVITY * (delta / 1000.0f);
			}
		}
		else if (jumpAllowed == false)
		{
			// Hack: We can't move in the regular way anymore, so make the
			// velocity smaller until the dos is half pixel above surface
			onHack = true;
			velocityY /= 2f;
			nextY = y; // Do not move!
		}
		else
		// We are half pixel above the surface!
		{
			onHack = false;
			velocityY = 0;
			nextY = y;
			jumpAllowed = true;
		}
		
		y = nextY;
		
		if (this.life == 0)
			this.destroy();
		
		// Poopee.
		if (attackAllowed > 0)
			attackAllowed -= delta;
		
		Debug.show("Reload: " + attackAllowed);
		Debug.show("Jump allowed= " + jumpAllowed);
	}
	
	public boolean canAttack()
	{
		return this.attackAllowed <= 0;
	}
	
	public void shoot(float targetX, float targetY) throws SlickException
	{
		if (canAttack())
		{
			world.add(new EntityFireball(x, y, targetX, targetY, this));
			attackAllowed = ATTACK_DELAY;
		}
	}
	
	public void placeMine() throws SlickException
	{
		if (canAttack())
		{
			world.add(new EntityFireball(x, y, x, y, this));
			attackAllowed = ATTACK_DELAY;
		}
	}
}