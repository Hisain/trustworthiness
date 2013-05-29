package eu.aniketos.wp2.components.trustworthiness.impl.messaging;

import java.util.Map;

import org.apache.log4j.Logger;

import eu.aniketos.wp2.components.trustworthiness.configuration.ConfigurationManagement;
import eu.aniketos.wp2.components.trustworthiness.ext.messaging.SecurityMetricsService;
import eu.aniketos.wp2.components.trustworthiness.rules.service.MetricRatingUpdate;
import eu.aniketos.wp2.components.trustworthiness.trust.service.ServiceEntityService;

/**
 * @author Hisain Elshaafi (TSSG)
 * 
 */
public class SecurityMetricsServiceImpl implements SecurityMetricsService {

	private static Logger logger = Logger
			.getLogger(SecurityMetricsService.class);

	private ConfigurationManagement config;

	private MetricRatingUpdate secPropertyUpdate;

	private ServiceEntityService serviceEntityService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * eu.aniketos.wp2.components.trustworthiness.messaging.QosMetricsService
	 * #receiveMetrics(java.util.Map)
	 */
	public void receiveMetrics(Map<String, String> metric) {

		if (metric == null
				|| metric.size() == 0
				|| !metric.containsKey("serviceId")
				|| metric.get("serviceId") == null
				|| metric.get("serviceId") == ""
				|| !metric.containsKey("property")
				|| metric.get("property") == null
				|| metric.get("property") == ""
				|| !metric.containsKey("type")
				|| metric.get("type") == null
				|| metric.get("type") == ""
				|| (metric.containsKey("subproperty") && (metric
						.get("subproperty") == null || metric
						.get("subproperty") == ""))) {
			logger.warn("received metric contains null or empty data");
			throw new RuntimeException(
					"received metric contains null or empty data");

		} else {

			try {
				secPropertyUpdate.updateScore(metric);
			} catch (Exception e) {
				logger.error("Exception: " + e.getMessage());
			}
		}
	}

	/**
	 * required for Spring dependency injection
	 * 
	 * @param config
	 *            set configuration field
	 */
	public void setConfig(ConfigurationManagement config) {
		this.config = config;
	}

	/**
	 * required for Spring dependency injection
	 * 
	 * @return configuration field
	 */
	public ConfigurationManagement getConfig() {
		return config;
	}

	/**
	 * required for Spring dependency injection
	 * 
	 * @param serviceEntityService
	 *            data access service object for Web services
	 */
	public void setServiceEntityService(
			ServiceEntityService serviceEntityService) {
		this.serviceEntityService = serviceEntityService;
	}

	/**
	 * required for Spring dependency injection
	 * 
	 * @return data access service object for Web services
	 */
	public ServiceEntityService getServiceEntityService() {
		return serviceEntityService;
	}

	/**
	 * required for Spring dependency injection
	 * 
	 * @return
	 */
	public MetricRatingUpdate getSecPropertyUpdate() {
		return secPropertyUpdate;
	}

	/**
	 * required for Spring dependency injection
	 * 
	 * @param secPropertyUpdate
	 */
	public void setSecPropertyUpdate(MetricRatingUpdate secPropertyUpdate) {
		this.secPropertyUpdate = secPropertyUpdate;
	}

}
