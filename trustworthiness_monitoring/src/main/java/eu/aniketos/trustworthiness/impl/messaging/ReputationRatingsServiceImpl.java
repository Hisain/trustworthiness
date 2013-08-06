package eu.aniketos.trustworthiness.impl.messaging;

import java.util.Map;

import org.apache.log4j.Logger;

import eu.aniketos.trustworthiness.configuration.ConfigurationManagement;
import eu.aniketos.trustworthiness.ext.messaging.IReputationRatingsService;
import eu.aniketos.trustworthiness.ext.rules.model.event.ConsumerRatingEvent;
import eu.aniketos.trustworthiness.impl.messaging.util.PropertyValidator;
import eu.aniketos.trustworthiness.rules.service.ReputationRatingUpdate;
import eu.aniketos.trustworthiness.trust.service.ServiceEntityService;

/**
 * @author Hisain Elshaafi (TSSG)
 * 
 */
public class ReputationRatingsServiceImpl implements IReputationRatingsService {

	private static Logger logger = Logger
			.getLogger(IReputationRatingsService.class);

	private ConfigurationManagement config;

	private ReputationRatingUpdate ratingUpdate;

	private ServiceEntityService serviceEntityService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see eu.aniketos.trustworthiness.messaging.QosMetricsService
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
				|| !rating.containsKey("value")
				|| rating.get("value") == null
				|| rating.get("value") == ""
				|| !PropertyValidator.isNumeric(rating.get("value"))
				|| (rating.containsKey("subproperty") && (rating
						.get("subproperty") == null || rating
						.get("subproperty") == ""))) {
			logger.warn("received consumer rating contains null, empty or invalid data");
			throw new RuntimeException(
					"received consumer rating contains null, empty or invalid data");

		} else {

			try {
				ratingUpdate.generateTrustRating(rating);
			} catch (Exception e) {
				logger.error("Exception: " + e.getMessage());
			}
		}
	}

	public void processReputationRating(ConsumerRatingEvent event) {

		if (event == null || event.getServiceId() == null
				|| event.getConsumerId() == null
				|| event.getTransactionId() == null
				|| event.getProperty() == null || event.getValue() == null
				|| event.getServiceId().isEmpty()
				|| event.getConsumerId().isEmpty()
				|| event.getTransactionId().isEmpty()
				|| !event.getProperty().equalsIgnoreCase("reputation")
				|| event.getProperty().isEmpty() || event.getValue().isEmpty()
				|| !PropertyValidator.isNumeric(event.getValue())) {

			logger.warn("received consumer rating contains null, empty or invalid data");
			throw new RuntimeException(
					"received consumer rating contains null, empty or invalid data");

		} else {

			try {
				ratingUpdate.generateTrustRating(event);
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
