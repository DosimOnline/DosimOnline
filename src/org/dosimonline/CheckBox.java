package org.dosimonline;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;

/**
 * @author yashax
 */

public class CheckBox extends Button
{
	private Image vImage;
	private boolean on;
	
	public CheckBox(int x, int y, Image image, Image hoverImage, Image vImage, boolean defaultStatus)
	{
		super(x, y, image, hoverImage);
		
		this.vImage = vImage;
		on = defaultStatus;
	}
	
	@Override
	public void update(Input input)
	{
		super.update(input);
		
		if (this.activated())
			on = !on;
	}
	
	@Override
	public void render(Graphics g)
	{
		super.render(g);
		
		if (on)
			g.drawImage(vImage, x, y);
			
	}
	
	public void setValue(boolean on)
	{
		this.on = on;
	}
	
	public boolean isOn()
	{
		return on;
	}
}
