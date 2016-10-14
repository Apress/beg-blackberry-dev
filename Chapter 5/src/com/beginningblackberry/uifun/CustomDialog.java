package com.beginningblackberry.uifun;

import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.FontFamily;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.Screen;
import net.rim.device.api.ui.Ui;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.SeparatorField;
import net.rim.device.api.ui.container.VerticalFieldManager;

public class CustomDialog extends Screen implements FieldChangeListener {
	private CustomButtonField okButton;
	
	public CustomDialog(String message) {
		super(new VerticalFieldManager(), Screen.DEFAULT_CLOSE);
		try {
			FontFamily alphaSansFamily = FontFamily.forName("BBAlpha Serif");
			Font appFont = alphaSansFamily.getFont(Font.PLAIN, 9, Ui.UNITS_pt);
			setFont(appFont);
		} catch (ClassNotFoundException e) {
		}

		add(new LabelField(message));
		
		add(new SeparatorField());
		
		okButton = new CustomButtonField("OK", Color.WHITE, Color.LIGHTGREY, Color.YELLOW, Color.GREEN, Field.FIELD_HCENTER);
		okButton.setChangeListener(this);
		add(okButton);
	}

	//...
	
	public void fieldChanged(Field field, int context) {
		if (field == okButton) {
			close();
		}
	}


	protected void sublayout(int width, int height) {
		layoutDelegate(width - 80, height - 80);
		setPositionDelegate(10, 10);
		setExtent(width - 60, Math.min(height - 60, getDelegate().getHeight() + 20));
		setPosition(30, (height - getHeight())/2);
	}

	protected void paintBackground(Graphics graphics) {
		graphics.setColor(0x999966);
		graphics.fillRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
		graphics.setColor(Color.BLACK);
		graphics.drawRoundRect(0, 0, getWidth(), getHeight(), 12, 12);
	}
	
	
}
