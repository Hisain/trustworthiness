package eu.aniketos.trustworthiness.impl.messaging.service;

import org.apache.log4j.Logger;

import eu.aniketos.trustworthiness.messaging.service.StatusService;

/**
 * @author Hisain Elshaafi (TSSG)
 *
 */
public class StatusServiceImpl implements StatusService {

	private static Logger logger = Logger.getLogger(StatusServiceImpl.class);
	
	/**
	 * displays a simple status message
	 */
	public StatusServiceImpl(){
		System.out.println("Trustworthiness Monitoring started.");
	}
	
	/* (non-Javadoc)
	 * @see eu.aniketos.trustworthiness.messaging.service.StatusService#initialize()
	 */
	public void initialize() {
		logger.info("Monitoring services initialized");
	}
	
}
