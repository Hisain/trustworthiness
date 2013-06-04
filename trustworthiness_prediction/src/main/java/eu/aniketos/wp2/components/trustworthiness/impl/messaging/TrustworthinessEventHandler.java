package eu.aniketos.wp2.components.trustworthiness.impl.messaging;

import org.apache.log4j.Logger;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

import eu.aniketos.wp2.components.trustworthiness.trust.management.atomic.ServiceTrustUpdatePolicy;

/**
 * handles events in relation to trustworthiness updates e.g. events generated
 * by trustworthiness monitoring module
 * 
 * @author Hisain Elshaafi
 * 
 */
public class TrustworthinessEventHandler implements EventHandler {

	private static Logger logger = Logger
			.getLogger(TrustworthinessEventHandler.class);

	private ServiceTrustUpdatePolicy trustUpdate;

	/**
	 * 
	 */
	public TrustworthinessEventHandler() {
		logger.debug("new TrustworthinessEventHandler");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.osgi.service.event.EventHandler#handleEvent(org.osgi.service.event
	 * .Event)
	 */
	public void handleEvent(Event event) {

		if (logger.isDebugEnabled()) {
			logger.debug("event " + event);
		}
		
		String topicName = event.getTopic();

		if (logger.isDebugEnabled()) {
			logger.debug("Received event for topic " + topicName);
			logger.debug("with properties:");
			String[] propertyNames = event.getPropertyNames();
			for (String propertyName : propertyNames) {
				String propertyValue = (String) event.getProperty(propertyName);
				logger.debug("- " + propertyName + ": " + propertyValue);
			}
		}

		// exclude prediction alerts from next operation
		if (!topicName.endsWith("alert")) {
			
			String serviceId = (String) event.getProperty("service.id");
			String scoreId = (String) event.getProperty("score.id");

			if (logger.isDebugEnabled()) {
				logger.debug("will now update trustworthiness for service "
						+ serviceId + " scoreId " + scoreId);
			}

			try {
				
				trustUpdate.updateTrust(serviceId);
				
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}
	}

	/**
	 * @return ServiceTrustUpdatePolicy to execute trustworthiness calculation
	 */
	public ServiceTrustUpdatePolicy getTrustUpdate() {
		return trustUpdate;
	}

	/**
	 * @param trustUpdate
	 *            to execute trustworthiness calculation
	 */
	public void setTrustUpdate(ServiceTrustUpdatePolicy trustUpdate) {
		this.trustUpdate = trustUpdate;
	}

}
