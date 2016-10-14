package com.beginningblackberry.uifun;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.MenuItem;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.BitmapField;
import net.rim.device.api.ui.component.ButtonField;
import net.rim.device.api.ui.component.CheckboxField;
import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.ui.component.EditField;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.Menu;
import net.rim.device.api.ui.component.ObjectChoiceField;
import net.rim.device.api.ui.component.PasswordEditField;
import net.rim.device.api.ui.component.SeparatorField;
import net.rim.device.api.ui.container.HorizontalFieldManager;
import net.rim.device.api.ui.container.MainScreen;

public class UiFunMainScreen extends MainScreen implements FieldChangeListener {
	class LoginMenuItem extends MenuItem {
		public LoginMenuItem() {
			super("Login", 20, 10);
		}

		public void run() {
			login();
		}		
	}
	
	class ClearMenuItem extends MenuItem {
		public ClearMenuItem() {
			super("Clear", 10, 20);
		}
		
		public void run() {
			clearTextFields();
			
		}
	}

	BitmapField bitmapField;
	EditField usernameField;
	PasswordEditField passwordField;
	ButtonField clearButton;
	ButtonField loginButton;
	ObjectChoiceField domainField;
	CheckboxField rememberCheckbox;
	
	public UiFunMainScreen() {
		
		Bitmap logoBitmap = Bitmap.getBitmapResource("res/apress_logo.png");
		bitmapField = new BitmapField(logoBitmap, Field.FIELD_HCENTER);
		add(bitmapField);
		add(new SeparatorField());
		add(new LabelField("Please enter your credentials:"));
		
		usernameField = new EditField("Username:", "");
		passwordField = new PasswordEditField("Password:", "");
		
		domainField = new ObjectChoiceField("Domain:", new String[] {"Home", "Work"});

		add(usernameField);
		add(passwordField);

		add(domainField);
		
		rememberCheckbox = new CheckboxField("Remember password:", false);
		add(rememberCheckbox);
		
		clearButton = new ButtonField("Clear", ButtonField.CONSUME_CLICK);
		clearButton.setChangeListener(this);
		loginButton = new ButtonField("Login", ButtonField.CONSUME_CLICK);
		loginButton.setChangeListener(this);

		HorizontalFieldManager buttonManager = new HorizontalFieldManager(Field.FIELD_RIGHT);
		buttonManager.add(clearButton);
		buttonManager.add(loginButton);		
		add(new SeparatorField());
		add(buttonManager);
	}

	
	protected void makeMenu(Menu menu, int instance) {
		super.makeMenu(menu, instance);
		menu.add(new LoginMenuItem());
		menu.add(new ClearMenuItem());
	}

	public void fieldChanged(Field field, int context) {
		if (field == clearButton) {
			clearTextFields();
		}
		else if (field == loginButton) {
			login();
		}
	}

	private void clearTextFields() {
		usernameField.setText("");
		passwordField.setText("");
	}
	
	private void login() {
		if (usernameField.getTextLength() == 0 || passwordField.getTextLength() == 0) {
			Dialog.alert("You must enter a username and password");
		}
		else {
			String username = usernameField.getText();
			String selectedDomain = (String)domainField.getChoice(domainField.getSelectedIndex());
			LoginSuccessScreen loginSuccessScreen = new LoginSuccessScreen(username, selectedDomain);
			UiApplication.getUiApplication().pushScreen(loginSuccessScreen);
		}
	}
	
}
