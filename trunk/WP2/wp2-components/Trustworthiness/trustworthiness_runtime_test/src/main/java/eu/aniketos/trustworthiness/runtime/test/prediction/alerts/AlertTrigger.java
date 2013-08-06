package eu.aniketos.trustworthiness.runtime.test.prediction.alerts;

import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;

import eu.aniketos.trustworthiness.ext.messaging.ISecurityPropertiesService;

public class AlertTrigger {

	private static Logger logger = Logger.getLogger(AlertTrigger.class);

	private ISecurityPropertiesService securityMetrics;

	/**
	 * @param securityMetics
	 */
	public AlertTrigger(ISecurityPropertiesService securityMetics) {
		super();
		this.securityMetrics = securityMetics;
	}

	public void initialize() {

		// security property set to 0 to trigger an alert
		logger.info("security property set to 0 to trigger an alert");
		for (int i = 0; i < 1; i++) {
			Map<String, String> metric = new HashMap<String, String>();
			metric.put("serviceId", "http://83.235.133.36/AniketosWS/DoUPModuleSoap12HttpPort?wsdl");
			metric.put("property", "confidentiality");
			metric.put("subproperty", "encryption");
			metric.put("value", "0");
			try {
				securityMetrics.receiveProperty(metric);
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}

	}
}
