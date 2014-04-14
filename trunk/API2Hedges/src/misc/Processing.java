package misc;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Properties;
import java.util.Vector;


import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;


import database.DbAccess;

import database.DistinctPriceYYYYMM;
import database.MinMaxPriceDate;
import database.PriceCurve;
import database.PriceCurve_Data;
import database.SqlStmts;
import database.SqlStmts_Data;
import database.TradeValuation;
import database.TradeValuation_Data;



/**
 * Used to manage the processing of the option model calculations.
 * 1) get data from Db, 2) calculate option valuations, 3) print output to file
 * 
 * @author mblackford mBret Bret Blackford
 *
 */
public class Processing {
	
	static Logger log = Logger.getLogger(Processing.class);
	
	DbAccess db;
	
	Vector dataVector1;
	Vector dataVector2 = new Vector();
	Vector dataVector3 = new Vector();
	Vector valuationVector3 = new Vector();
	Vector dataVector4 = new Vector();

	Vector sqlVec = new Vector();
	Properties props = null;
	
	String sql_table;
	String sql_prices;
	String sql_distinctPriceYYYYMM;
	String sql_tradeValuations;
	String sql_minMaxPriceDates;
	String sql_minMaxFx;
	String sql_priceDate;

	
	public Processing(Properties _props) {
		
		log.info("in Processing.Processing() \n");
		props = _props;
		
		sql_prices = props.getProperty("db.sql.price.table");
		sql_distinctPriceYYYYMM = props.getProperty("db.sql.pricedates.unique");
		sql_tradeValuations = props.getProperty("db.sql.API2hedges.tradeValuations");
		sql_minMaxPriceDates = props.getProperty("db.sql.MinMax.pricedate2");
		sql_minMaxFx = props.getProperty("db.sql.MinMaxFx");
		sql_priceDate = props.getProperty("db.sql.priceDate");
		
		log.info("sql_prices-[" + sql_prices + "] \n");
		log.info("db.sql.API2hedges.tradeValuations-[" + sql_tradeValuations + "] \n");
	}

	public void getData(){
		
		log.info("in Processing.getData()");
		log.info(" Getting ready for the hard stuff ...");
		ElapsedTime metrics = new ElapsedTime();
		
		db = new DbAccess(props);
		try {
			ElapsedTime time1 = new ElapsedTime();
			log.info(" (1) First let's get the prices \n");
			PriceCurve pricesObj = new PriceCurve();
			pricesObj.setSql(sql_prices);
			dataVector1 = db.makeDbCall(pricesObj);
			long time_1 = time1.elapsedTime();
			log.info("Processing.getData() PriceCurve transaction took [" + time_1 + "]ms " );
			log.info(" =+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=+=\n\n");

			
//			ElapsedTime time2 = new ElapsedTime();
//			log.info(" (2) Second let's get unique price dates");
//			DistinctPriceYYYYMM priceDateObj = new DistinctPriceYYYYMM();
//			priceDateObj.setSql(sql_distinctPriceYYYYMM);
//			dataVector2 = db.makeDbCall(priceDateObj);
//			long time_2 = time2.elapsedTime();
//			log.info("Processing.getData() DistinctPriceYYYYMM transaction took [" + time_2 + "]ms " );
			
			//getMonthlyPriceChange();
			
			ElapsedTime time3 = new ElapsedTime();
			log.info(" (3) Third let's get trade valuations \n");
			TradeValuation valuationsObj = new TradeValuation();
			valuationsObj.setSql(sql_tradeValuations);
			//dataVector3 = db.makeDbCall(valuationsObj);
			valuationVector3 = db.makeDbCall(valuationsObj);
			long time_3 = time3.elapsedTime();
			log.info("Processing.getData() TradeValuation transaction took [" + time_3 + "]ms " );
			log.info(" =-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=-=\n\n");
			
			getMonthlyPriceChange();
			log.info(" ========================================================================\n\n");

			ElapsedTime time4 = new ElapsedTime();
			log.info(" (4) Fourth let's get Min/Max price days (per month)");
			MinMaxPriceDate minMaxObj = new MinMaxPriceDate();
			minMaxObj.setSql(sql_minMaxPriceDates);
			dataVector4 = db.makeDbCall(minMaxObj);
			long time_4 = time4.elapsedTime();
			log.info("Processing.getData() minMaxObj transaction took [" + time_3 + "]ms " );
			
			Vector dataVec4;
			if (dataVector4.size() > 0) {
				log.info(" getting ready to obtain MinMaxDiff ...");
				dataVec4 = minMaxObj.getMinMaxDiff(dataVector4);
			}
			log.info(" ========================================================================\n\n");
//			
			getSql();
//			
			WriteToExcel write = new WriteToExcel(props);
			write.write(dataVector1, pricesObj);
//			write.write(dataVector2, priceDateObj);
//			write.write(dataVector3, valuationsObj);
			write.write(valuationVector3, valuationsObj);
			write.write(dataVector4, minMaxObj);
			
			write.write(sqlVec, new SqlStmts());
			
			write.writeFile();
			log.info(" ========================================================================\n\n");
			
			log.info("Processing.getData() PriceCurve transaction took [" + time_1 + "]ms or [" + time_1/1000 + "]seconds" );
//			log.info("Processing.getData() DistinctPriceYYYYMM took [" + time_2 + "]ms or [" + time_2/1000 + "]seconds" );
			log.info("Processing.getData() TradeValuation took [" + time_3 + "]ms or [" + time_3/1000 + "]seconds" );
			log.info("Processing.getData() MinMax took [" + time_4 + "]ms or [" + time_4/1000 + "]seconds" );
			
			log.info("Processing.getData() transaction took [" + metrics.elapsedTime() + "]ms or [" + metrics.elapsedTime_s() + "]seconds" );
			
		} catch (ClassNotFoundException e) {
			log.error(" *** ERROR 1 *** ");
			e.printStackTrace();
		} catch (SQLException e) {
			log.error(" *** ERROR 2 *** ");
			e.printStackTrace();
		} catch (Exception e) {
			log.error(" *** ERROR 3 *** ");
			e.printStackTrace();
		}
	}
	
