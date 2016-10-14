package com.beginningblackberry;

import net.rim.device.api.ui.UiApplication;

public class HelloWorldApp extends UiApplication {
	
	private HelloWorldMainScreen mainScreen;

	public HelloWorldApp(String[] args) {
		if (args.length > 0 && args[0].equals("alt")) {
			mainScreen = new HelloWorldMainScreen(true);
		}
		else {
			mainScreen = new HelloWorldMainScreen(false);
		}
		pushScreen(mainScreen);
	}

	public void deactivate() {
		mainScreen.appendLabelText("Went to background");
	}
	
	

	public void activate() {
	    mainScreen.appendLabelText("Came to foreground");
	}

	public static void main(String[] args) {
		HelloWorldApp app = new HelloWorldApp(args);
		app.enterEventDispatcher();
	}

}
