package org.dosimonline;
import it.randomtower.engine.World;
import java.util.Random;
import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
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
    public static float naziMoveSpeed = 1;
    private int helpDisplayTime = 1000;

    
    public WorldPlains(int id, GameContainer gc)
    {
        super(id, gc);
    }
    
    @Override
    public void init(GameContainer gc, StateBasedGame sbg) throws SlickException
    {
        super.init(gc, sbg);
        dos = new EntityDos(1920, 0); add(dos); setCameraOn(dos);
        add(new EntityWizard(1880, 392));
        
        //We call it "tile" instead of "block" because we don't want too many Minecraft easter eggs.
        for (int x = 0; x < 40; x++)
        {
            add(new TileGrass(x * 128, 464));
            for (int y = 1; y < 4; y++) {add(new TileDirt(x * 128, 464 + 128 * y));}
        }
        add(new TileRock(2428, 336));
        add(new TileRockSlab(2300, 400));
        add(new TileRockSlab(2556, 400));
        add(new TileRock(1600, 336));
        add(new TileRockSlab(1300, 400));
        for (int y = 0; y < 10; y++) {add(new TileTransparent(4128, y * 128));}
        for (int y = 0; y < 10; y++) {add(new TileTransparent(600, y * 128));}
    }
    
    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException 
    {
        super.render (gc, sbg, g);
        Color backgroundColor = new Color(117, 202, 255);
        g.setBackground(backgroundColor);
        g.drawString("SCORE: " + score, 20, 20);
        g.drawString("NAZIS' SPEED: " + naziMoveSpeed + "pxl/s", 20, 35);
        g.drawString("X: " + dos.x, 20, 50);
        g.drawString("Y: " + dos.y, 20, 65);
        if (helpDisplayTime > 0) {g.drawString("ASDW to move, left mouse to shoot", 500, 300);}
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
            add(new EntityNazi(naziX, -300));
            spawnNazi = 450;
        }
        if (gc.getInput().isMousePressed(0) && attackAllowed == 0)
        {
            add(new EntityFireball(dos.x, dos.y));
            attackAllowed = 200;
        }
        if (attackAllowed > 0) {attackAllowed--;}
        naziMoveSpeed += 0.00005;
        if (helpDisplayTime > 0) {helpDisplayTime--;}
    }
}
