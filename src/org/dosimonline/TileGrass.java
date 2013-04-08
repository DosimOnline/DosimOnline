package org.dosimonline;
import it.randomtower.engine.entity.Entity;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class TileGrass extends Entity
{
    public TileGrass (float x, float y) throws SlickException
    {
        super (x, y);
        setGraphic(new Image("org/dosimonline/res/tiles/grass.png"));
        setHitBox (0, 0, 128, 128);
        addType ("Solid");
    }
}