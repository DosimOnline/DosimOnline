package org.dosimonline;
import it.randomtower.engine.entity.Entity;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class EntityFireball extends Entity
{
    private int shallIDie = 200;
    public static boolean isBomb;
    private int direction = 0; //1 is left, 2 is right!

    
    public EntityFireball(float x, float y) throws SlickException
    {
        super (x, y);
        setGraphic(new Image("org/dosimonline/res/fireball.png"));
        setHitBox(0, 0, 32, 32);
        addType("Fireball");
    }

    @Override
    public void update(GameContainer gc, int delta) throws SlickException
    {
        super.update(gc, delta);

        if (!isBomb)
        {
            if (direction == 0 && EntityDos.direction == 1) {direction = 1;}
            else if (direction == 0 && EntityDos.direction == 2) {direction = 2;}
            if (direction == 1) {x -= 20;} else if (direction == 2) {x += 20;}
        }
        
        if (collide("Solid", x, y) != null) {destroy();}
        if (shallIDie > 0) {shallIDie--;}
        if (shallIDie == 0) {destroy();}
    }

    @Override
    public void destroy()
    {
        super.destroy();
    }
}