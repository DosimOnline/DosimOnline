package org.dosimonline;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 * @author Shpitzick
 */
public class CheckBox extends Button {
	private boolean checked;
	private static Image baseImage;
	private static Image hoverImage;
	private static Image checkedImage;
	public CheckBox(int x, int y, boolean defaultStatus) throws SlickException {
		super(x, y, "");
		checked = defaultStatus;
		
		if(baseImage == null) {
			baseImage = new Image("org/dosimonline/res/buttons/checkbox.png");
			hoverImage = new Image("org/dosimonline/res/buttons/checkboxHover.png");
			checkedImage = new Image("org/dosimonline/res/buttons/checkboxV.png");
		}
	}

	public void update(Input input) {
		super.update(input);

		if (this.activated()) {
			checked = !checked;
		}
	}

	@Override
	public void render(Graphics g) {
		if(hover)
			g.drawImage(hoverImage, x, y);
		else
			g.drawImage(baseImage, x, y);
		
		if(checked)
			g.drawImage(checkedImage, x, y);
	}
	
	@Override
	public int getWidth() {
		return baseImage.getWidth();
	}
	@Override
	public int getHeight() {
		return baseImage.getHeight();
	}
	public static int getCheckBoxWidth() {
		if(baseImage == null) {
			try {
				baseImage = new Image("org/dosimonline/res/buttons/checkbox.png");
				hoverImage = new Image("org/dosimonline/res/buttons/checkboxHover.png");
				checkedImage = new Image("org/dosimonline/res/buttons/checkboxV.png");
			} catch (SlickException e) {
				
			}
		}
		return baseImage.getWidth();
	}
	public void setValue(boolean on) {
		this.checked = on;
	}

	public boolean isChecked() {
		return this.checked;
	}
}