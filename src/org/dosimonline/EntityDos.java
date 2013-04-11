package org.dosimonline;
import it.randomtower.engine.entity.Entity;
import org.newdawn.slick.Animation;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.SpriteSheet;

public class EntityDos extends Entity
{
    private Animation dosWalkLeft;
    private Animation dosWalkRight;
    private Image dosStanding;
    private boolean jumpAllowed = true;
    private float jump;
    public static int direction = 1; //1 is left, 2 is right.
    private float gravity = WorldPlains.gravity;
    private float moveSpeed = 5;
    
    public EntityDos (float x, float y) throws SlickException
    {
        super(x, y);
        SpriteSheet dosSheet = new SpriteSheet("org/dosimonline/res/sprites/dos.png", 20, 36);
        dosWalkLeft = new Animation();
        dosWalkLeft.setAutoUpdate(true);
        for (int a = 1; a <= 3; a++)
        { dosWalkLeft.addFrame(dosSheet.getSprite(a, 0).getFlippedCopy(true, false), 150); }
        
        dosWalkRight = new Animation();
        dosWalkRight.setAutoUpdate(true);
        for (int a = 1; a <= 3; a++)
        { dosWalkRight.addFrame(dosSheet.getSprite(a, 0), 150); }
        dosStanding = dosSheet.getSprite(0, 0);
        define("right", Input.KEY_D);
        define("left", Input.KEY_A);
        define("up", Input.KEY_W);
        setHitBox(0, 0, dosStanding.getWidth(), dosStanding.getHeight());
        addType("Dos");
    }
    
    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException
    {
        super.render(gc, g);
        
        if (check("right"))
        {
            g.drawAnimation(dosWalkRight, x, y);
            if (collide("Solid", x + 1, y) == null) {x += 4;}
        }
        else if (check("left"))
        {
            g.drawAnimation(dosWalkLeft, x, y);
            if (collide("Solid", x - 1, y) == null) {x -= 4;}
        }
        else if (direction == 2) {g.drawImage(dosStanding, x, y);}
        else {g.drawImage(dosStanding.getFlippedCopy(true, false), x, y);}
    }
    
    @Override
    public void update(GameContainer gc, int delta) throws SlickException
    {
        super.update(gc, delta);
        if (pressed("right")) {direction = 2;}
        else if (pressed("left")) {direction = 1;}
        if (collide("Solid", x, y) != null) {jumpAllowed = false;}
        
        if (jumpAllowed && check("up"))
        {
            jumpAllowed = false;
            if (collide("Solid", x, y - 500) == null) {jump = 80;}
        }
        
        //Gravity
        if (collide("Solid", x, y + gravity) == null) {y += gravity;}
        else {jumpAllowed = true;}
        
        if (jump > 0)
        {
            y -= gravity + 4;
            jump --;
        }
        
        if (this.x <= 600) {x += 4;}
        if (this.x >= 4000) {x -= 4;}
        if (WorldPlains.life <= 0) {this.destroy();}
        
        if (collide("Ladder", x, y) != null && jump > 0) {jump = 0;}
        if (collide("Ladder", x, y) != null) {if (check("up")) {y -= gravity + 4;}}
        if (collide("Solid", x, y - 1) == null) {} else {y += gravity;}
    }
}