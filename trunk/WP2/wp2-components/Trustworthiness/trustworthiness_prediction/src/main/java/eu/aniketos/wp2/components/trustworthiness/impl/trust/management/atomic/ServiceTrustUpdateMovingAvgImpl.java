package eu.aniketos.wp2.components.trustworthiness.impl.trust.management.atomic;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;

import eu.aniketos.wp2.components.trustworthiness.configuration.ConfigurationManagement;
import eu.aniketos.wp2.components.trustworthiness.trust.management.TrustFactory;
import eu.aniketos.wp2.components.trustworthiness.trust.management.atomic.ServiceTrustUpdatePolicy;
import eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo.Trustworthiness;
import eu.aniketos.wp2.components.trustworthiness.trust.service.QoSMetricEntityService;
import eu.aniketos.wp2.components.trustworthiness.trust.service.RatingEntityService;
import eu.aniketos.wp2.components.trustworthiness.trust.service.SecurityEntityService;
import eu.aniketos.wp2.components.trustworthiness.trust.service.ServiceEntityService;
import eu.aniketos.wp2.components.trustworthiness.trust.service.TrustworthinessEntityService;
import eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo.QoSMetric;
import eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo.Rating;
import eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo.SecProperty;

/**
 * updates trustworthiness of (atomic) services using moving average approach
 * 
 * @author Hisain Elshaafi
 * 
 */
