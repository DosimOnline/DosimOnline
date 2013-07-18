package org.dosimonline.entities;
import it.randomtower.engine.entity.Entity;

import java.util.Random;
import org.dosimonline.states.Play;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Vector2f;

public class Nazi extends Entity {
	private SpriteSheet naziSheet;
	private Animation naziWalkLeft;
	private Animation naziWalkRight;
	private Dos dos;
	private float gravity = Play.gravity;
	private boolean isAfterSpawn = false;
	private int shallAddLife;
	private static final float NAZI_INITIAL_SPEED = 66.66f;
	private static float moveSpeed = NAZI_INITIAL_SPEED;

	public Nazi(float x, float y) throws SlickException {
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

	private void findDosToChase() {
		Dos closestDos = null;
		float closestDosSquaredDis = 0;

		for (Entity entity : world.getEntities()) {
			if (entity instanceof Dos) {
				float distanceSquared = new Vector2f(x, y)
					  .distanceSquared(new Vector2f(entity.x, entity.y));
				if (distanceSquared > closestDosSquaredDis) {
					closestDosSquaredDis = distanceSquared;
					closestDos = (Dos) entity;
				}
			}
		}

		this.dos = closestDos;
	}

	@Override
	public void destroy() {
		super.destroy();
	}

	@Override
	public void update(GameContainer container, int delta)
		  throws SlickException {
		super.update(container, delta);

		if (dos == null || dos.active == false) {
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
		if (isAfterSpawn == true) {
			if (collide("Ladder", x, y) != null) {
				if (dos.y - 180 > y) {
					y += gravity;
					if (dos.x > x)
						x -= currentSpeed;
					else
						x += currentSpeed;
				}
				if (dos.y - 30 < y) {
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
		Dos someDos = (Dos) collide("Dos", x, y);
		if (someDos != null) {
			someDos.life--;
			this.destroy();
		}

		StarOfDavid someStartOfDavid = (StarOfDavid) collide("Semitic Attack", x, y);
		if (someStartOfDavid != null) {
			someStartOfDavid.getShootingDos().score += 1;
			if (shallAddLife == 0) {
				someStartOfDavid.getShootingDos().life++;
			}
			this.destroy();
		}

		// Soft landing.
		if (collide("Solid", x, y + 36) != null
			  && collide("Solid", x, y + 1) == null) {
			y++;
			isAfterSpawn = true;
		}
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		super.render(gc, g);

		if (dos.x > x && isAfterSpawn == true && collide("Ladder", x, y) == null) {
			g.drawAnimation(naziWalkRight, x, y);
		} else if (dos.x < x && isAfterSpawn == true
			  && collide("Ladder", x, y) == null) {
			g.drawAnimation(naziWalkLeft, x, y);
		} else if (dos.x > x) {
			g.drawImage(naziSheet.getSprite(1, 0), x, y);
		} else {
			g.drawImage(naziSheet.getSprite(1, 0).getFlippedCopy(true, false),
				  x, y);
		}

	}
}