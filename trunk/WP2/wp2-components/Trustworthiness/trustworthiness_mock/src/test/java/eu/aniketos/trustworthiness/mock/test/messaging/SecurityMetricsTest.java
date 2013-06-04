package eu.aniketos.trustworthiness.mock.test.messaging;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import eu.aniketos.trustworthiness.ext.messaging.SecurityMetricsService;

/**
 * @author Hisain Elshaafi (TSSG)
 * 
 */
public class SecurityMetricsTest {

	private static Logger logger = Logger.getLogger(SecurityMetricsTest.class);

	private SecurityMetricsService securityMetrics;

	/**
	 * @param securityMetics
	 */
	public SecurityMetricsTest(SecurityMetricsService securityMetics) {
		super();
		this.securityMetrics = securityMetics;
	}

	/**
	 * 
	 */
	public void initialize() {

		// metric with null or empty service id
		logger.info("metric with null or empty service id");
		for (int i = 0; i < 1; i++) {
			Map<String, String> metric = new HashMap<String, String>();
			metric.put("serviceId", null);
			metric.put("type", "metric");
			metric.put("property", "confidentiality");
			metric.put("subproperty", "encryption");
			metric.put("value", "1");
			try {
				securityMetrics.receiveMetrics(metric);
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
			metric.put("property", "integrity");
			metric.put("subproperty", "signing");
			metric.put("value", "1");
			try {
				securityMetrics.receiveMetrics(metric);
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}

		for (int i = 0; i < 1; i++) {
			Map<String, String> metric = new HashMap<String, String>();
			metric.put("serviceId", "testId07");
			metric.put("type", "metric");
			metric.put("property", "confidentiality");
			metric.put("subproperty", "encryption");
			metric.put("value", "1");
			try {
				securityMetrics.receiveMetrics(metric);
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
			metric.put("serviceId", "testId07");
			metric.put("type", "metric");
			metric.put("property", "integrity");
			metric.put("subproperty", "signing");
			metric.put("value", "1");
			try {
				securityMetrics.receiveMetrics(metric);
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}
		
		for (int i = 0; i < 1; i++) {
			Map<String, String> metric = new HashMap<String, String>();
			metric.put("serviceId", "testId08");
			metric.put("type", "metric");
			metric.put("property", "confidentiality");
			metric.put("subproperty", "encryption");
			metric.put("value", "1");
			try {
				securityMetrics.receiveMetrics(metric);
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
			metric.put("serviceId", "testId08");
			metric.put("type", "metric");
			metric.put("property", "integrity");
			metric.put("subproperty", "signing");
			metric.put("value", "1");
			try {
				securityMetrics.receiveMetrics(metric);
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}
		
		for (int i = 0; i < 1; i++) {
			Map<String, String> metric = new HashMap<String, String>();
			metric.put("serviceId", "testId05");
			metric.put("type", "metric");
			metric.put("property", "confidentiality");
			metric.put("subproperty", "encryption");
			metric.put("value", "1");
			try {
				securityMetrics.receiveMetrics(metric);
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
			metric.put("serviceId", "testId05");
			metric.put("type", "metric");
			metric.put("property", "integrity");
			metric.put("subproperty", "signing");
			metric.put("value", "0");
			try {
				securityMetrics.receiveMetrics(metric);
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}
		
	}
}
