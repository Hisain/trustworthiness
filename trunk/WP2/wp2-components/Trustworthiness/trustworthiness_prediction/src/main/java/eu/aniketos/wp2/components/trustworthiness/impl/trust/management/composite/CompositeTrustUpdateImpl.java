package eu.aniketos.wp2.components.trustworthiness.impl.trust.management.composite;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;

import eu.aniketos.wp2.components.trustworthiness.configuration.ConfigurationManagement;
import eu.aniketos.wp2.components.trustworthiness.impl.trust.management.atomic.ServiceTrustworthiness;
import eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo.Composite;
import eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo.Atomic;
import eu.aniketos.wp2.components.trustworthiness.trust.management.atomic.ServiceTrustUpdatePolicy;
import eu.aniketos.wp2.components.trustworthiness.trust.management.atomic.Trustworthiness;
import eu.aniketos.wp2.components.trustworthiness.trust.management.composite.CompositeTrustUpdate;
import eu.aniketos.wp2.components.trustworthiness.trust.service.ServiceEntityService;

/**
 * calculates trustworthiness of simple composite services from components 
 * @author Hisain Elshaafi (TSSG)
 * 
 */
public class CompositeTrustUpdateImpl implements CompositeTrustUpdate {

	private static Logger logger = Logger
			.getLogger(CompositeTrustUpdateImpl.class);

	private ServiceEntityService serviceEntityService;

	private ServiceTrustUpdatePolicy trustUpdate;

	private ConfigurationManagement config;

	private EventAdmin eventAdmin;

	/* (non-Javadoc)
	 * @see eu.aniketos.wp2.components.trustworthiness.trust.management.composite.CompositeTrustUpdate#aggregateTrustworthiness(java.lang.String)
	 */
	public Trustworthiness aggregateTrustworthiness(String serviceId)
			throws Exception {

		Composite compositeService = serviceEntityService
				.getComposite(serviceId);


		double twScore = 1;

		double twConfidence = 1;

		/**
		 * TODO: update component trustworthiness before aggregation
		 */
		Set<Atomic> componentServices = compositeService.getComponentServices();
		
		if (componentServices == null || componentServices.size() == 0) {
			logger.error("no component service found for " + serviceId);
		}

		// TODO update atomic trust before aggregation
		for (Atomic service : componentServices) {
			logger.debug("component service " + service.getId());
			Trustworthiness componentTw =  trustUpdate.updateTrust(service.getId());

			double score = componentTw.getTrustworthinessScore();
			double confidence = componentTw.getQosConfidence();

			if (logger.isDebugEnabled()) {
				logger.debug(service + " trustworthiness " + score + ","
						+ confidence);
			}
			twScore *= score;
			twConfidence *= confidence;
		}
		
		BigDecimal scoreBD = new BigDecimal(String.valueOf(twScore)).setScale(
				3, BigDecimal.ROUND_HALF_UP);
		BigDecimal confidenceBD = new BigDecimal(String.valueOf(twConfidence))
				.setScale(3, BigDecimal.ROUND_HALF_UP);

		twScore = Double.parseDouble(scoreBD.toString());
		twConfidence = Double.parseDouble(confidenceBD.toString());

		Trustworthiness csTw = new ServiceTrustworthiness();
		csTw.setTrustworthinessScore(twScore);
		csTw.setQosConfidence(twConfidence);

		//save trustworthiness info
		compositeService.setTrustworthinessScore(twScore);
		compositeService.setQosConfidence(twConfidence);
		serviceEntityService.updateComposite(compositeService);
		
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

		return csTw;

	}

	/**
	 * @return
	 */
	public ServiceEntityService getServiceEntityService() {
		return serviceEntityService;
	}

	/**
	 * @param sEntityService
	 */
	public void setServiceEntityService(ServiceEntityService sEntityService) {
		this.serviceEntityService = sEntityService;
	}

	/**
	 * @return
	 */
	public ServiceTrustUpdatePolicy getTrustUpdate() {
		return trustUpdate;
	}

	/**
	 * @param trustUpdate
	 */
	public void setTrustUpdate(ServiceTrustUpdatePolicy trustUpdate) {
		this.trustUpdate = trustUpdate;
	}
	
	/**
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
	 * @return
	 */
	public EventAdmin getEventAdmin() {
		return eventAdmin;
	}
	
	/**
	 * @param eventAdmin
	 */
	public void setEventAdmin(EventAdmin eventAdmin) {
		this.eventAdmin = eventAdmin;
	}

}
