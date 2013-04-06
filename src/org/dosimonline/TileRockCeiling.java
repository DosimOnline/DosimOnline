package org.dosimonline;
import it.randomtower.engine.entity.Entity;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class TileRockCeiling extends Entity
{
    private SpriteSheet spriteSheet = new SpriteSheet("org/dosimonline/res/tiles/rock.png", 128, 64);
    
    public TileRockCeiling (float x, float y) throws SlickException
    {
        super (x, y);
        setGraphic(spriteSheet.getSprite(0, 1));
        setHitBox (0, 0, 128, 128);
        addType ("Solid");
    }
}
