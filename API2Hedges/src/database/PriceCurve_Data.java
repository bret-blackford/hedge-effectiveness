package database;

import java.util.Vector;

public class PriceCurve_Data {

	public String excelTab;
	public String sqlStmt;
	
	public String priceIndex;
	public String priceDate;
	public String price;
	public String delivDate;
	public String priceYYYYMM;
	public String delivYYYYMM;
	public String firstLessLast;


	public String delim = "|";
	
	public String toString() {
		String out = priceIndex + delim + priceDate + delim;
		out += price + delim + delivDate + delim + priceYYYYMM + delim;
		out += delivYYYYMM + delim + firstLessLast;

		return out;
	}
	
	
	public Vector<String> v_header(){
		
		Vector<String> columnHeader = new Vector<String>();
		
		columnHeader.add("A.priceIndex");
		columnHeader.add("B.priceDate");
		columnHeader.add("C.price");
		columnHeader.add("D.delivDate");
		columnHeader.add("E.priceYYYYMM");
		columnHeader.add("F.delivYYYYMM");
		columnHeader.add("G.firstLessLast");
				
		return columnHeader;
	}
}
