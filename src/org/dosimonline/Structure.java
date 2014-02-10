package org.dosimonline;

import it.randomtower.engine.World;

import java.util.Random;
import org.dosimonline.entities.Rabbi;

import org.dosimonline.tiles.BackgroundTile;
import org.dosimonline.tiles.SynBrick;
import org.dosimonline.tiles.SynBrickCeiling;
import org.dosimonline.tiles.Ladder;
import org.dosimonline.tiles.Rock;
import org.dosimonline.tiles.RockCeiling;
import org.dosimonline.tiles.SynBackgroundTile;
import org.newdawn.slick.SlickException;

public class Structure {
	public void add(World world, int x, int y) throws SlickException {
		Random random = new Random();
		int type = random.nextInt(5);

		if (type > 0) { // generate a normal, boring floor.
			world.add(new Rock(x, y + 128));
			world.add(new Rock(x, y));
			world.add(new Rock(x + 384, y + 128));
			world.add(new Rock(x + 384, y));
			world.add(new Rock(x, y));
			world.add(new RockCeiling(x, y - 64));
			for (int i = 2; i < 4; i++)
				world.add(new RockCeiling(x + 128 * i, y - 64));
			for (int i = 0; i < 4; i++)
				world.add(new BackgroundTile(x + 128 * i, y + 256));
			for (int i = 1; i < 3; i++)
				world.add(new BackgroundTile(x + 128 * i, y + 128));
			for (int i = 1; i < 3; i++)
				world.add(new BackgroundTile(x + 128 * i, y));
			world.add(new BackgroundTile(x + 128, y - 64));
			world.add(new Ladder(x + 128, y + 256));
			world.add(new Ladder(x + 128, y + 128));
			world.add(new Ladder(x + 128, y));
			world.add(new Ladder(x + 128, y - 128));
		} else { // generate an exciting floor. AKA a synagogue.
			world.add(new Rabbi(x + 256, y)); // These coordiantes were picken randomly.
			world.add(new SynBrick(x, y + 128));
			world.add(new SynBrick(x, y));
			world.add(new SynBrick(x + 384, y + 128));
			world.add(new SynBrick(x + 384, y));
			world.add(new SynBrick(x, y));
			world.add(new SynBrickCeiling(x, y - 64));
			for (int i = 2; i < 4; i++)
				world.add(new SynBrickCeiling(x + 128 * i, y - 64));
			for (int i = 0; i < 4; i++)
				world.add(new SynBackgroundTile(x + 128 * i, y + 256));
			for (int i = 1; i < 3; i++)
				world.add(new SynBackgroundTile(x + 128 * i, y + 128));
			for (int i = 1; i < 3; i++)
				world.add(new SynBackgroundTile(x + 128 * i, y));
			world.add(new SynBackgroundTile(x + 128, y - 64));
			world.add(new Ladder(x + 128, y + 256));
			world.add(new Ladder(x + 128, y + 128));
			world.add(new Ladder(x + 128, y));
			world.add(new Ladder(x + 128, y - 128));
		}
	}

	public void add(World world, int x, int y, int numOfFloors)
		  throws SlickException {
		for (int i = 0; i < numOfFloors; i++, y -= 448)
			add(world, x, y);
	}
}
