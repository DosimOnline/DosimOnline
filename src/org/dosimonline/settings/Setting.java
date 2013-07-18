package org.dosimonline.settings;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

/**
 * @author yashax
 */
public abstract class Setting {
	protected String name; // Unique string that represents the setting (will be written to settings file)
	protected String settingText; // Setting UI text
	protected int x;
	protected int y;
	private Object value;
	protected boolean applyImmediately; // Whether to apply the setting immediately or not
	public static final int SETTING_WIDTH = 333; // Width of each setting in pixels
	private ApplySetting applyMethod; // Callback for application of a setting

	protected Setting(String name, String settingText, int x, int y,
		  Object defaultValue, boolean applyImmediately, ApplySetting applyMethod) {
		this.name = name;
		this.settingText = settingText;
		this.x = x;
		this.y = y;
		this.value = defaultValue;
		this.applyImmediately = applyImmediately;
		this.applyMethod = applyMethod;
	}

	public String getName() {
		return name;
	}

	public abstract void render(Graphics g);

	public abstract void update(GameContainer gc);

	public Object getValue() {
		return this.value;
	}

	// Sets the value of the setting.
	protected void setValue(Object value, GameContainer gc) {
		this.value = value;
		if (applyImmediately && gc != null)
			apply(gc);
	}

	public void apply(GameContainer gc) {
		if (applyMethod != null)
			applyMethod.apply(gc, value);
	}

	@Override
	public String toString() {
		return name + ":" + value;
	}

	// setting is a string in the format "name:value"
	// this method parses the string and sets the value
	public boolean setFromString(String setting) {
		String[] splitted = setting.split(":", 2);
		if (splitted.length == 2 && splitted[0].equalsIgnoreCase(name)) {
			Object parsed = parse(splitted[1]);
			if (parsed == null)
				return false;

			this.setValue(parsed, null);
			return true;
		}

		return false;
	}

	protected abstract Object parse(String s);
}

interface ApplySetting {
	public void apply(GameContainer gc, Object value);
}