package com.beginningblackberry;

import net.rim.device.api.ui.UiApplication;

public class MainScreenUpdaterThread extends Thread {
	HelloWorldMainScreen mainScreen;
	
	public MainScreenUpdaterThread(HelloWorldMainScreen mainScreen) {
		this.mainScreen = mainScreen;
	}

	public void run() {
		for (int i = 0; i < 10; i++) {
			try {
				Thread.sleep(5000);
			} catch (InterruptedException ex) {

			}
			// Ensure we have the event lock
			synchronized(UiApplication.getEventLock()) {
				mainScreen.appendLabelText("Update");
			}
		}
	}
}
