package eu.aniketos.wp2.components.trustworthiness.impl.messaging;

import javax.annotation.Resource;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.Destination;
import javax.jms.MapMessage;
import javax.jms.MessageProducer;
import javax.jms.Session;
import org.apache.log4j.Logger;

import eu.aniketos.wp2.components.trustworthiness.configuration.ConfigurationManagement;
import eu.aniketos.wp2.components.trustworthiness.messaging.TrustworthinessPredictionJms;
import eu.aniketos.wp2.components.trustworthiness.trust.management.atomic.Trustworthiness;

/**
 * @author Hisain Elshaafi (TSSG)
 *
 */
public class TrustworthinessPredictionJmsImpl implements TrustworthinessPredictionJms {

	private static Logger logger = Logger.getLogger(TrustworthinessPredictionJmsImpl.class);
	
	private ConfigurationManagement config;
	
	@Resource(mappedName="java:/JmsXA")
	private ConnectionFactory connectionFactory;

	private Connection connection = null;
	
	@Resource(mappedName="jnp://localhost:1099/topic/TrustworthinessTopic")
	private Destination destination;

	public void sendTrustworthiness(Trustworthiness tw) throws Exception {

		if (logger.isInfoEnabled()) {
			logger.info("trust calculated " + tw.toString());
		}
		connection = connectionFactory.createConnection();
		Session session = connection.createSession(false,
				Session.AUTO_ACKNOWLEDGE);

		MessageProducer producer = session
				.createProducer(destination);

		MapMessage message = session.createMapMessage();
		message.setString("type", "trust");
		String serviceId = tw.getServiceId();
		message.setString("service", serviceId);
		message.setDouble("score", tw.getScore());
		message.setDouble("confidence", tw.getConfidence());
		message.setLong("timestamp", System.currentTimeMillis());

		if (logger.isDebugEnabled()) {
			logger.debug("sending trustworthiness level for " + serviceId);
		}

		connection.start();

		producer.send(message);

		if (logger.isDebugEnabled()) {
			logger.debug("sent trust");
		}

		if (producer != null) {
			producer.close();
		}

		if (session != null) {
			session.close();
		}
		if (connection != null) {
			connection.close();
		}
	}

}
