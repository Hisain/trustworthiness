package eu.aniketos.wp2.components.trustworthiness.rules.model.event;


public interface TrustEvent {

	/**
	 * @return
	 */
	public abstract String getServiceId();

	/**
	 * @param serviceId
	 */
	public abstract void setServiceId(String serviceId);

	/**
	 * @return
	 */
	public abstract String getValue();

	/**
	 * @param metricValue
	 */
	public abstract void setValue(String metricValue);

	
	/**
	 * @param property
	 */
	public abstract void setProperty(String property);

	/**
	 * @return
	 */
	public abstract String getProperty();

	/**
	 * @return
	 */
	public abstract String getSubproperty();

	/**
	 * @param subproperty
	 */
	public abstract void setSubproperty(String subproperty);
	
	/**
	 * @return
	 */
	public abstract String getTimestamp();
	
	/**
	 * @param timestamp
	 */
	public abstract void setTimestamp(String timestamp);

}