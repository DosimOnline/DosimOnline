package org.dosimonline;

import java.awt.Font;
import java.util.ArrayList;

import org.newdawn.slick.Color;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.TrueTypeFont;
import org.newdawn.slick.state.StateBasedGame;

/**
 *
 * @author gilnaa
 */
public class NotificationManager {

	private int screenWidth;

	final class Notification {

		private static final int PADDING = 10;
		TrueTypeFont font;
		float height, width;
		float x, y, desiredY;
		String text;
		Color color;
		int dismissDelay, pullOutDelay;

		private Notification(String text, int order, Color color,
			  int screenWidth, TrueTypeFont f) {
			this.text = text;
			this.color = new Color(color);
			this.font = f;
			this.width = font.getWidth(text);
			this.height = font.getHeight(text);

			setOrder(order);
			this.y = desiredY;
			this.x = screenWidth - (width + PADDING);

			this.dismissDelay = 5000;
			this.pullOutDelay = 500;
		}

		public boolean isValid() {
			return pullOutDelay > 0;
		}

		public void setOrder(int order) {
			desiredY = order * height + PADDING;
		}

		public void update(GameContainer gc, int delta) {
			if (desiredY < y)
				y -= delta / 10f;

			if (dismissDelay > 0)
				dismissDelay -= delta;
			else {
				x += delta / 10f;
				pullOutDelay -= delta;
				color.a -= 0.01f;
			}

		}

		public void render(Graphics g) {
			font.drawString(x, y, text, color);
		}
	}

	ArrayList<Notification> notifications;
	TrueTypeFont font;

	public NotificationManager() {
		notifications = new ArrayList<Notification>();
	}

	public void add(String text) {
		add(text, Color.white);
	}

	public void add(String text, Color color) {
		Notification e = new Notification(text, notifications.size(), color,
			  screenWidth, font);
		notifications.add(e);
	}

	public void init(GameContainer gc, StateBasedGame sbg) {
		screenWidth = gc.getWidth();
		Font f = new Font("Times New Roman", Font.PLAIN, 18);
		font = new TrueTypeFont(f, true);
	}

	public void update(GameContainer gc, int delta) {
		if (gc.getWidth() != screenWidth)
			screenWidth = gc.getWidth();

		ArrayList<Notification> dueToRemoval = new ArrayList<Notification>();
		for (Notification e : notifications) {
			e.update(gc, delta);
			if (!e.isValid())
				dueToRemoval.add(e);
		}
		if (dueToRemoval.size() > 0) {
			notifications.removeAll(dueToRemoval);
			for (int i = 0; i < notifications.size(); i++)
				notifications.get(i).setOrder(i);
		}
	}

	public void render(Graphics g) {
		for (Notification e : notifications)
			e.render(g);
	}

	private static NotificationManager instance = null;

	public static NotificationManager getInstance() throws SlickException {
		if (instance == null)
			instance = new NotificationManager();
		return instance;
	}
}
