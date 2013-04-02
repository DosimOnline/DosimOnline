package org.dosimonline;
import it.randomtower.engine.entity.Entity;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class EntityWizard extends Entity
{
    private int direction = 2; //1 is left, 2 is right.
    private Image wizard = new Image("org/dosimonline/res/sprites/wizard.png");
    
    public EntityWizard(float x, float y) throws SlickException
    {
        super (x, y);
        setGraphic(wizard);
        setHitBox(0, 0, 73, 72);
    }
    
    @Override
    public void update(GameContainer gc, int delta) throws SlickException
    {
        super.update(gc, delta);
        if (collide("Solid", x + 1, y) == null) {} else {direction = 1;}
        if (collide("Solid", x - 1, y) == null) {} else {direction = 2;}
        
        if (direction == 2) 
        {
            x++;
            currentImage = wizard;
        } 
        else
        {
            x--;
            currentImage = wizard.getFlippedCopy(true, false);
        }
    }
}
