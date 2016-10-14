package com.beginningblackberry.uifun;

import net.rim.device.api.ui.component.ObjectChoiceField;

public class CustomObjectChoiceField extends ObjectChoiceField {

	public CustomObjectChoiceField(String label, Object[] choices) {
		super(label, choices);
	}

	protected void layout(int width, int height) {
		super.layout(getPreferredWidth(), getPreferredHeight());
	}
	
}
