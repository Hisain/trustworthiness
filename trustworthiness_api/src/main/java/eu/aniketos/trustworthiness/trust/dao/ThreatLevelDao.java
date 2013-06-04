package eu.aniketos.trustworthiness.trust.dao;

import java.util.List;

import eu.aniketos.trustworthiness.impl.trust.pojo.ThreatLevel;

/**
 * data access service for security properties
 * 
 * @author Hisain Elshaafi (TSSG)
 * 
 */
public interface ThreatLevelDao {

	/**
	 * @param score
	 *            Rating object to add
	 */
	public abstract void addThreatLevel(ThreatLevel sec);

	/**
	 * @param score
	 *            Rating object to update
	 */
	public abstract void updateThreatLevel(ThreatLevel sec);

	/**
	 * @param serviceId
	 *            String service id
	 * @return list of existing scores for service
	 */
	public abstract List<ThreatLevel> getThreatLevelsByServiceId(final String serviceId);

	public abstract ThreatLevel getThreatLevel(String serviceId,
			String threatLevel);
	
	/**
	 * @param score
	 *            Rating object to delete
	 */
	public abstract void deleteThreatLevel(ThreatLevel sec);
	


	
	
}
