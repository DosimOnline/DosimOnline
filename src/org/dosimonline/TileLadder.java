package org.dosimonline;
import it.randomtower.engine.entity.Entity;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class TileLadder extends Entity
{
    public TileLadder(float x, float y) throws SlickException
    {
        super (x, y);
        setGraphic(new Image("org/dosimonline/res/tiles/ladder.png"));
        setHitBox(0, 0, 128, 128);
        addType("Ladder");
    }
}
