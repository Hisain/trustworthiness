package eu.aniketos.wp2.components.trustworthiness.impl.messaging.service;

import org.apache.log4j.Logger;

import eu.aniketos.wp2.components.trustworthiness.messaging.service.StatusService;

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
		System.out.println("TrustworthinessEntity Prediction started.");
	}
	
	/* (non-Javadoc)
	 * @see eu.aniketos.wp2.components.trustworthiness.messaging.service.StatusService#initialize()
	 */
	public void initialize() {
		logger.info("prediction services initialized.");
	}
	
}
