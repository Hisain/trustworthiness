package eu.aniketos.trustworthiness.impl.messaging;

import java.util.Map;

import org.apache.log4j.Logger;

import eu.aniketos.trustworthiness.configuration.ConfigurationManagement;
import eu.aniketos.trustworthiness.ext.messaging.IQosMetricsService;
import eu.aniketos.trustworthiness.ext.rules.model.event.TrustEvent;
import eu.aniketos.trustworthiness.impl.messaging.util.PropertyValidator;
import eu.aniketos.trustworthiness.rules.service.MetricRatingUpdate;
import eu.aniketos.trustworthiness.trust.service.ServiceEntityService;

/**
 * @author Hisain Elshaafi (TSSG)
 * 
 */
public class QoSMetricsServiceImpl implements IQosMetricsService {

	private static Logger logger = Logger.getLogger(IQosMetricsService.class);

	private ConfigurationManagement config;

	private MetricRatingUpdate qosUpdate;

	private ServiceEntityService serviceEntityService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see eu.aniketos.trustworthiness.messaging.QosMetricsService
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
				|| !metric.containsKey("value")
				|| metric.get("value") == null
				|| metric.get("value") == ""
				|| !PropertyValidator.isNumeric(metric.get("value"))
				|| (metric.containsKey("subproperty") && (metric
						.get("subproperty") == null || metric
						.get("subproperty") == ""))) {
			logger.warn("received metric contains null, empty or invalid data");
			throw new RuntimeException(
					"received metric contains null, empty or invalid data");

		} else {

			try {
				qosUpdate.generateRating(metric);
			} catch (Exception e) {
				logger.error("Exception: " + e.getMessage());
			}
		}
	}

	public void processQoSMetric(TrustEvent event) {

		if (event == null || event.getServiceId() == null
				|| event.getServiceId().isEmpty()
				|| event.getProperty() == null || event.getProperty().isEmpty()
				|| event.getValue() == null || event.getValue().isEmpty()
				|| !PropertyValidator.isNumeric(event.getValue())) {

			logger.warn("received metric contains null, empty or invalid data");
			throw new RuntimeException(
					"received metric contains null, empty or invalid data");

		} else {

			try {
				qosUpdate.generateRating(event);
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
