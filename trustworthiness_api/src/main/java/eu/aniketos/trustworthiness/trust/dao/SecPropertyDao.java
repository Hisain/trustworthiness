package eu.aniketos.trustworthiness.trust.dao;

import java.util.List;

import eu.aniketos.trustworthiness.impl.trust.pojo.SecProperty;

/**
 * data access service for security properties
 * 
 * @author Hisain Elshaafi (TSSG)
 * 
 */
public interface SecPropertyDao {

	/**
	 * @param score
	 *            Rating object to add
	 */
	public abstract void addThreatLevel(SecProperty sec);

	/**
	 * @param score
	 *            Rating object to update
	 */
	public abstract void updateSecProperty(SecProperty sec);

	/**
	 * @param serviceId
	 *            String service id
	 * @return list of existing scores for service
	 */
	public abstract List<SecProperty> getSecPropertiesByServiceId(final String serviceId);

	public abstract SecProperty getSecProperty(String serviceId,
			String secProperty);
	
	/**
	 * @param score
	 *            Rating object to delete
	 */
	public abstract void deleteSecProperty(SecProperty sec);
	


	
	
}
