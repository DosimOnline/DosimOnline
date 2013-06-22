package org.dosimonline;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;

/**
 * @author yashax
 */

public class Button
{
	protected int x;
	protected int y;

	private Image image;
	private Image hoverImage;

	private boolean hover = false;
	private boolean pressed = false;
	private boolean ready = false;

	public Button(int x, int y, Image image, Image hoverImage)
	{
		this.x = x;
		this.y = y;
		this.image = image;
		this.hoverImage = hoverImage;
	}

	public void update(Input input)
	{
		int mouseX = input.getMouseX();
		int mouseY = input.getMouseY();

		hover = (mouseX >= x && mouseX <= x + image.getWidth())
				&& (mouseY >= y && mouseY <= y + image.getHeight());

		pressed = input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON);

		if (hover && pressed)
			ready = true;
		else if (hover == false)
			ready = false;
	}

	public void render(Graphics g)
	{
		if (hover)
			g.drawImage(hoverImage, x, y);
		else
			g.drawImage(image, x, y);
	}

	public boolean activated()
	{
		boolean active = ready && !pressed;
		if (active)
			ready = false;

		return active;
	}
}
