package eu.aniketos.wp2.components.trustworthiness.impl.trust.management.atomic;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

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
	public Trustworthiness calculateTrust(Score ratingScore) {

		double twScore = 0;
		double twConfidence = 0;
		
		Atomic service = (Atomic) ratingScore.getService();

		if (service == null) {
			logger.error("service not found.");
			return null;

		}
		
		String serviceId = service.getId();
		
		if (logger.isInfoEnabled()) {
			logger.info("found service " + serviceId);
		}

		

		savedTw = service.getTrustScore();
		calcTime = service.getCalcTime();
		savedDeviation = service.getDeviation();
		savedTwWt = service.getMovingWt();

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

		BigDecimal scoreBD = new BigDecimal(String.valueOf(twScore)).setScale(
				3, BigDecimal.ROUND_HALF_UP);
		BigDecimal confidenceBD = new BigDecimal(String.valueOf(twConfidence))
				.setScale(3, BigDecimal.ROUND_HALF_UP);

		twScore = Double.parseDouble(scoreBD.toString());
		twConfidence = Double.parseDouble(confidenceBD.toString());
		Trustworthiness trust = new ServiceTrustworthiness(serviceId,
				twScore, twConfidence);

		// send alert if trustworthiness < alert threshold
		if (twScore < config.getConfig().getDouble("alert_threshold")) {
			Map<String, String> props = new HashMap<String, String>();
			props.put("service.id", serviceId);
			props.put("trustworthiness.score", Double.toString(twScore));
			props.put("trustworthiness.confidence",
					Double.toString(twConfidence));

			Event osgiEvent = new Event("eu/aniketos/trustworthiness/alert",
					props);
			eventAdmin.sendEvent(osgiEvent);
		}

		return trust;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see eu.aniketos.wp2.components.trustworthiness.trust.management.atomic.
	 * ServiceTrustUpdatePolicy#calculateTrust(java.lang.String)
	 */
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

		BigDecimal scoreBD = new BigDecimal(String.valueOf(twScore)).setScale(
				3, BigDecimal.ROUND_HALF_UP);
		BigDecimal confidenceBD = new BigDecimal(String.valueOf(twConfidence))
				.setScale(3, BigDecimal.ROUND_HALF_UP);

		twScore = Double.parseDouble(scoreBD.toString());
		twConfidence = Double.parseDouble(confidenceBD.toString());

		Trustworthiness tw = new ServiceTrustworthiness(service.getId(),
				twScore, twConfidence);

		// send alert if trustworthiness < alert threshold
		if (twScore < config.getConfig().getDouble("alert_threshold")) {
			Map<String, String> props = new HashMap<String, String>();
			props.put("service.id", serviceId);
			props.put("trustworthiness.score", Double.toString(twScore));
			props.put("trustworthiness.confidence",
					Double.toString(twConfidence));

			Event osgiEvent = new Event("eu/aniketos/trustworthiness/alert",
					props);
			eventAdmin.sendEvent(osgiEvent);
			logger.debug("trustworthiness below threshold, sent an alert.");
		} else {
			logger.debug("trustworthiness above threshold.");
		}

		return tw;
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
	public ScoreEntityService getScoreEntityService() {
		return scoreEntityService;
	}

	/**
	 * required for Spring dependency injection
	 * 
	 * @param scoreEntityService
	 *            data access service for rating scores
	 */
	public void setScoreEntityService(ScoreEntityService scoreEntityService) {
		this.scoreEntityService = scoreEntityService;
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
