package database;

import java.sql.ResultSet;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * Simple parent class
 * 
 * @author mblackford mBret Bret Blackford
 *
 */
public abstract class SqlSelection_Parent {

	static Logger log = Logger.getLogger(SqlSelection_Parent.class);
	Vector dataVector;
	
	String objName = "SqlSelection";
	String sql = "";
	int rowCount = 0;
	int numColumns = 0;
	Workbook workbook;
	Sheet sheet;


	public String getSql() {
		return sql;
	}
	
	public String printHeaders() {
		return "DEFAULT";
	}
	
	public Vector processResultSet(ResultSet result) {
		log.info( "SqlSelection.processResultSet(-" + objName + "-)" );
		log.info(" Do I ever get here??");
		
		return dataVector;
	}
	
	public abstract void printExcelWorksheet(Workbook workbook, Vector _inVec);
	
	
	protected void checkCell(Cell _cell) {
		if( _cell.getStringCellValue().equalsIgnoreCase("TRUE") ) {
			formatCell(_cell);
		}
	}
	
	/**
	 * Apache POI specific Excel formatting for a specific cell
	 * http://poi.apache.org/ 
	 * @param _cell
	 */
	public void formatCell(Cell _cell) {
		log.info( "in SqlSelection.formatCell(-" + objName + "-)" );
		CellStyle style = workbook.createCellStyle();
		style.setFillForegroundColor(IndexedColors.LIGHT_YELLOW.getIndex());
		style.setFillPattern(CellStyle.SOLID_FOREGROUND);
		
		_cell.setCellStyle(style);;
	}
	
	/**
	 * Apache POI specific Excel formatting for an entire row (header row)
	 * http://poi.apache.org/ 
	 */
	public void format() {
		log.info( "in SqlSelection.format(-" + objName + "-)" );
		
		try {
			CellStyle style = workbook.createCellStyle();
			
			style.setFillForegroundColor(IndexedColors.LIGHT_GREEN.getIndex());
			style.setFillPattern(CellStyle.SOLID_FOREGROUND);
			
			Cell cell;

			for (int i = 0; i < numColumns; i++) {
				cell = sheet.getRow(0).getCell(i);
				cell.setCellStyle(style);
			}
		} catch (Exception e) {
			log.error(" ERROR in SqlSelection.format(-" + objName + "-)", e);
		}
	}
}
