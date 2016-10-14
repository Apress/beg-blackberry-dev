package com.beginningblackberry.persistence;

import net.rim.device.api.ui.UiApplication;

public class PersistenceApplication extends UiApplication {
	public PersistenceApplication() {
		PersistenceScreen screen = new PersistenceScreen();
		pushScreen(screen);
	}
	
	public static void main(String[] args) {
		PersistenceApplication application = new PersistenceApplication();
		application.enterEventDispatcher();
	}

}
