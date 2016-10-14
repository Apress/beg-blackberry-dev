package com.beginningblackberry;

import net.rim.device.api.ui.UiApplication;

public class HelloWorldApp extends UiApplication {
	
	private HelloWorldMainScreen mainScreen;

	public HelloWorldApp(String[] args) {
		mainScreen = new HelloWorldMainScreen();
		pushScreen(mainScreen); 
	}

	public static void main(String[] args) {
		HelloWorldApp app = new HelloWorldApp(args);
		app.enterEventDispatcher(); // This call will not return until we close the main screen
	}

}
