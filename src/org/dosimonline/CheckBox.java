package org.dosimonline;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 * @author Shpitzick
 */
public class CheckBox {
	private Image vImage;
	private Image image;
	private Image hover;
	public boolean on;
	private boolean active;
	private int x, y;
	private Input input;

	public CheckBox(int x, int y, boolean defaultStatus) throws SlickException {
		this.x = x;
		this.y = y;
		on = defaultStatus;
		vImage = new Image("org/dosimonline/res/buttons/checkboxV.png");
		image = new Image("org/dosimonline/res/buttons/checkbox.png");
		hover = new Image("org/dosimonline/res/buttons/checkboxHover.png");
	}

	public void update(Input input) {
		this.input = input;
		int mouseX = input.getMouseX();
		int mouseY = input.getMouseY();

		active = mouseX > x && mouseX < x + image.getWidth()
			  && mouseY > y && mouseY < y + image.getHeight();
	}

	public void render(Graphics g) {
		if (active)
			g.drawImage(hover, x, y);
		else
			g.drawImage(image, x, y);

		if (on)
			g.drawImage(vImage, x, y);

		if (active && input.isMousePressed(Input.MOUSE_LEFT_BUTTON))
			on = !on;
	}

	public void setValue(boolean on) {
		this.on = on;
	}
}