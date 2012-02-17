package eu.aniketos.wp2.components.trustworthiness.impl.trust.management.atomic;

import java.text.DecimalFormat;
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
 * 
 * 
 * @author Hisain Elshaafi
 * 
 */
public class ServiceTrustUpdateMovingAvgImpl implements
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

	private double savedTw = 0;

	private double savedTwWt = 0;

	private double savedDeviation = 0;

	private double calcTime = 0;

	// private long confidenceThreshold;

	Map<String, Double> propertiesMap = new HashMap<String, Double>();

	private double alpha = 1;

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
	 * 
	 */
	public Trustworthiness calculateTrust(Score ratingScore) {

		double twScore = 0;
		double twConfidence = 0;

		// needs testing!
		Atomic service = (Atomic) ratingScore.getService();

		if (service != null) {

			logger.info("found service " + service.getId());
		}

		savedTw = service.getTrustScore();
		calcTime = service.getCalcTime();
		savedDeviation = service.getDeviation();
		savedTwWt = service.getMovingWt();

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

		double twWt = savedTwWt + scoreWt;

		if (logger.isDebugEnabled()) {
			logger.debug("scoreWt " + scoreWt);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("scoreWt " + scoreWt);
		}

		twScore = (savedTwWt * savedTwWt + scoreWt * ratingScore.getScore())
				/ twWt;

		service.setTrustScore(twScore);

		/*
		 * calculation of confidence
		 */
		double nConfidence = 0;
		double dConfidence = 0;

		/*
		 * calculate confidence based on number of scores
		 */
		nConfidence = 1 - Math.pow(Math.E, -(alpha * twWt));

		/*
		 * calculate confidence based on score deviation
		 */
		double deviation = 0;

		/*
		 * calculate deviation
		 */
		deviation = ((twWt - scoreWt) * savedTw)
				+ (scoreWt * Math.abs(twScore - ratingScore.getScore())) / twWt;

		service.setDeviation(deviation);

		dConfidence = 1 - deviation;
		if (logger.isDebugEnabled()) {
			logger.debug("dConfidence " + dConfidence);
			logger.debug("nConfidence " + nConfidence);

			twConfidence = dConfidence * nConfidence;

		} else {
			if (logger.isInfoEnabled()) {
				logger.info("service " + service + " not found");
			}
			/*
			 * no previous history, get default score.
			 */
			twScore = defaultScore;
			twConfidence = 0;

		}

		service.setConfidence(twConfidence);

		calcTime = nowInHour;
		service.setCalcTime(calcTime);

		service.setMovingWt(twWt);

		logger.debug("updating service with moving average values..");
		serviceEntityService.updateAtomic(service);

		DecimalFormat df = new DecimalFormat("#.###");
		twScore = Double.parseDouble(df.format(twScore));
		twConfidence = Double.parseDouble(df.format(twConfidence));
		Trustworthiness trust = new ServiceTrustworthiness(service.getId(),
				twScore, twConfidence);

		// send alert if trustworthiness < alert threshold
		if (twScore < config.getConfig().getDouble("alert_threshold")) {
			Dictionary props = new Properties();
			props.put("service.id", service.getId());
			props.put("trustworthiness.score", Double.toString(twScore));
			props.put("trustworthiness.confidence", Double.toString(twConfidence));

			Event osgiEvent = new Event("eu/aniketos/trustworthiness/alert",
					props);
			eventAdmin.sendEvent(osgiEvent);
		}

		return trust;
	}

	public Trustworthiness calculateTrust(String serviceId) {
		double twScore = 0;
		double twConfidence = 0;

		Atomic service = serviceEntityService.getAtomic(serviceId);

		if (service != null) {

			logger.info("found service " + service.getId());
		}

		savedTw = service.getTrustScore();
		calcTime = service.getCalcTime();
		savedDeviation = service.getDeviation();
		savedTwWt = service.getMovingWt();

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

		double twWt = savedTwWt + scoreWt;

		if (logger.isDebugEnabled()) {
			logger.debug("scoreWt " + scoreWt);
		}

		if (logger.isDebugEnabled()) {
			logger.debug("scoreWt " + scoreWt);
		}

		twScore = (savedTwWt * savedTwWt) / twWt;

		service.setTrustScore(twScore);

		/*
		 * calculation of confidence
		 */
		double nConfidence = 0;
		double dConfidence = 0;

		/*
		 * calculate confidence based on number of scores
		 */
		nConfidence = 1 - Math.pow(Math.E, -(alpha * twWt));

		/*
		 * calculate confidence based on score deviation
		 */
		double deviation = 0;

		/*
		 * calculate deviation
		 */
		deviation = savedDeviation;

		service.setDeviation(deviation);

		dConfidence = 1 - deviation;
		if (logger.isDebugEnabled()) {
			logger.debug("dConfidence " + dConfidence);
			logger.debug("nConfidence " + nConfidence);

			twConfidence = dConfidence * nConfidence;

		} else {
			if (logger.isInfoEnabled()) {
				logger.info("service " + service + " not found");
			}
			/*
			 * no previous history, get default score.
			 */
			twScore = defaultScore;
			twConfidence = 0;

		}

		service.setConfidence(twConfidence);

		calcTime = nowInHour;
		service.setCalcTime(calcTime);

		service.setMovingWt(twWt);

		logger.debug("updating service with moving average values..");
		serviceEntityService.updateAtomic(service);

		DecimalFormat df = new DecimalFormat("#.###");
		twScore = Double.parseDouble(df.format(twScore));
		twConfidence = Double.parseDouble(df.format(twConfidence));
		Trustworthiness tw = new ServiceTrustworthiness(service.getId(),
				twScore, twConfidence);

		// send alert if trustworthiness < alert threshold
		if (twScore < config.getConfig().getDouble("alert_threshold")) {
			Dictionary props = new Properties();
			props.put("service.id", serviceId);
			props.put("trustworthiness.score", Double.toString(twScore));
			props.put("trustworthiness.confidence", Double.toString(twConfidence));

			Event osgiEvent = new Event("eu/aniketos/trustworthiness/alert",
					props);
			eventAdmin.sendEvent(osgiEvent);
		}

		return tw;
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
