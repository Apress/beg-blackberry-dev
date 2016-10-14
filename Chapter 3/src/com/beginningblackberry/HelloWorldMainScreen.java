package com.beginningblackberry;

import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.container.MainScreen;

public class HelloWorldMainScreen extends MainScreen {

	private LabelField labelField;

	public HelloWorldMainScreen(boolean isAlternateEntry) {
		if (isAlternateEntry) {
			labelField = new LabelField("Goodbye World!");
		}
		else {
			labelField = new LabelField("Hello World!");
		}
		add(labelField);
	}
	
	public HelloWorldMainScreen() {
		labelField = new LabelField("Hello World");
		add(labelField);
		
		MainScreenUpdaterThread thread = new MainScreenUpdaterThread(this);
		thread.start();
	}
	
	public void appendLabelText(String text) {
		labelField.setText(labelField.getText() + " \r\n" + text);
	}


    
}
