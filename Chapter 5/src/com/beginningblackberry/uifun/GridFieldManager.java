package com.beginningblackberry.uifun;

import net.rim.device.api.ui.Field;
import net.rim.device.api.ui.Manager;

public class GridFieldManager extends Manager {
	private int numColumns;

	public GridFieldManager(int numColumns, long style) {
		super(style);
		this.numColumns = numColumns;
	}

	protected void sublayout(int width, int height) {
		int[] columnWidths = new int[numColumns];
		int availableWidth = width;
		int availableHeight = height;
		
		// For each column size all the fields and get the maximum width
		for(int column = 0; column < numColumns; column++) {		
			for(int fieldIndex = column; fieldIndex < getFieldCount(); fieldIndex += numColumns) {
				Field field = getField(fieldIndex);
				layoutChild(field, availableWidth, availableHeight);
				if (field.getWidth() > columnWidths[column]) {
					columnWidths[column] = field.getWidth();
				}
				
			}
			
			availableWidth -= columnWidths[column];
		}
		
		int currentRow = 0;
		int currentRowHeight = 0;
		int rowYOffset = 0;
		
		// Set the position of each field
		for(int fieldIndex = 0; fieldIndex < getFieldCount(); fieldIndex++) {
			Field field = getField(fieldIndex);
			int fieldOffset = 0;
			if ((field.getStyle() & Field.FIELD_RIGHT) == Field.FIELD_RIGHT) {
				fieldOffset = columnWidths[fieldIndex % numColumns] - field.getWidth();
			}
			if (fieldIndex % numColumns == 0) {
				setPositionChild(field, 0 + fieldOffset, rowYOffset);
			}
			else {
				setPositionChild(field, columnWidths[(fieldIndex % numColumns) - 1] + fieldOffset, rowYOffset);
			}
			
			if (field.getHeight() > currentRowHeight) {
				currentRowHeight = field.getHeight();
			}
			
			if (fieldIndex % numColumns == numColumns - 1) {
				currentRow ++;
				rowYOffset += currentRowHeight;
				currentRowHeight = 0;
			}
		}
		
		int totalWidth = 0;
		for(int i = 0; i < numColumns; i++) {
			totalWidth += columnWidths[i];
		}
		setExtent(totalWidth, rowYOffset + currentRowHeight);
	}
}
