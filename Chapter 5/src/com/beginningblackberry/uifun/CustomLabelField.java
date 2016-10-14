package com.beginningblackberry.uifun;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.DrawStyle;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Graphics;

public class CustomLabelField extends Field {
	private Bitmap image;
	private String label;
	private int foregroundColor;
	private int backgroundColor;

	public CustomLabelField(String label, int foregroundColor,
			int backgroundColor, Bitmap image, long style) {
		super(style);
		this.label = label;
		this.foregroundColor = foregroundColor;
		this.backgroundColor = backgroundColor;
		this.image = image;
	}

	public CustomLabelField(String label, int foregroundColor,
			int backgroundColor, long style) {
		super(style);
		this.label = label;
		this.foregroundColor = foregroundColor;
		this.backgroundColor = backgroundColor;
	}

	protected void layout(int width, int height) {
		if ((getStyle() & Field.USE_ALL_WIDTH) == Field.USE_ALL_WIDTH) {
			setExtent(width, Math.min(height, getPreferredHeight()));
		}
		else {
			setExtent(getPreferredWidth(), getPreferredHeight());
		}
	}
	
	protected void paint(Graphics graphics) {
		graphics.setBackgroundColor(backgroundColor);
		graphics.clear();
		graphics.setColor(foregroundColor);
		if (image != null) {
			int textY = (getHeight() - getFont().getHeight()) / 2;
			int imageY = (getHeight() - image.getHeight()) / 2;
			graphics.drawBitmap(0, imageY, image.getWidth(), image.getHeight(),
					image, 0, 0);
			graphics.drawText(label, image.getWidth(), textY, DrawStyle.ELLIPSIS, getWidth()-image.getWidth());
		}
		else {
			graphics.drawText(label, 0, 0, DrawStyle.ELLIPSIS, getWidth());
		}
	}

	public int getPreferredHeight() {
		if (image != null) {
			return Math.max(getFont().getHeight(), image.getHeight());
		}
		else {
			return getFont().getHeight();
		}
	}

	public int getPreferredWidth() {
		int width = getFont().getAdvance(label);
		if (image != null) {
			width += image.getWidth();
		}
		return width;
	}
	
	
}
