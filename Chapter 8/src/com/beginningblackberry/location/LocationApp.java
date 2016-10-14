package com.beginningblackberry.location;

import net.rim.device.api.ui.UiApplication;

public class LocationApp extends UiApplication {
	public LocationApp() {
		LocationMainScreen screen = new LocationMainScreen();
		pushScreen(screen);
	}
	
	public static void main(String[] args) {
		LocationApp app = new LocationApp();
		app.enterEventDispatcher();
	}
}
