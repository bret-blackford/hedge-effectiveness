package database;

import java.util.Vector;

import org.apache.log4j.Logger;

/**
 * 
 * @author mblackford  M. Bret Blackford (mBret)
 *
 */
public class SqlStmts_Data {
	
	static Logger log = Logger.getLogger(SqlStmts_Data.class);
	
	public String excelTab;
	public String sqlStmt;
	
	public String delim = "|";
	
	
	public String toString() {
		String out = excelTab + delim + sqlStmt;

		return out;
	}
	
	
	public Vector<String> v_header(){
		
		Vector<String> columnHeader = new Vector<String>();
		
		columnHeader.add("A.ExcelTab");
		columnHeader.add("B.SqlStatement");
				
		return columnHeader;
	}
}
