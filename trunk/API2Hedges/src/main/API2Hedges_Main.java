package main;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;


import misc.ElapsedTime;
import misc.Processing;

import org.apache.log4j.Logger;

public class API2Hedges_Main {

	private static Logger log = Logger.getLogger(API2Hedges_Main.class);
	public static Properties props = null;
	

	public static void main(String[] args) {
		System.out.println("I am here ...");
		log.info("====START====================");
		log.info("====in API2Hedges_Main.main()==\n");
		ElapsedTime metrics = new ElapsedTime();
		
		props = new Properties();
		if( args != null && args.length > 0 ) {
			try {
				props.load( new FileInputStream(args[0]) );
			} catch(FileNotFoundException e) {
				log.error("DUDE! No such file!");
				log.error(e);
			} catch(IOException e) {
				log.error("EXCEPTION while getting result set", e); 
				e.printStackTrace(); 
			}
		} else {
			log.info("No parameters used. No PROPERTY file passed in.");
		}
		
		Processing process = new Processing(props);
		process.getData();
		
		log.info("====DONE====================");
		log.info("====DONE====================\n");
		log.info("API2Hedges_Main.main() took [" + metrics.elapsedTime() + "]ms or [" +  metrics.elapsedTime_s() + "]seconds");
		log.info("");
		log.info("====DONE==================== FINAL \n\n\n\n");
	}

}
