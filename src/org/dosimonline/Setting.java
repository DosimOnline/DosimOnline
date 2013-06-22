package org.dosimonline;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

/**
 * @author yashax
 */

public abstract class Setting
{
	protected String name;
	protected String settingText;
	protected int x;
	protected int y;
	protected Object value;
	protected boolean liveApply;
	
	protected final int SETTING_WIDTH = 333;
	private ApplySetting applyMethod;
	
	protected Setting(String name, String settingText, int x, int y, Object defaultValue, boolean liveApply, ApplySetting applyMethod)
	{
		this.name = name;
		this.settingText = settingText;
		this.x = x;
		this.y = y;
		this.value = defaultValue;
		this.liveApply = liveApply;
		this.applyMethod = applyMethod;
	}
	
	public String getName()
	{
		return name;
	}
	
	public abstract void render(Graphics g);
	public abstract void update(GameContainer gc);
	protected void setValue(Object value, GameContainer gc)
	{
		this.value = value;
		if (liveApply && gc != null)
			apply(gc);
			
	}
	
	public void apply(GameContainer gc)
	{
		if (applyMethod != null)
			applyMethod.apply(gc, value);
	}
	
	@Override
	public String toString()
	{
		return name + ":" + value;
	}
	
	public boolean setFromString(String setting)
	{
		String[] splitted = setting.split(":", 2);
		if (splitted.length == 2 && splitted[0].equalsIgnoreCase(name))
		{
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


interface ApplySetting
{
	public void apply(GameContainer gc, Object value);
}
