package org.dosimonline.settings;
import org.dosimonline.Button;
import org.dosimonline.DosimOnline;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 * @author yashax and Shpitzick
 */
public class PlusMinusSetting extends Setting {
	private Button plus;
	private Button minus;
	private float change; // How the value changes in each change
	private float minValue, maxValue; // The limits of the setting

	public PlusMinusSetting(String name, String settingText, int x, int y,
		  float defaultValue, boolean liveApply, ApplySetting applyMethod,
		  float change, float minValue, float maxValue) throws SlickException {
		
		super(name, settingText, x, y, defaultValue, liveApply, applyMethod);

		this.change = change;
		this.minValue = minValue;
		this.maxValue = maxValue;

		minus = new Button(x, y, " - ");
		plus = new Button(x + SETTING_WIDTH - Button.getWidth("+"), y, "+");
	}

	@Override
	public void render(Graphics g) {
		plus.render(g);
		minus.render(g);

		String text = settingText + ": " + ((int) ((float) getValue()));

		// Places the text in the center (according to the width/height of the
		// text)
		int textX = x
			  + DosimOnline.font.getWidth(" - ")
			  + (SETTING_WIDTH - DosimOnline.font.getWidth("+") - DosimOnline.font.getWidth(" - ")
			  - g.getFont().getWidth(text)) / 2;
		int textY = y + (plus.getHeight() - g.getFont().getHeight(text))
			  / 2;

		g.drawString(text, textX, textY);

	}

	@Override
	public void update(GameContainer gc) {
		plus.update(gc.getInput());
		minus.update(gc.getInput());

		float val = ((float) getValue()), newValue;

		if (plus.activated()) {
			newValue = val + change;
			if (newValue <= maxValue)
				setValue(newValue, gc);
		} else if (minus.activated()) {
			newValue = val - change;
			if (newValue >= minValue)
				setValue(newValue, gc);
		}
	}

	@Override
	protected Object parse(String s) {
		try {
			return Float.parseFloat(s);
		} catch (Exception e) {
			return null;
		}
	}
}