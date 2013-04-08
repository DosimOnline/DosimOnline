package org.dosimonline;
import it.randomtower.engine.entity.Entity;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class TileBackground extends Entity
{
    public TileBackground(float x, float y) throws SlickException
    {
        super (x, y);
        Image image = new Image("org/dosimonline/res/tiles/background.png");
        setGraphic(image);
    }
}