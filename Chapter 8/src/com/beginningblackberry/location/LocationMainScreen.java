package com.beginningblackberry.location;

import javax.microedition.location.Coordinates;
import javax.microedition.location.Location;
import javax.microedition.location.LocationListener;
import javax.microedition.location.LocationProvider;
import javax.microedition.location.QualifiedCoordinates;

import net.rim.blackberry.api.invoke.Invoke;
import net.rim.blackberry.api.invoke.MapsArguments;
import net.rim.blackberry.api.maps.MapView;
import net.rim.device.api.lbs.MapField;
import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.MenuItem;
import net.rim.device.api.ui.UiApplication;
import net.rim.device.api.ui.component.LabelField;
import net.rim.device.api.ui.component.Menu;
import net.rim.device.api.ui.component.RichTextField;
import net.rim.device.api.ui.container.HorizontalFieldManager;
import net.rim.device.api.ui.container.MainScreen;


public class LocationMainScreen extends MainScreen {

	private LabelField latitudeLabel;
	private LabelField longitudeLabel;
	private RichTextField messageField;
	private MapField mapField;
	private LocationHandler locationHandler = new LocationHandler(this, true);

	public LocationMainScreen() {
		HorizontalFieldManager latManager = new HorizontalFieldManager();
		latManager.add(new LabelField("Latitude:"));
		latitudeLabel = new LabelField("");
		latManager.add(latitudeLabel);
		
		add(latManager);
		
		HorizontalFieldManager longManager = new HorizontalFieldManager();
		longManager.add(new LabelField("Longitude:"));
		longitudeLabel = new LabelField("");
		longManager.add(longitudeLabel);
		
		add(longManager);
		
		// MapField is only available in JDE 4.5 and later
		mapField = new MapField(Field.FIELD_HCENTER);
		mapField.setPreferredSize(200, 150);
		add(mapField);

		messageField = new RichTextField();
		add(messageField);
		
	}

	public void setLocation(double longitude, double latitude) {
		synchronized(UiApplication.getEventLock()) {
			longitudeLabel.setText(Double.toString(longitude));
			latitudeLabel.setText(Double.toString(latitude));
			mapField.moveTo((int)(latitude * 100000), (int)(longitude * 100000));
			mapField.setZoom(1);
		}
	}
	
	public void setMessage(String message) {
		synchronized (UiApplication.getEventLock()) {
			messageField.setText(message);
			
		}
	}
	
	private void update() {
		locationHandler.start();
	}
	
	private void map() {
		String document = "<lbs>";
		Coordinates[] coordinates = locationHandler.getCoordinateHistory();
		for (int i = 0; i < coordinates.length; i++) {
			document += "<location x='"
					+ (int) (coordinates[i].getLongitude() * 100000) + "' y='"
					+ (int) (coordinates[i].getLatitude() * 100000)
					+ "' label='Location " + i
					+ "' description='Marker for history coordinate " + i
					+ "'/>";
		}
		document += "</lbs>";
		MapsArguments args = new MapsArguments(MapsArguments.ARG_LOCATION_DOCUMENT, document);
		Invoke.invokeApplication(Invoke.APP_TYPE_MAPS, args);
	}

	private void route() {
		String document = "<lbs>";
		Coordinates[] coordinates = locationHandler.getCoordinateHistory();
		document += "<getRoute>";
		for (int i = 0; i < coordinates.length; i++) {
			document += "<location x='"
					+ (int) (coordinates[i].getLongitude() * 100000) + "' y='"
					+ (int) (coordinates[i].getLatitude() * 100000)
					+ "' label='Location " + i
					+ "' description='Marker for history coordinate " + i
					+ "'/>";
		}
		document += "</getRoute>";
		document += "</lbs>";
		MapsArguments args = new MapsArguments(MapsArguments.ARG_LOCATION_DOCUMENT, document);
		Invoke.invokeApplication(Invoke.APP_TYPE_MAPS, args);
	}
	
	private void customView() {
		Coordinates[] coordinates = locationHandler.getCoordinateHistory();
		if (coordinates.length > 0) {
			MapView view = new MapView();
			Coordinates lastCoordinates = coordinates[coordinates.length - 1];
			view.setLatitude((int)(lastCoordinates.getLatitude() * 100000));
			view.setLongitude((int)(lastCoordinates.getLongitude() * 100000));
			view.setZoom(MapView.MAX_ZOOM);
			
			MapsArguments args = new MapsArguments(view);
			Invoke.invokeApplication(Invoke.APP_TYPE_MAPS, args);
		}
	}
	
	protected void makeMenu(Menu menu, int instance) {
		super.makeMenu(menu, instance);
		menu.add(new MenuItem("Update", 10, 10) {
			public void run() {
				update();
			}
		});
		menu.add(new MenuItem("Map", 10, 10) {
			public void run() {
				map();
			}
		});
		menu.add(new MenuItem("Route", 10, 10) {
			public void run() {
				route();
			}
		});
		menu.add(new MenuItem("Custom View", 10, 10) {
			public void run() {
				customView();
			}
		});
	}
	
	
}
