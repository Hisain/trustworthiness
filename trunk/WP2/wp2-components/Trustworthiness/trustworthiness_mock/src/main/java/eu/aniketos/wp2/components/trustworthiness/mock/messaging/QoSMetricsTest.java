package eu.aniketos.wp2.components.trustworthiness.mock.messaging;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.log4j.Logger;

import eu.aniketos.wp2.components.trustworthiness.ext.messaging.QosMetricsService;
import eu.aniketos.wp2.components.trustworthiness.ext.rules.model.event.TrustEvent;

/**
 * @author Hisain Elshaafi (TSSG)
 * 
 */
public class QoSMetricsTest {

	private static Logger logger = Logger.getLogger(QoSMetricsTest.class);

	private QosMetricsService qosMetrics;

	/**
	 * @param qosMetics
	 */
	public QoSMetricsTest(QosMetricsService qosMetics) {
		super();
		this.qosMetrics = qosMetics;
	}

	/**
	 * 
	 */
	public void initialize() {

		Random r = new Random();

		// metric with null or empty service id
		logger.info("metric with null or empty service id");
		
		for (int i = 0; i < 1; i++) {
			TrustEvent event = new MetricEventImpl();
			event.setServiceId(null);
			event.setProperty("availability");
			event.setSubproperty("uptime");
			event.setValue(Double.toString(0.9 + r.nextDouble() / 10));
			try {
				qosMetrics.processQoSMetric(event);
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}

		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {

			logger.error(e.getMessage());
		}
		
		for (int i = 0; i < 1; i++) {
			Map<String, String> metric = new HashMap<String, String>();
			metric.put("serviceId", null);
			metric.put("type", "metric");
			metric.put("property", "availability");
			metric.put("subproperty", "uptime");
			metric.put("value", Double.toString(0.9 + r.nextDouble() / 10));
			try {
				qosMetrics.receiveMetrics(metric);
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}

				
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {

			logger.error(e.getMessage());
		}
		for (int i = 0; i < 1; i++) {
			Map<String, String> metric = new HashMap<String, String>();
			metric.put("serviceId", "");
			metric.put("type", "metric");
			metric.put("property", "availability");
			metric.put("subproperty", "uptime");
			metric.put("value", Double.toString(0.9 + r.nextDouble() / 10));
			try {
				qosMetrics.receiveMetrics(metric);
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {

			logger.error(e.getMessage());
		}
		for (int i = 0; i < 1; i++) {
			TrustEvent event = new MetricEventImpl();
			event.setServiceId("testId05");
			event.setProperty("availability");
			event.setSubproperty("uptime");
			event.setValue(Double.toString(0.9 + r.nextDouble() / 10));
			try {
				qosMetrics.processQoSMetric(event);
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
			Map<String, String> metric = new HashMap<String, String>();
			metric.put("serviceId", "testId05");
			metric.put("type", "metric");
			metric.put("property", "availability");
			metric.put("subproperty", "uptime");
			metric.put("value", Double.toString(0.9 + r.nextDouble() / 10));
			try {
				qosMetrics.receiveMetrics(metric);
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
			Map<String, String> metric = null;
			try {
				qosMetrics.receiveMetrics(metric);
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
			Map<String, String> metric = new HashMap<String, String>();
			try {
				qosMetrics.receiveMetrics(metric);
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
			Map<String, String> metric = new HashMap<String, String>();
			metric.put("serviceId", "testId05");
			metric.put("type", "metric");
			metric.put("property", "reliability");
			metric.put("subproperty", "error");
			metric.put("value", Double.toString(0.9 + r.nextDouble() / 10));
			try {
				qosMetrics.receiveMetrics(metric);
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
			Map<String, String> metric = new HashMap<String, String>();
			metric.put("serviceId", "testId07");
			metric.put("type", "metric");
			metric.put("property", "reliability");
			metric.put("subproperty", "error");
			metric.put("value", Double.toString(0.9 + r.nextDouble() / 10));
			try {
				qosMetrics.receiveMetrics(metric);
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
			Map<String, String> metric = new HashMap<String, String>();
			metric.put("serviceId", "testId08");
			metric.put("type", "metric");
			metric.put("property", "reliability");
			metric.put("subproperty", "error");
			metric.put("value", Double.toString(0.5 + r.nextDouble() / 10));
			try {
				qosMetrics.receiveMetrics(metric);
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}

	}
}
