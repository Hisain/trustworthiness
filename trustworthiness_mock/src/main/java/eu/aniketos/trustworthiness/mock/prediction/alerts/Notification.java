package eu.aniketos.trustworthiness.mock.prediction.alerts;

import org.apache.log4j.Logger;
import org.osgi.service.event.Event;
import org.osgi.service.event.EventHandler;

public class Notification implements EventHandler {

	private static Logger logger = Logger.getLogger(Notification.class);

	/* (non-Javadoc)
	 * @see org.osgi.service.event.EventHandler#handleEvent(org.osgi.service.event.Event)
	 */
	public void handleEvent(Event event) {
		String topicName = event.getTopic();

		if (topicName.endsWith("eu/aniketos/trustworthiness/prediction/alert")) {

			logger.info("Received a new trust alert, topic:" + topicName);
			logger.info("with properties:");
			String[] propertyNames = event.getPropertyNames();
			for (String propertyName : propertyNames) {
				String propertyValue = (String) event.getProperty(propertyName);
				logger.debug("- " + propertyName + ": " + propertyValue);
			}

		} else {
			logger.info("Received event is not an alert, topic:" + topicName);
		}

	}

}
