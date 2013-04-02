package org.dosimonline;
import it.randomtower.engine.entity.Entity;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class TileTransparent extends Entity
{
    public TileTransparent (float x, float y) throws SlickException
    {
        super (x, y);
        setGraphic(new Image("org/dosimonline/res/tiles/transparent.png"));
        setHitBox (0, 0, 128, 128);
        addType ("Solid");
    }
}
