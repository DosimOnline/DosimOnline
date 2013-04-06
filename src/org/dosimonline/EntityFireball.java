package org.dosimonline;
import it.randomtower.engine.entity.Entity;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class EntityFireball extends Entity
{
    private int shallIDie = 100;
    
    public EntityFireball(float x, float y) throws SlickException
    {
        super (x, y);
        setGraphic(new Image("org/dosimonline/res/fireball.png"));
        setHitBox(0, 0, 32, 32);
        addType("Fireball");
    }

    @Override
    public void update(GameContainer container, int delta) throws SlickException
    {
        super.update(container, delta);
        if (EntityDos.direction == 1) {x -= 20;}
        else {x += 20;}
        if (collide("Solid", x, y) == null) {} else {destroy();}
        if (shallIDie > 0) {shallIDie--;}
        if (shallIDie == 0) {destroy();}
    }

    @Override
    public void destroy()
    {
        super.destroy();
    }
}
