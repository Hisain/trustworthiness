package eu.aniketos.wp2.components.trustworthiness.mock.messaging;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.log4j.Logger;

import eu.aniketos.wp2.components.trustworthiness.messaging.QoSMetricsService;

/**
 * @author Hisain Elshaafi (TSSG)
 * 
 */
public class QoSMetricsSender {

	private static Logger logger = Logger.getLogger(QoSMetricsSender.class);

	private QoSMetricsService qosMetrics;

	/**
	 * @param qosMetics
	 */
	public QoSMetricsSender(QoSMetricsService qosMetics) {
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
		for (int i = 0; i < 10; i++) {
			Map<String, String> metric = new HashMap<String, String>();
			metric.put("serviceId", "id05");
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
		for (int i = 0; i < 10; i++) {
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
		for (int i = 0; i < 10; i++) {
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
			metric.put("serviceId", "id05");
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
			metric.put("serviceId", "id07");
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
			metric.put("serviceId", "id08");
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
