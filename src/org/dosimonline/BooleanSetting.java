package org.dosimonline;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * @author yashax
 */

public class BooleanSetting extends Setting
{
	private static Image cbImage, cbHoverImage, cbVImage; // cb = checkbox

	private CheckBox checkbox;
	private boolean oldValue;

	public BooleanSetting(String name, String settingText, int x, int y,
			boolean defaultValue, boolean liveApply, ApplySetting applyMethod)
			throws SlickException
	{
		super(name, settingText, x, y, defaultValue, liveApply, applyMethod);

		if (cbImage == null)
			cbImage = new Image("org/dosimonline/res/buttons/checkbox.png");
		if (cbHoverImage == null)
			cbHoverImage = new Image(
					"org/dosimonline/res/buttons/checkboxHover.png");
		if (cbVImage == null)
			cbVImage = new Image("org/dosimonline/res/buttons/checkboxV.png");

		checkbox = new CheckBox(x + SETTING_WIDTH - cbImage.getWidth(), y,
				cbImage, cbHoverImage, cbVImage, defaultValue);
	}

	@Override
	protected void setValue(Object value, GameContainer gc)
	{
		super.setValue(value, gc);
		checkbox.setValue((boolean) value);
	}

	@Override
	public void render(Graphics g)
	{
		checkbox.render(g);

		int textX = x + (SETTING_WIDTH - g.getFont().getWidth(settingText)) / 2;
		int textY = y
				+ (cbImage.getHeight() - g.getFont().getHeight(settingText))
				/ 2;

		g.drawString(settingText, textX, textY);
	}

	@Override
	public void update(GameContainer gc)
	{
		oldValue = checkbox.isOn();

		checkbox.update(gc.getInput());

		if (checkbox.isOn() != oldValue)
			setValue(checkbox.isOn(), gc);
	}
	
	@Override
	protected Object parse(String s)
	{
		return Boolean.parseBoolean(s);
	}
}
