package database;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Iterator;
import java.util.Properties;
import java.util.Vector;

import misc.CheckString;
import misc.ElapsedTime;
import misc.Processing;

import org.apache.log4j.Logger;

/**
 * Class written to provide jdbc Db access to an
 * Allegro 7.6 table in Db PRODACI1
 * The class is not generic but written specifically for
 * use on the Arch Coal Allegro Db implementation. To make
 * more generic you would need to pass in the Db url string.
 * 
 * jdbc requires that the ojdbc6.jar be added to the Project Libraries - Build Path
 * 
 * @author mblackford mBret Bret Blackford
 *
 */
public class DbAccess {
	
	static Logger log = Logger.getLogger(DbAccess.class);
	
	Connection conn;
	Vector resultVector = new Vector();
	
    String serverName = "acidb1.aci.corp.net";
    String portNumber = "1522";
    String sid = "prodaci1";
    String url = "jdbc:oracle:thin:@" + serverName + ":" + portNumber + ":" + sid;
    String username = "blackfordm";
    String password = "--";
    String selectSQL = "";
    String dbDriver = "";
    String dynamicSql = "";
    String sql_minMaxFx = "";
    
    public DbAccess(Properties _props) {
    	serverName = _props.getProperty("db.server");
    	portNumber = _props.getProperty("db.port");
    	sid = _props.getProperty("db.sid");
    	url = _props.getProperty("db.dataSource");
    	username = _props.getProperty("db.userName");
    	password = _props.getProperty("db.passWord");
    	dbDriver = _props.getProperty("db.driver");
    	
    	dynamicSql = _props.getProperty("db.sql.dynamic.MinMax");
    	sql_minMaxFx = _props.getProperty("db.sql.MinMaxFx");
    }
    
    
	private void makeConnection() throws ClassNotFoundException, SQLException {

		log.info("\t  in DbAccess.makeConnection()");
		log.info("\t   Going to try the connection now ...");
		log.info("\t         url-[" + url + "]");
		log.info("\t    username-[" + username + "]");
		//log.info("\t    password-[" + password + "]");
		log.info("\t    dbDriver-[" + dbDriver + "]");
		
		//Class.forName("oracle.jdbc.driver.OracleDriver");
		Class.forName(dbDriver);
		conn = DriverManager.getConnection(url, username, password);
		
		log.info("Connection done !! \n");
	}
    
	public Float makeDbCall(String _index, String _tradeDate, String _delivDate, String _priceDate) {
		
		log.info(" -");
		log.info(" ---------------------------------------------------------------------------------------------");
		log.info("in DbAccess.makeDbCall(" + _index + "," + _tradeDate + "," + _delivDate + "," + _priceDate + ") \n");
		log.info("_index[" + _index + "] _tradeDate[" + _tradeDate + "] _deliveDate[" + _delivDate + "} _priceDate[" + _priceDate + "]");
		ElapsedTime metrics = new ElapsedTime();
		Float price = new Float(0.0);
		CallableStatement callableStatement = null;

		//String getFunctionData = "{? = call cpms.allegro_price_pkg.get_price_diff(?,?,?,?)}";
		String getFunctionData = sql_minMaxFx;

		try {
			log.info(" attempting to make a connection to Db function");
			log.info(" calling -- [" + getFunctionData + "]");
			
			makeConnection();
			log.info(" connection made");
			
			callableStatement = conn.prepareCall(getFunctionData);
			
			callableStatement.setString(2, _index);
			callableStatement.setDate(3,  java.sql.Date.valueOf(_tradeDate) ); // in "YYYY-MM-DD" format
			callableStatement.setDate(4,  java.sql.Date.valueOf(_delivDate) );
			callableStatement.setDate(5,  java.sql.Date.valueOf(_priceDate) );
			callableStatement.registerOutParameter(1, java.sql.Types.FLOAT);	
			
			callableStatement.executeUpdate();
			
			price = callableStatement.getFloat(1);		
			log.info("in DbAccess.makeDbCall(String, String, String, String) and received 1:[" + price + "] from fx");
			
		} catch (ClassNotFoundException | SQLException e) {

			log.error(" ***** ");
			log.error(" ERROR in DbAccess.makeDbCall(String, String, String, String) ");
			log.error("\n\n" + e.toString() );
			log.error("\n");
			
			e.printStackTrace();
		}
		log.info(" DbAccess.makeDbCall(String, String, String, String) transaction took [" + metrics.elapsedTime() + "]ms or [" + metrics.elapsedTime_s() + "]seconds \n" );
		return price;
	}
	
    public Vector makeDbCall(SqlSelection_Parent sqlObject) throws SQLException, ClassNotFoundException {
        
		log.info("in DbAccess.makeDbCall() \n");
		ElapsedTime metrics = new ElapsedTime();

		makeConnection();

		String sql = CheckString.check( sqlObject.getSql() );
		sql = sqlObject.getSql();  //overwrite sql
		log.info("in DbAcess.makeDbCall(" + sql + ") \n");

		// creating PreparedStatement object to execute query
		PreparedStatement preStatement = conn.prepareStatement(sql);

		ResultSet result = preStatement.executeQuery();

		Vector dataVector = sqlObject.processResultSet(result);
		
		log.info(" leaving DbAccess.makeDbCall(" + sqlObject.objName + ") \n\n" );
		log.info(" DbAccess.makeDbCall() transaction took [" + metrics.elapsedTime() + "]ms or [" + metrics.elapsedTime_s() + "]seconds \n" );
		
		return dataVector;
    }
    
    public ResultSet makeSqlCall(String sql) throws ClassNotFoundException, SQLException {
    	
    	log.info("in DbAccess.makeSqlCall()");
    	log.debug(" SQL=[" + sql + "] \n");
    	
    	//makeConnection();
    	PreparedStatement preStatement = conn.prepareStatement(sql);
    	ResultSet  result = preStatement.executeQuery();
    	return result;
    }
    

}
