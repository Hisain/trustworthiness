/**
 * Copyright (c) 2013, Waterford Institute of Technology
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met
 *    - Redistributions of source code must retain the above copyright
 *      notice, this list of conditions and the following disclaimer.
 *    - Redistributions in binary form must reproduce the above copyright
 *      notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *    - Neither the name of Waterford Institute of Technology nor the
 *      names of its contributors may be used to endorse or promote products
 *      derived from this software without specific prior written permission.
 *      
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL WATERFORD INSTITUTE OF TECHNOLOGY BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */
package eu.aniketos.trustworthiness.impl.rules.service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import org.joda.time.format.DateTimeFormatter;
import org.joda.time.format.ISODateTimeFormat;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;

import org.apache.commons.configuration.ConversionException;
import org.apache.log4j.Logger;

import eu.aniketos.trustworthiness.configuration.ConfigurationManagement;
import eu.aniketos.trustworthiness.ext.rules.model.event.TrustEvent;
import eu.aniketos.trustworthiness.impl.rules.model.event.RuleMetricEventImpl;
import eu.aniketos.trustworthiness.impl.trust.pojo.Atomic;
import eu.aniketos.trustworthiness.impl.trust.pojo.QoSMetric;
import eu.aniketos.trustworthiness.rules.service.MetricRatingUpdate;
import eu.aniketos.trustworthiness.rules.service.RuleExecuter;
import eu.aniketos.trustworthiness.trust.management.TrustFactory;
import eu.aniketos.trustworthiness.trust.service.QoSMetricEntityService;
import eu.aniketos.trustworthiness.trust.service.ServiceEntityService;

/**
 * @author Hisain Elshaafi (TSSG)
 * 
 */
public class QosRatingUpdateImpl implements MetricRatingUpdate {

	private static Logger logger = Logger.getLogger(QosRatingUpdateImpl.class);

	private ConfigurationManagement config;

	private ServiceEntityService serviceEntityService;

	private QoSMetricEntityService qosEntityService;

	private TrustFactory trustFactory;

	private RuleExecuter ruleExecuter;

	private EventAdmin eventAdmin;

	int maxDescriptionSize = 0;

	Map<String, String> properties = new HashMap<String, String>();

	/**
	 * 
	 */
	@SuppressWarnings("unchecked")
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

		logger.debug("check number of properties");
		Object props = config.getConfig().getProperty("property.name");

