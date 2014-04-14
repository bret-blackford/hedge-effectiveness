package database;

import java.util.Vector;

public class MinMaxPriceDate_Data {

	public String excelTab;
	public String sqlStmt;
	
	public String priceIndex;
	public String delivDate;
	public String priceDate;
	public String price;
	public String delivDt;
	public String priceDt;
	public String delivDay;
	public String priceDay;
	public String minLessMaxPrice = "0";

	public String delim = "|";
	
	public String toString() {
		String out = priceIndex + delim + delivDate + delim + priceDate + delim;
		out += price + delim + delivDt + delim + priceDt + delim;
		out += delivDay + delim + priceDay + delim + minLessMaxPrice;
		
		return out;
	}
	
	
	public Vector<String> v_header(){
		
		Vector<String> columnHeader = new Vector<String>();
		columnHeader.add("A.priceIndex");
		columnHeader.add("B.DelivDate");
		columnHeader.add("C.priceDate");
		columnHeader.add("D.Price");
		columnHeader.add("E.DelivDt");
		columnHeader.add("F.PriceDt");
		columnHeader.add("G.DelivDay");
		columnHeader.add("H.PriceDay");
	
		columnHeader.add("G.MinLessMaxPrice");
		
		return columnHeader;
	}
}
