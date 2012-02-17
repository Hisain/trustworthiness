package eu.aniketos.wp2.components.trustworthiness.impl.rules.service;

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

import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;
import org.springframework.osgi.extensions.annotation.ServiceReference;
import org.springframework.transaction.annotation.Transactional;

import org.apache.log4j.Logger;
import org.drools.runtime.ExecutionResults;
import eu.aniketos.wp2.components.trustworthiness.configuration.ConfigurationManagement;
import eu.aniketos.wp2.components.trustworthiness.impl.rules.model.event.AlertEventImpl;
import eu.aniketos.wp2.components.trustworthiness.impl.rules.model.event.MetricEventImpl;
import eu.aniketos.wp2.components.trustworthiness.rules.service.RuleExecuter;
import eu.aniketos.wp2.components.trustworthiness.rules.service.ScoreUpdate;
import eu.aniketos.wp2.components.trustworthiness.trust.management.TrustFactory;
import eu.aniketos.wp2.components.trustworthiness.trust.service.ScoreEntityService;
import eu.aniketos.wp2.components.trustworthiness.trust.service.ServiceEntityService;
import eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo.Atomic;
import eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo.Score;

/**
 * @author Hisain Elshaafi (TSSG)
 * 
 */
public class ScoreUpdateImpl extends Observable implements ScoreUpdate {

	private static Logger logger = Logger.getLogger(ScoreUpdateImpl.class);

	private ConfigurationManagement config;

	private ServiceEntityService serviceEntityService;

	private ScoreEntityService scoreEntityService;

	private TrustFactory trustFactory;

	private RuleExecuter ruleExecuter;

	private EventAdmin eventAdmin;

	public void initialize() {
		// replaced with osgi events *whiteboard*
		// monitorHelperService.setupObservers(this);

		/*
		 * Map<String,Score> props = new HashMap<String,Score>();
		 * props.put("test", new Score()); logger.info(eventAdmin);
		 * eventAdmin.sendEvent(new Event("trust-event/qos", props));
		 */
	}

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

		List<Object> facts = new ArrayList<Object>();

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

		String metricValue = event.get("value");
		
		if (metricValue==null){
			throw new Exception(
					"metric did not contain required event elements. message will be ignored.");
		}

		String value = config.getConfig().getString(propertySub + "[@value]");
		String type = config.getConfig().getString(propertySub + "[@type]");
		String limit = config.getConfig().getString(propertySub + "[@limit]");

		if (logger.isDebugEnabled()) {
			logger.debug("property " + property + ", value=" + value
					+ ", type=" + type + ", limit=" + limit);
		}

		if (event.get("type").equalsIgnoreCase("metric")) {

			facts.add(new MetricEventImpl(service, property, subproperty,
					value, type, limit, metricValue));
		} else if (event.get("type").equalsIgnoreCase("alert")) {
			facts.add(new AlertEventImpl(service, property, subproperty, value,
					type, limit, String.valueOf(1)));
		}

		Score score = trustFactory.createScore(service);

		Map scoreMap = new HashMap();
		scoreMap.put("_type_", "Score");
		scoreMap.put("service", serviceId);
		facts.add(scoreMap);

		logger.debug("now firing rules for score update");

		Collection<?> results = ruleExecuter.execute(facts,
				property.toLowerCase(), "Score", "score");
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
			score.setScore((Double) scoreMap.get("score"));
			score.setRecency((Long) scoreMap.get("recency"));
			score.setProperty((String) scoreMap.get("property"));

			scoreEntityService.addScore(score);

			Dictionary props = new Properties();
			props.put("service.id",serviceId);
			props.put("score.id", score.getId());
			
			Event osgiEvent = new Event("eu/aniketos/trustworthiness/qos", props);
			eventAdmin.sendEvent(osgiEvent);
			
			logger.debug("sent event to topic eu/aniketos/trustworthiness/qos ");

		} else {
			logger.warn("no score calculated from alert for " + service.getId());
		}
	}

	// needs testing
	public void addRemoteObserver(Observer o) {
		addObserver(o);

	}

	public ConfigurationManagement getConfig() {
		return config;
	}

	public void setConfig(ConfigurationManagement config) {
		this.config = config;
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

	public TrustFactory getTrustFactory() {
		return trustFactory;
	}

	public void setTrustFactory(TrustFactory trustFactory) {
		this.trustFactory = trustFactory;
	}

	public RuleExecuter getRuleExecuter() {
		return ruleExecuter;
	}

	public void setRuleExecuter(RuleExecuter ruleExecuter) {
		this.ruleExecuter = ruleExecuter;
	}

	public EventAdmin getEventAdmin() {
		return eventAdmin;
	}
	
	public void setEventAdmin(EventAdmin eventAdmin) {
		this.eventAdmin = eventAdmin;
	}
}