/**
 * Copyright (c) 2013, Waterford Institute of Technology
 * All rights reserved.

 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met
 *    - Redistributions of source code must retain the above copyright
 *      notice, this list of conditions and the following disclaimer.
 *    - Redistributions in binary form must reproduce the above copyright
 *      notice, this list of conditions and the following disclaimer in the
 *      documentation and/or other materials provided with the distribution.
 *    - Neither the name of Waterford Institute of Technology nor the
 *      names of its contributors may be used to endorse or promote products
 *      derived from this software without specific prior written permission.
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
package eu.aniketos.trustworthiness.impl.messaging;

import org.apache.log4j.Logger;

import eu.aniketos.trustworthiness.impl.rules.model.event.RuleAlertEventImpl;
import eu.aniketos.trustworthiness.rules.model.event.RuleAlertEvent;
import eu.aniketos.trustworthiness.trust.management.TrustFactory;
import eu.aniketos.trustworthiness.trust.service.ServiceEntityService;
import eu.aniketos.notification.INotification;
import eu.aniketos.notification.Notification;

public class NotificationServiceImpl implements INotification {

	private static Logger logger = Logger
			.getLogger(NotificationServiceImpl.class);

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

			RuleAlertEvent event = new RuleAlertEventImpl(serviceId);

			event.setEventDescription(alert.getAlertDesc());

			event.setProperty("threat");

			// TODO: update ratings and trustworthiness as result of threat

		} else if (alert.getAlertType().equals("ContractViolation")) {

			RuleAlertEvent event = new RuleAlertEventImpl(serviceId);

			event.setEventDescription(alert.getAlertDesc());

			event.setProperty("contract_violation");

			// TODO: update ratings and trustworthiness as result of violation
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
