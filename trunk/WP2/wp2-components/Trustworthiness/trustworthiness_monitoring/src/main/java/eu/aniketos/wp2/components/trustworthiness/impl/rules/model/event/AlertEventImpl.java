package eu.aniketos.wp2.components.trustworthiness.impl.rules.model.event;

import eu.aniketos.wp2.components.trustworthiness.rules.model.event.RuleAlertEvent;

/**
 * alert events class
 * 
 * @author Hisain Elshaafi (TSSG)
 * 
 */
public class AlertEventImpl implements RuleAlertEvent {

	private String serviceId;

	private String property;

	private String subproperty;

	// new alertValue reading causing the event
	private String value;

	// proposed value in contractValue
	private String contractValue;

	private String type;

	private String limit;

	private String timestamp;

	private String eventDescription;
	
	/**
	 * @param serviceId
	 *            atomic or composite serviceId object
	 * @param property
	 *            metric's trustworthiness property
	 * @param subproperty
	 *            subcategory of the trustworthiness property if exists
	 * @param contractValue
	 *            required value
	 * @param type
	 *            type of alert i.e. percentage, real number, etc.
	 * @param limit
	 *            the type of limit requirement i.e. max, min, etc
	 * @param value
	 *            actual alert value
	 */
	public AlertEventImpl(String serviceId, String property,
			String subproperty, String contractValue, String type,
			String limit, String value, String timestamp) {

		this.serviceId = serviceId;
		this.property = property;
		this.subproperty = subproperty;
		this.value = value;
		this.contractValue = contractValue;
		this.type = type;
		this.limit = limit;
		this.timestamp = timestamp;
	}

	/**
	 * @return
	 */
	public String getServiceId() {
		return serviceId;
	}

	/**
	 * @param serviceId
	 */
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	/**
	 * @return
	 */
	public String getValue() {
		return value;
	}

	/**
	 * @param alertValue
	 */
	public void setValue(String alertValue) {
		this.value = alertValue;
	}

	/**
	 * @return
	 */
	public String getContractValue() {
		return contractValue;
	}

	/**
	 * @param contractValue
	 */
	public void setContractValue(String contractValue) {
		this.contractValue = contractValue;
	}

	/**
	 * @param type
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return
	 */
	public String getType() {
		return type;
	}

	/**
	 * @param limit
	 */
	public void setLimit(String limit) {
		this.limit = limit;
	}

	/**
	 * @return
	 */
	public String getLimit() {
		return limit;
	}

	/**
	 * @param property
	 */
	public void setProperty(String property) {
		this.property = property;
	}

	/**
	 * @return
	 */
	public String getProperty() {
		return property;
	}

	/**
	 * @return
	 */
	public String getSubproperty() {
		return subproperty;
	}

	/**
	 * @param subproperty
	 */
	public void setSubproperty(String subproperty) {
		this.subproperty = subproperty;
	}

	/**
	 * @return
	 */
	public String getTimestamp() {
		return timestamp;
	}

	/**
	 * @param timestamp
	 */
	public void setTimestamp(String timestamp) {
		this.timestamp = timestamp;
	}
	
	/* (non-Javadoc)
	 * @see eu.aniketos.wp2.components.trustworthiness.rules.model.event.TrustEvent#getEventDescription()
	 */
	public String getEventDescription() {
		// 
		return eventDescription;
	}

	/* (non-Javadoc)
	 * @see eu.aniketos.wp2.components.trustworthiness.rules.model.event.TrustEvent#setEventDescription(java.lang.String)
	 */
	public void setEventDescription(String eventDescription) {
		// 
		this.eventDescription = eventDescription;
		
	}

}
