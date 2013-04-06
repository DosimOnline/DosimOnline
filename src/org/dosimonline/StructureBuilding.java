package org.dosimonline;
import it.randomtower.engine.World;
import org.newdawn.slick.SlickException;

public class StructureBuilding
{
    public void add (int x, World world, int y) throws SlickException
    {
        world.add(new TileRock(x, y + 128));
        world.add(new TileRock(x, y));
        world.add(new TileRock(x + 384, y + 128));
        world.add(new TileRock(x + 384, y));
        world.add(new TileRock(x, y));
        world.add(new TileRockCeiling(x, y - 64));
        for (int a = 2; a < 4; a++) {world.add(new TileRockCeiling(x + 128 * a, y - 64));} 
        for (int a = 0; a < 4; a++) {world.add(new TileBackground(x + 128 * a, y + 256));}
        for (int a = 1; a < 3; a++) {world.add(new TileBackground(x + 128 * a, y + 128));}
        for (int a = 1; a < 3; a++) {world.add(new TileBackground(x + 128 * a, y));}
        world.add(new TileBackground(x + 128, y - 64));
        world.add(new TileLadder(x + 128, y + 256));
        world.add(new TileLadder(x + 128, y + 128));
        world.add(new TileLadder(x + 128, y));
        world.add(new TileLadder(x + 128, y - 128));
    }
    
    public void add (int x, World world, int y, int numOfFloors) throws SlickException
    {
        for (int a = 1; a <= numOfFloors; a++, y -= 448)
        {
            add(x, world, y);
        }
    }
}
