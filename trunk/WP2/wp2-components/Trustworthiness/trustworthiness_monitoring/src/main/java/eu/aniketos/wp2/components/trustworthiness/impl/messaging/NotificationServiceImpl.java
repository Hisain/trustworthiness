package eu.aniketos.wp2.components.trustworthiness.impl.messaging;

import org.apache.log4j.Logger;

import eu.aniketos.wp2.components.trustworthiness.impl.rules.model.event.AlertEventImpl;
import eu.aniketos.wp2.components.trustworthiness.rules.model.event.RuleAlertEvent;
import eu.aniketos.wp2.components.trustworthiness.trust.management.TrustFactory;
import eu.aniketos.wp2.components.trustworthiness.trust.service.ServiceEntityService;
import eu.aniketos.wp4.components.notification.INotification;
import eu.aniketos.wp4.components.notification.Notification;

public class NotificationServiceImpl implements INotification {

	private static Logger logger = Logger.getLogger(NotificationServiceImpl.class);

	private ServiceEntityService serviceEntityService;

	private TrustFactory trustFactory;

	public void alert(Notification alert) {

		if (alert == null || alert.getService() == null
				|| alert.getService().length() == 0
				|| alert.getAlertType() == null
				|| alert.getAlertType().length() == 0) {

			logger.warn("received alert contains null or empty data");

			throw new RuntimeException(
					"received alert contains null or empty data");
		}

		String serviceId = alert.getService();

		if ((serviceEntityService.getAtomic(serviceId)) == null) {
			logger.warn("The service in notification is not registered.");
			throw new RuntimeException(
					"The service in notification is not registered.");
			/*
			 * logger.info("creating new service entry"); service =
			 * trustFactory.createService(serviceId);
			 * 
			 * serviceEntityService.addAtomic(service);
			 */
		}

		if (alert.getAlertType().equals("ThreatLevelChange")) {
			
			RuleAlertEvent event = new AlertEventImpl(serviceId);
			
			event.setEventDescription(alert.getAlertDesc());
			
			event.setProperty("threat");
			
			//TODO: update ratings and trustworthiness as result of threat
		
		} else if (alert.getAlertType().equals("ContractViolation")) {
			
			RuleAlertEvent event = new AlertEventImpl(serviceId);
			
			event.setEventDescription(alert.getAlertDesc());
			
			event.setProperty("contract_violation");
			
			//TODO: update ratings and trustworthiness as result of violation
		}
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

}
