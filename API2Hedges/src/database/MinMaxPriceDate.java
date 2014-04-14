package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;

public class MinMaxPriceDate extends SqlSelection_Parent {

	static Logger log = Logger.getLogger(MinMaxPriceDate.class);
	

	public String getSql() {
		return sql;
	}
	
	public void setSql(String _sql) {
		sql = _sql;
	}

	
	public Vector processResultSet(ResultSet rs) {

		objName = "MinMaxPriceDate";
		log.info("in MinMaxPriceDate.processResultSet()");
		dataVector = new Vector();
		int count = 0;

		MinMaxPriceDate_Data data;

		try {
			while (rs.next()) {
				data = new MinMaxPriceDate_Data();
				
				data.priceIndex		= rs.getString("PRICEINDEX");
				data.delivDate		= rs.getString("DELIVDATE");
				data.priceDate		= rs.getString("PRICEDATE");
				data.price			= rs.getString("PRICE");
				data.delivDt		= rs.getString("DELIVDT");
				data.priceDt		= rs.getString("PRICEDT");
				data.delivDay		= rs.getString("DELIVDAY");
				data.priceDay		= rs.getString("PRICEDAY");
				
				log.info("[" + count++ + "]" + data.toString());
				dataVector.add(data);
			}

		} catch (SQLException e) {
			log.error(" ERROR in MinMaxPriceDate.processResultSet()", e);
		}
		log.info(" leaving MinMaxPriceDate.processResultSet()  ");
		return dataVector;
	}
	
	public Vector getMinMaxDiff(Vector _inVec) {
		
		if( _inVec.isEmpty() ) {
			log.info(" in MinMaxPriceData.getMinMaxDiff() and _inVec is emppty ****");
		}
		Vector newVec = new Vector();
		
		MinMaxPriceDate_Data dataRow1;
		MinMaxPriceDate_Data dataRow2;
		String priceDiff = "0";
		Float priceDiffNum = new Float(0.0);
		
		int countt = 0;
		int size = _inVec.size();
		Iterator itrRow = _inVec.iterator();
		while (itrRow.hasNext()) {
					
			dataRow1 = (MinMaxPriceDate_Data)itrRow.next();
			dataRow2 = (MinMaxPriceDate_Data)itrRow.next();
			
			priceDiffNum = Float.valueOf(dataRow1.price) - Float.valueOf(dataRow2.price);
			log.info("priceDiffNum-[(" + countt + "|" + size + ")" + priceDiffNum + "]");
			
			if( priceDiffNum != null ) {
				priceDiff = priceDiffNum.toString();
			}
			
			newVec.add(priceDiff);
			newVec.add(priceDiff); //WHY 2 SAME ??
			
			countt++;
		}
		log.info(" in MinMaxPriceData.getMinMaxDiff() preparing to check differences ");
		
		int count = 0;
		Iterator itrRow2 = _inVec.iterator();
		while (itrRow2.hasNext()) {
			dataRow1 = (MinMaxPriceDate_Data) itrRow2.next();
			dataRow1.minLessMaxPrice = (String) newVec.get(count);
			
			log.info("[" + count++ + "]" + dataRow1.toString());
		}

		
		return _inVec;
	}
	
	
	public void printExcelWorksheet(Workbook _workbook, Vector _inVec) {

		workbook = _workbook;
		sheet = workbook.createSheet("4.MinMaxPriceDate");
		
		log.info("in MinMaxPriceDate.printExcelWorksheet() ...");
		log.info(" received vector sized [" + _inVec.size() + "]");
				
		addHeaders();
		
		Iterator itrRow = _inVec.iterator();
		
		while (itrRow.hasNext()) {
			
			Row row = sheet.createRow(rowCount);
			int cellCount = 0;
			
			MinMaxPriceDate_Data dataRow = (MinMaxPriceDate_Data)itrRow.next();
			
			Cell cellA = row.createCell(cellCount++);
			cellA.setCellValue(dataRow.priceIndex);
			Cell cellB = row.createCell(cellCount++);
			cellB.setCellValue(dataRow.delivDate);
			Cell cellC = row.createCell(cellCount++);
			cellC.setCellValue(dataRow.priceDate);
			Cell cellD = row.createCell(cellCount++);
			cellD.setCellValue(new Double(dataRow.price));
			Cell cellE = row.createCell(cellCount++);
			cellE.setCellValue(dataRow.delivDt);
			Cell cellF = row.createCell(cellCount++);
			cellF.setCellValue(dataRow.priceDt);
			Cell cellG = row.createCell(cellCount++);
			cellG.setCellValue(dataRow.delivDay);
			Cell cellH = row.createCell(cellCount++);
			cellH.setCellValue(dataRow.priceDay);
			
			Cell cellI = row.createCell(cellCount++);
			cellI.setCellValue(new Double(dataRow.minLessMaxPrice));

			log.info("[" + rowCount + "]" + dataRow.toString());
			rowCount++;
		}
		format();
	}
	
	private void addHeaders() {
		
		log.info("in MinMaxPriceDate.addHeaders()");
		
		Vector vec = new MinMaxPriceDate_Data().v_header();
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