public class ServiceTrustUpdateMovingAvgImpl implements
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

	Map<String, Double> propertiesMap = new HashMap<String, Double>();

	private double alpha = 1;

	/**
	 * 
	 */
	public void initialize() {

		logger.debug("ServiceTrustUpdatePolicyImpl");

		/*
		 * Set score decay constant from configuration TODO use drools for this
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
	 * method updates trustworthiness based on new rating score
	 */
	public Trustworthiness calculateTrust(Rating ratingScore) {

		String serviceId = ratingScore.getService().getId();

		if (serviceId == null) {
			logger.error("service not found.");
			return null;

		}

		if (logger.isInfoEnabled()) {
			logger.info("found service " + serviceId);
		}

		double securityScore = calcSecurityScore(serviceId);

		Map<String, Double> qos = updateQoS(serviceId);

		double qosScore = qos.get("qosScore");
		double qosConfidence = qos.get("qosConfidence");
		double qosDeviation = qos.get("qosDeviation");
		double nowInHour = qos.get("nowInHour");
		double qosTotalScoreWt = qos.get("qosTotalScoreWt");

		Map<String, Double> reputation = calcReputation(ratingScore);

		double repScore = reputation.get("repScore");
		double repConfidence = reputation.get("repConfidence");
		double repDeviation = reputation.get("repDeviation");
		// double nowInHour = qos.get("nowInHour");
		double repTotalScoreWt = reputation.get("repTotalScoreWt");

		// determine overall trustworthiness score
		// TODO: separate reputation from qos
		double trustworthinessScore = qosScore * securityScore;

		BigDecimal securityBD = new BigDecimal(String.valueOf(securityScore))
				.setScale(3, BigDecimal.ROUND_HALF_UP);
		BigDecimal trustworthinessBD = new BigDecimal(
				String.valueOf(trustworthinessScore)).setScale(3,
				BigDecimal.ROUND_HALF_UP);

		securityScore = Double.parseDouble(securityBD.toString());
		trustworthinessScore = Double.parseDouble(trustworthinessBD.toString());

		Trustworthiness trustworthiness = trustworthinessEntityService.getTrustworthiness(serviceId);
		if (trustworthiness == null) {
			trustworthiness = trustFactory.createTrustworthiness(serviceId);
		}

		trustworthiness.setId(serviceId);
		trustworthiness.setQosScore(qosScore);
		trustworthiness.setQosConfidence(qosConfidence);
		trustworthiness.setQosDeviation(qosDeviation);
		trustworthiness.setQosMovingWt(qosTotalScoreWt);
		trustworthiness.setReputationScore(repScore);
		trustworthiness.setReputationConfidence(repConfidence);
		trustworthiness.setReputationDeviation(repDeviation);
		trustworthiness.setReputationMovingWt(repTotalScoreWt);
		trustworthiness.setCalcTime(nowInHour);
		trustworthiness.setSecurityScore(securityScore);
		trustworthiness.setTrustworthinessScore(trustworthinessScore);
	

		// send alert if trustworthiness > allowed change before alert
		double scoreChange = Math.abs(trustworthinessScore
				- trustworthiness.getLastAlertScore());
		if (scoreChange > config.getConfig().getDouble("trust_change_alert")) {

			trustworthiness.setLastAlertScore(trustworthinessScore);

			Map<String, String> props = new HashMap<String, String>();
			props.put("service.id", serviceId);
			props.put("trustworthiness.score",
					Double.toString(trustworthinessScore));
			props.put("trustworthiness.confidence",
					Double.toString(qosConfidence));
			props.put("alert.type", "TRUST_LEVEL_CHANGE");

			// send alert if trustworthiness < threshold
			if (trustworthinessScore < config.getConfig().getDouble(
					"trust_threshold")) {

				props.put("alert.description", "UNTRUSTED_ATOMIC_SERVICE");

				logger.debug("trustworthiness below threshold.");

			} else {
				logger.debug("trustworthiness above threshold.");

			}

			Event osgiEvent = new Event("eu/aniketos/trustworthiness/prediction/alert",
					props);
			eventAdmin.sendEvent(osgiEvent);

			logger.debug("trustworthiness change above alert level: "
					+ scoreChange + ", sent an alert. ");

		} else {
			logger.debug("trustworthiness change below alert level: "
					+ scoreChange);

		}

		logger.debug("updating service with results..");
		trustworthinessEntityService.updateTrustworthiness(trustworthiness);

		return trustworthiness;
	}

	/**
	 * method updates trustworthiness based on new rating score
	 */
	public Trustworthiness calculateTrust(QoSMetric qosMetric) {

		String serviceId = qosMetric.getService().getId();

		if (serviceId == null) {
			logger.error("service not found.");
			return null;

		}

		if (logger.isInfoEnabled()) {
			logger.info("found service " + serviceId);
		}

		double securityScore = calcSecurityScore(serviceId);

		Map<String, Double> qos = calcQoS(qosMetric);

		double qosScore = qos.get("qosScore");
		double qosConfidence = qos.get("qosConfidence");
		double qosDeviation = qos.get("qosDeviation");
		double nowInHour = qos.get("nowInHour");
		double qosTotalScoreWt = qos.get("qosTotalScoreWt");

		Map<String, Double> reputation = updateReputation(serviceId);

		double repScore = reputation.get("repScore");
		double repConfidence = reputation.get("repConfidence");
		double repDeviation = reputation.get("repDeviation");
		// double nowInHour = qos.get("nowInHour");
		double repTotalScoreWt = reputation.get("repTotalScoreWt");

		// determine overall trustworthiness score
		double trustworthinessScore = ((qosWeight * qosScore + reputationWeight
				* repScore))
				* securityScore;

		BigDecimal securityBD = new BigDecimal(String.valueOf(securityScore))
				.setScale(3, BigDecimal.ROUND_HALF_UP);
		BigDecimal trustworthinessBD = new BigDecimal(
				String.valueOf(trustworthinessScore)).setScale(3,
				BigDecimal.ROUND_HALF_UP);

		securityScore = Double.parseDouble(securityBD.toString());
		trustworthinessScore = Double.parseDouble(trustworthinessBD.toString());


		Trustworthiness trustworthiness = trustworthinessEntityService.getTrustworthiness(serviceId);
		if (trustworthiness == null) {
			trustworthiness = trustFactory.createTrustworthiness(serviceId);
		}

		trustworthiness.setId(serviceId);
		trustworthiness.setQosScore(qosScore);
		trustworthiness.setQosConfidence(qosConfidence);
		trustworthiness.setQosDeviation(qosDeviation);
		trustworthiness.setQosMovingWt(qosTotalScoreWt);
		trustworthiness.setReputationScore(repScore);
		trustworthiness.setReputationConfidence(repConfidence);
		trustworthiness.setReputationDeviation(repDeviation);
		trustworthiness.setReputationMovingWt(repTotalScoreWt);
		trustworthiness.setCalcTime(nowInHour);
		trustworthiness.setSecurityScore(securityScore);
		trustworthiness.setTrustworthinessScore(trustworthinessScore);
	

		// send alert if trustworthiness > allowed change before alert
		double scoreChange = Math.abs(trustworthinessScore
				- trustworthiness.getLastAlertScore());
		if (scoreChange > config.getConfig().getDouble("trust_change_alert")) {

			trustworthiness.setLastAlertScore(trustworthinessScore);

			Map<String, String> props = new HashMap<String, String>();
			props.put("service.id", serviceId);
			props.put("trustworthiness.score",
					Double.toString(trustworthinessScore));
			props.put("trustworthiness.confidence",
					Double.toString(qosConfidence));
			props.put("alert.type", "TRUST_LEVEL_CHANGE");

			// send alert if trustworthiness < threshold
			if (trustworthinessScore < config.getConfig().getDouble(
					"trust_threshold")) {

				props.put("alert.description", "UNTRUSTED_ATOMIC_SERVICE");

				logger.debug("trustworthiness below threshold.");

			} else {
				logger.debug("trustworthiness above threshold.");

			}

			Event osgiEvent = new Event("eu/aniketos/trustworthiness/prediction/alert",
					props);
			eventAdmin.sendEvent(osgiEvent);

			logger.debug("trustworthiness change above alert level: "
					+ scoreChange + ", sent an alert. ");

		} else {
			logger.debug("trustworthiness change below alert level: "
					+ scoreChange);

		}

		logger.debug("updating service with results..");
		trustworthinessEntityService.updateTrustworthiness(trustworthiness);

		return trustworthiness;
	}

	private Map<String, Double> calcQoS(QoSMetric ratingScore) {

		Map<String, Double> qos = new HashMap<String, Double>();

		String serviceId = ratingScore.getService().getId();

		Trustworthiness trustworthiness = trustworthinessEntityService.getTrustworthiness(serviceId);

		double qosScore = 0;

		double qosConfidence = 0;

		double savedTw = trustworthiness.getQosScore();

		double calcTime = trustworthiness.getCalcTime();

		double savedTwWt = trustworthiness.getQosMovingWt();

		/* current time in hrs */
		int hourMsecs = 3600000;

		double now = System.currentTimeMillis();

		double nowInHour = now / (double) hourMsecs;

		double scoreAge = nowInHour - calcTime;

		if (logger.isDebugEnabled()) {
			logger.debug("score age " + scoreAge);
		}

		/*
				 * 
				 */
		double recencyWt = Math.pow(Math.E, -scoreAge * decayConstant);

		if (logger.isDebugEnabled()) {
			logger.debug("recencyWt " + recencyWt);
		}

		/*
		 * identify all categories
		 */
		String property = ratingScore.getProperty();

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

		double qosTotalScoreWt = savedTwWt + scoreWt;

		if (logger.isDebugEnabled()) {
			logger.debug("scoreWt " + scoreWt);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("scoreWt " + scoreWt);
		}

		qosScore = (savedTw * savedTwWt + scoreWt * ratingScore.getScore())
				/ qosTotalScoreWt;

		trustworthiness.setTrustworthinessScore(qosScore);

		/*
		 * calculation of confidence
		 */
		double nConfidence = 0;
		double dConfidence = 0;

		/*
		 * calculate confidence based on number of scores
		 */
		nConfidence = 1 - Math.pow(Math.E, -(alpha * qosTotalScoreWt));

		/*
		 * calculate confidence based on score deviation
		 */
		double deviation = 0;

		/*
		 * calculate deviation
		 */
		deviation = ((qosTotalScoreWt - scoreWt) * savedTw)
				+ (scoreWt * Math.abs(qosScore - ratingScore.getScore()))
				/ qosTotalScoreWt;

		dConfidence = 1 - deviation;
		if (logger.isDebugEnabled()) {
			logger.debug("dConfidence " + dConfidence);
			logger.debug("nConfidence " + nConfidence);

			qosConfidence = dConfidence * nConfidence;

		} else {
			if (logger.isInfoEnabled()) {
				logger.info("service " + trustworthiness + " not found");
			}
			/*
			 * no previous history, get default score.
			 */
			qosScore = defaultScore;
			qosConfidence = 0;

		}

		calcTime = nowInHour;

		BigDecimal scoreBD = new BigDecimal(String.valueOf(qosScore)).setScale(
				3, BigDecimal.ROUND_HALF_UP);
		BigDecimal confidenceBD = new BigDecimal(String.valueOf(qosConfidence))
				.setScale(3, BigDecimal.ROUND_HALF_UP);
		BigDecimal deviationBD = new BigDecimal(String.valueOf(deviation))
				.setScale(5, BigDecimal.ROUND_HALF_UP);
		BigDecimal nowInHourBD = new BigDecimal(String.valueOf(nowInHour))
				.setScale(3, BigDecimal.ROUND_HALF_UP);
		BigDecimal totalScoreWtBD = new BigDecimal(
				String.valueOf(qosTotalScoreWt)).setScale(3,
				BigDecimal.ROUND_HALF_UP);

		qosScore = Double.parseDouble(scoreBD.toString());
		qosConfidence = Double.parseDouble(confidenceBD.toString());
		deviation = Double.parseDouble(deviationBD.toString());
		nowInHour = Double.parseDouble(nowInHourBD.toString());
		qosTotalScoreWt = Double.parseDouble(totalScoreWtBD.toString());

		qos.put("qosScore", qosScore);
		qos.put("qosConfidence", qosConfidence);
		qos.put("qosTotalScoreWt", qosTotalScoreWt);
		qos.put("qosDeviation", deviation);
		qos.put("nowInHour", nowInHour);

		return qos;
	}

	private Map<String, Double> calcReputation(Rating ratingScore) {

		Map<String, Double> reputation = new HashMap<String, Double>();

		String serviceId = ratingScore.getService().getId();

		Trustworthiness trustworthiness = trustworthinessEntityService.getTrustworthiness(serviceId);

		double repScore = 0;

		double repConfidence = 0;

		double savedTw = trustworthiness.getTrustworthinessScore();

		double calcTime = trustworthiness.getCalcTime();

		double repDeviation = trustworthiness.getReputationDeviation();

		double savedTwWt = trustworthiness.getReputationMovingWt();

		/* current time in hrs */
		int hourMsecs = 3600000;

		double now = System.currentTimeMillis();

		double nowInHour = now / (double) hourMsecs;

		double scoreAge = nowInHour - calcTime;

		if (logger.isDebugEnabled()) {
			logger.debug("score age " + scoreAge);
		}

		/*
				 * 
				 */
		double recencyWt = Math.pow(Math.E, -scoreAge * decayConstant);

		if (logger.isDebugEnabled()) {
			logger.debug("recencyWt " + recencyWt);
		}

		/*
		 * identify all categories
		 */
		String property = ratingScore.getProperty();

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

		double repTotalScoreWt = savedTwWt + scoreWt;

		if (logger.isDebugEnabled()) {
			logger.debug("scoreWt " + scoreWt);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("scoreWt " + scoreWt);
		}

		repScore = (savedTw * savedTwWt + scoreWt * ratingScore.getScore())
				/ repTotalScoreWt;

		trustworthiness.setTrustworthinessScore(repScore);

		/*
		 * calculation of confidence
		 */
		double nConfidence = 0;
		double dConfidence = 0;

		/*
		 * calculate confidence based on number of scores
		 */
		nConfidence = 1 - Math.pow(Math.E, -(alpha * repTotalScoreWt));

		/*
		 * calculate confidence based on score deviation
		 */
		double deviation = 0;

		/*
		 * calculate deviation
		 */
		deviation = ((repTotalScoreWt - scoreWt) * savedTw)
				+ (scoreWt * Math.abs(repScore - ratingScore.getScore()))
				/ repTotalScoreWt;

		trustworthiness.setReputationDeviation(deviation);

		dConfidence = 1 - deviation;
		if (logger.isDebugEnabled()) {
			logger.debug("dConfidence " + dConfidence);
			logger.debug("nConfidence " + nConfidence);

			repConfidence = dConfidence * nConfidence;

		} else {
			if (logger.isInfoEnabled()) {
				logger.info("service " + trustworthiness + " not found");
			}
			/*
			 * no previous history, get default score.
			 */
			repScore = defaultScore;
			repConfidence = 0;

		}

		calcTime = nowInHour;

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

	/*
	 * (non-Javadoc)
	 * 
	 * @see eu.aniketos.wp2.components.trustworthiness.trust.management.atomic.
	 * ServiceTrustUpdatePolicy#calculateTrust(java.lang.String)
	 */
	public Trustworthiness updateTrust(String serviceId) {

		Map<String, Double> qos = updateQoS(serviceId);
		Map<String, Double> reputation = updateReputation(serviceId);

		double securityScore = calcSecurityScore(serviceId);

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
		double trustworthinessScore = ((qosWeight * qosScore + reputationWeight
				* repScore))
				* securityScore;

		BigDecimal securityBD = new BigDecimal(String.valueOf(securityScore))
				.setScale(3, BigDecimal.ROUND_HALF_UP);
		BigDecimal trustworthinessBD = new BigDecimal(
				String.valueOf(trustworthinessScore)).setScale(3,
				BigDecimal.ROUND_HALF_UP);

		securityScore = Double.parseDouble(securityBD.toString());
		trustworthinessScore = Double.parseDouble(trustworthinessBD.toString());

		Trustworthiness trustworthiness = trustworthinessEntityService.getTrustworthiness(serviceId);
		if (trustworthiness == null) {
			trustworthiness = trustFactory.createTrustworthiness(serviceId);
		}

		trustworthiness.setId(serviceId);
		trustworthiness.setQosScore(qosScore);
		trustworthiness.setQosConfidence(qosConfidence);
		trustworthiness.setQosDeviation(qosDeviation);
		trustworthiness.setQosMovingWt(qosTotalScoreWt);
		trustworthiness.setReputationScore(repScore);
		trustworthiness.setReputationConfidence(repConfidence);
		trustworthiness.setReputationDeviation(repDeviation);
		trustworthiness.setReputationMovingWt(repTotalScoreWt);
		trustworthiness.setCalcTime(nowInHour);
		trustworthiness.setSecurityScore(securityScore);
		trustworthiness.setTrustworthinessScore(trustworthinessScore);
	

		// send alert if trustworthiness > allowed change before alert
		double scoreChange = Math.abs(trustworthinessScore
				- trustworthiness.getLastAlertScore());
		if (scoreChange > config.getConfig().getDouble("trust_change_alert")) {

			trustworthiness.setLastAlertScore(trustworthinessScore);

			Map<String, String> props = new HashMap<String, String>();
			props.put("service.id", serviceId);
			props.put("trustworthiness.score",
					Double.toString(trustworthinessScore));
			props.put("trustworthiness.confidence",
					Double.toString(qosConfidence));
			props.put("alert.type", "TRUST_LEVEL_CHANGE");

			// send alert if trustworthiness < threshold
			if (trustworthinessScore < config.getConfig().getDouble(
					"trust_threshold")) {

				props.put("alert.description", "UNTRUSTED_ATOMIC_SERVICE");

				logger.debug("trustworthiness below threshold.");

			} else {
				logger.debug("trustworthiness above threshold.");

			}

			Event osgiEvent = new Event("eu/aniketos/trustworthiness/prediction/alert",
					props);
			eventAdmin.sendEvent(osgiEvent);

			logger.debug("trustworthiness change above alert level: "
					+ scoreChange + ", sent an alert. ");

		} else {
			logger.debug("trustworthiness change below alert level: "
					+ scoreChange);

		}

		logger.debug("updating service with results..");
		trustworthinessEntityService.updateTrustworthiness(trustworthiness);

		return trustworthiness;
	}

	private double calcSecurityScore(String serviceId) {
		// TODO support security update similar to moving avg in qos
		double securityScore = 0;
		double totSecurity = 0;

		// determine security score
		List<SecProperty> serviceSecProps = null;
		serviceSecProps = securityEntityService
				.getSecPropertiesByServiceId(serviceId);

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

	private Map<String, Double> updateQoS(String serviceId) {

		Map<String, Double> qos = new HashMap<String, Double>();

		double qosScore = 0;
		double qosConfidence = 0;

		Trustworthiness trustworthiness = trustworthinessEntityService.getTrustworthiness(serviceId);

		if (trustworthiness != null) {

			logger.info("found service " + trustworthiness.getId());
		}

		double savedTw = trustworthiness.getQosScore();
		double calcTime = trustworthiness.getCalcTime();
		double savedQosDeviation = trustworthiness.getQosDeviation();
		double savedTwWt = trustworthiness.getQosMovingWt();

		/* current time in hrs */
		int hourMsecs = 3600000;
		double now = System.currentTimeMillis();
		double nowInHour = now / (double) hourMsecs;

		/*
		 * if (service == null) { logger.warn("source = null"); return; }
		 */

		double scoreAge = nowInHour - calcTime;

		if (logger.isDebugEnabled()) {
			logger.debug("score age " + scoreAge);
		}

		/*
				 * 
				 */
		double recencyWt = Math.pow(Math.E, -scoreAge * decayConstant);

		if (logger.isDebugEnabled()) {
			logger.debug("recencyWt " + recencyWt);
		}

		double scoreWt = recencyWt;

		double totalScoreWt = savedTwWt + scoreWt;

		if (logger.isDebugEnabled()) {
			logger.debug("scoreWt " + scoreWt);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("scoreWt " + scoreWt);
		}

		qosScore = (savedTw * savedTwWt) / totalScoreWt;

		trustworthiness.setTrustworthinessScore(qosScore);

		/*
		 * calculation of confidence
		 */
		double nConfidence = 0;
		double dConfidence = 0;

		/*
		 * calculate confidence based on number of scores
		 */
		nConfidence = 1 - Math.pow(Math.E, -(alpha * totalScoreWt));

		/*
		 * calculate confidence based on score deviation
		 */
		double deviation = 0;

		/*
		 * calculate deviation
		 */
		deviation = savedQosDeviation;

		trustworthiness.setQosDeviation(deviation);

		dConfidence = 1 - deviation;
		if (logger.isDebugEnabled()) {
			logger.debug("dConfidence " + dConfidence);
			logger.debug("nConfidence " + nConfidence);

			qosConfidence = dConfidence * nConfidence;

		} else {
			if (logger.isInfoEnabled()) {
				logger.info("service " + trustworthiness + " not found");
			}
			/*
			 * no previous history, get default score.
			 */
			qosScore = defaultScore;
			qosConfidence = 0;

		}

		calcTime = nowInHour;

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

	private Map<String, Double> updateReputation(String serviceId) {

		Map<String, Double> reputation = new HashMap<String, Double>();

		double repScore = 0;

		double repConfidence = 0;

		Trustworthiness trustworthiness = trustworthinessEntityService.getTrustworthiness(serviceId);

		if (trustworthiness != null) {

			logger.info("found service " + trustworthiness.getId());
		}

		double savedRepScore = trustworthiness.getReputationScore();
		double calcTime = trustworthiness.getCalcTime();
		double savedRepDeviation = trustworthiness.getReputationDeviation();
		double savedTwWt = trustworthiness.getReputationMovingWt();

		/* current time in hrs */
		int hourMsecs = 3600000;
		double now = System.currentTimeMillis();
		double nowInHour = now / (double) hourMsecs;

		/*
		 * if (service == null) { logger.warn("source = null"); return; }
		 */

		double scoreAge = nowInHour - calcTime;

		if (logger.isDebugEnabled()) {
			logger.debug("score age " + scoreAge);
		}

		/*
				 * 
				 */
		double recencyWt = Math.pow(Math.E, -scoreAge * decayConstant);

		if (logger.isDebugEnabled()) {
			logger.debug("recencyWt " + recencyWt);
		}

		double scoreWt = recencyWt;

		double repTotalScoreWt = savedTwWt + scoreWt;

		if (logger.isDebugEnabled()) {
			logger.debug("scoreWt " + scoreWt);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("scoreWt " + scoreWt);
		}

		repScore = (savedRepScore * savedTwWt) / repTotalScoreWt;

		/*
		 * calculation of confidence
		 */
		double nConfidence = 0;
		double dConfidence = 0;

		/*
		 * calculate confidence based on number of scores
		 */
		nConfidence = 1 - Math.pow(Math.E, -(alpha * repTotalScoreWt));

		/*
		 * calculate confidence based on score deviation
		 */
		double repDeviation = 0;

		/*
		 * calculate deviation
		 */
		repDeviation = savedRepDeviation;

		trustworthiness.setQosDeviation(repDeviation);

		dConfidence = 1 - repDeviation;
		if (logger.isDebugEnabled()) {
			logger.debug("dConfidence " + dConfidence);
			logger.debug("nConfidence " + nConfidence);

			repConfidence = dConfidence * nConfidence;

		} else {
			if (logger.isInfoEnabled()) {
				logger.info("service " + trustworthiness + " not found");
			}
			/*
			 * no previous history, get default score.
			 */
			repScore = defaultScore;
			repConfidence = 0;

		}

		calcTime = nowInHour;

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
	 * required for Spring dependency injection
	 * 
	 * @return QoSMetricEntityService object
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
	 * required for Spring dependency injection
	 * 
	 * @return SecurityEntityService object
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
