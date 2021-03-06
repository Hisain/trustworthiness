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
package eu.aniketos.trustworthiness.impl.trust.management.composite;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.log4j.Logger;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventAdmin;

import eu.aniketos.trustworthiness.configuration.ConfigurationManagement;
import eu.aniketos.trustworthiness.impl.trust.pojo.Atomic;
import eu.aniketos.trustworthiness.impl.trust.pojo.Composite;
import eu.aniketos.trustworthiness.impl.trust.pojo.TrustworthinessEntity;
import eu.aniketos.trustworthiness.trust.management.TrustFactory;
import eu.aniketos.trustworthiness.trust.management.atomic.ServiceTrustUpdatePolicy;
import eu.aniketos.trustworthiness.trust.management.composite.CompositeTrustUpdate;
import eu.aniketos.trustworthiness.trust.service.ServiceEntityService;
import eu.aniketos.trustworthiness.trust.service.TrustworthinessEntityService;

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

	public TrustworthinessEntity aggregateTrustworthiness(
			Composite compositeService) throws Exception {

		String serviceId = compositeService.getId();

		double trustworthinessScore = 1;

		double averageComponentTrustworthinessScore = 0;

		double lowestComponentTrustworthinessScore = 1;

		double qosScore = 1;

		double qosConfidence = 1;

		double repScore = 1;

		double repConfidence = 1;

		double securityScore = 1;

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

			if (logger.isDebugEnabled()) {
				logger.debug("component service " + service.getId());
			}

			TrustworthinessEntity componentTrustworthiness = trustUpdate
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

				logger.debug(service + " trustworthiness: "
						+ componentTrustworthinessScore + ","
						+ componentQosScore + "," + componentQosConfidence
						+ "," + componentRepScore + ","
						+ componentRepConfidence + "," + componentSecurityScore);
			}

			//
			averageComponentTrustworthinessScore = (averageComponentTrustworthinessScore
					* (i - 1) + componentTrustworthinessScore)
					/ i;

			lowestComponentTrustworthinessScore = Math.min(
					lowestComponentTrustworthinessScore,
					componentTrustworthinessScore);

			qosScore *= (qosScore * (i - 1) + componentQosScore) / i;
			if (logger.isDebugEnabled()) {
				logger.debug("updated CS qosScore " + qosScore);
			}

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

		TrustworthinessEntity csTw = trustworthinessEntityService
				.getTrustworthiness(serviceId);

		if (csTw == null) {
			csTw = trustFactory.createTrustworthiness(serviceId);
		}

		BigDecimal trustworthinessScoreBD = new BigDecimal(
				String.valueOf(trustworthinessScore)).setScale(3,
				BigDecimal.ROUND_HALF_UP);
		BigDecimal qosScoreBD = new BigDecimal(String.valueOf(qosScore))
				.setScale(3, BigDecimal.ROUND_HALF_UP);
		BigDecimal qosConfidenceBD = new BigDecimal(
				String.valueOf(qosConfidence)).setScale(3,
				BigDecimal.ROUND_HALF_UP);
		BigDecimal repScoreBD = new BigDecimal(String.valueOf(repScore))
				.setScale(3, BigDecimal.ROUND_HALF_UP);
		BigDecimal repConfidenceBD = new BigDecimal(
				String.valueOf(repConfidence)).setScale(3,
				BigDecimal.ROUND_HALF_UP);
		BigDecimal securityScoreBD = new BigDecimal(
				String.valueOf(securityScore)).setScale(3,
				BigDecimal.ROUND_HALF_UP);
		BigDecimal averageComponentTrustworthinessScoreBD = new BigDecimal(
				String.valueOf(averageComponentTrustworthinessScore)).setScale(
				3, BigDecimal.ROUND_HALF_UP);
		BigDecimal lowestComponentTrustworthinessScoreBD = new BigDecimal(
				String.valueOf(lowestComponentTrustworthinessScore)).setScale(
				3, BigDecimal.ROUND_HALF_UP);

		trustworthinessScore = Double.parseDouble(trustworthinessScoreBD
				.toString());
		qosScore = Double.parseDouble(qosScoreBD.toString());
		qosConfidence = Double.parseDouble(qosConfidenceBD.toString());
		repScore = Double.parseDouble(repScoreBD.toString());
		repConfidence = Double.parseDouble(repConfidenceBD.toString());
		securityScore = Double.parseDouble(securityScoreBD.toString());
		averageComponentTrustworthinessScore = Double
				.parseDouble(averageComponentTrustworthinessScoreBD.toString());
		lowestComponentTrustworthinessScore = Double
				.parseDouble(lowestComponentTrustworthinessScoreBD.toString());

		// save trustworthiness info
		csTw.setTrustworthinessScore(trustworthinessScore);
		csTw.setSecurityScore(securityScore);
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

			Event osgiEvent = new Event(
					"eu/aniketos/trustworthiness/prediction/alert", props);
			eventAdmin.sendEvent(osgiEvent);

			if (logger.isDebugEnabled()) {
				logger.debug("trustworthiness change above alert level: "
						+ scoreChange + ", sent an alert. ");
			}

		} else {

			if (logger.isDebugEnabled()) {
				logger.debug("trustworthiness change below alert level: "
						+ scoreChange);
			}

		}

		trustworthinessEntityService.updateTrustworthiness(csTw);

		return csTw;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see eu.aniketos.trustworthiness.trust.management.composite
	 * .CompositeTrustUpdate#aggregateTrustworthiness(java.lang.String)
	 */
	public TrustworthinessEntity aggregateTrustworthiness(String serviceId)
			throws Exception {

		Composite compositeService = serviceEntityService
				.getComposite(serviceId);

		logger.debug("call aggregateTrustworthiness method with service as parameter");
		TrustworthinessEntity csTw = aggregateTrustworthiness(compositeService);

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
