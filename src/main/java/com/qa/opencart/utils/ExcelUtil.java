package com.qa.opencart.utils;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
//import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

public class ExcelUtil {

	private static final String TEST_DATA_SHEET_PATH_STRING = "src/test/resources/testdata/opencarttestdata.xlsx";
	private static Workbook book;
	private static Sheet sheet;

	public static Object[][] getTestData(String sheetName) throws EncryptedDocumentException, IOException, InvalidFormatException
	// sheetname you have to give we need to supply and read the data
	{
		System.out.println("=====> reading data from sheet: " + sheetName);
		Object data[][] = null;
		try {
			// Step 1: Open the Excel file using a FileInputStream
			FileInputStream ip = new FileInputStream(TEST_DATA_SHEET_PATH_STRING);
			// Now we need to write apache poi code(2lines code) and no need to remember
			// this code
			

			// Step 2: Create a Workbook instance from the file input stream
			book = WorkbookFactory.create(ip);

			// Step 3: Access the desired sheet by its name
			sheet = book.getSheet(sheetName);

			// Step 4: Initialize a 2D array to store Excel data (excluding the header row)
			// Rows: total rows - 1 (excluding header)
			// Columns: number of cells in the first row (assumed to be the header)
			data = new Object[sheet.getLastRowNum()][sheet.getRow(0).getLastCellNum()];

			// Step 5: Loop through each row (starting from 1 to skip header row)
			for (int i = 0; i < sheet.getLastRowNum(); i++) {
				// Step 6: Loop through each cell (column) in the current row
				for (int j = 0; j < sheet.getRow(0).getLastCellNum(); j++) {
					// Step 7: Read the cell value, convert to String, and store in data array
					data[i][j] = sheet.getRow(i + 1).getCell(j).toString();
				}
			}
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return data;

	}

}
