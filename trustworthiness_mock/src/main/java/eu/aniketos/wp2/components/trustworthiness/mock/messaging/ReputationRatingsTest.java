package eu.aniketos.wp2.components.trustworthiness.mock.messaging;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

import org.apache.log4j.Logger;

import eu.aniketos.wp2.components.trustworthiness.messaging.ReputationMetricsService;
import eu.aniketos.wp2.components.trustworthiness.rules.model.event.TrustEvent;

/**
 * @author Hisain Elshaafi (TSSG)
 * 
 */
public class ReputationRatingsTest {

	private static Logger logger = Logger
			.getLogger(ReputationRatingsTest.class);

	private ReputationMetricsService repMetrics;

	/**
	 * @param qosMetics
	 */
	public ReputationRatingsTest(ReputationMetricsService repMetrics) {
		super();
		this.repMetrics = repMetrics;
	}

	/**
	 * 
	 */
	public void initialize() {

		Random r = new Random();

		//
		logger.info("normal ratings");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {

			logger.error(e.getMessage());
		}
		for (int i = 0; i <10; i++) {
			
			TrustEvent event = new MetricEventImpl();
			
			event.setServiceId("testId10");
			
			event.setProperty("reputation");
			
			event.setValue(Double.toString(0.9 + r.nextDouble() / 10));
			
			//TODO
			String now = (new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ")).format(new Date()); 
			event.setTimestamp(now);
			
			if (logger.isDebugEnabled()) {
				logger.debug(" timestamp=" + now);
			}
			
			try {
				repMetrics.processReputationRating(event);
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}

		// rating with null or empty service id
		logger.info("rating with null or empty service id");

		for (int i = 0; i < 1; i++) {
			
			TrustEvent event = new MetricEventImpl();
			
			event.setServiceId(null);
			
			event.setProperty("reputation");
			
			event.setValue(Double.toString(0.9 + r.nextDouble() / 10));
			
			try {
				repMetrics.processReputationRating(event);
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {

			logger.error(e.getMessage());
		}
		
		logger.info("sending metric as null");
		
		for (int i = 0; i < 2; i++) {
		
			TrustEvent event = null;
			
			try {
				repMetrics.processReputationRating(event);
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {

			logger.error(e.getMessage());
		}
		
		logger.info("sending metric as empty");
		
		for (int i = 0; i < 2; i++) {
		
			TrustEvent event = new MetricEventImpl("", "", "", "");
			
			try {
				repMetrics.processReputationRating(event);
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}

	}
}
