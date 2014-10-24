package org.dosimonline.tiles;

import it.randomtower.engine.entity.Entity;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Ladder extends Entity {
	public Ladder(float x, float y) throws SlickException {
		super(x, y);
		SpriteSheet tiles = new SpriteSheet("org/dosimonline/res/tiles.png", 128, 128);
		Image image = tiles.getSprite(2, 0);
		setGraphic(image);
		setHitBox(24, 0, 80, 128);
		addType("Ladder");
	}
}
