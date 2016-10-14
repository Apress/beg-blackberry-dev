package com.beginningblackberry.location;

import java.util.Date;
import java.util.Vector;

import javax.microedition.location.Coordinates;
import javax.microedition.location.Criteria;
import javax.microedition.location.Location;
import javax.microedition.location.LocationException;
import javax.microedition.location.LocationListener;
import javax.microedition.location.LocationProvider;
import javax.microedition.location.QualifiedCoordinates;

import net.rim.device.api.i18n.SimpleDateFormat;


public class LocationHandler extends Thread implements LocationListener {
	private LocationMainScreen screen;
	private boolean periodicUpdates;
	private Vector coordinateHistory = new Vector();
	
	public LocationHandler(LocationMainScreen screen, boolean update) {
		this.screen = screen;
		this.periodicUpdates = update;
	}

	public LocationHandler(LocationMainScreen screen) {
		this.screen = screen;
	}
	
	public Coordinates[] getCoordinateHistory() {
		Coordinates[] coordinates = new Coordinates[coordinateHistory.size()];
		coordinateHistory.copyInto(coordinates);
		return coordinates;
	}
	
	
	public void locationUpdated(LocationProvider provider, Location location) {
		QualifiedCoordinates qualifiedCoordinates = location.getQualifiedCoordinates();
		
		screen.setLocation(qualifiedCoordinates.getLongitude(), qualifiedCoordinates.getLatitude());

		String message = "Successfully got location at ";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm:ss");
		message += simpleDateFormat.format(new Date(location.getTimestamp()));

		if (coordinateHistory.size() > 0) {
			Coordinates lastCoordinates = (Coordinates)coordinateHistory.lastElement();
			message += "\nDistance from last update: " + lastCoordinates.distance(qualifiedCoordinates);
		}
		
		coordinateHistory.addElement(qualifiedCoordinates);
		
		message += "\nMethod:";
		int method = location.getLocationMethod();
		if ((method & Location.MTA_ASSISTED) == Location.MTA_ASSISTED) {
			message += " Assisted GPS";
		}
		if ((method & Location.MTA_UNASSISTED) == Location.MTA_UNASSISTED) {
			message += " Unassisted GPS";
		}
		if ((method & Location.MTE_CELLID) == Location.MTE_CELLID) {
			message += " Cell Site";
		}
		
		message += "\nHorizontal (Longitude) Accuracy: ";
		
		message += qualifiedCoordinates.getHorizontalAccuracy();
		
		message += "\nVertical (Latitude) Accuracy: ";
		
		message += qualifiedCoordinates.getVerticalAccuracy();

		screen.setMessage(message);
		
	}

	public void providerStateChanged(LocationProvider provider, int newState) {
		// Do nothing for our application
	}

	public void run() {
		Criteria csCriteria = new Criteria();
		csCriteria.setHorizontalAccuracy(Criteria.NO_REQUIREMENT);
		csCriteria.setVerticalAccuracy(Criteria.NO_REQUIREMENT);
		csCriteria.setCostAllowed(true);
		csCriteria.setPreferredPowerConsumption(Criteria.POWER_USAGE_LOW);

		Criteria criteria = new Criteria();
		criteria.setVerticalAccuracy(50);
		criteria.setHorizontalAccuracy(50);
		criteria.setCostAllowed(true);
		criteria.setPreferredPowerConsumption(Criteria.POWER_USAGE_HIGH);
		
		try {
			screen.setMessage("Getting location...");
			LocationProvider provider = LocationProvider.getInstance(criteria);
			Location location = provider.getLocation(-1);			
		
			locationUpdated(provider, location);
			if (periodicUpdates) {
				// Update every 3 minutes
				provider.setLocationListener(this, 180, -1, 10);
			}
		} catch (LocationException e) {
			screen.setMessage("LocationException occurred getting location: " + e.getMessage());
		} catch (InterruptedException e) {
			screen.setMessage("InterruptedException occurred getting location: " + e.getMessage());
		}
	}

}
