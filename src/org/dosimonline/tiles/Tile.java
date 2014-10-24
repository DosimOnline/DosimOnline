package org.dosimonline.tiles;
import it.randomtower.engine.entity.Entity;
import org.newdawn.slick.*;

public class Tile extends Entity {
	public Tile(float x, float y, int sheetPos, String type) throws SlickException {
		super(x, y);
		SpriteSheet tiles = new SpriteSheet("org/dosimonline/res/tiles.png", 128, 128);
		Image image = tiles.getSprite(sheetPos, 0);
		setGraphic(image);
		setHitBox(0, 0, 128, 128);
		addType(type);
	}
}
