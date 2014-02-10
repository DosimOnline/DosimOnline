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
	private static final float GRAVITY = 8;
	private static final float SPEED = 5f;
	private final SpriteSheet naziSheet;
	private final Animation walkLeft;
	private final Animation walkRight;
	private Dos dos;
	private int naziWidth, naziHeight;
	public final int lifeAddTimeout;

	public Nazi(float x, float y) throws SlickException {
		super(x, y);

		dos = null;
		lifeAddTimeout = new Random().nextInt(30);

		naziSheet = new SpriteSheet("org/dosimonline/res/sprites/nazi.png", 20,
			  55);
		walkLeft = new Animation();
		walkLeft.setAutoUpdate(true);
		walkLeft.addFrame(
			  naziSheet.getSprite(0, 0).getFlippedCopy(true, false), 150);
		walkLeft.addFrame(
			  naziSheet.getSprite(1, 0).getFlippedCopy(true, false), 150);

		walkRight = new Animation();
		walkRight.setAutoUpdate(true);
		walkRight.addFrame(naziSheet.getSprite(0, 0), 150);
		walkRight.addFrame(naziSheet.getSprite(1, 0), 150);
		
		// There is no actual reason for picking walkLeft over walkRight; like many things here, it was chosen randomly.
		naziWidth = walkLeft.getCurrentFrame().getWidth();
		naziHeight = walkLeft.getCurrentFrame().getHeight();
		
		name = generateName();
		
		addType("Nazi"); // Gilnaa: lol wut Itay: yeah LOL
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
	public void update(GameContainer container, int delta)
		  throws SlickException {
		super.update(container, delta);

		if (dos == null || dos.active == false)
			findDosToChase();

		if (dos != null) {

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
				else if (dos.y - 30 < y) {
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
	}

	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		super.render(gc, g);
		Play.drawCenteredString(name, g, (int) x + naziWidth / 2, (int) y - naziWidth);
		
		if (dos != null)
			if (dos.x > x)
				g.drawAnimation(walkRight, x, y);
			else
				g.drawAnimation(walkLeft, x, y);
	}
	
	private String generateName() {
		String names[] = {
			"NO JEWZ PLZ",
			"HOMOHATER",
			"ANTI HOMO",
			"ANTI JEW",
			"ADULF IZ LUV",
			"NO NIGGAZ PLS",
			"SUP NIGGA",
			"BLACK AINT MA COLOUR",
			"HOW GISPY",
		};

		Random random = new Random();
		return names[random.nextInt(names.length)];
	}
}
