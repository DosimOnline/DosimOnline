package org.dosimonline;
import it.randomtower.engine.entity.Entity;
import java.util.Random;
import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class EntityNazi extends Entity
{
    private SpriteSheet naziSheet;
    private Animation naziWalkLeft;
    private Animation naziWalkRight;
    private float gravity = WorldPlains.gravity;
    private int isAfterSpawn = 400;
    private Random random = new Random();
    private int shallAddLife = random.nextInt(30);
    
    public EntityNazi (float x, float y) throws SlickException
    {
        super (x, y);
        naziSheet = new SpriteSheet("org/dosimonline/res/sprites/nazi.png", 20, 55);
        naziWalkLeft = new Animation();
        naziWalkLeft.setAutoUpdate(true);
        naziWalkLeft.addFrame(naziSheet.getSprite(0, 0).getFlippedCopy(true, false), 150);
        naziWalkLeft.addFrame(naziSheet.getSprite(1, 0).getFlippedCopy(true, false), 150);
        
        naziWalkRight = new Animation();
        naziWalkRight.setAutoUpdate(true);
        naziWalkRight.addFrame(naziSheet.getSprite(0, 0), 150);
        naziWalkRight.addFrame(naziSheet.getSprite(1, 0), 150);
        
        addType("Anti Semitic");
        setHitBox(0, 20, 20, 35);
    }

    @Override
    public void destroy() {super.destroy();}

    @Override
    public void update(GameContainer container, int delta) throws SlickException
    {
        super.update(container, delta);
        //Gravity
        if (collide("Solid", x, y + gravity) != null) {y -= gravity;} y += gravity;
        
        if (WorldPlains.dos.x > x)
        {
            if (collide("Solid", x + WorldPlains.naziMoveSpeed, y) != null) 
            {
                if (collide("Solid", x + WorldPlains.naziMoveSpeed, y - 10) == null) {x++; y--;}
            }
        } else
        {
            if (collide("Solid", x - WorldPlains.naziMoveSpeed, y) != null)
            {
                if (collide("Solid", x - WorldPlains.naziMoveSpeed, y - 10) == null) {x--; y--;}
            }
        }
        
        if (collide("Dos", x, y) != null)
        {
            this.destroy();
            WorldPlains.life -= 1;
        }
        if (collide("Fireball", x, y) == null) {} else
        {
            this.destroy();
            WorldPlains.score += 1;
            if (shallAddLife == 0) {WorldPlains.life++;}
        }
        if (WorldPlains.dos.y < y - 10 && collide("Ladder", x, y) != null) {y -= gravity + 4;}
        if (WorldPlains.dos.y >= y - 10 && collide("Ladder", x, y) != null) 
        {
            if (WorldPlains.dos.x > x) {x += WorldPlains.naziMoveSpeed;}
            else {x -= WorldPlains.naziMoveSpeed;}
        }
        
        if (collide("Solid", x, y - gravity) != null) 
        {
            y += gravity;
            if (WorldPlains.dos.x > x) {x += WorldPlains.naziMoveSpeed;}
            else {x -= WorldPlains.naziMoveSpeed;}
        }
        
        //Releasing from spawn limitations
        if (isAfterSpawn > 0) {isAfterSpawn--;}
    }
    
    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException
    {
        super.render(gc, g);
        if (isAfterSpawn > 0) {g.drawImage(naziSheet.getSprite(1, 0), x, y);}
        if (WorldPlains.dos.x > x && isAfterSpawn == 0 && collide("Ladder", x, y) == null)
        {
            if (collide("Solid", x + WorldPlains.naziMoveSpeed, y) == null) {x += WorldPlains.naziMoveSpeed;}
            g.drawAnimation(naziWalkRight, x, y);
        }
        else if (WorldPlains.dos.x < x && isAfterSpawn == 0 && collide("Ladder", x, y) == null)
        {
            if (collide("Solid", x - WorldPlains.naziMoveSpeed, y) == null) {x -= WorldPlains.naziMoveSpeed;}
            g.drawAnimation(naziWalkLeft, x, y);
        }
        else if (WorldPlains.dos.x > x) {g.drawImage(naziSheet.getSprite(1, 0), x, y);}
        else {g.drawImage(naziSheet.getSprite(1, 0).getFlippedCopy(true, false), x, y);}
    }
}