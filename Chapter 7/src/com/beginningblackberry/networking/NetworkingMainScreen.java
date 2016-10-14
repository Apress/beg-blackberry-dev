package com.beginningblackberry.networking;

import net.rim.blackberry.api.browser.URLEncodedPostData;
import net.rim.device.api.system.Bitmap;
import net.rim.device.api.ui.MenuItem;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.BitmapField;
import net.rim.device.api.ui.component.Dialog;
import net.rim.device.api.ui.component.EditField;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.Menu;
import net.rim.device.api.ui.component.RichTextField;
import net.rim.device.api.ui.component.SeparatorField;
import net.rim.device.api.ui.container.MainScreen;

public class NetworkingMainScreen extends MainScreen {
	private EditField urlField;
	private EditField postDataField;
	private BitmapField imageOutputField;
	private RichTextField textOutputField;
	
	public NetworkingMainScreen() {
		
		setTitle("Networking");
		urlField = new EditField("URL:", "");
		textOutputField = new RichTextField();
		imageOutputField = new BitmapField();
	
		add(urlField);
		add(new SeparatorField());
		
		postDataField = new EditField("Post data:", "");
		add(postDataField);
		add(new SeparatorField());
		
		add(new LabelField("Image retrieved:"));
		add(imageOutputField);
		add(new SeparatorField());
		add(new LabelField("Text retrieved:"));
		add(textOutputField);
	}
	
	private void getURL() {		
		HttpRequestDispatcher dispatcher = new HttpRequestDispatcher(urlField.getText(), "GET", this);
		dispatcher.start();
	}

	protected void makeMenu(Menu menu, int instance) {
		super.makeMenu(menu, instance);
		menu.add(new MenuItem("Get", 10, 10) {
			public void run() {
				getURL();
			}
		});
		menu.add(new MenuItem("Post", 10, 10) {
			public void run() {
				postURL();
			}
		});
		menu.add(new MenuItem("Socket Get", 10, 10) {
			public void run() {
				socketGet();
			}
		});
	}
	
	private void socketGet() {
		SocketConnector connector = new SocketConnector(urlField.getText(), this);
		connector.start();
	}
	
	private void postURL() {
		String postString = postDataField.getText();
		URLEncodedPostData encodedData = new URLEncodedPostData(null, false);
		encodedData.append("content", postString);
		HttpRequestDispatcher dispatcher = new HttpRequestDispatcher(urlField
				.getText(), "POST", this, encodedData.getBytes());
		dispatcher.start();
	}

	public void requestSucceeded(byte[] result, String contentType) {
		if (contentType.equals("image/png") ||
				contentType.equals("image/jpeg") ||
				contentType.equals("image/gif")) {
			Bitmap bitmap = Bitmap.createBitmapFromBytes(result, 0, result.length, 1);
			synchronized (UiApplication.getEventLock()) {
				imageOutputField.setBitmap(bitmap);
			}
			
		}
		else if (contentType.startsWith("text/")) {
			String strResult = new String(result);
			synchronized (UiApplication.getEventLock()) {
				textOutputField.setText(strResult);
			}
		}
		else {
			synchronized (UiApplication.getEventLock()) {
				Dialog.alert("Unknown content type: " + contentType);
			}
		}
	}
	
	public void requestFailed(final String message) {
		UiApplication.getUiApplication().invokeLater(new Runnable() {
			public void run() {
				Dialog.alert("Request failed.  Reason: " + message);
			}
		});
		
	}
	
}
