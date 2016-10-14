package com.beginningblackberry.uifun;

import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.Keypad;

public class CustomButtonField extends Field {
	private String label;
	private int foregroundColor;
	private int backgroundColor;	
	private int focusedForegroundColor;
	private int focusedBackgroundColor;
	
	
	public CustomButtonField(String label, int foregroundColor,
			int backgroundColor, int focusedForegroundColor,
			int focusedBackgroundColor, long style) {
		super(style);
		this.label = label;
		this.foregroundColor = foregroundColor;
		this.backgroundColor = backgroundColor;
		this.focusedForegroundColor = focusedForegroundColor;
		this.focusedBackgroundColor = focusedBackgroundColor;
	}

	public CustomButtonField(String label, int foregroundColor,
			int backgroundColor, long style) {
		super(style);
		this.label = label;
		this.foregroundColor = foregroundColor;
		this.backgroundColor = backgroundColor;
	}
	
	public int getPreferredHeight() {
		return getFont().getHeight() + 8;
	}

	public int getPreferredWidth() {
		return getFont().getAdvance(label) + 8;
	}

	protected void layout(int width, int height) {
		setExtent(Math.min(width, getPreferredWidth()), Math.min(height, getPreferredHeight()));
	}

	protected void paint(Graphics graphics) {
		if (isFocus()) {
			graphics.setColor(focusedBackgroundColor);
			graphics.fillRoundRect(1, 1, getWidth()-2, getHeight()-2, 12, 12);
			graphics.setColor(Color.WHITE);
			graphics.setGlobalAlpha(100);
			graphics.fillRoundRect(3, 3, getWidth()-6, getHeight()/2, 12, 12);
			graphics.setGlobalAlpha(255);
			graphics.setColor(focusedForegroundColor);
			graphics.drawText(label, 4, 4);
		}
		else {
			graphics.setColor(backgroundColor);
			graphics.fillRoundRect(1, 1, getWidth()-2, getHeight()-2, 12, 12);
			graphics.setColor(foregroundColor);
			graphics.drawText(label, 4, 4);
		}
	}

	public boolean isFocusable() {
		return true;
	}

	protected void drawFocus(Graphics graphics, boolean on) {
	}
	
	protected void onFocus(int direction) {
		super.onFocus(direction);
		invalidate();
	}
	
	protected void onUnfocus() {
		super.onUnfocus();
		invalidate();
	}
	
	protected boolean navigationClick(int status, int time) {
		fieldChangeNotify(0);
		return true;
	}
	
	protected boolean keyChar(char character, int status, int time) {
		if (character == Keypad.KEY_ENTER) {
			fieldChangeNotify(0);
			return true;
		}
		return super.keyChar(character, status, time);
	}

	
	
}
