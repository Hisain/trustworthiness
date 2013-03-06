package eu.aniketos.wp2.components.trustworthiness.impl.rules.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Observable;
import java.util.Observer;
import java.util.Properties;

import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;

import org.apache.commons.configuration.ConversionException;
import org.apache.log4j.Logger;
import eu.aniketos.wp2.components.trustworthiness.configuration.ConfigurationManagement;
import eu.aniketos.wp2.components.trustworthiness.impl.rules.model.event.AlertEventImpl;
import eu.aniketos.wp2.components.trustworthiness.impl.rules.model.event.RuleMetricEventImpl;
import eu.aniketos.wp2.components.trustworthiness.rules.model.event.TrustEvent;
import eu.aniketos.wp2.components.trustworthiness.rules.service.RuleExecuter;
import eu.aniketos.wp2.components.trustworthiness.rules.service.RatingUpdate;
import eu.aniketos.wp2.components.trustworthiness.trust.management.TrustFactory;
import eu.aniketos.wp2.components.trustworthiness.trust.service.RatingEntityService;
import eu.aniketos.wp2.components.trustworthiness.trust.service.ServiceEntityService;
import eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo.Atomic;
import eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo.Rating;

/**
 * @author Hisain Elshaafi (TSSG)
 * 
 */
public class ReputationRatingUpdateImpl extends Observable implements RatingUpdate {

	private static Logger logger = Logger.getLogger(ReputationRatingUpdateImpl.class);

	private ConfigurationManagement config;

	private ServiceEntityService serviceEntityService;

	private RatingEntityService ratingEntityService;

	private TrustFactory trustFactory;

	private RuleExecuter ruleExecuter;

	private EventAdmin eventAdmin;

	int maxDescriptionSize = 0;

