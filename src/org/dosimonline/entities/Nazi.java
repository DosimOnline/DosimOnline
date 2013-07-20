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
    private static final float GRAVITY = 1000;
	private static final float INITIAL_SPEED = 66.66f;
        private static final float CLIMB_SPEED = -500;
        private static final int MAX_VERTICAL_SPEED = 1000;
        
	private SpriteSheet naziSheet;
	private Animation naziWalkLeft;
	private Animation naziWalkRight;
	private Dos dos;
        private float velocityY;
	private boolean spawned;
	private int lifeAddTimeout;
        private static float moveSpeed = INITIAL_SPEED;
	
        
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

		addType("Anti Semitic"); // Gilnaa: lol wut
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
                
                float nextX = x;
                if (dos.x < x) {
                    nextX -= INITIAL_SPEED * (delta / 1000f);
                } else if (dos.x > x) {
                    nextX += INITIAL_SPEED * (delta / 1000f);
                }
                if(collide("Solid", nextX, y) == null) {
                    x = nextX;
                }
                
                if(collide("Ladder", x, y) == null) {
                    // Going up
                    if(velocityY < 0) {
                        if(collide("Solid", x, y - velocityY) != null) {
                            velocityY = 0;
                        }
                    }
                } else {
                    if (dos.y > y)
                        velocityY = -CLIMB_SPEED * (delta / 1000f);
                    else if (dos.y < y)
                        velocityY = +CLIMB_SPEED * (delta / 1000f);
                }
                y += velocityY;

                if(isAirborne()) {
                    if(velocityY < MAX_VERTICAL_SPEED)
                        velocityY += 10 * (delta / 1000f);
                } else {
                    if(velocityY > 0) {
                        y = (int)y;
                        while (collide("Solid", x, y) != null)
                            y -= 1;
                    }
                    velocityY = 0;
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
			if (lifeAddTimeout == 0) {
				someStartOfDavid.getShootingDos().life++;
			}
			this.destroy();
		}
	}
        private boolean isAirborne() {
            return collide("Solid", x, y) == null && 
                   collide("Ladder", x, y) == null;
        }
	@Override
	public void render(GameContainer gc, Graphics g) throws SlickException {
		super.render(gc, g);

		if (dos.x > x && spawned == true && collide("Ladder", x, y) == null) {
			g.drawAnimation(naziWalkRight, x, y);
		} else if (dos.x < x && spawned == true
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