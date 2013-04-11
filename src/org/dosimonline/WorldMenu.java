package org.dosimonline;
import it.randomtower.engine.World;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.Music;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class WorldMenu extends World
{
    private Image hakotel;
    private Image logo;
    
    public WorldMenu(int id, GameContainer gc)
    {
        super(id, gc);
    }
    
    @Override
    public void init(GameContainer gc, StateBasedGame game) throws SlickException
    {
        hakotel = new Image("org/dosimonline/res/hakotel.png"); //I won't tell you what kotel is.
        logo = new Image("org/dosimonline/res/logo.png");
        Music music = new Music("org/dosimonline/res/audio/Makche-Alleviation.ogg");
        music.loop();
        music.setVolume((float)0.04);
    }
    
    @Override
    public void render(GameContainer gc, StateBasedGame sbg, Graphics g) throws SlickException
    {
        g.drawImage(hakotel, 0, 0);
        g.drawImage(logo, 0, 0);
        g.drawString("Hit \"S\" to start", 550, logo.getHeight() + 50); 
    }
    
    @Override
    public void update(GameContainer gc, StateBasedGame sbg, int i) throws SlickException
    {
        if (gc.getInput().isKeyPressed(Input.KEY_ESCAPE))
        {
            gc.exit();
            gc.destroy();
        }

        if (gc.getInput().isKeyPressed(Input.KEY_S))
        {
            sbg.enterState(2);
        }
    }
}