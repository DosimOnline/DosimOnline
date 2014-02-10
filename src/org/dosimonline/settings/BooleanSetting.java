package org.dosimonline.settings;

import org.dosimonline.CheckBox;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 * @author yashax
 */
public class BooleanSetting extends Setting {
	private final CheckBox checkbox;
	private boolean oldValue;

	public BooleanSetting(String name, String settingText, int x, int y,
		  boolean defaultValue, boolean liveApply, ApplySetting applyMethod)
		  throws SlickException {
		super(name, settingText, x, y, defaultValue, liveApply, applyMethod);

		checkbox = new CheckBox(
			  x + SETTING_WIDTH - CheckBox.getCheckBoxWidth(), y,
			  defaultValue);
	}

	@Override
	protected void setValue(Object value, GameContainer gc) {
		super.setValue(value, gc);
		checkbox.setValue((boolean) value);
	}

	@Override
	public void render(Graphics g) {
		checkbox.render(g);

		// Places the text in the center (according to the width/height of the
		// text)
		int textX = x + (SETTING_WIDTH - g.getFont().getWidth(settingText)) / 2;
		int textY = y
			  + (checkbox.getHeight() - g.getFont().getHeight(settingText))
			  / 2;

		g.drawString(settingText, textX, textY);
	}

	@Override
	public void update(GameContainer gc) {
		oldValue = checkbox.isChecked();

		checkbox.update(gc.getInput());

		if (checkbox.isChecked() != oldValue)
			setValue(checkbox.isChecked(), gc);
	}

	@Override
	protected Object parse(String s) {
		return Boolean.parseBoolean(s);
	}
}
