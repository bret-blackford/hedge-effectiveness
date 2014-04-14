package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;

public class DistinctPriceYYYYMM extends SqlSelection_Parent {

	static Logger log = Logger.getLogger(DistinctPriceYYYYMM.class);
	

	public String getSql() {
		return sql;
	}
	
	public void setSql(String _sql) {
		sql = _sql;
	}

	
	public Vector processResultSet(ResultSet rs) {

		objName = "PriceCurve";
		log.info("in DistinctPriceYYYYMM.processResultSet()");
		dataVector = new Vector();
		int count = 0;

		DistinctPriceYYYYMM_Data data;

		try {
			while (rs.next()) {
				data = new DistinctPriceYYYYMM_Data();
				
				data.priceYYYYMM = rs.getString("PRICEYYYYMM");
				
				log.info("[" + count++ + "]" + data.toString());
				dataVector.add(data);
			}

		} catch (SQLException e) {
			log.error(" ERROR in DistinctPriceYYYYMM.processResultSet()", e);
		}
		log.info(" leaving DistinctPriceYYYYMM.processResultSet()  ");
		return dataVector;
	}
	
	public void printExcelWorksheet(Workbook _workbook, Vector _inVec) {

		workbook = _workbook;
		sheet = workbook.createSheet("2.DistinctPriceYYYYMM");
		
		log.info("in DistinctPriceYYYYMM.printExcelWorksheet() ...");
		log.info(" received vector sized [" + _inVec.size() + "]");
				
		addHeaders();
		
		Iterator itrRow = _inVec.iterator();
		
		while (itrRow.hasNext()) {
			
			Row row = sheet.createRow(rowCount);
			int cellCount = 0;
			
			DistinctPriceYYYYMM_Data dataRow = (DistinctPriceYYYYMM_Data)itrRow.next();
			
			Cell cellA = row.createCell(cellCount++);
			cellA.setCellValue(dataRow.priceYYYYMM);

			log.info("[" + rowCount + "]" + dataRow.toString());
			rowCount++;
		}
		format();
	}
	
	private void addHeaders() {
		
		log.info("in DistinctPriceYYYYMM.addHeaders()");
		
		Vector vec = new DistinctPriceYYYYMM_Data().v_header();
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
