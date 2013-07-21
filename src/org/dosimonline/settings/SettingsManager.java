package org.dosimonline.settings;

import java.io.FileWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import org.dosimonline.Debug;
import org.dosimonline.DosimOnline;

import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;

/**
 * @author yashax
 */
public class SettingsManager {
	private ArrayList<Setting> settings;
	int nextSettingY;

	private SettingsManager() throws SlickException {
		// First setting y
		nextSettingY = 42;

		// The settings are in the middle of the screen
		int settingsX = (DosimOnline.dm.getWidth() - Setting.SETTING_WIDTH) / 2;

		settings = new ArrayList<Setting>();

		// Defining all game settings:
		add(new PlusMinusSetting("fps", "Target FPS", settingsX, nextSettingY,
				60, false, new ApplySetting() {
					@Override
					public void apply(GameContainer gc, Object value) {
						float fps = (float) value;
						gc.setTargetFrameRate((int) fps);
					};
				}, 10.0f, 30.0f, 500.0f));

		add(new PlusMinusSetting("volume", "Music volume", settingsX,
				nextSettingY, 10.0f, true, new ApplySetting() {
					@Override
					public void apply(GameContainer gc, Object value) {
						gc.setMusicVolume(((float) value) / 10.0f);
					}
				}, 1, 0, 10));

		add(new BooleanSetting("vsync", "Enable VSync", settingsX,
				nextSettingY, true, true, new ApplySetting() {
					@Override
					public void apply(GameContainer gc, Object value) {
						gc.setVSync((boolean) value);
					};
				}));

		add(new BooleanSetting("showdebug", "Show Debug information",
				settingsX, nextSettingY, false, true, new ApplySetting() {
					@Override
					public void apply(GameContainer gc, Object value) {
						Debug.setVisible((boolean) value);
					}
				}));

		this.loadSettings();
	}

	// Helps to order the settings in the GUI
	private void add(Setting s) {
		settings.add(s);
		nextSettingY += 58;
	}

	// Updates the settings
	public void update(GameContainer gc) {
		for (Setting setting : settings)
			setting.update(gc);
	}

	// Renders the settings
	public void render(Graphics g) {
		for (Setting setting : settings)
			setting.render(g);
	}

	private void loadSettings() {
		try {
			// Read all lines of the settings file and get the iterator of the
			// lines collection
			Iterator<String> fileSettings = Files.readAllLines(
					Paths.get("settings.txt"), StandardCharsets.UTF_8)
					.iterator();

			for (Setting s : settings) {
				s.setFromString(fileSettings.next()); // Set the setting from
				// each line
			}

		} catch (Exception e) {
			// The read has failed so we creating a new settings file
			this.writeSettings();
		}
	}

	public void writeSettings() {
		PrintWriter writer = null;
		try {
			writer = new PrintWriter(new FileWriter("settings.txt"));

			// write each setting in new line
			for (Setting s : settings)
				writer.println(s.toString());
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (writer != null)
				writer.close();
		}
	}

	public void apply(GameContainer gc) {
		for (Setting setting : settings)
			setting.apply(gc);
	}

	// Singleton...
	private static SettingsManager instance = null;

	public static SettingsManager getInstance() throws SlickException {
		if (instance == null)
			instance = new SettingsManager();
		return instance;
	}
}