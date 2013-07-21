package org.dosimonline;

import java.util.ArrayList;

import org.newdawn.slick.Graphics;

public class Debug {
	private static ArrayList<String> debugInfo = new ArrayList<>();
	private static final int X = 20;
	private static int nextLineY = 100;
	private static boolean showDebugInfo;

	public static void show(String s) {
		debugInfo.add(s);
	}

	public static void render(Graphics g) {
		if (showDebugInfo) {
			for (String s : debugInfo) {
				g.drawString(s, X, nextLineY);
				nextLineY += 20;
			}

			nextLineY = 100;
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