package eu.aniketos.trustworthiness.runtime.test.monitoring;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import org.apache.log4j.Logger;

import eu.aniketos.trustworthiness.ext.messaging.IQosMetricsService;
import eu.aniketos.trustworthiness.ext.rules.model.event.TrustEvent;
import eu.aniketos.trustworthiness.runtime.test.monitoring.event.MetricEventImpl;

/**
 * @author Hisain Elshaafi (TSSG)
 * 
 */
public class QoSMetricsTest {

	private static Logger logger = Logger.getLogger(QoSMetricsTest.class);

	private IQosMetricsService qosMetrics;

	/**
	 * @param qosMetics
	 */
	public QoSMetricsTest(IQosMetricsService qosMetics) {
		super();
		this.qosMetrics = qosMetics;
	}

	/**
	 * 
	 */
	public void initialize() {

		Map<String, String> metric = new HashMap<String, String>();
		metric.put("serviceId", "Aid1130Test");
		metric.put("property", "performance");
		metric.put("subproperty", "delay");
		metric.put("limit", "W");
		metric.put("value", "W98");
		try {
			qosMetrics.receiveMetrics(metric);
		} catch (Exception e) {
			logger.error(e.getMessage());
		}

		/*Random r = new Random();

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
			metric.put("property", "reliability");
			metric.put("subproperty", "error");
			metric.put("value", Double.toString(0.5 + r.nextDouble() / 10));
			try {
				qosMetrics.receiveMetrics(metric);
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}*/

	}
}
