package eu.aniketos.wp2.components.trustworthiness.impl.trust.management;

import java.util.Map;
import java.util.Observable;

import org.apache.log4j.Logger;
import org.osgi.service.event.Event;

import eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo.Score;
import eu.aniketos.wp2.components.trustworthiness.messaging.TrustworthinessPredictionJms;
import eu.aniketos.wp2.components.trustworthiness.trust.management.TrustMonitor;
import eu.aniketos.wp2.components.trustworthiness.trust.management.atomic.ServiceTrustUpdate;
import eu.aniketos.wp2.components.trustworthiness.trust.management.atomic.ServiceTrustUpdatePolicy;
import eu.aniketos.wp2.components.trustworthiness.trust.management.atomic.Trustworthiness;

/**
 * Session Bean implementation class TrustMonitorImpl
 */
public class TrustMonitorService implements TrustMonitor {

	private static Logger logger = Logger.getLogger(TrustMonitor.class);

	private ServiceTrustUpdate trustUpdate;

	private ServiceTrustUpdatePolicy trustUpdatePolicy;
	
	private TrustworthinessPredictionJms twPrediction;

	public void update(Observable o, Object arg) {
			
			
	}

	public ServiceTrustUpdate getTrustUpdate() {
		return trustUpdate;
	}

	public void setTrustUpdate(ServiceTrustUpdate trustUpdate) {
		this.trustUpdate = trustUpdate;
	}

	public ServiceTrustUpdatePolicy getTrustUpdatePolicy() {
		return trustUpdatePolicy;
	}

	public void setTrustUpdatePolicy(
			ServiceTrustUpdatePolicy trustUpdatePolicy) {
		this.trustUpdatePolicy = trustUpdatePolicy;
	}

	public TrustworthinessPredictionJms getTwPrediction() {
		return twPrediction;
	}

	public void setTwPrediction(TrustworthinessPredictionJms twPrediction) {
		this.twPrediction = twPrediction;
	}

	/**
	 * TODO
	 */
	public void handleEvent(Event event) {
		String topicName = event.getTopic();
		logger.info("Received event for topic " + topicName);
		logger.info("with properties:");
		String[] propertyNames = event.getPropertyNames();
		for (String propertyName : propertyNames) {
			String propertyValue = (String) event.getProperty(propertyName);
			logger.info("- " + propertyName + ": " + propertyValue);
			
			
		}
		
		if (topicName.startsWith("trust-event")) {
			Map<String,Score> map = (Map)event;
			logger.debug(event.getPropertyNames());
		try {
			Score score = (Score)map.get("score");
			Trustworthiness trust = trustUpdate.calculateScore(score,
					trustUpdatePolicy);
			if (logger.isDebugEnabled()){
				logger.debug("TrustMonitor: trust " + trust);
				logger.debug("Sending trust to subscribers..");
			}
			twPrediction.sendTrustworthiness(trust);
			logger.debug("trust update notified");
			
		} catch (Exception e) {
			logger.error(e.getMessage());
			e.printStackTrace();
		}
		}
	}

	
}
