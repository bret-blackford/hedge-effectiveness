package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Vector;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;

public class TradeValuation extends SqlSelection_Parent{

	static Logger log = Logger.getLogger(TradeValuation.class);
	

	public String getSql() {
		return sql;
	}
	
	public void setSql(String _sql) {
		sql = _sql;
	}

	
	public Vector processResultSet(ResultSet rs) {

		objName = "TradeValuation";
		log.info("in TradeValuation.processResultSet()");
		dataVector = new Vector();
		int count = 0;

		TradeValuation_Data data;

		try {
			while (rs.next()) {
				data = new TradeValuation_Data();
				
				data.tradePurpose	= rs.getString("Purpose");
				data.execution		= rs.getString("EXECUTION");
				data.positionType	= rs.getString("PoS");
				data.description	= rs.getString("DESCRIPTION");
				data.createdOn		= rs.getString("CreatedOn");
				data.trade			= rs.getString("TRADE");
				data.price			= rs.getString("PRICE");
				data.priceIndex		= rs.getString("PRICEINDEX");	
				data.acctgMethod	= rs.getString("ACCOUNTINGMETHOD");
				data.begDate		= rs.getString("BegDate");
				data.quantStatus	= rs.getString("QUANTITYSTATUS");
				data.timePeriod		= rs.getString("TIMEPERIOD");
				data.begTime		= rs.getString("BEGTIME");
				data.endTime		= rs.getString("ENDTIME");
				data.counterparty	= rs.getString("COUNTERPARTY");
				data.product		= rs.getString("PRODUCT");
				data.tradebook		= rs.getString("TRADEBOOK");
				data.quantity		= rs.getString("QUANTITY");
				data.marketPrice	= rs.getString("MARKETPRICE");
				data.value			= rs.getString("VALUE");
				data.marketValue	= rs.getString("MARKETVALUE");
				data.location		= rs.getString("LOCATION");
				data.priceStatus	= rs.getString("STATUS");
				data.valuation		= rs.getString("VALUATION");
				data.company		= rs.getString("Co.");
				data.priceQuantity	= rs.getString("PRICEQUANTITY");
				data.SYSDATE		= rs.getString("SYSDATE");
				data.tradeDate		= rs.getString("TRADEDATE");
				
				
				log.info("[" + count++ + "]" + data.toString());
				dataVector.add(data);
			}

		} catch (SQLException e) {
			log.error(" ERROR in TradeValuation.processResultSet()", e);
		}
		log.info(" leaving TradeValuation.processResultSet()  ");
		return dataVector;
	}
	
	public void printExcelWorksheet(Workbook _workbook, Vector _inVec) {

		workbook = _workbook;
		sheet = workbook.createSheet("3.TradeValuation");
		
		log.info("in TradeValuation.printExcelWorksheet() ...");
		log.info(" received vector sized [" + _inVec.size() + "]");
				
		addHeaders();
		
		Iterator itrRow = _inVec.iterator();
		
		while (itrRow.hasNext()) {
			
			Row row = sheet.createRow(rowCount);
			int cellCount = 0;
			
			TradeValuation_Data dataRow = (TradeValuation_Data)itrRow.next();
			
			Cell cellA = row.createCell(cellCount++);
			cellA.setCellValue(dataRow.tradePurpose);
			Cell cellB = row.createCell(cellCount++);
			cellB.setCellValue(dataRow.execution);
			Cell cellC = row.createCell(cellCount++);
			cellC.setCellValue(dataRow.positionType);
			Cell cellD = row.createCell(cellCount++);
			cellD.setCellValue(dataRow.description);
			Cell cellE = row.createCell(cellCount++);
			cellE.setCellValue(dataRow.createdOn);
			Cell cellF = row.createCell(cellCount++);
			cellF.setCellValue(dataRow.trade);
			
			Cell cellG = row.createCell(cellCount++);
			cellG.setCellValue(new Double(dataRow.price));

			Cell cellH = row.createCell(cellCount++);
			cellH.setCellValue(dataRow.priceIndex);
			Cell cellI = row.createCell(cellCount++);
			cellI.setCellValue(dataRow.acctgMethod);
			Cell cellJ = row.createCell(cellCount++);
			cellJ.setCellValue(dataRow.begDate);
			Cell cellK = row.createCell(cellCount++);
			cellK.setCellValue(dataRow.quantStatus);
			Cell cellL = row.createCell(cellCount++);
			cellL.setCellValue(dataRow.timePeriod);
			Cell cellM = row.createCell(cellCount++);
			cellM.setCellValue(dataRow.begTime);
			Cell cellN = row.createCell(cellCount++);
			cellN.setCellValue(dataRow.endTime);
			Cell cellO = row.createCell(cellCount++);
			cellO.setCellValue(dataRow.counterparty);
			Cell cellP = row.createCell(cellCount++);
			cellP.setCellValue(dataRow.product);
			Cell cellQ = row.createCell(cellCount++);
			cellQ.setCellValue(dataRow.tradebook);
			Cell cellR = row.createCell(cellCount++);
			cellR.setCellValue(dataRow.marketPrice);
			Cell cellS = row.createCell(cellCount++);
			cellS.setCellValue(dataRow.value);
			Cell cellT = row.createCell(cellCount++);
			cellT.setCellValue(dataRow.marketValue);
			Cell cellU = row.createCell(cellCount++);
			cellU.setCellValue(dataRow.location);
			Cell cellV = row.createCell(cellCount++);
			cellV.setCellValue(dataRow.priceStatus);
			Cell cellW = row.createCell(cellCount++);
			cellW.setCellValue(dataRow.valuation);
			Cell cellX = row.createCell(cellCount++);
			cellX.setCellValue(dataRow.company);
			Cell cellY = row.createCell(cellCount++);
			cellY.setCellValue(new Double(dataRow.priceQuantity));
			//checkCell(cellY);
			
			Cell cellZ = row.createCell(cellCount++);
			log.error(" data.moPriceChange-[" + dataRow.moPriceChange + "]");
			cellZ.setCellValue(new Double(dataRow.moPriceChange));
			Cell cellAA = row.createCell(cellCount++);
			cellAA.setCellValue(new Double(dataRow.moPriceChangeDesAra));
			Cell cellAB = row.createCell(cellCount++);
			cellAB.setCellValue(new Double(dataRow.diff));

			Cell cellAC = row.createCell(cellCount++);
			cellAC.setCellValue(dataRow.SYSDATE);

			Cell cellAD = row.createCell(cellCount++);
			cellAD.setCellValue(new Double(dataRow.DIFFxQUANT));
			
			Cell cellAE = row.createCell(cellCount++);
			cellAE.setCellValue(dataRow.tradeDate);

			log.info("[" + rowCount + "]" + dataRow.toString());
			rowCount++;
		}
		format();
	}
	
	private void addHeaders() {
		
		log.info("in TradeValuation.addHeaders()");
		
		Vector vec = new TradeValuation_Data().v_header();
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
