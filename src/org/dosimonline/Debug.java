package org.dosimonline;

import java.awt.Font;
import java.util.ArrayList;

import org.newdawn.slick.Graphics;
import org.newdawn.slick.TrueTypeFont;

/**
 * @author yashax
 */

public class Debug {
	private static TrueTypeFont font = new TrueTypeFont(new Font(
			"Arial", Font.PLAIN, 18), true);
	private static ArrayList<String> debugInfo = new ArrayList<>();
	private static final int X = 20;
	private static final int FIRST_Y = 100;
	private static int nextLineY = FIRST_Y;
	private static boolean showDebugInfo;

	public static void show(String s) {
		debugInfo.add(s);
	}

	public static void render(Graphics g) {
		if (showDebugInfo) {
			for (String s : debugInfo) {
				font.drawString(X, nextLineY, s);
				nextLineY += font.getHeight(s);
			}

			nextLineY = FIRST_Y;
			debugInfo.clear();
		}
	}

	public static void setVisible(boolean visible) {
		showDebugInfo = visible;
	}

	public static boolean isVisible() {
		return showDebugInfo;
	}
}