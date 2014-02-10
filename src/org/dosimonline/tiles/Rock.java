package org.dosimonline.tiles;

import it.randomtower.engine.entity.Entity;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class Rock extends Entity {
	public Rock(float x, float y) throws SlickException {
		super(x, y);
		SpriteSheet tiles = new SpriteSheet("org/dosimonline/res/tiles.png",
			  16, 16);
		Image image = tiles.getSprite(0, 0).getScaledCopy(8);
		setGraphic(image);
		setHitBox(0, 0, 128, 128);
		addType("Solid");
	}
}
