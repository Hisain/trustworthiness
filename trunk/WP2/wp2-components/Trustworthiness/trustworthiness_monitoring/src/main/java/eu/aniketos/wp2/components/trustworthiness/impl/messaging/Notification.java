package eu.aniketos.wp2.components.trustworthiness.impl.messaging;

import java.util.HashMap;
import java.util.Map;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.ObjectMessage;

import org.apache.log4j.Logger;

import eu.aniketos.wp2.components.trustworthiness.rules.service.ScoreUpdate;

/**
 * @author Hisain Elshaafi (TSSG)
 * 
 */
public class Notification implements MessageListener {

	private static Logger logger = Logger.getLogger(Notification.class);

	ScoreUpdate scoreUpdate;

	public void onMessage(Message message) {
		// comment to deactive

		/**
		 * TODO
		 * 
		 */
		if (message instanceof ObjectMessage) {
			ObjectMessage oMessage = (ObjectMessage) message;

			try {
				if (oMessage.getObject() instanceof Map) {
					Map<String, String> alert = (Map<String, String>) oMessage
							.getObject();
					logger.debug("alert message received ");

					String service = alert.get("serviceId");
					Map<String, String> alertMessage = new HashMap<String, String>();
					// alertMessage.put("type", "alert");
					// alertMessage.put("property", "port-scanning");
					// alertMessage.put("subproperty", "port-scanning");
					alertMessage.put("service", service);

					// scoreUpdate.updateScore(alertMessage);
					try {
						scoreUpdate.updateScore(null);
					} catch (Exception e) {
						logger.error(e.getMessage());
					}

				}
			} catch (JMSException e) {
				logger.error(e.getMessage());
			}
		}
	}

}
