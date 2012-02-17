package eu.aniketos.wp2.components.trustworthiness.impl.rules.model.event;

import eu.aniketos.wp2.components.trustworthiness.rules.model.event.MetricEvent;
import eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo.Service;

/**
 * @author Hisain Elshaafi (TSSG)
 *
 */
public class MetricEventImpl implements MetricEvent {

	private Service service;
	private String property;
	private String subproperty;
	
	// new metricValue reading causing the event
	private String metricValue;
	
	// proposed value in contractValue
	private String contractValue;
	private String type;
	private String limit;
	
	public MetricEventImpl(Service service, String property, String subproperty, String contractValue, String type, String limit, String metricValue) {
		
		this.service = service;
		this.property = property;
		this.subproperty = subproperty;
		this.metricValue = metricValue;
		this.contractValue = contractValue;
		this.type =  type;
		this.limit = limit;
	}

	public Service getService() {
		return service;
	}

	public void setService(Service service) {
		this.service = service;
	}

	public String getMetricValue() {
		return metricValue;
	}

	public void setMetricValue(String metricValue) {
		this.metricValue = metricValue;
	}
	
	public String getContractValue() {
		return contractValue;
	}

	public void setContractValue(String contractValue) {
		this.contractValue = contractValue;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getType() {
		return type;
	}

	public void setLimit(String limit) {
		this.limit = limit;
	}

	public String getLimit() {
		return limit;
	}

	public void setProperty(String property) {
		this.property = property;
	}

	public String getProperty() {
		return property;
	}

	public String getSubproperty() {
		return subproperty;
	}

	public void setSubproperty(String subproperty) {
		this.subproperty = subproperty;
	}
	
	
}
