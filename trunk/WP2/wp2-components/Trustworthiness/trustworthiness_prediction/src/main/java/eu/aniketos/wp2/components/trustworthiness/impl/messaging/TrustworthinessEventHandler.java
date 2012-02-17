package eu.aniketos.wp2.components.trustworthiness.impl.messaging;

import org.apache.log4j.Logger;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

import eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo.Score;
import eu.aniketos.wp2.components.trustworthiness.trust.management.atomic.ServiceTrustUpdatePolicy;

public class TrustworthinessEventHandler implements EventHandler {

	private static Logger logger = Logger
			.getLogger(TrustworthinessEventHandler.class);

	private ServiceTrustUpdatePolicy trustUpdate;

	private boolean called = false;

	public TrustworthinessEventHandler() {
		logger.debug("new TrustworthinessEventHandler");
	}

	public void handleEvent(Event event) {
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
			logger.debug("will now update trustworthiness for service "
					+ serviceId + " scoreId " + scoreId);

			try {
				trustUpdate.calculateTrust(serviceId);
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}
	}

	public boolean isCalled() {
		return called;
	}

	public void setCalled(boolean called) {
		this.called = called;
	}

	public ServiceTrustUpdatePolicy getTrustUpdate() {
		return trustUpdate;
	}

	public void setTrustUpdate(ServiceTrustUpdatePolicy trustUpdate) {
		this.trustUpdate = trustUpdate;
	}

}
