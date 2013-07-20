package org.dosimonline;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;

/**
 * @author Shpitzick
 */
public class CheckBox extends Button {
	private boolean checked;

	public CheckBox(int x, int y, boolean defaultStatus) throws SlickException {
		super(x, y, ":)");
		checked = defaultStatus;
		this.setText(getCheckedString());
	}

	public void update(Input input) {
		super.update(input);
		
		if (this.activated())
		{
			checked = !checked;
			this.setText(getCheckedString());
		}
	}

	private String getCheckedString()
	{
		if (checked)
			return ":)";
		else
			return "  ";
	}
	
	public void render(Graphics g) {
		super.render(g);
	}

	public void setValue(boolean on) {
		this.checked = on;
	}
	
	public boolean isChecked()
	{
		return this.checked;
	}
}