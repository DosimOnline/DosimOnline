package org.dosimonline;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;

import org.lwjgl.opengl.Display;
import org.lwjgl.opengl.DisplayMode;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;

/**
 * @author yashax
 */

public class SettingsManager
{
	private Setting[] settings;

	private SettingsManager() throws SlickException
	{
		DisplayMode dm = Display.getDesktopDisplayMode();

		settings = new Setting[] {
				new PlusMinusSetting("fps", "Target FPS",
						dm.getWidth() / 2 - 175, 42, 80, false,
						new ApplySetting()
						{
							@Override
							public void apply(GameContainer gc, Object value)
							{
								float fps = (float) value;
								gc.setTargetFrameRate((int) fps);
							};
						}, 5.0f, 30.0f, 100.0f),
				new PlusMinusSetting("volume", "Music volume",
						dm.getWidth() / 2 - 175, 158, 10.0f, true,
						new ApplySetting()
						{
							@Override
							public void apply(GameContainer gc, Object value)
							{
								gc.setMusicVolume(((float) value) / 10.0f);
							}
						}, 1, 0, 10),
				new BooleanSetting("vsync", "Enable VSync",
						dm.getWidth() / 2 - 175, 100, true, true,
						new ApplySetting()
						{
							@Override
							public void apply(GameContainer gc, Object value)
							{
								gc.setVSync((boolean) value);
							};
						}) };

		this.loadSettings();
	}

	public Setting[] getSettings()
	{
		return this.settings;
	}

	private void loadSettings()
	{
		try
		{
			Iterator<String> fileSettings = Files.readAllLines(
					Paths.get("settings.txt"), StandardCharsets.UTF_8)
					.iterator();

			for (Setting s : settings)
			{
				s.setFromString(fileSettings.next());
			}

		}
		catch (Exception e)
		{
			this.writeSettings();
		}
	}

	public void writeSettings()
	{
		PrintWriter writer = null;
		try
		{
			writer = new PrintWriter(new FileWriter("settings.txt"));
			for (Setting s : settings)
				writer.println(s.toString());
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		finally
		{
			if (writer != null)
				writer.close();
		}
	}

	public void apply(GameContainer gc)
	{
		for (int i = 0; i < settings.length; i++)
			settings[i].apply(gc);
	}

	private static SettingsManager instance = null;

	public static SettingsManager getInstance() throws SlickException
	{
		if (instance == null)
			instance = new SettingsManager();
		return instance;
	}
}
