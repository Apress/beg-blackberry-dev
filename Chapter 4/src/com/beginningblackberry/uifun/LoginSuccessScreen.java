package com.beginningblackberry.uifun;

import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.container.MainScreen;

public class LoginSuccessScreen extends MainScreen {
	public LoginSuccessScreen(String username, String domain) {
		add(new LabelField("Logged in!"));
		add(new LabelField("Username: " + username));
		add(new LabelField("Domain: " + domain));
	}
}
