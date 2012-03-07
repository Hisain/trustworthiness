package eu.aniketos.wp2.components.trustworthiness.impl.trust.management.atomic;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.apache.log4j.Logger;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;

import eu.aniketos.wp2.components.trustworthiness.configuration.ConfigurationManagement;
import eu.aniketos.wp2.components.trustworthiness.trust.management.atomic.ServiceTrustUpdatePolicy;
import eu.aniketos.wp2.components.trustworthiness.trust.management.atomic.Trustworthiness;
import eu.aniketos.wp2.components.trustworthiness.trust.service.ScoreEntityService;
import eu.aniketos.wp2.components.trustworthiness.trust.service.ServiceEntityService;
import eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo.Score;
import eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo.Atomic;

/**
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

	private ScoreEntityService scoreEntityService;

	private ConfigurationManagement config;

	private EventAdmin eventAdmin;

	/* set default score (0.0) if not configured */
	private double defaultScore = 0.0;

	/* default tw level decay constant */
	private static double decayConstant = 0.25;

	// private long confidenceThreshold;

	Map<String, Double> propertiesMap = new HashMap<String, Double>();

	private double alpha = 1;

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
	public Trustworthiness calculateTrust(Score ratingScore) throws Exception {

		Trustworthiness trust = null;

		Atomic service = (Atomic) ratingScore.getService();
		if (service != null) {

			logger.info("found service " + service.getId());

			trust = calculateTrust(service.getId());

		} else {
			if (logger.isInfoEnabled()) {
				logger.info("service " + service + " not found");
			}

		}

		return trust;
	}

	public Trustworthiness calculateTrust(String serviceId) throws Exception {
		double score = 0;

		double confidence = 0;

		List<Score> serviceScores = null;

		/* current time in hrs */
		int hourMsecs = 3600000;
		double now = System.currentTimeMillis();
		double nowInHour = now / (double) hourMsecs;

		/*
		 * if (service == null) { logger.warn("source = null"); return; }
		 */

		logger.debug("try retrieve previous score");

		Atomic service = serviceEntityService.getAtomic(serviceId);
		if (service == null) {

			if (logger.isInfoEnabled()) {
				logger.info("service " + service + " not found");
			}
			throw new Exception("no record of service " + serviceId);

		}

		logger.info("found service " + service.getId());

		serviceScores = scoreEntityService
				.getScoresByServiceId(service.getId());

		if (serviceScores == null || serviceScores.size() == 0) {

			if (logger.isInfoEnabled()) {
				logger.info("no recorded ratings for service " + serviceId);
			}
			throw new Exception("no recorded ratings for service " + serviceId);

		}

		/*
		 * TODO: check expiry and delete if expired
		 */

		double totalScoreWt = 0;

		for (Score serviceScore : serviceScores) {

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
		for (Score serviceScore : serviceScores) {

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

			score += scoreWt * serviceScore.getScore();
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

		for (Score serviceScore : serviceScores) {
			/*
			 * calculate deviation
			 */
			double scoreWt = serviceScore.getScoreWt();
			deviation += scoreWt * Math.abs(score - serviceScore.getScore());
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

		confidence = dConfidence * nConfidence;

		if (logger.isInfoEnabled()) {
			logger.info("trustworthiness for " + service + " " + score);
		}

		BigDecimal scoreBD = new BigDecimal(String.valueOf(score)).setScale(3,
				BigDecimal.ROUND_HALF_UP);
		BigDecimal confidenceBD = new BigDecimal(String.valueOf(confidence))
				.setScale(3, BigDecimal.ROUND_HALF_UP);

		score = Double.parseDouble(scoreBD.toString());
		confidence = Double.parseDouble(confidenceBD.toString());

		Trustworthiness trust = new ServiceTrustworthiness(service.getId(),
				score, confidence);

		service.setTrustScore(score);
		service.setDeviation(d);
		service.setConfidence(confidence);
		service.setCalcTime(nowInHour);
		service.setMovingWt(totalScoreWt);

		serviceEntityService.updateAtomic(service);

		// send alert if trustworthiness < alert threshold
		if (score < config.getConfig().getDouble("alert_threshold")) {
			Dictionary props = new Properties();
			props.put("service.id", serviceId);
			props.put("trustworthiness.score", Double.toString(score));
			props.put("trustworthiness.confidence", Double.toString(confidence));

			Event osgiEvent = new Event("eu/aniketos/trustworthiness/alert",
					props);
			eventAdmin.sendEvent(osgiEvent);
			
			logger.debug("trustworthiness below threshold, sent an alert.");
		} else {
			logger.debug("trustworthiness above threshold.");

		}

		return trust;
	}

	public ServiceEntityService getServiceEntityService() {
		return serviceEntityService;
	}

	public void setServiceEntityService(
			ServiceEntityService serviceEntityService) {
		this.serviceEntityService = serviceEntityService;
	}

	public ScoreEntityService getScoreEntityService() {
		return scoreEntityService;
	}

	public void setScoreEntityService(ScoreEntityService scoreEntityService) {
		this.scoreEntityService = scoreEntityService;
	}

	public ConfigurationManagement getConfig() {
		return config;
	}

	public void setConfig(ConfigurationManagement config) {
		this.config = config;
	}

	public EventAdmin getEventAdmin() {
		return eventAdmin;
	}

	public void setEventAdmin(EventAdmin eventAdmin) {
		this.eventAdmin = eventAdmin;
	}

}
