package com.beginningblackberry.uifun;
import net.rim.device.api.ui.UiApplication;

public class UiFunApplication extends UiApplication {
	public UiFunApplication() {
		UiFunMainScreen mainScreen = new UiFunMainScreen();
		pushScreen(mainScreen);
	}

	public static void main(String[] args) {
		UiFunApplication app = new UiFunApplication();
		app.enterEventDispatcher();
	}
}
