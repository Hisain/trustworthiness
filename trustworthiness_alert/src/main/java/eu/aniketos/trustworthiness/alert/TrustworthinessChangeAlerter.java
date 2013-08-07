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
package eu.aniketos.trustworthiness.alert;

import org.apache.log4j.Logger;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

import eu.aniketos.trustworthiness.trust.service.ServiceEntityService;
import eu.aniketos.notification.IAlert;
import eu.aniketos.notification.Notification;

public class TrustworthinessChangeAlerter implements EventHandler {

	private static Logger logger = Logger
			.getLogger(TrustworthinessChangeAlerter.class);

	private ServiceEntityService serviceEntityService;

	private IAlert alert;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.osgi.service.event.EventHandler#handleEvent(org.osgi.service.event
	 * .Event)
	 */
	public void handleEvent(Event event) {

		String topicName = event.getTopic();

		if (topicName.endsWith("eu/aniketos/trustworthiness/prediction/alert")) {

			if (logger.isDebugEnabled()) {
				logger.debug("Received a new trust alert, type:"
						+ event.getProperty("alert.type"));
				logger.debug("with properties:");
				String[] propertyNames = event.getPropertyNames();
				for (String propertyName : propertyNames) {
					String propertyValue = (String) event
							.getProperty(propertyName);
					logger.debug("- " + propertyName + ": " + propertyValue);
				}
			}

			String serviceId = (String) event.getProperty("service.id");
			String trustScore = (String) event
					.getProperty("trustworthiness.score");
			String alertDescription = (String) event
					.getProperty("alert.description");

			if (event.getProperty("alert.type").equals("TRUST_LEVEL_CHANGE")) {
				alert.alert(serviceId, Notification.TRUST_LEVEL_CHANGE,
						trustScore, alertDescription);
			}

		} else {
			if (logger.isInfoEnabled()) {
				logger.info("Received event is not an alert, topic:"
						+ topicName);
			}
		}
	}

	/**
	 * @return data access service object for Web service
	 */
	public ServiceEntityService getServiceEntityService() {
		return serviceEntityService;
	}

	/**
	 * @param serviceEntityService
	 *            data access service object for Web services
	 */
	public void setServiceEntityService(
			ServiceEntityService serviceEntityService) {
		this.serviceEntityService = serviceEntityService;
	}

	/**
	 * @return notification module alert
	 */
	public IAlert getAlert() {
		return alert;
	}

	/**
	 * @param alert
	 *            notification module alert
	 */
	public void setAlert(IAlert alert) {
		this.alert = alert;
	}

}
