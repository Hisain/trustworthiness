package eu.aniketos.wp2.components.trustworthiness.impl.rules.model.event;

import eu.aniketos.wp2.components.trustworthiness.rules.model.event.RuleConsumerRatingEvent;

/**
 * metric events class
 * 
 * @author Hisain Elshaafi (TSSG)
 *
 */
public class RuleConsumerRatingEventImpl implements RuleConsumerRatingEvent {

	private String serviceId;
	
	private String consumerId;
	
	private String transactionId;
	
	private String property;
	
	private String subproperty;
	
	// new metricValue reading causing the event
	private String metricValue;

// proposed value in contractValue
		private String contractValue;
		
		private String type;

		private String limit;
	
	private String timestamp;
	
	private String eventDescription;
	
	public RuleConsumerRatingEventImpl() {}
	
	/**
	 * @param serviceId
	 *            atomic or composite serviceId object
	 * @param property
	 *            metric's trustworthiness property
	 * @param subproperty
	 *            subcategory of the trustworthiness property if exists
	 * @param metricValue
	 *            actual metric value
	 */
	public RuleConsumerRatingEventImpl(String serviceId, String consumerId, String transactionId, String property, String subproperty,  String contractValue, String type,
			String limit, String value, String timestamp) {
		
		this.serviceId = serviceId;
		this.consumerId = consumerId;
		this.transactionId = transactionId;
		this.property = property;
		this.subproperty = subproperty;
		this.contractValue = contractValue;
		this.type = type;
		this.limit = limit;
		this.metricValue = value;
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
	
	public String getConsumerId() {
		// 
		return consumerId;
	}

	public void setConsumerId(String consumerId) {
		// 
		this.consumerId = consumerId;
	}

	public String getTransactionId() {
		// 
		return transactionId;
	}

	public void setTransactionId(String transactionId) {
		// 
		this.transactionId = transactionId;
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
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * eu.aniketos.wp2.components.trustworthiness.impl.rules.model.event.MetricEvent
	 * #getContractValue()
	 */
	public String getContractValue() {
		return contractValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * eu.aniketos.wp2.components.trustworthiness.impl.rules.model.event.MetricEvent
	 * #setContractValue(java.lang.String)
	 */
	public void setContractValue(String contractValue) {
		this.contractValue = contractValue;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * eu.aniketos.wp2.components.trustworthiness.impl.rules.model.event.MetricEvent
	 * #setType(java.lang.String)
	 */
	public void setType(String type) {
		this.type = type;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * eu.aniketos.wp2.components.trustworthiness.impl.rules.model.event.MetricEvent
	 * #getType()
	 */
	public String getType() {
		return type;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * eu.aniketos.wp2.components.trustworthiness.impl.rules.model.event.MetricEvent
	 * #setLimit(java.lang.String)
	 */
	public void setLimit(String limit) {
		this.limit = limit;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * eu.aniketos.wp2.components.trustworthiness.impl.rules.model.event.MetricEvent
	 * #getLimit()
	 */
	public String getLimit() {
		return limit;
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
