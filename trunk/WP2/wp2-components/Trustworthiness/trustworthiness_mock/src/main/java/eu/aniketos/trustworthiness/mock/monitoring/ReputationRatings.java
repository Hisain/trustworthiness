package eu.aniketos.trustworthiness.mock.monitoring;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;

import eu.aniketos.trustworthiness.ext.messaging.IReputationRatingsService;
import eu.aniketos.trustworthiness.ext.rules.model.event.ConsumerRatingEvent;

/**
 * @author Hisain Elshaafi (TSSG)
 * 
 */
public class ReputationRatings {

	private static Logger logger = Logger.getLogger(ReputationRatings.class);

	private IReputationRatingsService repMetrics;

	/**
	 * @param qosMetics
	 */
	public ReputationRatings(IReputationRatingsService repMetrics) {
		super();
		this.repMetrics = repMetrics;
	}

	/**
	 * 
	 */
	public void initialize() {

		// sending metrics for Aniketos sample services
		logger.info("Sending reputation ratings for Aniketos sample services");

		Scanner scanner = null;
		
		try {
		
			scanner = new Scanner(new File("data/reputation_data.txt"));
		
		} catch (FileNotFoundException e) {
			logger.error(e.getMessage());
		}


		while (scanner != null && scanner.hasNext()) {
			String line = scanner.next();

			String[] qosData = line.split(",");

			ConsumerRatingEvent event = new ConsumerRatingEventImpl();

			event.setServiceId(qosData[0]);

			event.setProperty(qosData[1]);

			event.setConsumerId(qosData[2]);

			event.setTransactionId(qosData[3]);

			event.setValue(qosData[4]);

			DateTime dt = new DateTime();
			DateTimeFormatter fmt = ISODateTimeFormat.dateTimeNoMillis();
			String timestamp = fmt.print(dt);

			event.setTimestamp(timestamp);

			try {
				repMetrics.processReputationRating(event);
			} catch (Exception e) {
				logger.error(e.getMessage());
			}

			if (logger.isDebugEnabled()) {
				logger.debug("added reputation for service: " + qosData[0]);
			}
		}

	}
}
