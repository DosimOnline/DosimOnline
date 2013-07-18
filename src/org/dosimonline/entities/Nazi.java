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
	private SpriteSheet naziSheet;
	private Animation naziWalkLeft;
	private Animation naziWalkRight;
	private Dos dos;
        private float velocityY;
	private boolean isAfterSpawn = false;
	private int shallAddLife;
        private static final float GRAVITY = 1000;
	private static final float NAZI_INITIAL_SPEED = 66.66f;
        private static final float CLIMB_SPEED = -500;
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
                // Dos chasing.
		if (isAfterSpawn) {
                    if (collide("Ladder", x, y) != null) {
                        if (dos.y - 180 > y) {
                            velocityY -= CLIMB_SPEED * (delta / 1000.0f);
                        }
                        if (dos.y < y) {
                            velocityY += CLIMB_SPEED * (delta / 1000.0f);
                        }

                    }
                    if (dos.x > x && collide("Solid", x + currentSpeed, y) == null)
                            x += currentSpeed;
                    if (dos.x < x && collide("Solid", x - currentSpeed, y) == null)
                            x -= currentSpeed;
		}
		// Gravity.
                float nextY = y + velocityY * (delta / 1000.0f);
                
                Entity surface = collide("Solid", x, nextY);
                Entity ladder = collide("Ladder", x, nextY);
                if (ladder == null) {
                    if (surface == null) {
                        velocityY += GRAVITY * (delta / 1000.0f);
                    } else  {
                        velocityY = 0;
                        nextY = y;
                    }                
                }
                y = nextY;
                // Soft landing.
		if (collide("Solid", x, y + 36) != null
			  && collide("Solid", x, y + 1) == null) {
			y++;
			isAfterSpawn = true;
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