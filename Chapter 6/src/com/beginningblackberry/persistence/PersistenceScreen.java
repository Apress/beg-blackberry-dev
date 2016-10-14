package com.beginningblackberry.persistence;

import java.io.IOException;
import java.util.Hashtable;

import net.rim.device.api.system.PersistentObject;
import net.rim.device.api.system.PersistentStore;
import net.rim.device.api.ui.component.CheckboxField;
import net.rim.device.api.ui.component.DateField;
import net.rim.device.api.ui.component.EditField;
import net.rim.device.api.ui.component.NumericChoiceField;
import net.rim.device.api.ui.container.MainScreen;

public class PersistenceScreen extends MainScreen {

	Hashtable persistentHashtable;
	
	CheckboxField checkboxField;
	NumericChoiceField numericChoiceField;
	DateField dateField;
	
	PersistentObject persistentObject;
	static final long KEY = 0x9df9f961bc6d6baL;

	EditField editField;
	
	public PersistenceScreen() {
		setTitle("Persistence Test");
		editField = new EditField("Persistent Data:", "");
		add(editField);
		
		checkboxField = new CheckboxField("Boolean data", false);
		numericChoiceField = new NumericChoiceField("Numeric data:", 1, 10, 1);
		dateField = new DateField("Date:", System.currentTimeMillis(), DateField.DATE);

		add(checkboxField);
		add(numericChoiceField);
		add(dateField);

		persistentObject = PersistentStore.getPersistentObject(KEY);
		
		if (persistentObject.getContents() == null) {
			persistentHashtable = new Hashtable();
			persistentObject.setContents(persistentHashtable);
		}
		else {
			persistentHashtable = (Hashtable)persistentObject.getContents();
		}
		
		if (persistentHashtable.containsKey("EditData")) {
			editField.setText((String)persistentHashtable.get("EditData"));
		}
		if (persistentHashtable.containsKey("BoolData")) {
			Boolean booleanObject = (Boolean)persistentHashtable.get("BoolData");
			checkboxField.setChecked(booleanObject.booleanValue());
		}
		if (persistentHashtable.containsKey("IntData")) {
			Integer intObject = (Integer)persistentHashtable.get("IntData");
			numericChoiceField.setSelectedValue(intObject.intValue());
		}
		if (persistentHashtable.containsKey("Date")) {
			Long longObject = (Long)persistentHashtable.get("Date");
			dateField.setDate(longObject.longValue());
		}
	}

	public void save() throws IOException {
		persistentHashtable.put("EditData", editField.getText());
		persistentHashtable.put("BoolData", new Boolean(checkboxField.getChecked()));
		persistentHashtable.put("IntData", new Integer(numericChoiceField.getSelectedValue()));
		persistentHashtable.put("Date", new Long(dateField.getDate()));
		persistentObject.commit();
	}
	
	
	
}
