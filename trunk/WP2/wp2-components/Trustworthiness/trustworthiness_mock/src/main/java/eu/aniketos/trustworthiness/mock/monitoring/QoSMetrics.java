package eu.aniketos.trustworthiness.mock.monitoring;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

import org.apache.log4j.Logger;

import eu.aniketos.trustworthiness.ext.messaging.IQosMetricsService;
import eu.aniketos.trustworthiness.ext.rules.model.event.TrustEvent;

/**
 * @author Hisain Elshaafi (TSSG)
 * 
 */
public class QoSMetrics {

	private static Logger logger = Logger.getLogger(QoSMetrics.class);

	private IQosMetricsService qosMetrics;

	/**
	 * @param qosMetics
	 */
	public QoSMetrics(IQosMetricsService qosMetics) {
		super();
		this.qosMetrics = qosMetics;
	}

	/**
	 * 
	 */
	public void initialize() {

		// sending metrics for Aniketos sample services
		logger.info("Sending QoS metrics for Aniketos sample services");
		
		Scanner scanner = null;
		try {
			scanner = new Scanner(new File("data/qos_data.txt"));

		} catch (FileNotFoundException e) {
			logger.error(e.getMessage());
		}

		while (scanner != null && scanner.hasNext()) {
			
			String line = scanner.next();

			String[] qosData = line.split(",");

			TrustEvent event = new MetricEventImpl();

			event.setServiceId(qosData[0]);
			
			event.setProperty(qosData[1]);
			
			event.setSubproperty(qosData[2]);
			
			event.setValue(qosData[3]);

			try {
				qosMetrics.processQoSMetric(event);
			} catch (Exception e) {
				logger.error(e.getMessage());
			}

			if (logger.isDebugEnabled()) {
				logger.debug("added QoS for service: " + qosData[0]);
			}
		}

	}
}
