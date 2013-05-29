package eu.aniketos.wp2.components.trustworthiness.impl.messaging;

import java.util.Map;

import org.apache.log4j.Logger;

import eu.aniketos.wp2.components.trustworthiness.configuration.ConfigurationManagement;
import eu.aniketos.wp2.components.trustworthiness.ext.messaging.ReputationMetricsService;
import eu.aniketos.wp2.components.trustworthiness.ext.rules.model.event.ConsumerRatingEvent;
import eu.aniketos.wp2.components.trustworthiness.rules.service.ReputationRatingUpdate;
import eu.aniketos.wp2.components.trustworthiness.trust.service.ServiceEntityService;

/**
 * @author Hisain Elshaafi (TSSG)
 * 
 */
public class ReputationRatingServiceImpl implements ReputationMetricsService {

	private static Logger logger = Logger
			.getLogger(ReputationMetricsService.class);

	private ConfigurationManagement config;

	private ReputationRatingUpdate ratingUpdate;

	private ServiceEntityService serviceEntityService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * eu.aniketos.wp2.components.trustworthiness.messaging.QosMetricsService
	 * #receiveMetrics(java.util.Map)
	 */
	public void receiveRatings(Map<String, String> rating) {

		if (rating == null
				|| rating.size() == 0
				|| !rating.containsKey("serviceId")
				|| rating.get("serviceId") == null
				|| rating.get("serviceId") == ""
				|| !rating.containsKey("consumerId")
				|| rating.get("consumerId") == null
				|| rating.get("consumerId") == ""
				|| !rating.containsKey("transactionId")
				|| rating.get("transactionId") == null
				|| rating.get("transactionId") == ""
				|| !rating.containsKey("property")
				|| rating.get("property") == null
				|| rating.get("property") == ""
				|| !rating.containsKey("type")
				|| rating.get("type") == null
				|| rating.get("type") == ""
				|| (rating.containsKey("subproperty") && (rating
						.get("subproperty") == null || rating
						.get("subproperty") == ""))) {
			logger.warn("received consumer rating contains null or empty data");
			throw new RuntimeException(
					"received consumer rating contains null or empty data");

		} else {

			try {
				ratingUpdate.updateScore(rating);
			} catch (Exception e) {
				logger.error("Exception: " + e.getMessage());
			}
		}
	}

	public void processReputationRating(ConsumerRatingEvent event)
			throws Exception {

		if (event == null || event.getServiceId() == null
				|| event.getConsumerId() == null
				|| event.getTransactionId() == null
				|| event.getProperty() == null || event.getValue() == null
				|| event.getServiceId().length() == 0
				|| event.getConsumerId().length() == 0
				|| event.getTransactionId().length() == 0
				|| event.getProperty().length() == 0
				|| event.getValue().length() == 0) {
			logger.warn("received consumer rating contains null or empty data");
			throw new Exception(
					"received consumer rating contains null or empty data");

		} else {

			ratingUpdate.updateScore(event);
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
	public ReputationRatingUpdate getRatingUpdate() {
		return ratingUpdate;
	}

	/**
	 * required for Spring dependency injection
	 * 
	 * @param ratingUpdate
	 */
	public void setRatingUpdate(ReputationRatingUpdate ratingUpdate) {
		this.ratingUpdate = ratingUpdate;
	}

}