		if (props == null) {

			logger.warn("No properties found");

		} else if (props instanceof Collection) {

			if (logger.isDebugEnabled()) {
				logger.debug("Number of properties: "
						+ ((Collection<?>) props).size());
			}

			Object[] propsObjArray = ((Collection<String>) props).toArray();
			String[] propsArray = Arrays.copyOf(propsObjArray,
					propsObjArray.length, String[].class);

			for (int i = 0; i < propsObjArray.length; i++) {

				Object subprops = config.getConfig().getProperty(
						"property(" + i + ").subproperties.subproperty.name");
				Object subpropTypes = config.getConfig().getProperty(
						"property(" + i + ").subproperties.subproperty.type");
				Object subpropLimits = config.getConfig().getProperty(
						"property(" + i + ").subproperties.subproperty.limit");
				Object subpropValues = config.getConfig().getProperty(
						"property(" + i + ").subproperties.subproperty.value");

				if (logger.isDebugEnabled()) {
					logger.debug("subproperties " + subprops);
				}

				String[] subpropsArray = null;
				if (subprops instanceof Collection) {

					if (logger.isDebugEnabled()) {
						logger.debug("Number of subproperties for "
								+ propsArray[i] + "="
								+ ((Collection<?>) subprops).size());
					}

					Object[] subpropsObjArray = ((Collection<String>) subprops)
							.toArray();
					subpropsArray = Arrays.copyOf(subpropsObjArray,
							subpropsObjArray.length, String[].class);

					String[] subpropTypesArray = null;
					if (subpropTypes instanceof Collection) {
						Object[] subpropTypesObjArray = ((Collection<String>) subpropTypes)
								.toArray();
						subpropTypesArray = Arrays.copyOf(subpropTypesObjArray,
								subpropTypesObjArray.length, String[].class);
					}

					String[] subpropLimitsArray = null;
					if (subpropLimits instanceof Collection) {
						Object[] subpropLimitsObjArray = ((Collection<String>) subpropLimits)
								.toArray();
						subpropLimitsArray = Arrays.copyOf(
								subpropLimitsObjArray,
								subpropLimitsObjArray.length, String[].class);
					}

					String[] subpropValuesArray = null;
					if (subprops instanceof Collection) {
						Object[] subpropValuesObjArray = ((Collection<String>) subpropValues)
								.toArray();
						subpropValuesArray = Arrays.copyOf(
								subpropValuesObjArray,
								subpropValuesObjArray.length, String[].class);
					}

					//

					int j = 0;
					for (String subprop : subpropsArray) {

						if (logger.isDebugEnabled()) {
							logger.debug(subprop);
						}

						properties.put(propsArray[i] + "." + subprop + ".type",
								subpropTypesArray[j]);
						properties.put(
								propsArray[i] + "." + subprop + ".limit",
								subpropLimitsArray[j]);
						properties.put(
								propsArray[i] + "." + subprop + ".value",
								subpropValuesArray[j]);

						if (logger.isDebugEnabled()) {
							logger.debug("added subproperty: " + propsArray[i]
									+ "." + subprop);
						}

						j++;
					}

				} else if (subprops != null
						&& !(subprops instanceof Collection)) {

					properties.put(propsArray[i] + "." + subprops + ".type",
							(String) subpropTypes);
					properties.put(propsArray[i] + "." + subprops + ".limit",
							(String) subpropLimits);
					properties.put(propsArray[i] + "." + subprops + ".value",
							(String) subpropValues);

					if (logger.isDebugEnabled()) {
						logger.debug("added subproperty: " + propsArray[i]
								+ "." + subprops);
					}
				}

			}
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see eu.aniketos.trustworthiness.rules.service.MetricRatingUpdate
	 * #updateScore(java.util.Map)
	 */
	public void generateRating(Map<String, String> event) throws Exception {

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
				|| event.get("property") == null) {

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

		} else {
			propertySub = property + "." + property;
		}

		if (logger.isDebugEnabled()) {
			logger.debug(propertySub);
		}

		String eventValue = event.get("value");

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

		String contractValue = properties.get(propertySub + ".value");
		String type = properties.get(propertySub + ".type");
		String limit = properties.get(propertySub + ".limit");

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

		TrustEvent ruleEvent = new RuleMetricEventImpl(serviceId, property,
				subproperty, contractValue, type, limit, eventValue, timestamp);

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

	public void generateRating(TrustEvent event) throws Exception {

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

		String contractValue = properties.get(propertySub + ".value");
		String type = properties.get(propertySub + ".type");
		String limit = properties.get(propertySub + ".limit");

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

		QoSMetric metricRating = trustFactory.createQoSRating(service);
		metricRating.setEventDescription(ruleEvent.getEventDescription());

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

			metricRating.setScore(scoreValue);
			metricRating.setRecency(Long.parseLong((String) scoreMap
					.get("recency")));
			metricRating.setProperty((String) scoreMap.get("property"));

			qosEntityService.addMetric(metricRating);

			Dictionary props = new Properties();
			props.put("service.id", serviceId);
			props.put("score.id", metricRating.getId());

			Event osgiEvent = new Event(
					"eu/aniketos/trustworthiness/monitoring/qos", props);
			eventAdmin.sendEvent(osgiEvent);

			logger.debug("sent event to topic eu/aniketos/trustworthiness/monitoring/qos ");

		} else {
			logger.warn("no score calculated from alert for " + serviceId);
		}
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
	public QoSMetricEntityService getQosEntityService() {
		return qosEntityService;
	}

	/**
	 * required for Spring dependency injection
	 * 
	 * @param qosEntityService
	 */
	public void setQosEntityService(QoSMetricEntityService qosEntityService) {
		this.qosEntityService = qosEntityService;
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