/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package org.dosimonline;

import java.util.ArrayList;
import org.newdawn.slick.Color;
import org.newdawn.slick.Font;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;

/**
 *
 * @author gilnaa
 */
public class NotificationManager {
    final class Notification {
        private static final int PADDING = 10;
        
        float height, width;
        float x, y, desiredY;
        String text;
        Color color;
        
        int dismissDelay, pullOutDelay;
        
        public Notification(String text, int order, Color color) {
            this.text = text;
            this.color = new Color(color);
            
            width = getFont().getWidth(text);
            height = getFont().getHeight(text);
            setOrder(order);
            y = desiredY;
            x = -1;
            
            dismissDelay = 500;
            pullOutDelay = 100;
        }
        public boolean isValid () {
            return pullOutDelay > 0;
        }
        public void setOrder(int order) {
            desiredY = order * height + PADDING;
        }
        public Font getFont() {
            return DosimOnline.font;
        }
        public void update(GameContainer gc, int delta) {
            if(x < 0) {
                x = gc.getWidth() - (width + 10);
            }
            
            if(desiredY < y) {
                y -= delta / 10f;
            }
            
            if(dismissDelay > 0) {
                dismissDelay -= delta / 1000f;
            } else {
                x += delta / 10f;
                pullOutDelay -= delta / 1000f;
                color.a -= 0.001f;
            }
            
        }
        public void render(Graphics g) {
            getFont().drawString(x, y, text, color);
        }
    }
    
    ArrayList<Notification> notifications;
    public NotificationManager() {
        notifications = new ArrayList<Notification>();
    }
    public void add(String text) {
        add(text, Color.white);
    }
    public void add(String text, Color color) {
        Notification e = new Notification(text, notifications.size(), color);
        notifications.add(e);
    }
    public void update(GameContainer gc, int delta) {
        ArrayList<Notification> dueToRemoval = new ArrayList<Notification>();
        for(Notification e : notifications) {
            e.update(gc, delta);
            if(!e.isValid()) {
                dueToRemoval.add(e);
            }
        }
        if(dueToRemoval.size() > 0) {
            notifications.removeAll(dueToRemoval);
            for(int i = 0; i < notifications.size(); i++ ) {
                notifications.get(i).setOrder(i);
            }
        }
    }
    public void render(Graphics g) {
        for(Notification e : notifications) {
            e.render(g);
        }
    }
}
