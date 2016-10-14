package com.beginningblackberry;

import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.container.MainScreen;

public class HelloWorldMainScreen extends MainScreen {

	private LabelField labelField;

	public HelloWorldMainScreen() {
		labelField = new LabelField("Hello World!");
		add(labelField);
	}
}
