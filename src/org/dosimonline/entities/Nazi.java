package org.dosimonline.entities;

import it.randomtower.engine.entity.Entity;

import java.util.Random;

import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;
import org.newdawn.slick.geom.Vector2f;

public class Nazi extends Entity {

	private static final float GRAVITY = 8;
	private static final float SPEED = 10;
	private static final float INITIAL_SPEED = 66.66f;
	private static final float CLIMB_SPEED = -500;
	private static final int MAX_VERTICAL_SPEED = 1000;
	private final SpriteSheet naziSheet;
	private final Animation naziWalkLeft;
	private final Animation naziWalkRight;
	private Dos dos;
	private boolean spawned;
	public final int lifeAddTimeout;

	public Nazi(float x, float y) throws SlickException {
		super(x, y);

		dos = null;
		lifeAddTimeout = new Random().nextInt(30);

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

		addType("Anti Semitic"); // Gilnaa: lol wut Itay: yeah LOL
		setHitBox(0, 20, 20, 35);
	}

	private void findDosToChase() {
		Dos closestDos = null;
		float closestDosSquaredDis = 0;

		for (Entity entity : world.getEntities())
			if (entity instanceof Dos) {
				float distanceSquared = new Vector2f(x, y)
					.distanceSquared(new Vector2f(entity.x, entity.y));
				if (distanceSquared > closestDosSquaredDis) {
					closestDosSquaredDis = distanceSquared;
					closestDos = (Dos) entity;
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

		//Gravity.
		if (collide("Solid", x, y + GRAVITY) == null && collide("Ladder", x, y) == null)
			y += GRAVITY;

		//Dos chasing.
		if (collide("Ladder", x, y) != null) {
			if (dos.y - 110 > y) {
				y += GRAVITY;
				if (dos.x > x)
					x -= SPEED;
				else
					x += SPEED;
			}
			if (dos.y - 30 < y) {
				y -= GRAVITY;
				if (dos.x > x)
					x -= SPEED;
				else
					x += SPEED;
			}
		}
		if (dos.x > x && collide("Solid", x + SPEED, y) == null)
			x += SPEED;
		if (dos.x < x && collide("Solid", x - SPEED, y) == null)
			x -= SPEED;

		//Soft landing.
		if (collide("Solid", x, y + 36) != null && collide("Solid", x, y + 1) == null)
			y++;

		// life.
		Dos someDos = (Dos) collide("Dos", x, y);
		if (someDos != null) {
			someDos.life--;
			this.destroy();
		}
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		super.render(gc, g);

		if (dos.x > x)
			g.drawAnimation(naziWalkRight, x, y);
		else
			g.drawAnimation(naziWalkLeft, x, y);
	}
}