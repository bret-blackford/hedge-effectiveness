package misc;

import org.apache.log4j.Logger;

/**  
 * * Class used to obtain processing metrics.  Might be used to determine  
 * * how long a data base call takes, or how long a specific transaction  
 * * takes.  
 * *
 * * @author bb8273  mBret Bret Blackford
 * * @version $Id: ElapsedTime.java,v 1.2 2005/01/11 19:28:59 bb8273 Exp $  
 * 
 * */ 
public class ElapsedTime {
	
	static Logger log = Logger.getLogger(ElapsedTime.class);
	
	private long start = 0;
	private long end = 0;
	private long total = 0;

	public ElapsedTime() {
		start();
	}

	public long start() {
		try {
			//start = new Date().getTime();
			start = System.currentTimeMillis();
		} catch (Exception e) {
			log.error("ERROR in ElapsedTime.start()", e);
		}
		
		return start;
	}

	/**
	 * * Provides the elapsed time from when start() was called * Until this
	 * method is called. Time is in milliseconds. * @return a long value - in
	 * milliseconds
	 * */
	public long elapsedTime() {
		// end = new Date().getTime();
		end = System.currentTimeMillis();
		total = end - start;
		return total;
	}

	/**
	 * * Provides the elapsed time from when start() was called * Until this
	 * method is called. Time is in seconds. * @return a long value - in seconds
	 * */
	public long elapsedTime_s() {
		// end = new Date().getTime();
		end = System.currentTimeMillis();
		total = end - start;
		return (total / 1000);
	}
}

