package eu.aniketos.wp2.components.trustworthiness.impl.rules.model.event;

import eu.aniketos.wp2.components.trustworthiness.rules.model.event.SREvent;
import eu.aniketos.wp2.components.trustworthiness.impl.trust.pojo.Service;

/**
 * alert events class
 * 
 * @author Hisain Elshaafi (TSSG)
 * 
 */
public class AlertEventImpl implements SREvent {

	private Service service;
	 
	private String property;
	
	private String subproperty;

	// new alertValue reading causing the event
	private String alertValue;

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
	 *            type of alert i.e. percentage, real number, etc.
	 * @param limit
	 *            the type of limit requirement i.e. max, min, etc
	 * @param alertValue
	 *            actual alert value
	 */
	public AlertEventImpl(Service service, String property, String subproperty,
			String contractValue, String type, String limit, String alertValue) {

		this.service = service;
		this.property = property;
		this.subproperty = subproperty;
		this.alertValue = alertValue;
		this.contractValue = contractValue;
		this.type = type;
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
	public String getAlertValue() {
		return alertValue;
	}

	/**
	 * @param alertValue
	 */
	public void setAlertValue(String alertValue) {
		this.alertValue = alertValue;
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
