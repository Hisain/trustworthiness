package eu.aniketos.wp2.components.trustworthiness.mock.messaging;

import java.util.Random;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

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
		
		for (int i = 0; i < 10; i++) {

			TrustEvent event = new MetricEventImpl();

			event.setServiceId("testId10");

			event.setProperty("reputation");

			event.setValue(Double.toString(0.9 + r.nextDouble() / 10));

			DateTime dt = new DateTime();
			DateTimeFormatter fmt = ISODateTimeFormat.dateTimeNoMillis();
			String timestamp = fmt.print(dt);

			event.setTimestamp(timestamp);

			if (logger.isDebugEnabled()) {
				logger.debug(" timestamp=" + timestamp);
			}
			
			String eventDescription = "nice service, happy to do business with..";
			event.setEventDescription(eventDescription);

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
		
		for (int i = 0; i < 10; i++) {

			TrustEvent event = new MetricEventImpl();

			event.setServiceId("testId05");

			event.setProperty("reputation");

			event.setValue(Double.toString(0.9 + r.nextDouble() / 10));

			DateTime dt = new DateTime();
			DateTimeFormatter fmt = ISODateTimeFormat.dateTimeNoMillis();
			String timestamp = fmt.print(dt);

			event.setTimestamp(timestamp);

			if (logger.isDebugEnabled()) {
				logger.debug(" timestamp=" + timestamp);
			}
			
			String eventDescription = "thanks..";
			event.setEventDescription(eventDescription);

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
		
		logger.info("invalid timestamp");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {

			logger.error(e.getMessage());
		}
		
		for (int i = 0; i < 3; i++) {

			TrustEvent event = new MetricEventImpl();

			event.setServiceId("testId10");

			event.setProperty("reputation");

			event.setValue(Double.toString(0.9 + r.nextDouble() / 10));

			event.setTimestamp("34523453z");

			try {
				repMetrics.processReputationRating(event);
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}

	}
}
