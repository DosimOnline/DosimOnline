package org.dosimonline.tiles;

import it.randomtower.engine.entity.Entity;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class SynBrickCeiling extends Entity {
	public SynBrickCeiling(float x, float y) throws SlickException {
		super(x, y);
		SpriteSheet tiles = new SpriteSheet("org/dosimonline/res/tiles.png",
			  128, 64);
		Image image = tiles.getSprite(3, 1);
		setGraphic(image);
		setHitBox(0, 0, 128, 64);
		addType("Solid");
	}
}
