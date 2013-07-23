package eu.aniketos.trustworthiness.mock.test.messaging;

import java.util.Random;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import eu.aniketos.trustworthiness.ext.messaging.ReputationRatingsService;
import eu.aniketos.trustworthiness.ext.rules.model.event.ConsumerRatingEvent;
import eu.aniketos.trustworthiness.mock.messaging.ConsumerRatingEventImpl;

/**
 * @author Hisain Elshaafi (TSSG)
 * 
 */
public class ReputationRatingsTest {

	private static Logger logger = Logger
			.getLogger(ReputationRatingsTest.class);

	private ReputationRatingsService repMetrics;

	/**
	 * @param qosMetics
	 */
	public ReputationRatingsTest(ReputationRatingsService repMetrics) {
		super();
		this.repMetrics = repMetrics;
	}

	/**
	 * 
	 */
	public void initialize() {

		Random r = new Random();

		// //////////////////////////////////////////
		logger.info("normal ratings");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {

			logger.error(e.getMessage());
		}

		for (int i = 0; i < 10; i++) {

			ConsumerRatingEvent event = new ConsumerRatingEventImpl();

			event.setServiceId("testId10");

			event.setConsumerId("c149");

			event.setTransactionId("t" + Integer.toString(r.nextInt()));

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

		// ////////////////////////////////////////

		for (int i = 0; i < 3; i++) {

			ConsumerRatingEvent event = new ConsumerRatingEventImpl();

			event.setServiceId("testId05");

			event.setConsumerId("c159");

			event.setTransactionId("t235");

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

		// ////////////////////////////////////////

		// rating with null or empty service id
		logger.info("rating with null or empty service id");

		for (int i = 0; i < 1; i++) {

			ConsumerRatingEvent event = new ConsumerRatingEventImpl();

			event.setServiceId(null);

			event.setConsumerId("c169");

			event.setTransactionId("t236");

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

		// ////////////////////////////////////////

		// rating with null or empty service id
		logger.info("rating with null or empty consumer id");

		for (int i = 0; i < 1; i++) {

			ConsumerRatingEvent event = new ConsumerRatingEventImpl();

			event.setServiceId("testId05");

			event.setConsumerId(null);

			event.setTransactionId("t288");

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

		// /////////////////////////////

		// rating with null or empty service id
		logger.info("rating with null or empty transaction id");

		for (int i = 0; i < 1; i++) {

			ConsumerRatingEvent event = new ConsumerRatingEventImpl();

			event.setServiceId("testId05");

			event.setConsumerId("c177");

			event.setTransactionId(null);

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

		// ////////////////////////////////////////

		logger.info("sending metric as null");

		for (int i = 0; i < 2; i++) {

			ConsumerRatingEvent event = null;

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

		// ////////////////////////////////////////

		logger.info("sending metric as empty");

		for (int i = 0; i < 2; i++) {

			ConsumerRatingEvent event = new ConsumerRatingEventImpl("", "", "",
					"", "", "");

			try {
				repMetrics.processReputationRating(event);
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}

		// ////////////////////////////////////////

		logger.info("invalid timestamp");
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {

			logger.error(e.getMessage());
		}

		for (int i = 0; i < 3; i++) {

			ConsumerRatingEvent event = new ConsumerRatingEventImpl();

			event.setServiceId("testId10");

			event.setConsumerId("c155");

			event.setTransactionId("t237");

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
