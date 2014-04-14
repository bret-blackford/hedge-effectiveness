package database;

import java.util.Vector;

public class DistinctPriceYYYYMM_Data {

	public String excelTab;
	public String sqlStmt;
	
	public String priceYYYYMM;

	public String delim = "|";
	
	public String toString() {
		String out = priceYYYYMM;
		return out;
	}
	
	
	public Vector<String> v_header(){
		
		Vector<String> columnHeader = new Vector<String>();
		columnHeader.add("A.priceYYYYMM");
		return columnHeader;
	}
}
