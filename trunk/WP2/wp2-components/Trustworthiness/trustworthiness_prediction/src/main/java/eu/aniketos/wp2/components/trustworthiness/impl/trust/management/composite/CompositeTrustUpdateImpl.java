package eu.aniketos.wp2.components.trustworthiness.impl.trust.management.composite;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;

import eu.aniketos.wp2.components.trustworthiness.configuration.ConfigurationManagement;
import eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo.Composite;
import eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo.Atomic;
import eu.aniketos.wp2.components.trustworthiness.trust.management.TrustFactory;
import eu.aniketos.wp2.components.trustworthiness.trust.management.atomic.ServiceTrustUpdatePolicy;
import eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo.Trustworthiness;
import eu.aniketos.wp2.components.trustworthiness.trust.management.composite.CompositeTrustUpdate;
import eu.aniketos.wp2.components.trustworthiness.trust.service.ServiceEntityService;
import eu.aniketos.wp2.components.trustworthiness.trust.service.TrustworthinessEntityService;

/**
 * calculates trustworthiness of simple composite services from components
 * 
 * @author Hisain Elshaafi (TSSG)
 * 
 */
public class CompositeTrustUpdateImpl implements CompositeTrustUpdate {

	private static Logger logger = Logger
			.getLogger(CompositeTrustUpdateImpl.class);

	private ServiceEntityService serviceEntityService;

	private TrustworthinessEntityService trustworthinessEntityService;

	private TrustFactory trustFactory;

	private ServiceTrustUpdatePolicy trustUpdate;

	private ConfigurationManagement config;

	private EventAdmin eventAdmin;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * eu.aniketos.wp2.components.trustworthiness.trust.management.composite
	 * .CompositeTrustUpdate#aggregateTrustworthiness(java.lang.String)
	 */
	public Trustworthiness aggregateTrustworthiness(String serviceId)
			throws Exception {

		Composite compositeService = serviceEntityService
				.getComposite(serviceId);

		double trustworthinessScore = 1;

		double averageComponentTrustworthinessScore = 0;

		double lowestComponentTrustworthinessScore = 1;

		double qosScore = 1;

		double qosConfidence = 1;

		double repScore = 1;

		double repConfidence = 1;

		double securityScore = 0;

		double i = 0;

		/**
		 * TODO: update component trustworthiness before aggregation
		 */
		Set<Atomic> componentServices = compositeService.getComponentServices();

		if (componentServices == null || componentServices.size() == 0) {

			logger.error("no component service found for " + serviceId);

			throw new Exception("no component service found for " + serviceId);
		}

		// TODO update atomic trust before aggregation
		for (Atomic service : componentServices) {
			++i;

			logger.debug("component service " + service.getId());
			Trustworthiness componentTrustworthiness = trustUpdate
					.updateTrust(service.getId());

			double componentTrustworthinessScore = componentTrustworthiness
					.getTrustworthinessScore();
			double componentQosScore = componentTrustworthiness.getQosScore();
			double componentQosConfidence = componentTrustworthiness
					.getQosConfidence();
			double componentRepScore = componentTrustworthiness
					.getReputationScore();
			double componentRepConfidence = componentTrustworthiness
					.getReputationConfidence();
			double componentSecurityScore = componentTrustworthiness
					.getSecurityScore();

			if (logger.isDebugEnabled()) {
				logger.debug(service + " trustworthiness "
						+ componentTrustworthinessScore + ","
						+ componentQosConfidence);
			}

			//
			averageComponentTrustworthinessScore = (averageComponentTrustworthinessScore
					* (i - 1) + componentTrustworthinessScore)
					/ i;
			lowestComponentTrustworthinessScore = Math.min(
					lowestComponentTrustworthinessScore,
					componentTrustworthinessScore);

			qosScore *= (qosScore * (i - 1) + componentQosScore) / i;
			qosConfidence *= (qosConfidence * (i - 1) + componentQosConfidence)
					/ i;

			repScore *= (repScore * (i - 1) + componentRepScore) / i;
			repConfidence *= (repConfidence * (i - 1) + componentRepConfidence)
					/ i;

			securityScore = Math.min(securityScore, componentSecurityScore);
		}

		double qosWeight = config.getConfig().getFloat("qos_weight");
		double reputationWeight = config.getConfig().getFloat(
				"reputation_weight");

		trustworthinessScore = (qosWeight * qosScore + reputationWeight
				* repScore)
				* securityScore;

		BigDecimal scoreBD = new BigDecimal(
				String.valueOf(trustworthinessScore)).setScale(3,
				BigDecimal.ROUND_HALF_UP);
		BigDecimal confidenceBD = new BigDecimal(String.valueOf(qosConfidence))
				.setScale(3, BigDecimal.ROUND_HALF_UP);

		trustworthinessScore = Double.parseDouble(scoreBD.toString());
		qosConfidence = Double.parseDouble(confidenceBD.toString());

		Trustworthiness csTw = trustworthinessEntityService
				.getTrustworthiness(serviceId);
		if (csTw == null) {
			csTw = trustFactory.createTrustworthiness(serviceId);
		}

		// save trustworthiness info
		csTw.setTrustworthinessScore(trustworthinessScore);
		csTw.setQosScore(qosScore);
		csTw.setQosConfidence(qosConfidence);
		csTw.setReputationScore(repScore);
		csTw.setReputationConfidence(repConfidence);
		csTw.setAverageComponentTrustworthinessScore(averageComponentTrustworthinessScore);
		csTw.setLowestComponentTrustworthinessScore(lowestComponentTrustworthinessScore);

		// send alert if trustworthiness > allowed change before alert
		double scoreChange = Math.abs(trustworthinessScore
				- csTw.getLastAlertScore());
		if (scoreChange > config.getConfig().getDouble("trust_change_alert")) {

			csTw.setLastAlertScore(trustworthinessScore);

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

				props.put("alert.description", "UNTRUSTED_SERVICE_COMPOSITION");

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

		trustworthinessEntityService.updateTrustworthiness(csTw);

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
