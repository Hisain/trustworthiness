package eu.aniketos.wp2.components.trustworthiness.impl.trust.management.atomic;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;

import eu.aniketos.wp2.components.trustworthiness.configuration.ConfigurationManagement;
import eu.aniketos.wp2.components.trustworthiness.trust.management.TrustFactory;
import eu.aniketos.wp2.components.trustworthiness.trust.management.atomic.ServiceTrustUpdatePolicy;
import eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo.TrustworthinessEntity;
import eu.aniketos.wp2.components.trustworthiness.trust.service.QoSMetricEntityService;
import eu.aniketos.wp2.components.trustworthiness.trust.service.RatingEntityService;
import eu.aniketos.wp2.components.trustworthiness.trust.service.SecurityEntityService;
import eu.aniketos.wp2.components.trustworthiness.trust.service.ServiceEntityService;
import eu.aniketos.wp2.components.trustworthiness.trust.service.TrustworthinessEntityService;
import eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo.QoSMetric;
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

	private TrustworthinessEntityService trustworthinessEntityService;

	private TrustFactory trustFactory;
	
	private RatingEntityService ratingEntityService;

	private QoSMetricEntityService qosEntityService;

	private SecurityEntityService securityEntityService;

	private ConfigurationManagement config;

	private EventAdmin eventAdmin;

	/* set default score (0.0) if not configured */
	private double defaultScore = 0.0;

	private double reputationWeight = 0.5;

	private double qosWeight = 0.5;

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
		 * set qos weight
		 */
		if (!config.getConfig().containsKey("qos_weight")) {
			logger.warn("qos weight is not specified, will be set to .5");
		} else {
			qosWeight = config.getConfig().getFloat("qos_weight");

			if ((qosWeight < 0) || (qosWeight > 1)) {
				logger.error("incorrect qos weight specified. "
						+ "must be 0 <= qos_weight <= 1. "
						+ "will be set to .5");
				qosWeight = .5f;
			}
		}

		/**
		 * set reputation weight
		 */
		if (!config.getConfig().containsKey("reputation_weight")) {
			logger.warn("reputation weight is not specified, will be set to .5");
		} else {
			reputationWeight = config.getConfig().getFloat("reputation_weight");

			if ((reputationWeight < 0) || (reputationWeight > 1)) {
				logger.error("incorrect reputation weight specified. "
						+ "must be 0 <= reputation_weight <= 1. "
						+ "will be set to .5");
				reputationWeight = .5f;
			}
		}

		/**
		 * ensure weights sum is 1
		 */
		if (reputationWeight + qosWeight != 1) {
			logger.error("incorrect reputation and qos weights specified. "
					+ "total must be equal to 1. ");
			reputationWeight = .5;
			qosWeight = .5;
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

		Object properties = config.getConfig().getProperty("property.name");
		String[] propsArray = {};
		
		if (properties == null) {

			if (logger.isDebugEnabled()) {
				logger.debug("No properties found");
			}

		} else if (properties instanceof Collection) {

			if (logger.isDebugEnabled()) {
				logger.debug("Number of properties: "
						+ ((Collection<?>) properties).size());
			}

			Object[] propsObjArray = ((Collection<String>) properties).toArray();
			propsArray = Arrays.copyOf(propsObjArray,
					propsObjArray.length, String[].class);
		}

		for (String property : propsArray) {

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
	public TrustworthinessEntity calculateTrust(Rating rating) throws Exception {

		TrustworthinessEntity trust = null;

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
	public TrustworthinessEntity updateTrust(String serviceId) throws Exception {

		logger.debug("try retrieve previous score");

		Atomic service = serviceEntityService.getAtomic(serviceId);
		if (service == null) {

			if (logger.isInfoEnabled()) {
				logger.info("service " + service + " not found");
			}
			throw new Exception("no record of service " + serviceId);

		}

		logger.info("found service " + service.getId());

		List<Rating> serviceRepScores = null;

		serviceRepScores = ratingEntityService.getRatingsByServiceId(serviceId);

		List<QoSMetric> serviceQosScores = null;

		serviceQosScores = qosEntityService.getMetricsByServiceId(serviceId);

		List<SecProperty> serviceSecProps = null;

		serviceSecProps = securityEntityService
				.getSecPropertiesByServiceId(serviceId);

		if ((serviceRepScores == null || serviceRepScores.size() == 0)
				&& (serviceQosScores == null || serviceQosScores.size() == 0)
				&& (serviceSecProps == null || serviceSecProps.size() == 0)) {

			if (logger.isInfoEnabled()) {
				logger.info("no recorded ratings for service " + serviceId);
			}
			throw new Exception("no recorded ratings for service " + serviceId);

		}

		Map<String, Double> qos = calcQoS(serviceQosScores);

		Map<String, Double> reputation = calcReputation(serviceRepScores);

		double securityScore = calcSecurityScore(serviceSecProps);

		double qosScore = qos.get("qosScore");
		double qosConfidence = qos.get("qosConfidence");
		double qosDeviation = qos.get("qosDeviation");
		double nowInHour = qos.get("nowInHour");
		double qosTotalScoreWt = qos.get("qosTotalScoreWt");

		double repScore = reputation.get("repScore");
		double repConfidence = reputation.get("repConfidence");
		double repDeviation = reputation.get("repDeviation");
		// double nowInHour = qos.get("nowInHour");
		double repTotalScoreWt = reputation.get("repTotalScoreWt");

		// determine overall trustworthiness score
		// TODO: more advanced formula
		double trustworthinessScore = (qosWeight * qosScore + reputationWeight
				* repScore)
				* securityScore;

		if (logger.isInfoEnabled()) {
			logger.info("trustworthiness for " + service + "="
					+ trustworthinessScore);
		}

		BigDecimal securityBD = new BigDecimal(String.valueOf(securityScore))
				.setScale(3, BigDecimal.ROUND_HALF_UP);
		BigDecimal trustworthinessBD = new BigDecimal(
				String.valueOf(trustworthinessScore)).setScale(3,
				BigDecimal.ROUND_HALF_UP);

		securityScore = Double.parseDouble(securityBD.toString());
		trustworthinessScore = Double.parseDouble(trustworthinessBD.toString());

		TrustworthinessEntity trustworthinessEntity = trustworthinessEntityService.getTrustworthiness(serviceId);
		if (trustworthinessEntity == null) {
			trustworthinessEntity = trustFactory.createTrustworthiness(serviceId);
		}

		trustworthinessEntity.setId(serviceId);
		trustworthinessEntity.setQosScore(qosScore);
		trustworthinessEntity.setQosConfidence(qosConfidence);
		trustworthinessEntity.setQosDeviation(qosDeviation);
		trustworthinessEntity.setQosMovingWt(qosTotalScoreWt);
		trustworthinessEntity.setReputationScore(repScore);
		trustworthinessEntity.setReputationConfidence(repConfidence);
		trustworthinessEntity.setReputationDeviation(repDeviation);
		trustworthinessEntity.setReputationMovingWt(repTotalScoreWt);
		trustworthinessEntity.setCalcTime(nowInHour);
		trustworthinessEntity.setSecurityScore(securityScore);
		trustworthinessEntity.setTrustworthinessScore(trustworthinessScore);

		// send alert if trustworthiness > allowed change before alert
		double scoreChange = Math.abs(trustworthinessScore - trustworthinessEntity.getLastAlertScore());
		if (scoreChange > config
				.getConfig().getDouble("trust_change_alert")) {

			trustworthinessEntity.setLastAlertScore(trustworthinessScore);

			Map<String, String> props = new HashMap<String, String>();
			props.put("service.id", serviceId);
			props.put("trustworthiness.score",
					Double.toString(trustworthinessScore));
			props.put("trustworthiness.confidence",
					Double.toString(qosConfidence));
			props.put("alert.type", "TRUST_LEVEL_CHANGE");

			// send alert if trustworthiness < threshold
			if (trustworthinessScore < config.getConfig().getDouble("trust_threshold")) {

				props.put("alert.description", "UNTRUSTED_ATOMIC_SERVICE");

				logger.debug("trustworthiness below threshold.");

			} else {
				logger.debug("trustworthiness above threshold.");

			}

			Event osgiEvent = new Event("eu/aniketos/trustworthiness/prediction/alert",
					props);
			eventAdmin.sendEvent(osgiEvent);

			logger.debug("trustworthiness change above alert level: " + scoreChange + ", sent an alert. ");

		} else {
			logger.debug("trustworthiness change below alert level: " + scoreChange);

		}

		logger.debug("updating service with results..");
		trustworthinessEntityService.updateTrustworthiness(trustworthinessEntity);

		return trustworthinessEntity;
	}

	private Map<String, Double> calcReputation(List<Rating> serviceScores) {

		Map<String, Double> reputation = new HashMap<String, Double>();

		double repScore = 0;

		double repConfidence = 0;

		/*
		 * TODO: check expiry and delete if expired
		 */

		double repTotalScoreWt = 0;

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
			repTotalScoreWt += scoreWt;
		}

		logger.debug("total score weight: " + repTotalScoreWt);

		/*
		 * calculation of score
		 */
		for (Rating serviceScore : serviceScores) {

			double scoreWt = 0;

			if (repTotalScoreWt > 0) {

				scoreWt = serviceScore.getScoreWt() / repTotalScoreWt;

			} else {
				logger.warn("total property weight=" + repTotalScoreWt);
			}

			/*
			 * allowed values: -1 < a < +1;
			 */
			if (logger.isDebugEnabled()) {
				logger.debug("scoreWt " + scoreWt);
			}

			serviceScore.setScoreWt(scoreWt);

			repScore += scoreWt * serviceScore.getScore();
		}

		/*
		 * calculation of confidence
		 */
		double nConfidence = 0;
		double dConfidence = 0;

		/*
		 * calculate confidence based on number of scores
		 */
		nConfidence = 1 - Math.pow(Math.E, -(alpha * repTotalScoreWt));

		if (logger.isDebugEnabled()) {
			logger.debug("serviceScores.size() " + serviceScores.size());
			// logger.debug("confidenceThreshold " + confidenceThreshold);
		}

		/*
		 * calculate confidence based on score deviation
		 */
		double repDeviation = 0;
		double totalWt = 0;

		for (Rating serviceScore : serviceScores) {
			/*
			 * calculate deviation
			 */
			double scoreWt = serviceScore.getScoreWt();
			repDeviation += scoreWt
					* Math.abs(repScore - serviceScore.getScore());
			totalWt += scoreWt;
		}

		double d = 0;
		if (totalWt > 0) {
			d = repDeviation / totalWt;
			dConfidence = 1 - d;
			if (logger.isDebugEnabled()) {
				logger.debug("dConfidence " + dConfidence);
				logger.debug("nConfidence " + nConfidence);
			}

		} else {
			logger.warn("total weight was 0 or less.");
		}

		repConfidence = dConfidence * nConfidence;

		BigDecimal scoreBD = new BigDecimal(String.valueOf(repScore)).setScale(
				3, BigDecimal.ROUND_HALF_UP);
		BigDecimal confidenceBD = new BigDecimal(String.valueOf(repConfidence))
				.setScale(3, BigDecimal.ROUND_HALF_UP);
		BigDecimal deviationBD = new BigDecimal(String.valueOf(repDeviation))
				.setScale(5, BigDecimal.ROUND_HALF_UP);
		BigDecimal nowInHourBD = new BigDecimal(String.valueOf(nowInHour))
				.setScale(3, BigDecimal.ROUND_HALF_UP);
		BigDecimal totalScoreWtBD = new BigDecimal(
				String.valueOf(repTotalScoreWt)).setScale(3,
				BigDecimal.ROUND_HALF_UP);

		repScore = Double.parseDouble(scoreBD.toString());
		repConfidence = Double.parseDouble(confidenceBD.toString());
		repDeviation = Double.parseDouble(deviationBD.toString());
		nowInHour = Double.parseDouble(nowInHourBD.toString());
		repTotalScoreWt = Double.parseDouble(totalScoreWtBD.toString());

		reputation.put("repScore", repScore);
		reputation.put("repConfidence", repConfidence);
		reputation.put("repTotalScoreWt", repTotalScoreWt);
		reputation.put("repDeviation", repDeviation);
		reputation.put("nowInHour", nowInHour);

		return reputation;
	}

	private Map<String, Double> calcQoS(List<QoSMetric> serviceScores) {

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

		for (QoSMetric serviceScore : serviceScores) {

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
		for (QoSMetric serviceScore : serviceScores) {

			double scoreWt = 0;

			if (totalScoreWt > 0) {

				scoreWt = serviceScore.getScoreWt() / totalScoreWt;

			} else {
				logger.warn("total property weight=" + totalScoreWt);
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

		for (QoSMetric serviceScore : serviceScores) {
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
			logger.warn("total weight was 0 or less.");
		}

		qosConfidence = dConfidence * nConfidence;

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

		qosScore = Double.parseDouble(scoreBD.toString());
		qosConfidence = Double.parseDouble(confidenceBD.toString());
		deviation = Double.parseDouble(deviationBD.toString());
		nowInHour = Double.parseDouble(nowInHourBD.toString());
		totalScoreWt = Double.parseDouble(totalScoreWtBD.toString());

		qos.put("qosScore", qosScore);
		qos.put("qosConfidence", qosConfidence);
		qos.put("qosTotalScoreWt", totalScoreWt);
		qos.put("qosDeviation", deviation);
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
	 * @return
	 */
	public TrustworthinessEntityService getTrustworthinessEntityService() {
		return trustworthinessEntityService;
	}

	/**
	 * @param trustworthinessEntityService
	 */
	public void setTrustworthinessEntityService(
			TrustworthinessEntityService trustworthinessEntityService) {
		this.trustworthinessEntityService = trustworthinessEntityService;
	}
	
	/**
	 * required for Spring dependency injection
	 * 
	 * @return
	 */
	public TrustFactory getTrustFactory() {
		return trustFactory;
	}

	/**
	 * required for Spring dependency injection
	 * 
	 * @param trustFactory
	 */
	public void setTrustFactory(TrustFactory trustFactory) {
		this.trustFactory = trustFactory;
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
	public QoSMetricEntityService getQosEntityService() {
		return qosEntityService;
	}

	/**
	 * @param qosEntityService
	 */
	public void setQosEntityService(QoSMetricEntityService qosEntityService) {
		this.qosEntityService = qosEntityService;
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
