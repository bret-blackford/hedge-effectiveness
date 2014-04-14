package database;

import java.sql.ResultSet;
import org.apache.poi.ss.usermodel.CellStyle;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Vector;

import misc.BooleanTest;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 * 
 * @author mblackford M. Bret Blackford (mBret)
 *
 */
public class PriceCurve extends SqlSelection_Parent {

	static Logger log = Logger.getLogger(PriceCurve.class);
	

	public String getSql() {
		return sql;
	}
	
	public void setSql(String _sql) {
		sql = _sql;
	}

	
	public Vector processResultSet(ResultSet rs) {

		objName = "PriceCurve";
		log.info("in PriceCurve.processResultSet()");
		dataVector = new Vector();
		int count = 0;

		PriceCurve_Data data;

		try {
			while (rs.next()) {
				data = new PriceCurve_Data();
				
				data.priceIndex		= rs.getString("PRICEINDEX");
				data.priceDate		= rs.getString("PRICEDATE");
				data.price			= rs.getString("PRICE");
				data.delivDate		= rs.getString("DELIVDATE");
				data.priceYYYYMM	= rs.getString("PRICE_YYYYMM");
				data.delivYYYYMM	= rs.getString("DELIV_YYYYMM");
				
				log.info("[" + count++ + "]" + data.toString());
				dataVector.add(data);
			}

		} catch (SQLException e) {
			log.error(" ERROR in PriceCurve.processResultSet()", e);
		}
		log.info(" leaving PriceCurve.processResultSet()  ");
		return dataVector;
	}
	
	 private void getMinMax(Vector _inVec) {
		
		 Iterator itr = _inVec.iterator();
		 while( itr.hasNext() ) {
			 
		 }
	}
	
	public void printExcelWorksheet(Workbook _workbook, Vector _inVec) {

		workbook = _workbook;
		sheet = workbook.createSheet("1.PriceCurve");
		
		log.info("in PriceCurve.printExcelWorksheet() ...");
		log.info(" received vector sized [" + _inVec.size() + "]");
				
		addHeaders();
		
		Iterator itrRow = _inVec.iterator();
		
		while (itrRow.hasNext()) {
			
			Row row = sheet.createRow(rowCount);
			int cellCount = 0;
			
			PriceCurve_Data dataRow = (PriceCurve_Data)itrRow.next();
			
			Cell cellA = row.createCell(cellCount++);
			cellA.setCellValue(dataRow.priceIndex);
			Cell cellB = row.createCell(cellCount++);
			cellB.setCellValue(dataRow.priceDate);
			Cell cellC = row.createCell(cellCount++);
			cellC.setCellValue(new Double(dataRow.price));
			//checkCell(cellC);
			Cell cellD = row.createCell(cellCount++);
			cellD.setCellValue(dataRow.delivDate);
			//checkCell(cellD);
			Cell cellE = row.createCell(cellCount++);
			cellE.setCellValue(dataRow.priceYYYYMM);
			//checkCell(cellE);
			Cell cellF = row.createCell(cellCount++);
			cellF.setCellValue(dataRow.delivYYYYMM);
			//checkCell(cellF);

			log.info("[" + rowCount + "]" + dataRow.toString());
			rowCount++;
		}
		format();
	}
	
	private void addHeaders() {
		
		log.info("in PriceCurve.addHeaders()");
		
		Vector vec = new PriceCurve_Data().v_header();
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
	

}
