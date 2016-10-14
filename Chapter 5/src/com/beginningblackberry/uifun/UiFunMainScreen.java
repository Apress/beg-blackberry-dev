package com.beginningblackberry.uifun;

import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.Color;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.FieldChangeListener;
import net.rim.device.api.ui.Font;
import net.rim.device.api.ui.FontFamily;
import net.rim.device.api.ui.Graphics;
import net.rim.device.api.ui.MenuItem;
import net.rim.device.api.ui.Ui;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.BitmapField;
import net.rim.device.api.ui.component.CheckboxField;
import net.rim.device.api.ui.component.EditField;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.Menu;
import net.rim.device.api.ui.component.ObjectChoiceField;
import net.rim.device.api.ui.component.PasswordEditField;
import net.rim.device.api.ui.component.SeparatorField;
import net.rim.device.api.ui.container.HorizontalFieldManager;
import net.rim.device.api.ui.container.MainScreen;
import net.rim.device.api.ui.component.ButtonField;;

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
	CustomButtonField clearButton;
	CustomButtonField loginButton;
	ObjectChoiceField domainField;
	CheckboxField rememberCheckbox;
	
	public UiFunMainScreen() {
		try {
			FontFamily alphaSansFamily = FontFamily.forName("BBAlpha Serif");
			Font appFont = alphaSansFamily.getFont(Font.PLAIN, 9, Ui.UNITS_pt);
			setFont(appFont);
		} catch (ClassNotFoundException e) {
		}
		
		Bitmap logoBitmap = Bitmap.getBitmapResource("res/apress_logo.png");
		bitmapField = new BitmapField(logoBitmap, Field.FIELD_LEFT);
		HorizontalFieldManager hfmLabel = new HorizontalFieldManager(Field.USE_ALL_WIDTH) {
			protected void paint(Graphics graphics) {
				graphics.setBackgroundColor(Color.BLACK);
				graphics.clear();
				super.paint(graphics);
			}
		};
		/*HorizontalFieldManager hfmLabel = new HorizontalFieldManager(Field.USE_ALL_WIDTH);
		Background blackBackground = BackgroundFactory.createSolidBackground(Color.BLACK);
		hfmLabel.setBackground(blackBackground);
		*/
		hfmLabel.add(bitmapField);
		add(hfmLabel);
		add(new SeparatorField());
		Bitmap loginImage = Bitmap.getBitmapResource("res/login_arrow.png");
		add(new CustomLabelField("Please enter your credentials:", Color.WHITE, 0x999966, loginImage, Field.USE_ALL_WIDTH));
		
		usernameField = new EditField("", "");
		LabelField usernameLabel = new LabelField("   Username:", Field.FIELD_RIGHT);
		passwordField = new PasswordEditField("", "");
		LabelField passwordLabel = new LabelField("Password:", Field.FIELD_RIGHT);		
		domainField = new ObjectChoiceField("", new String[] {"Home", "Work"});
		LabelField domainLabel = new LabelField("Domain:", Field.FIELD_RIGHT);

		GridFieldManager gridFieldManager = new GridFieldManager(2, 0);
		gridFieldManager.add(usernameLabel);
		gridFieldManager.add(usernameField);
		gridFieldManager.add(passwordLabel);
		gridFieldManager.add(passwordField);
		gridFieldManager.add(domainLabel);
		gridFieldManager.add(domainField);
		
		add(gridFieldManager);
		rememberCheckbox = new CheckboxField("Remember password", false, Field.FIELD_RIGHT);
		add(rememberCheckbox);
		
		clearButton = new CustomButtonField("Clear", Color.WHITE, Color.LIGHTGREY, Color.YELLOW, Color.GREEN, 0);
		clearButton.setChangeListener(this);
		loginButton = new CustomButtonField("Login", Color.WHITE, Color.LIGHTGREY, Color.YELLOW, Color.GREEN, 0);
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

	
	protected void paint(Graphics graphics) {
		graphics.setBackgroundColor(0xCCCC99);
		graphics.clear();
		super.paint(graphics);
	}

	

	private void clearTextFields() {
		usernameField.setText("");
		passwordField.setText("");
	}
	
	private void login() {
		if (usernameField.getTextLength() == 0 || passwordField.getTextLength() == 0) {
			UiApplication.getUiApplication().pushModalScreen(new CustomDialog("You must enter a username and password"));
			//Dialog.alert("You must enter a username and password");
		}
		else {
			String username = usernameField.getText();
			String selectedDomain = (String)domainField.getChoice(domainField.getSelectedIndex());
			LoginSuccessScreen loginSuccessScreen = new LoginSuccessScreen(username, selectedDomain);
			UiApplication.getUiApplication().pushScreen(loginSuccessScreen);
		}
	}
	
}
