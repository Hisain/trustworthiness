package eu.aniketos.wp2.components.trustworthiness.impl.trust.management.atomic;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;

import eu.aniketos.wp2.components.trustworthiness.configuration.ConfigurationManagement;
import eu.aniketos.wp2.components.trustworthiness.trust.management.atomic.ServiceTrustUpdatePolicy;
import eu.aniketos.wp2.components.trustworthiness.trust.management.atomic.Trustworthiness;
import eu.aniketos.wp2.components.trustworthiness.trust.service.RatingEntityService;
import eu.aniketos.wp2.components.trustworthiness.trust.service.SecurityEntityService;
import eu.aniketos.wp2.components.trustworthiness.trust.service.ServiceEntityService;
import eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo.Rating;
import eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo.Atomic;
import eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo.SecProperty;
import eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo.Service;

/**
 * updates trustworthiness of (atomic) services using recursive approach
 * 
 * TODO: store data required for moving avg updates
 * 
 * @author Hisain Elshaafi
 * 
 */
public class ServiceTrustUpdateRecursiveImpl implements
		ServiceTrustUpdatePolicy {

	private static Logger logger = Logger
			.getLogger(ServiceTrustUpdateRecursiveImpl.class);

	private ServiceEntityService serviceEntityService;

	private RatingEntityService ratingEntityService;

	private SecurityEntityService securityEntityService;

	private ConfigurationManagement config;

	private EventAdmin eventAdmin;

	/* set default score (0.0) if not configured */
	private double defaultScore = 0.0;

	private double confidentialityWeight = 0.0;

	private double integrityWeight = 0.0;

	/* default tw level decay constant */
	private static double decayConstant = 0.25;

	// private long confidenceThreshold;

	Map<String, Double> propertiesMap = new HashMap<String, Double>();

	private double alpha = 1;

	/**
	 * 
	 */
	public void initialize() {

		logger.debug("ServiceTrustUpdatePolicyImpl");

		/*
		 * Set score half life from configuration TODO use drools for this
		 */
		if (!config.getConfig().containsKey("score_decay_constant")) {
			logger.warn("trust score decay constant is not specified, will be set to no expiry.");
		} else {
			decayConstant = config.getConfig()
					.getDouble("score_decay_constant");

			if (decayConstant < 0) {
				logger.error("incorrect decay constant specified. must be > 0. will be set to no expirty.");
			} else if (decayConstant == 0) {
				logger.warn("score decay constant set to 0. will be reset to 0.25.");

			} else if (decayConstant > 25.0) {
				logger.warn("score decay constant too high. will be reset to 0.25.");
			}
		}

		/**
		 * set default score
		 */
		if (!config.getConfig().containsKey("default_score")) {
			logger.warn("default trust score is not specified, will be set to 0.0");
		} else {
			defaultScore = config.getConfig().getFloat("default_score");

			if ((defaultScore < 0) || (defaultScore > 1)) {
				logger.error("incorrect default score specified. "
						+ "must be 0 <= default_score <= 1. "
						+ "will be set to 0.0");
				defaultScore = 0.0f;
			}
		}

		/**
		 * set confidentiality weight
		 */
		if (!config.getConfig().containsKey("confidentiality_weight")) {
			logger.warn("confidentiality weight is not specified, will be set to 1.0");
		} else {
			confidentialityWeight = config.getConfig().getFloat(
					"confidentiality_weight");

			if ((confidentialityWeight < 0) || (confidentialityWeight > 10)) {
				logger.error("incorrect confidentiality weight specified. "
						+ "must be 0 <= confidentiality_weight <= 10. "
						+ "will be set to 1.0");
				confidentialityWeight = 1.0f;
			}
		}

		/**
		 * set integrity weight
		 */
		if (!config.getConfig().containsKey("integrity_weight")) {
			logger.warn("integrity weight is not specified, will be set to 1.0");
		} else {
			integrityWeight = config.getConfig().getFloat("integrity_weight");

			if ((integrityWeight < 0) || (integrityWeight > 10)) {
				logger.error("incorrect integrity weight specified. "
						+ "must be 0 <= integrity_weight <= 10. "
						+ "will be set to 1.0");
				integrityWeight = 1.0f;
			}
		}

		/*
		 * if (!config.getConfig().containsKey("confidence_threshold")) {
		 * logger.warn("confidence threshold is not set, will be set to 1"); }
		 * else { confidenceThreshold = config.getConfig().getLong(
		 * "confidence_threshold");
		 * 
		 * if (confidenceThreshold < 1) {
		 * logger.error("incorrect confidence threshold specified. " +
		 * "recommended ~100-10000. " + "will be set to 1"); confidenceThreshold
		 * = 1; } }
		 */

		String[] properties = config.getConfig().getStringArray("properties");

		for (String property : properties) {

			double propertyWt = config.getConfig().getDouble(property);
			logger.info("property " + property + " weight=" + propertyWt);
			propertiesMap.put(property, propertyWt);
		}

		if (config.getConfig().containsKey("confidence_constant")) {
			alpha = config.getConfig().getDouble("confidence_constant");
		} else {
			logger.warn("confidence constant is not set, will be set to 1");
		}

	}

	/**
	 * @throws Exception
	 * 
	 */
	public Trustworthiness calculateTrust(Rating rating) throws Exception {

		Trustworthiness trust = null;

		Service service = rating.getService();

		if (service != null) {

			logger.info("found service " + service.getId());

			trust = updateTrust(service.getId());

		} else {
			if (logger.isInfoEnabled()) {
				logger.info("service " + service + " not found");
			}

		}

		return trust;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see eu.aniketos.wp2.components.trustworthiness.trust.management.atomic.
	 * ServiceTrustUpdatePolicy#calculateTrust(java.lang.String)
	 */
	public Trustworthiness updateTrust(String serviceId) throws Exception {

		logger.debug("try retrieve previous score");

		Atomic service = serviceEntityService.getAtomic(serviceId);
		if (service == null) {

			if (logger.isInfoEnabled()) {
				logger.info("service " + service + " not found");
			}
			throw new Exception("no record of service " + serviceId);

		}

		logger.info("found service " + service.getId());

		List<Rating> serviceScores = null;

		serviceScores = ratingEntityService.getRatingsByServiceId(service
				.getId());

		List<SecProperty> serviceSecProps = null;

		serviceSecProps = securityEntityService
				.getSecPropertiesByServiceId(serviceId);

		if ((serviceScores == null || serviceScores.size() == 0)
				&& (serviceSecProps == null || serviceSecProps.size() == 0)) {

			if (logger.isInfoEnabled()) {
				logger.info("no recorded ratings for service " + serviceId);
			}
			throw new Exception("no recorded ratings for service " + serviceId);

		}

		Map<String, Double> qos = calcQoS(serviceScores);

		double securityScore = calcSecurityScore(serviceSecProps);

		double qosScore = qos.get("qosScore");
		double qosConfidence = qos.get("qosConfidence");
		double deviation = qos.get("deviation");
		double nowInHour = qos.get("nowInHour");
		double totalScoreWt = qos.get("totalScoreWt");

		// determine overall trustworthiness score
		// TODO: separate reputation from qos
		double trustworthinessScore = qosScore * securityScore;

		if (logger.isInfoEnabled()) {
			logger.info("trustworthiness for " + service + "="
					+ trustworthinessScore);
		}

		BigDecimal scoreBD = new BigDecimal(String.valueOf(qosScore)).setScale(
				3, BigDecimal.ROUND_HALF_UP);
		BigDecimal confidenceBD = new BigDecimal(String.valueOf(qosConfidence))
				.setScale(3, BigDecimal.ROUND_HALF_UP);
		BigDecimal deviationBD = new BigDecimal(String.valueOf(deviation))
				.setScale(5, BigDecimal.ROUND_HALF_UP);
		BigDecimal nowInHourBD = new BigDecimal(String.valueOf(nowInHour))
				.setScale(3, BigDecimal.ROUND_HALF_UP);
		BigDecimal totalScoreWtBD = new BigDecimal(String.valueOf(totalScoreWt))
				.setScale(3, BigDecimal.ROUND_HALF_UP);
		BigDecimal securityBD = new BigDecimal(String.valueOf(securityScore))
				.setScale(3, BigDecimal.ROUND_HALF_UP);
		BigDecimal trustworthinessBD = new BigDecimal(
				String.valueOf(trustworthinessScore)).setScale(3,
				BigDecimal.ROUND_HALF_UP);

		qosScore = Double.parseDouble(scoreBD.toString());
		qosConfidence = Double.parseDouble(confidenceBD.toString());
		deviation = Double.parseDouble(deviationBD.toString());
		nowInHour = Double.parseDouble(nowInHourBD.toString());
		totalScoreWt = Double.parseDouble(totalScoreWtBD.toString());
		securityScore = Double.parseDouble(securityBD.toString());
		trustworthinessScore = Double.parseDouble(trustworthinessBD.toString());

		Trustworthiness trust = new ServiceTrustworthiness(service.getId(),
				trustworthinessScore, qosScore, qosConfidence, securityScore);

		service.setQosScore(qosScore);
		service.setQosConfidence(qosConfidence);
		service.setDeviation(deviation);
		service.setCalcTime(nowInHour);
		service.setMovingWt(totalScoreWt);
		service.setSecurityScore(securityScore);
		service.setTrustworthinessScore(trustworthinessScore);

		logger.debug("updating service with results..");
		serviceEntityService.updateAtomic(service);

		// send alert if trustworthiness < alert threshold
		if (trustworthinessScore < config.getConfig().getDouble(
				"alert_threshold")) {

			Map<String, String> props = new HashMap<String, String>();
			props.put("service.id", serviceId);
			props.put("trustworthiness.score",
					Double.toString(trustworthinessScore));
			props.put("trustworthiness.confidence",
					Double.toString(qosConfidence));

			Event osgiEvent = new Event("eu/aniketos/trustworthiness/alert",
					props);
			eventAdmin.sendEvent(osgiEvent);

			logger.debug("trustworthiness below threshold, sent an alert.");
		} else {
			logger.debug("trustworthiness above threshold.");

		}

		return trust;
	}

	private Map<String, Double> calcQoS(List<Rating> serviceScores) {

		Map<String, Double> qos = new HashMap<String, Double>();

		double qosScore = 0;

		double qosConfidence = 0;

		/*
		 * TODO: check expiry and delete if expired
		 */

		double totalScoreWt = 0;

		/* current time in hrs */
		int hourMsecs = 3600000;
		double now = System.currentTimeMillis();
		double nowInHour = now / (double) hourMsecs;

		for (Rating serviceScore : serviceScores) {

			double recency = serviceScore.getRecency();
			double scoreAge = nowInHour - recency;

			if (logger.isDebugEnabled()) {
				logger.debug("score age " + scoreAge);
			}

			/*
			 * calculate recency weight
			 */
			double recencyWt = Math.pow(Math.E, -scoreAge * decayConstant);

			if (logger.isDebugEnabled()) {
				logger.debug("recencyWt " + recencyWt);
			}

			/*
			 * identify all properties
			 */
			String property = serviceScore.getProperty();

			double propertyWt = 0;

			if (!propertiesMap.containsKey(property)) {

				logger.error("property " + property
						+ " is not configured correctly.");

			} else {

				propertyWt = propertiesMap.get(property);

				if (propertyWt < 0 || propertyWt > 1) {
					/*
					 * no weight for this property, may use "continue" here
					 */
					propertyWt = 0;
				}

				if (logger.isDebugEnabled()) {
					logger.debug("propertyWt: " + propertyWt);
				}

			}
			double scoreWt = propertyWt * recencyWt;

			if (logger.isDebugEnabled()) {
				logger.debug("scoreWt " + scoreWt);
			}
			serviceScore.setScoreWt(scoreWt);
			totalScoreWt += scoreWt;
		}

		logger.debug("total score weight: " + totalScoreWt);

		/*
		 * calculation of score
		 */
		for (Rating serviceScore : serviceScores) {

			double scoreWt = 0;

			if (totalScoreWt > 0) {

				scoreWt = serviceScore.getScoreWt() / totalScoreWt;

			} else {
				logger.error("total property weight was 0 or less.");
			}

			/*
			 * allowed values: -1 < a < +1;
			 */
			if (logger.isDebugEnabled()) {
				logger.debug("scoreWt " + scoreWt);
			}

			serviceScore.setScoreWt(scoreWt);

			qosScore += scoreWt * serviceScore.getScore();
		}

		/*
		 * calculation of confidence
		 */
		double nConfidence = 0;
		double dConfidence = 0;

		/*
		 * calculate confidence based on number of scores
		 */
		nConfidence = 1 - Math.pow(Math.E, -(alpha * totalScoreWt));

		if (logger.isDebugEnabled()) {
			logger.debug("serviceScores.size() " + serviceScores.size());
			// logger.debug("confidenceThreshold " + confidenceThreshold);
		}

		/*
		 * calculate confidence based on score deviation
		 */
		double deviation = 0;
		double totalWt = 0;

		for (Rating serviceScore : serviceScores) {
			/*
			 * calculate deviation
			 */
			double scoreWt = serviceScore.getScoreWt();
			deviation += scoreWt * Math.abs(qosScore - serviceScore.getScore());
			totalWt += scoreWt;
		}

		double d = 0;
		if (totalWt > 0) {
			d = deviation / totalWt;
			dConfidence = 1 - d;
			if (logger.isDebugEnabled()) {
				logger.debug("dConfidence " + dConfidence);
				logger.debug("nConfidence " + nConfidence);
			}

		} else {
			logger.error("total weight was 0 or less.");
		}

		qosConfidence = dConfidence * nConfidence;

		qos.put("qosScore", qosScore);
		qos.put("qosConfidence", qosConfidence);
		qos.put("totalScoreWt", totalScoreWt);
		qos.put("deviation", deviation);
		qos.put("nowInHour", nowInHour);

		return qos;
	}

	private double calcSecurityScore(List<SecProperty> serviceSecProps) {
		double securityScore = 0;
		double totSecurity = 0;
		double propertyWeight = 0;

		for (SecProperty secProp : serviceSecProps) {
			if (secProp.getProperty().equals("confidentiality")) {
				propertyWeight = confidentialityWeight;
			} else if (secProp.getProperty().equals("integrity")) {
				propertyWeight = integrityWeight;
			}
			totSecurity += secProp.getScore() * propertyWeight;
		}
		securityScore = 1 - Math.exp(-totSecurity);
		return securityScore;
	}

	/**
	 * required for Spring dependency injection
	 * 
	 * @return data access service for atomic and composite Web services
	 */
	public ServiceEntityService getServiceEntityService() {
		return serviceEntityService;
	}

	/**
	 * required for Spring dependency injection
	 * 
	 * @param serviceEntityService
	 *            data access service for atomic and composite Web services
	 */
	public void setServiceEntityService(
			ServiceEntityService serviceEntityService) {
		this.serviceEntityService = serviceEntityService;
	}

	/**
	 * required for Spring dependency injection
	 * 
	 * @return data access service for rating scores
	 */
	public RatingEntityService getRatingEntityService() {
		return ratingEntityService;
	}

	/**
	 * required for Spring dependency injection
	 * 
	 * @param ratingEntityService
	 *            data access service for rating scores
	 */
	public void setRatingEntityService(RatingEntityService ratingEntityService) {
		this.ratingEntityService = ratingEntityService;
	}

	/**
	 * @return
	 */
	public SecurityEntityService getSecurityEntityService() {
		return securityEntityService;
	}

	/**
	 * @param securityEntityService
	 */
	public void setSecurityEntityService(
			SecurityEntityService securityEntityService) {
		this.securityEntityService = securityEntityService;
	}

	/**
	 * required for Spring dependency injection
	 * 
	 * @return object to retrieve configuration
	 */
	public ConfigurationManagement getConfig() {
		return config;
	}

	/**
	 * required for Spring dependency injection
	 * 
	 * @param config
	 *            object to retrieve configuration
	 */
	public void setConfig(ConfigurationManagement config) {
		this.config = config;
	}

	/**
	 * required for Spring dependency injection
	 * 
	 * @return OSGi event admin
	 */
	public EventAdmin getEventAdmin() {
		return eventAdmin;
	}

	/**
	 * required for Spring dependency injection
	 * 
	 * @param eventAdmin
	 *            OSGi event admin
	 */
	public void setEventAdmin(EventAdmin eventAdmin) {
		this.eventAdmin = eventAdmin;
	}

}
