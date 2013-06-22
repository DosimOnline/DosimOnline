package org.dosimonline;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * @author yashax
 */

public class PlusMinusSetting extends Setting
{
	private static Image plusImage, plusActiveImage, minusImage,
			minusActiveImage;

	private Button plus;
	private Button minus;

	private float change;

	private float minValue, maxValue;

	public PlusMinusSetting(String name, String settingText, int x, int y,
			float defaultValue, boolean liveApply, ApplySetting applyMethod,
			float change, float minValue, float maxValue) throws SlickException
	{
		super(name, settingText, x, y, defaultValue, liveApply, applyMethod);

		if (plusImage == null)
			plusImage = new Image("org/dosimonline/res/buttons/plus.png");
		if (plusActiveImage == null)
			plusActiveImage = new Image(
					"org/dosimonline/res/buttons/plusActive.png");
		if (minusImage == null)
			minusImage = new Image("org/dosimonline/res/buttons/minus.png");
		if (minusActiveImage == null)
			minusActiveImage = new Image(
					"org/dosimonline/res/buttons/minusActive.png");

		this.change = change;
		this.minValue = minValue;
		this.maxValue = maxValue;

		minus = new Button(x, y, minusImage, minusActiveImage);
		plus = new Button(x + SETTING_WIDTH - plusImage.getWidth(), y,
				plusImage, plusActiveImage);
	}

	@Override
	public void render(Graphics g)
	{
		plus.render(g);
		minus.render(g);

		String text = settingText + ": " + ((int) ((float) value));

		int textX = x
				+ minusImage.getWidth()
				+ (SETTING_WIDTH - plusImage.getWidth() - minusImage.getWidth() - g
						.getFont().getWidth(text)) / 2;
		int textY = y + (plusImage.getHeight() - g.getFont().getHeight(text))
				/ 2;

		g.drawString(text, textX, textY);

	}

	@Override
	public void update(GameContainer gc)
	{
		plus.update(gc.getInput());
		minus.update(gc.getInput());

		float val = ((float) value), newValue;

		if (plus.activated())
		{
			newValue = val + change;
			if (newValue <= maxValue)
				setValue(newValue, gc);
		}
		else if (minus.activated())
		{
			newValue = val - change;
			if (newValue >= minValue)
				setValue(newValue, gc);
		}
	}

	public float getValue()
	{
		return ((float) value);
	}
	
	@Override
	protected Object parse(String s)
	{
		try
		{
			return Float.parseFloat(s);
		}
		catch (Exception e)
		{
			return null;
		}
	}
}
