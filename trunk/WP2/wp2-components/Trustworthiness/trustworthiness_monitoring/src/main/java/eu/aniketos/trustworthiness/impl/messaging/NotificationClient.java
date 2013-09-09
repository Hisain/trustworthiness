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

import java.security.SecureRandom;

import javax.jms.Connection;
import javax.jms.ExceptionListener;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageConsumer;
import javax.jms.MessageListener;
import javax.jms.Session;
import javax.jms.Topic;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.apache.activemq.ActiveMQSslConnectionFactory;
import org.apache.log4j.Logger;

import eu.aniketos.trustworthiness.configuration.ConfigurationManagement;
import eu.aniketos.trustworthiness.impl.rules.model.event.AlertEventImpl;
import eu.aniketos.trustworthiness.rules.model.event.AlertEvent;
import eu.aniketos.trustworthiness.rules.service.AlertRatingUpdate;

/**
 * A client to receive all notifications from Aniketos Notification module
 * 
 * @version 1.0
 * @author Erlend Andreas Gjære (SINTEF), erlendandreas.gjare@sintef.no
 * @author Hisain Elshaafi (TSSG)
 * 
 */
public class NotificationClient implements MessageListener, ExceptionListener {

	private static Logger logger = Logger.getLogger(NotificationClient.class);

	private ConfigurationManagement config;

	private AlertRatingUpdate threatUpdate;

	/** The JMS session object we re-use to create subscriptions **/
	private Session session;

	/**
	 * Creates an instance of the simple subscription client
	 */
	public void initialize() {

		/** URL of the ActiveMQ broker, prepended by transport protocol **/
		String brokerUrl = config.getConfig().getString("broker_url");

		/** Topic to subscribe to for receiving access to all notifications **/
		String topicName = config.getConfig().getString("topic");

		/** Username for authenticating with the ActiveMQ broker **/
		String userName = config.getConfig().getString("user");

		/** The password corresponding to the username **/
		String password = config.getConfig().getString("password");

		// Creates a connection
		ActiveMQSslConnectionFactory connectionFactory = new ActiveMQSslConnectionFactory(
				brokerUrl);

		// Setting the trust manager to the connection factory
		connectionFactory.setKeyAndTrustManagers(null, trustedCerts,
				new SecureRandom());
		connectionFactory.setWatchTopicAdvisories(false);

		try {
			
			if (logger.isDebugEnabled()){
				logger.debug("creating connection to " + brokerUrl);
			}
			// Connect to the broker
			Connection connection = connectionFactory.createConnection(
					userName, password);

			// Creating session for sending messages
			session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
			connection.start();

			// Creating the topic (i.e. creating a link to the already existing
			// one on the broker)
			Topic topic = session.createTopic(topicName);

			// MessageConsumer is used for receiving (consuming) messages from
			// the topic (above given threshold)
			MessageConsumer consumer = session.createConsumer(topic);
			consumer.setMessageListener(this);

			if (logger.isDebugEnabled()) {
				logger.debug("Connection to Notification with Id "
						+ connection.getClientID() + " Listening for messages");
			}

		} catch (JMSException e) {
			session = null;
			logger.error("[JMS Exception] " + e.getMessage());
		}
	}

	public void onException(JMSException exception) {
		logger.error("[JMS Exception] " + exception.getMessage());
	}

	public void onMessage(Message message) {

		try {
			//
			if (!message.propertyExists("serviceId")
					|| !message.propertyExists("alertType")
					|| !message.propertyExists("value")
					|| !message.propertyExists("threatId")
					|| message.getStringProperty("serviceId") == null
					|| message.getStringProperty("alertType") == null
					|| message.getIntProperty("value") > 1
					|| message.getIntProperty("value") < 0
					|| message.getStringProperty("threatId") == null

			) {

				/*throw new JMSException(
						"[Client : Unknown message] JMSMessageID="
								+ message.getJMSMessageID());*/
				
				logger.warn("received alert contains null or empty data");
				
			} else {


				if (logger.isDebugEnabled()) {
					logger.debug("[Client : Message received] "
							+ message.getStringProperty("serviceId") + " "
							+ ": alertType="
							+ message.getStringProperty("alertType")
							+ "; value=" + message.getStringProperty("value")
							+ "; description='"
							+ message.getStringProperty("description") + "'"
							+ "; threatId="
							+ message.getStringProperty("threatId")
							+ "; importance="
							+ message.getIntProperty("importance")
							+ "; serverTime="
							+ message.getStringProperty("serverTime")
							+ "; messageId=" + message.getJMSMessageID());
				}

				AlertEvent event = new AlertEventImpl();
				event.setServiceId(message.getStringProperty("serviceId"));
				event.setProperty(message.getStringProperty("alertType"));
				event.setValue(String.valueOf(message.getIntProperty("value")));
				event.setEventDescription(message
						.getStringProperty("description"));
				event.setEventId(message.getStringProperty("threatId"));
				event.setImportance(message.getStringProperty("importance"));
				event.setTimestamp(message.getStringProperty("serverTime"));

				threatUpdate.generateRating(event);

			}

		} catch (JMSException e) {
			logger.error("[JMS Exception] " + e.getMessage());
		} catch (Exception e) {
			logger.error("[Exception] " + e.getMessage());
		}
	}

	// Creating the Trust manager for accepting SSL certificates
	TrustManager[] trustedCerts = new TrustManager[] { new X509TrustManager() {

		public java.security.cert.X509Certificate[] getAcceptedIssuers() {
			return null;
		}

		public void checkClientTrusted(
				java.security.cert.X509Certificate[] certificates,
				String authType) {
		}

		public void checkServerTrusted(
				java.security.cert.X509Certificate[] certificates,
				String authType) {
		}
	} };

	/**
	 * required for Spring dependency injection
	 * 
	 * @param config
	 *            set configuration field
	 */
	public void setConfig(ConfigurationManagement config) {
		this.config = config;
	}

	/**
	 * required for Spring dependency injection
	 * 
	 * @return configuration field
	 */
	public ConfigurationManagement getConfig() {
		return config;
	}

	/**
	 * @return
	 */
	public AlertRatingUpdate getThreatUpdate() {
		return threatUpdate;
	}

	/**
	 * @param threatUpdate
	 */
	public void setThreatUpdate(AlertRatingUpdate threatUpdate) {
		this.threatUpdate = threatUpdate;
	}

}