	/**
	 * 
	 */
	public void initialize() {
		if (config.getConfig().containsKey("max_event_description_size")) {
			try {
				maxDescriptionSize = config.getConfig().getInt(
						"max_event_description_size");
			} catch (ConversionException ce) {
				logger.error("max_event_description_size conversion exception: "
						+ ce.getMessage());
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * eu.aniketos.wp2.components.trustworthiness.rules.service.RatingUpdate
	 * #updateScore(java.util.Map)
	 */
	public void updateScore(Map<String, String> event) throws Exception {

		String serviceId = event.get("serviceId");
		Atomic service = null;
		if ((service = serviceEntityService.getAtomic(serviceId)) == null) {
			logger.info("creating new service entry");
			service = trustFactory.createService(serviceId);

			serviceEntityService.addAtomic(service);
		}

		if (!event.containsKey("property")
				|| (event.containsKey("subproperty") && event
						.get("subproperty") == null)
				|| !event.containsKey("type") || event.get("property") == null
				|| event.get("type") == null) {

			logger.warn("metric did not contain required event elements.");
			logger.warn("message will be ignored.");
			throw new Exception(
					"metric did not contain required event elements. message will be ignored.");

		}

		/*
		 * TODO: organise with type of metric, content, names, etc
		 */

		String property = event.get("property");
		String propertySub = property;
		String subproperty = null;
		if (event.containsKey("subproperty")) {
			subproperty = event.get("subproperty");
			propertySub = property + "." + subproperty;
			if (logger.isDebugEnabled()) {
				logger.debug(property);
			}
		}

		String eventValue = event.get("value");

		if (eventValue == null) {
			throw new Exception(
					"metric did not contain required event elements. message will be ignored.");
		}

		String eventTimestamp = event.get("timestamp");
		String timestamp = null;
		if (eventTimestamp != null) {

			DateTimeFormatter fmt = ISODateTimeFormat.dateTimeNoMillis();

			try {
				timestamp = Long
						.toString(fmt.parseMillis(eventTimestamp) / 3600000);

			} catch (IllegalArgumentException iae) {

				logger.error("date format parse exception: "
						+ iae.getStackTrace());
			}

			if (logger.isDebugEnabled()) {
				logger.debug(eventTimestamp + " is converted to " + timestamp);
			}

		}

		// no timestamp or not valid text
		if (timestamp == null) {
			timestamp = Long.toString(System.currentTimeMillis() / 3600000);
		}

		String contractValue = config.getConfig().getString(
				propertySub + "[@value]");
		String type = config.getConfig().getString(propertySub + "[@type]");
		String limit = config.getConfig().getString(propertySub + "[@limit]");

		if (logger.isDebugEnabled()) {
			logger.debug("property=" + propertySub + ", contractValue="
					+ contractValue + ", type=" + type + ", limit=" + limit
					+ ", timestamp= " + timestamp);
		}

		// if property is missing quit
		if (contractValue == null || type == null || limit == null) {
			logger.warn("property " + property
					+ " for the metric is missing in configuration.");
			return;
		}

		TrustEvent ruleEvent = null;

		if (event.get("type").equalsIgnoreCase("metric")) {

			ruleEvent = new RuleMetricEventImpl(serviceId, property,
					subproperty, contractValue, type, limit, eventValue,
					timestamp);

		} else if (event.get("type").equalsIgnoreCase("alert")) {
			ruleEvent = new AlertEventImpl(serviceId, property, subproperty,
					contractValue, type, limit, eventValue, timestamp);

		}

		String eventDescription = null;
		if (event.containsKey("eventDescription")) {
			eventDescription = event.get("EventDescription");
		}
		if (eventDescription == null) {
			eventDescription = "";
		} else if (eventDescription.length() > maxDescriptionSize) {
			eventDescription = eventDescription
					.substring(0, maxDescriptionSize);
		}
		ruleEvent.setEventDescription(eventDescription);

		fireRule(ruleEvent, service);

	}

	public void updateScore(TrustEvent event) throws Exception {

		String serviceId = event.getServiceId();
		Atomic service = null;
		if ((service = serviceEntityService.getAtomic(serviceId)) == null) {
			logger.info("creating new service entry");
			service = trustFactory.createService(serviceId);

			serviceEntityService.addAtomic(service);
		}

		/*
		 * TODO: organise with type of metric, content, names, etc
		 */

		String property = event.getProperty();
		String propertySub = property;
		String subproperty = null;
		if ((subproperty = event.getSubproperty()) != null) {
			propertySub = property + "." + subproperty;
			if (logger.isDebugEnabled()) {
				logger.debug(property);
			}
		}

		// TODO: should do more checking of validity of fields

		String metricValue = event.getValue();

		String eventTimestamp = event.getTimestamp();
		String timestamp = null;

		if (eventTimestamp != null) {

			DateTimeFormatter fmt = ISODateTimeFormat.dateTimeNoMillis();

			try {
				timestamp = Long
						.toString(fmt.parseMillis(eventTimestamp) / 3600000);

			} catch (IllegalArgumentException iae) {

				logger.error("date format parse exception: "
						+ iae.getStackTrace());
			}

			if (logger.isDebugEnabled()) {
				logger.debug(eventTimestamp + " is converted to " + timestamp);
			}

		}

		// no timestamp or not valid text
		if (timestamp == null) {
			timestamp = Long.toString(System.currentTimeMillis() / 3600000);
		}

		String contractValue = config.getConfig().getString(
				propertySub + "[@value]");
		String type = config.getConfig().getString(propertySub + "[@type]");
		String limit = config.getConfig().getString(propertySub + "[@limit]");

		if (logger.isDebugEnabled()) {
			logger.debug("property=" + propertySub + ", metricValue="
					+ metricValue + ", contractValue=" + contractValue
					+ ", type=" + type + ", limit=" + limit + ", timestamp= "
					+ timestamp);
		}

		// if property is missing quit
		if (contractValue == null || type == null || limit == null) {
			logger.warn("property " + property
					+ " for the metric is missing in configuration.");
			return;
		}

		RuleMetricEventImpl ruleEvent = new RuleMetricEventImpl(serviceId,
				property, subproperty, contractValue, type, limit, metricValue,
				timestamp);

		String eventDescription = event.getEventDescription();
		if (eventDescription == null) {
			eventDescription = "";
		} else if (eventDescription.length() > maxDescriptionSize) {
			eventDescription = eventDescription
					.substring(0, maxDescriptionSize);
		}
		ruleEvent.setEventDescription(eventDescription);

		fireRule(ruleEvent, service);

	}

	/**
	 * @param ruleEvent
	 * @param service
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void fireRule(TrustEvent ruleEvent, Atomic service) {

		List<Object> facts = new ArrayList<Object>();

		facts.add(ruleEvent);

		Rating rating = trustFactory.createReputationRating(service);
		rating.setEventDescription(ruleEvent.getEventDescription());

		String serviceId = service.getId();

		Map scoreMap = new HashMap();
		scoreMap.put("_type_", "Score");
		scoreMap.put("service", serviceId);
		facts.add(scoreMap);

		logger.debug("now firing rules for score update");

		Collection<?> results = ruleExecuter.execute(facts, ruleEvent
				.getProperty().toLowerCase(), "Score", "score");
		Iterator<?> scoreIterator = results.iterator();

		if (scoreIterator.hasNext()) {
			scoreMap = (Map) scoreIterator.next();

			logger.debug(scoreMap.entrySet());
		} else {
			logger.warn("no score retrieved from rule");
		}

		if (logger.isDebugEnabled()) {
			logger.debug("size of retrieved score map " + scoreMap.size());
		}

		if (scoreMap.containsKey("score")) {
			Double scoreValue = (Double) scoreMap.get("score");
			BigDecimal scoreBD = new BigDecimal(String.valueOf(scoreValue))
					.setScale(6, BigDecimal.ROUND_HALF_UP);

			scoreValue = Double.parseDouble(scoreBD.toString());

			rating.setScore(scoreValue);
			rating.setRecency(Long.parseLong((String) scoreMap.get("recency")));
			rating.setProperty((String) scoreMap.get("property"));

			ratingEntityService.addRating(rating);

			Dictionary props = new Properties();
			props.put("service.id", serviceId);
			props.put("score.id", rating.getId());

			Event osgiEvent = new Event("eu/aniketos/trustworthiness/qos",
					props);
			eventAdmin.sendEvent(osgiEvent);

			logger.debug("sent event to topic eu/aniketos/trustworthiness/qos ");

		} else {
			logger.warn("no score calculated from alert for " + serviceId);
		}
	}

	// needs testing
	/**
	 * required for Spring dependency injection
	 * 
	 * @param o
	 */
	public void addRemoteObserver(Observer o) {
		addObserver(o);

	}

	/**
	 * required for Spring dependency injection
	 * 
	 * @return
	 */
	public ConfigurationManagement getConfig() {
		return config;
	}

	/**
	 * @param config
	 */
	public void setConfig(ConfigurationManagement config) {
		this.config = config;
	}

	/**
	 * required for Spring dependency injection
	 * 
	 * @return
	 */
	public ServiceEntityService getServiceEntityService() {
		return serviceEntityService;
	}

	/**
	 * required for Spring dependency injection
	 * 
	 * @param serviceEntityService
	 */
	public void setServiceEntityService(
			ServiceEntityService serviceEntityService) {
		this.serviceEntityService = serviceEntityService;
	}

	/**
	 * required for Spring dependency injection
	 * 
	 * @return
	 */
	public RatingEntityService getRatingEntityService() {
		return ratingEntityService;
	}

	/**
	 * required for Spring dependency injection
	 * 
	 * @param ratingEntityService
	 */
	public void setRatingEntityService(RatingEntityService ratingEntityService) {
		this.ratingEntityService = ratingEntityService;
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
	 * @return
	 */
	public RuleExecuter getRuleExecuter() {
		return ruleExecuter;
	}

	/**
	 * required for Spring dependency injection
	 * 
	 * @param ruleExecuter
	 */
	public void setRuleExecuter(RuleExecuter ruleExecuter) {
		this.ruleExecuter = ruleExecuter;
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