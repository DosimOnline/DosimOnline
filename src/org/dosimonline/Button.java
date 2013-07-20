package org.dosimonline;
import org.newdawn.slick.Font;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 * @author yashax (and Shpitzick, too!)
 */
public class Button {
	protected int x;
	protected int y;
	private String text;
	private static Image side, sideActive, middle, middleActive;
	private Font font;
	private boolean hover = false;
	private boolean pressed = false;
	private boolean ready = false;

	public Button(int x, int y, String text) throws SlickException {
		this.x = x;
		this.y = y;
		this.text = text;

		side = new Image("org/dosimonline/res/buttons/side.png");
		sideActive = new Image("org/dosimonline/res/buttons/sideActive.png");
		middle = new Image("org/dosimonline/res/buttons/middle.png");
		middleActive = new Image("org/dosimonline/res/buttons/middleActive.png");
		
		font = DosimOnline.font;
	}

	public void update(Input input) {
		int mouseX = input.getMouseX();
		int mouseY = input.getMouseY();

		hover = (mouseX > x && mouseX < x + Button.getWidth(text))
			  && (mouseY >= y && mouseY <= y + this.getHeight());

		pressed = input.isMouseButtonDown(Input.MOUSE_LEFT_BUTTON);

		if (hover && pressed)
			ready = true;
		else if (hover == false)
			ready = false;
	}

	public void render(Graphics g) {
		if (font != null) {
			if (hover) {
				g.drawImage(sideActive, x, y);
				g.drawImage(sideActive.getFlippedCopy(true, false), x + sideActive.getWidth() + font.getWidth(text), y);
				for (int i = x + side.getWidth(); i < x + side.getWidth() + font.getWidth(text); i++)
					g.drawImage(middleActive, i, y);
			} else {
				g.drawImage(side, x, y);
				g.drawImage(side.getFlippedCopy(true, false), x + sideActive.getWidth() + font.getWidth(text), y);
				for (int i = x + side.getWidth(); i < x + side.getWidth() + font.getWidth(text); i++)
					g.drawImage(middle, i, y);
			}
			font.drawString(x + side.getWidth(), y, text);
		}
	}

	public boolean activated() {
		boolean active = ready && !pressed;
		if (active)
			ready = false;

		return active;
	}
	
	public static int getWidth(String text) {
		return side.getWidth() * 2 + DosimOnline.font.getWidth(text);
	}
	
	public int getHeight()
	{
		return middle.getHeight();
	}
	
	public void setText(String text)
	{
		this.text = text;
	}
}
