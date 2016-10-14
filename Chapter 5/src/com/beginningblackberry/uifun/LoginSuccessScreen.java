package com.beginningblackberry.uifun;

import net.rim.device.api.system.Display;
import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.FontFamily;
import net.rim.device.api.ui.Ui;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.SeparatorField;
import net.rim.device.api.ui.container.MainScreen;

public class LoginSuccessScreen extends MainScreen implements Runnable {
	private int verticalOffset;
	private final static long animationTime = 300;
	private long animationStart = 0;
	
	public LoginSuccessScreen(String username, String domain) {
		try {
			FontFamily alphaSansFamily = FontFamily.forName("BBAlpha Serif");
			Font appFont = alphaSansFamily.getFont(Font.PLAIN, 9, Ui.UNITS_pt);
			setFont(appFont);
		} catch (ClassNotFoundException e) {
		}

		add(new CustomLabelField("Logged In!", Color.WHITE, 0x999966, Field.USE_ALL_WIDTH));
		add(new SeparatorField());
		GridFieldManager gridFieldManager = new GridFieldManager(2, 0);
		gridFieldManager.add(new CustomLabelField("Username:", Color.BLACK, Color.WHITE, Field.FIELD_RIGHT));
		gridFieldManager.add(new CustomLabelField(username, Color.BLACK, Color.LIGHTGREY, Field.USE_ALL_WIDTH));
		gridFieldManager.add(new CustomLabelField("Domain:", Color.BLACK, Color.WHITE, Field.FIELD_RIGHT));
		gridFieldManager.add(new CustomLabelField(domain, Color.BLACK, Color.LIGHTGREY, Field.USE_ALL_WIDTH));
		add(gridFieldManager);
		
		verticalOffset = Display.getHeight();
		//new Thread(this).start();
	}

	
	public void run() {
		int nSteps = 10;
		int stepSize = Display.getHeight() / nSteps;
		for(int i = 0; i < nSteps; i++) {
			verticalOffset -= stepSize;
			synchronized(UiApplication.getEventLock()) {
				updateLayout();
			}
			try {
				Thread.sleep(20);
			} catch (InterruptedException ex) {
			}
		}
		
	}

	protected void sublayout(int width, int height) {
		super.sublayout(width, height);
		if (verticalOffset > 0) {
			if (animationStart == 0) {
				// start the animation
				animationStart = System.currentTimeMillis();
			}
			else {
				long timeElapsed = System.currentTimeMillis() - animationStart;
				if (timeElapsed >= animationTime) {
					verticalOffset = 0;
				}
				else {
					float percentDone = (float)timeElapsed / (float)animationTime;
					verticalOffset = Display.getHeight() - (int)(percentDone * Display.getHeight());
				}
			}
		}
		setPosition(0, verticalOffset);
		
		if (verticalOffset > 0) {
			UiApplication.getUiApplication().invokeLater(new Runnable() {
				public void run() {
					updateLayout();				
				}
			});
		}
	}	
	
}