	public void getSql() {
		SqlStmts_Data sql1 = new SqlStmts_Data();
		sql1.excelTab = "1.PriceCurve";
		sql1.sqlStmt = sql_prices;
		sqlVec.add(sql1);
		
		SqlStmts_Data sql2 = new SqlStmts_Data();
		sql2.excelTab = "3.TradeValuations";
		sql2.sqlStmt =sql_tradeValuations;
		sqlVec.add(sql2);
		
		SqlStmts_Data sql2a = new SqlStmts_Data();
		sql2a.excelTab = "3.TradeValuations";
		sql2a.sqlStmt =sql_minMaxFx;
		sqlVec.add(sql2a);
		
		SqlStmts_Data sql3 = new SqlStmts_Data();
		sql3.excelTab = "4.MinMaxPriceDate";
		sql3.sqlStmt =sql_minMaxPriceDates;
		sqlVec.add(sql3);
		
	}
	
	private void getMonthlyPriceChange() {
		log.info("in Processing.getMonthlyPriceChange()");
		
		Integer count = 0;
//		Integer size = dataVector3.size();
		Integer size = valuationVector3.size();
		log.info(" preparing to loop thru vector with [" + size + "] elements");
		
		Float monthChange = new Float(0.0);
		Float desAraChange = new Float(0.0);
		Float diffTimesQuant = new Float (0.0);
		
		TradeValuation_Data data;
		String index = "";
		
		while( count < size ) {
			
			//data = (TradeValuation_Data)dataVector3.elementAt(count);
			data = (TradeValuation_Data)valuationVector3.elementAt(count);
			
			if( data.priceIndex.equalsIgnoreCase("FIXED PRICE") ) {
				log.info("\n data.priceIndex-[" + data.priceIndex + "]");
				log.info(" data-[" + data.toString() + "]");
			}
			else {
				if(data.priceIndex.equals("Argus-McClosky-API-2_Monthly-CME")) {
					index = "CME-API-2";
				}
				else if (data.priceIndex.equals("Argus-McClosky-API-2_Monthly")) {
					index = "ICE-API-2";
				}
				else {
					log.info(" . . . . . . . index-[" + data.priceIndex + "]");
					index = "UNKNOWN";
				}
				
				//data.SYSDATE = "2014-03-31"; //change !!!
				//NOTE: specific trade date can be passed in from PROPERT file
				if( !sql_priceDate.equalsIgnoreCase("SYSDATE") ){
					data.SYSDATE = sql_priceDate;
				}
				
				monthChange = db.makeDbCall(index, data.tradeDate, data.begTime, data.SYSDATE);
				desAraChange = db.makeDbCall("GC-DES-ARA", data.tradeDate, data.begTime, data.SYSDATE);
				
				data.moPriceChange = monthChange.toString();
				data.moPriceChangeDesAra = desAraChange.toString();
				data.diff = new Float(monthChange - desAraChange).toString();
				diffTimesQuant = new Float(data.priceQuantity) * new Float(data.diff);
				data.DIFFxQUANT = diffTimesQuant.toString();
	
//				dataVector3.set(count, data);
				valuationVector3.set(count, data);
			}
			count++;
		}
	}
	
}
