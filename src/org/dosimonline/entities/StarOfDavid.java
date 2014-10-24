package org.dosimonline.entities;

import it.randomtower.engine.entity.Entity;
import java.util.Random;

import org.dosimonline.NotificationManager;
import org.dosimonline.states.Play;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Sound;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Vector2f;

public class StarOfDavid extends Entity {
	private final Image image;
	private Sound doubleKill, tripleKill, multiKill;
	private Vector2f direction;
	private int deathTimeout = 2000; // milliseconds
	private final float moveSpeed = 1200; // px/s
	private final Dos shootingDos;
	public int kills;
	private final NotificationManager notifyManager;

	public StarOfDavid(float x, float y, float targetX, float targetY,
			Dos shootingDos) throws SlickException {
		super(x, y);

		image = new Image("org/dosimonline/res/starOfDavid.png");
		setGraphic(image);
		setHitBox(0, 0, image.getWidth(), image.getHeight());
		addType("Star of David");
		setCentered(true);

		doubleKill = new Sound("org/dosimonline/res/audio/double_airhorn.ogg");
		tripleKill = new Sound("org/dosimonline/res/audio/triple_airhorn.ogg");
		multiKill = new Sound("org/dosimonline/res/audio/wow.ogg");

		this.shootingDos = shootingDos;

		if (x == targetX && y == targetY) // Mine (doesn't move)

			direction = new Vector2f();
		else
			direction = new Vector2f(targetX - x, targetY - y);
		direction.normalise();

		notifyManager = NotificationManager.getInstance();
	}

	public Dos getShootingDos() {
		return this.shootingDos;
	}

	@Override
	public void update(GameContainer gc, int delta) throws SlickException {
		super.update(gc, delta);
		image.rotate(10);

		x += direction.getX() * moveSpeed * (delta / 1000.0f);
		y += direction.getY() * moveSpeed * (delta / 1000.0f);

		Nazi someNazi = (Nazi) collide("Nazi", x, y);
		if (someNazi != null) {
			shootingDos.life += (someNazi.lifeAddTimeout == 0 ? 1 : 0);
			someNazi.destroy();
			kills++;
		}
		if (collide("Solid", x, y) != null)
			this.destroy();

		if (deathTimeout > 0)
			deathTimeout -= delta;
		else
			this.destroy();

	}

	@Override
	public void destroy() {
		shootingDos.score += kills > 1 ? kills * 2 : kills;
		Play.raiseNazisKilled(kills); // No actual impact yet. Debug only.

		if (kills == 2) {
			notifyManager.add("wow, double kill!", Color.green);
			Play.drawMLGText(3500);
			if (Play.getIsMLG())
				doubleKill.play();
		}
		if (kills == 3) {
			notifyManager.add("OH BABY A TRIPLE!!", Color.blue);
			Play.drawMLGText(6000);
			if (Play.getIsMLG())
				tripleKill.play();
		}
		if (kills > 3) {
			notifyManager.add("OMG THEY'RE ALL DOWN", Color.red);
			Play.drawMLGText(10000);
			if (Play.getIsMLG())
				multiKill.play();
		}
		super.destroy();
	}
}
