package com.beginningblackberry.networking;

import net.rim.device.api.ui.UiApplication;

public class NetworkingApplication extends UiApplication {

	public NetworkingApplication() {
		NetworkingMainScreen scr = new NetworkingMainScreen();
		pushScreen(scr);
	}

	public static void main(String[] args) {
		NetworkingApplication application = new NetworkingApplication();
		application.enterEventDispatcher();
	}
}
