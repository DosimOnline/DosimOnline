package org.dosimonline;
import it.randomtower.engine.entity.Entity;
import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class EntityNazi extends Entity
{
    private Animation naziWalkLeft;
    private Animation naziWalkRight;
    
    public EntityNazi (float x, float y) throws SlickException
    {
        super (x, y);
        SpriteSheet naziSheet = new SpriteSheet("org/dosimonline/res/sprites/nazi.png", 20, 35);
        naziWalkLeft = new Animation();
        naziWalkLeft.setAutoUpdate(true);
        naziWalkLeft.addFrame(naziSheet.getSprite(0, 0).getFlippedCopy(true, false), 150);
        naziWalkLeft.addFrame(naziSheet.getSprite(1, 0).getFlippedCopy(true, false), 150);
        
        naziWalkRight = new Animation();
        naziWalkRight.setAutoUpdate(true);
        naziWalkRight.addFrame(naziSheet.getSprite(0, 0), 150);
        naziWalkRight.addFrame(naziSheet.getSprite(1, 0), 150);
        
        addType("Anti Semitic");
        setHitBox(0, 0, 20, 35);
    }

    @Override
    public void destroy() {super.destroy();}

    @Override
    public void update(GameContainer container, int delta) throws SlickException
    {
        super.update(container, delta);
        if (collide("Dos", x, y) == null) {} else 
        {
            this.destroy();
            WorldPlains.score -= 1;
        }
        if (collide("Fireball", x, y) == null) {} else
        {
            this.destroy();
            WorldPlains.score += 1;
        }
    }
    
    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException
    {
        super.render(gc, g);
        if (collide("Solid", x, y + 4) == null) {y += 4;}
        
        if (WorldPlains.dos.x > x)
        {
            if (collide("Solid", x + WorldPlains.naziMoveSpeed, y) == null) {x += WorldPlains.naziMoveSpeed;}
            else {y -= 4.5;}
            g.drawAnimation(naziWalkRight, x, y);
        } else
        {
            if (collide("Solid", x - WorldPlains.naziMoveSpeed, y) == null) {x -= WorldPlains.naziMoveSpeed;}
            else {y -= 4.5;}
            g.drawAnimation(naziWalkLeft, x, y);
        }
    }
}
