package database;

import java.util.Iterator;
import java.util.Properties;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * 
 * @author mblackford  M. Bret Blackford (mBret)
 *
 */
public class SqlStmts extends SqlSelection_Parent{
	
	static Logger log = Logger.getLogger(SqlStmts.class);
	
	public void printExcelWorksheet(Workbook _workbook, Vector _inVec) {

		workbook = _workbook;
		sheet = workbook.createSheet("6.SqlStmts");
		log.info("in SqlStmts.printExcelWorksheet() ...");

		getSql();
		addHeaders();
		
		Iterator itrRow = _inVec.iterator();
		
		while (itrRow.hasNext()) {
			
			Row row = sheet.createRow(rowCount);
			int cellCount = 0;
			
			SqlStmts_Data dataRow = (SqlStmts_Data)itrRow.next();
			
			Cell cellA = row.createCell(cellCount++);
			cellA.setCellValue(dataRow.excelTab);
			Cell cellB = row.createCell(cellCount++);
			cellB.setCellValue(dataRow.sqlStmt);

			log.info( "{{" + rowCount + "}:}: " + dataRow.toString() );
			rowCount++;
		}
		format();
	}
	
	private void addHeaders() {
		
		log.info("in SqlStmts.addHeaders()");
		
		Vector vec = new SqlStmts_Data().v_header();
		Row row = sheet.createRow(rowCount++);
		int cellCount_H = 0;
		numColumns = vec.size();
		
		Iterator column = vec.iterator();
		
		while(column.hasNext()) {
			String data = (String)column.next();
			Cell cell = row.createCell(cellCount_H);
			cell.setCellValue(data);
			
			cellCount_H++;
		}
	}
	
	
	public void getSql(Properties _props) {
		//TODO
	}

}
