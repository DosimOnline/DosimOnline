package org.dosimonline;
import it.randomtower.engine.entity.Entity;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class TileDirt extends Entity
{
    public TileDirt (float x, float y) throws SlickException
    {
        super (x, y);
        SpriteSheet tiles = new SpriteSheet("org/dosimonline/res/tiles.png", 8, 8);
        Image image = tiles.getSprite(4, 0).getScaledCopy(16);
        setGraphic(image);
        setHitBox (0, 0, 128, 128);
        addType("Solid");
    }
}