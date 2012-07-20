package eu.aniketos.wp2.components.trustworthiness.impl.rules.model.event;

import eu.aniketos.wp2.components.trustworthiness.rules.model.event.MetricEvent;
import eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo.Service;

/**
 * metric events class
 * 
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
	
	/**
	 * @param service
	 *            atomic or composite service object
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
	public MetricEventImpl(Service service, String property, String subproperty, String contractValue, String type, String limit, String metricValue) {
		
		this.service = service;
		this.property = property;
		this.subproperty = subproperty;
		this.metricValue = metricValue;
		this.contractValue = contractValue;
		this.type =  type;
		this.limit = limit;
	}

	/**
	 * @return
	 */
	public Service getService() {
		return service;
	}

	/**
	 * @param service
	 */
	public void setService(Service service) {
		this.service = service;
	}

	/**
	 * @return
	 */
	public String getMetricValue() {
		return metricValue;
	}

	/**
	 * @param metricValue
	 */
	public void setMetricValue(String metricValue) {
		this.metricValue = metricValue;
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
	
	
}
