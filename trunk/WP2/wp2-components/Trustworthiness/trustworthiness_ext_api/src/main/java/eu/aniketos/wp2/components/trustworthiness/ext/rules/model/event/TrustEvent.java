package eu.aniketos.wp2.components.trustworthiness.ext.rules.model.event;

/**
 * @author Hisain Elshaafi 
 * Interface for external and internal events indicating
 *         possible change in trustworthiness
 */
public interface TrustEvent {

	/**
	 * @return serviceId unique service id
	 */
	public abstract String getServiceId();

	/**
	 * @param serviceId
	 */
	public abstract void setServiceId(String serviceId);

	/**
	 * @return value event's new property value
	 */
	public abstract String getValue();

	/**
	 * @param value
	 *            event's new property value
	 */
	public abstract void setValue(String metricValue);

	/**
	 * @param property
	 *            name or id of service property
	 */
	public abstract void setProperty(String property);

	/**
	 * @return property 
	 * 			  name or id of service property
	 */
	public abstract String getProperty();

	/**
	 * @return subproperty 
	 * 			  name or id of subcategory of service property
	 */
	public abstract String getSubproperty();

	/**
	 * @param subproperty
	 *            name or id of subcategory of service property
	 */
	public abstract void setSubproperty(String subproperty);

	/**
	 * @return timestamp 
	 * 			  of ISO 8601 format
	 */
	public abstract String getTimestamp();

	/**
	 * @param timestamp
	 *            of ISO 8601 format
	 */
	public abstract void setTimestamp(String timestamp);

	/**
	 * @return eventDescription 
	 * 			  event description, comment, info, etc
	 */
	public abstract String getEventDescription();

	/**
	 * @param eventDescription
	 *            event description, comment, info, etc
	 */
	public abstract void setEventDescription(String eventDescription);

}