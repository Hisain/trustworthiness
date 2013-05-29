package eu.aniketos.wp2.components.trustworthiness.impl.messaging;

import java.util.Map;

import org.apache.log4j.Logger;

import eu.aniketos.wp2.components.trustworthiness.configuration.ConfigurationManagement;
import eu.aniketos.wp2.components.trustworthiness.ext.messaging.QosMetricsService;
import eu.aniketos.wp2.components.trustworthiness.ext.rules.model.event.TrustEvent;
import eu.aniketos.wp2.components.trustworthiness.rules.service.MetricRatingUpdate;
import eu.aniketos.wp2.components.trustworthiness.trust.service.ServiceEntityService;

/**
 * @author Hisain Elshaafi (TSSG)
 * 
 */
public class QoSMetricsServiceImpl implements QosMetricsService {

	private static Logger logger = Logger.getLogger(QosMetricsService.class);

	private ConfigurationManagement config;

	private MetricRatingUpdate qosUpdate;

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
				qosUpdate.updateScore(metric);
			} catch (Exception e) {
				logger.error("Exception: " + e.getMessage());
			}
		}
	}

	public void processQoSMetric(TrustEvent event) {

		if (event == null || event.getServiceId() == null
				|| event.getProperty() == null || event.getValue() == null) {
			logger.warn("received metric contains null or empty data");
			throw new RuntimeException(
					"received metric contains null or empty data");

		} else {

			try {
				qosUpdate.updateScore(event);
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
	public MetricRatingUpdate getQosUpdate() {
		return qosUpdate;
	}

	/**
	 * required for Spring dependency injection
	 * 
	 * @param qosUpdate
	 */
	public void setQosUpdate(MetricRatingUpdate qosUpdate) {
		this.qosUpdate = qosUpdate;
	}

}
