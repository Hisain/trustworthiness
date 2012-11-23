package eu.aniketos.wp2.components.trustworthiness.mock.messaging;

import eu.aniketos.wp2.components.trustworthiness.rules.model.event.TrustEvent;

/**
 * metric events class
 * 
 * @author Hisain Elshaafi (TSSG)
 *
 */
public class MetricEventImpl implements TrustEvent {

	private String serviceId;
	
	private String property;
	
	private String subproperty;
	
	// new metricValue reading causing the event
	private String metricValue;
	
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
	 *            type of metric i.e. percentage, real number, etc.
	 * @param limit
	 *            the type of limit requirement i.e. max, min, etc
	 * @param alertValue
	 *            actual metric value
	 */
	public MetricEventImpl() {}
	
	public MetricEventImpl(String serviceId, String property, String subproperty, String metricValue) {
		
		this.serviceId = serviceId;
		this.property = property;
		this.subproperty = subproperty;
		this.metricValue = metricValue;
	}
	
	public MetricEventImpl(String serviceId, String property, String subproperty, String metricValue, String timestamp) {
		
		this.serviceId = serviceId;
		this.property = property;
		this.subproperty = subproperty;
		this.metricValue = metricValue;
		this.timestamp = timestamp;
	}

	/* (non-Javadoc)
	 * @see eu.aniketos.wp2.components.trustworthiness.impl.rules.model.event.MetricEvent#getService()
	 */
	public String getServiceId() {
		return serviceId;
	}

	/* (non-Javadoc)
	 * @see eu.aniketos.wp2.components.trustworthiness.impl.rules.model.event.MetricEvent#setService(eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo.String)
	 */
	public void setServiceId(String serviceId) {
		this.serviceId = serviceId;
	}

	/* (non-Javadoc)
	 * @see eu.aniketos.wp2.components.trustworthiness.impl.rules.model.event.MetricEvent#getMetricValue()
	 */
	public String getValue() {
		return metricValue;
	}

	/* (non-Javadoc)
	 * @see eu.aniketos.wp2.components.trustworthiness.impl.rules.model.event.MetricEvent#setMetricValue(java.lang.String)
	 */
	public void setValue(String metricValue) {
		this.metricValue = metricValue;
	}
	
	/* (non-Javadoc)
	 * @see eu.aniketos.wp2.components.trustworthiness.impl.rules.model.event.MetricEvent#setProperty(java.lang.String)
	 */
	public void setProperty(String property) {
		this.property = property;
	}

	/* (non-Javadoc)
	 * @see eu.aniketos.wp2.components.trustworthiness.impl.rules.model.event.MetricEvent#getProperty()
	 */
	public String getProperty() {
		return property;
	}

	/* (non-Javadoc)
	 * @see eu.aniketos.wp2.components.trustworthiness.impl.rules.model.event.MetricEvent#getSubproperty()
	 */
	public String getSubproperty() {
		return subproperty;
	}

	/* (non-Javadoc)
	 * @see eu.aniketos.wp2.components.trustworthiness.impl.rules.model.event.MetricEvent#setSubproperty(java.lang.String)
	 */
	public void setSubproperty(String subproperty) {
		this.subproperty = subproperty;
	}

	/* (non-Javadoc)
	 * @see eu.aniketos.wp2.components.trustworthiness.rules.model.event.TrustEvent#getTimestamp()
	 */
	public String getTimestamp() {
		return timestamp;
	}

	/* (non-Javadoc)
	 * @see eu.aniketos.wp2.components.trustworthiness.rules.model.event.TrustEvent#setTimestamp(java.lang.String)
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
