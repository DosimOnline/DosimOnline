package org.dosimonline;
import it.randomtower.engine.World;
import java.util.Random;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class WorldPlains extends World
{
    public static EntityDos dos;
    public static EntityNazi nazi;
    private Random random = new Random();
    private int spawnNazi;
    public static int score = 0;
    private int attackAllowed;
    public static float naziMoveSpeed = 4;
    private int helpDisplayTime = 300;
    public static float gravity = 7;
    public static int life = 5;
    private Structure building = new Structure();
    
    public WorldPlains(int id, GameContainer gc)
    {
        super(id, gc);
    }
    
    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException
    {
        super.init(gc, sbg);
        
        //We call it "tile" instead of "block" because we don't want too many Minecraft easter eggs.
        for (int x = -1; x < 60; x++)
        {
            add(new TileGrass(x * 128, 464));
            for (int y = 1; y < 4; y++) {add(new TileDirt(x * 128, 464 + 128 * y));}
        }
        for (int a = 0, x = 650; a < 10; a++, x += 700)
        {
            int numOfFloors = random.nextInt(10) + 3;
            building.add(x, this, 80, numOfFloors);
        }
        
        dos = new EntityDos(1920, 0); add(dos); setCameraOn(dos);
    }
    
    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException 
    {
        super.render (gc, sbg, g);
        Color backgroundColor = new Color(117, 202, 255);
        g.setBackground(backgroundColor);
        g.drawString("SCORE: " + score, 20, 20);
        g.drawString("NAZIS' SPEED: " + naziMoveSpeed + "pxl/s", 20, 35);
        for (int a = 0; a < life; a++) {g.drawImage(new Image("org/dosimonline/res/heart.png"),20 + a * 32, 55);}
        g.drawString("Reload: " + attackAllowed, 20, 80);
        if (helpDisplayTime > 0) {g.drawString("ASDW to move, left mouse to shoot", DosimOnline.dm.getWidth() / 2 - 100, DosimOnline.dm.getHeight() / 2 - 100);}
        if (life <= 0) {g.drawString("LOL! U DIED!", 1152 / 2, 896 / 2);}
    }
    
    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException
    {
        super.update (gc, sbg, i);
        if (gc.getInput().isKeyPressed(Input.KEY_ESCAPE)) {sbg.enterState(1);}
        
        if (spawnNazi > 0) {spawnNazi--;}
        if (spawnNazi == 0)
        {
            int naziX = random.nextInt(4100) + 600;
            add(new EntityNazi(naziX, -700));
            spawnNazi = 450;
        }
        naziMoveSpeed += 0.00005;
        
        if (gc.getInput().isMousePressed(0) && attackAllowed == 0 && life > 0)
        {
            add(new EntityFireball(dos.x, dos.y));
            EntityFireball.isBomb = false;
            attackAllowed = 200;
        }
        
        if (gc.getInput().isMousePressed(1) && attackAllowed == 0 && life > 0)
        {
            add(new EntityFireball(dos.x, dos.y));
            EntityFireball.isBomb = true;
            attackAllowed = 200;
        }
        
        if (attackAllowed > 0) {attackAllowed--;}
        if (helpDisplayTime > 0) helpDisplayTime--;
    }
}