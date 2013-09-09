package eu.aniketos.trustworthiness.rules.model.event;

import eu.aniketos.trustworthiness.ext.rules.model.event.TrustEvent;

public interface AlertEvent extends TrustEvent {

	/**
	 * @return event ID
	 */
	public String getEventId();

	/**
	 * @param eventId event ID
	 */
	public void setEventId(String eventId);

	/**
	 * @return alert importance
	 */
	public String getImportance();

	/**
	 * @param importance; alert importance
	 */
	public void setImportance(String importance);
}
