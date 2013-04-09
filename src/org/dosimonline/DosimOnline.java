package org.dosimonline;
import java.io.IOException;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class DosimOnline extends StateBasedGame
{
    public static String gamename = "Dosim Online";
    
    public DosimOnline(String gamename)
    {
        super(gamename);
    }
    
    @Override
    public void initStatesList(GameContainer gc) throws SlickException
    {
        addState(new WorldMenu(1, gc));
        addState(new WorldPlains(2, gc));
        enterState(1);
    }
    
    public static void main(String[] args) throws IOException
    {
        try
        {
            AppGameContainer appgc = new AppGameContainer(new DosimOnline(gamename));
            appgc.setDisplayMode(1152, 896, false);
            appgc.setFullscreen(false);
            appgc.setShowFPS(false);
            appgc.setTargetFrameRate(200);
            appgc.start();
        } catch (SlickException e)
        {
            e.printStackTrace();
        }
    }
}