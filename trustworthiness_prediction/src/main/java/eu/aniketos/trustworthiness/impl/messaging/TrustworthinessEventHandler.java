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
package eu.aniketos.trustworthiness.impl.messaging;

import org.apache.log4j.Logger;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

import eu.aniketos.trustworthiness.trust.management.atomic.ServiceTrustUpdatePolicy;

/**
 * handles events in relation to trustworthiness updates e.g. events generated
 * by trustworthiness monitoring module
 * 
 * @author Hisain Elshaafi
 * 
 */
public class TrustworthinessEventHandler implements EventHandler {

	private static Logger logger = Logger
			.getLogger(TrustworthinessEventHandler.class);

	private ServiceTrustUpdatePolicy trustUpdate;

	/**
	 * 
	 */
	public TrustworthinessEventHandler() {
		logger.debug("new TrustworthinessEventHandler");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.osgi.service.event.EventHandler#handleEvent(org.osgi.service.event
	 * .Event)
	 */
	public void handleEvent(Event event) {

		if (logger.isDebugEnabled()) {
			logger.debug("event " + event);
		}

		String topicName = event.getTopic();

		if (logger.isDebugEnabled()) {
			logger.debug("Received event for topic " + topicName);
			logger.debug("with properties:");
			String[] propertyNames = event.getPropertyNames();
			for (String propertyName : propertyNames) {
				String propertyValue = (String) event.getProperty(propertyName);
				logger.debug("- " + propertyName + ": " + propertyValue);
			}
		}

		// exclude prediction alerts from next operation
		if (!topicName.endsWith("alert")) {

			String serviceId = (String) event.getProperty("service.id");
			String scoreId = (String) event.getProperty("score.id");

			if (logger.isDebugEnabled()) {
				logger.debug("will now update trustworthiness for service "
						+ serviceId + " scoreId " + scoreId);
			}

			try {

				trustUpdate.updateTrust(serviceId);

			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}
	}

	/**
	 * @return ServiceTrustUpdatePolicy to execute trustworthiness calculation
	 */
	public ServiceTrustUpdatePolicy getTrustUpdate() {
		return trustUpdate;
	}

	/**
	 * @param trustUpdate
	 *            to execute trustworthiness calculation
	 */
	public void setTrustUpdate(ServiceTrustUpdatePolicy trustUpdate) {
		this.trustUpdate = trustUpdate;
	}

}
