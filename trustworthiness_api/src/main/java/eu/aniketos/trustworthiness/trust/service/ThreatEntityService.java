package eu.aniketos.trustworthiness.trust.service;

import java.util.List;

import eu.aniketos.trustworthiness.impl.trust.pojo.ThreatLevel;

/**
 * data access service for scores
 * 
 * @author Hisain Elshaafi (TSSG)
 * 
 */
public interface ThreatEntityService {

	/**
	 * @param score
	 *            Rating object to add
	 */
	public abstract void addThreatLevel(ThreatLevel level);

	/**
	 * @param score
	 *            Rating object to update
	 */
	public abstract void updateThreatLevel(ThreatLevel level);

	/**
	 * @param serviceId
	 *            String service id
	 * @return list of existing scores for service
	 */
	public abstract List<ThreatLevel> getThreatLevelsByServiceId(final String serviceId);
	
	public abstract ThreatLevel getThreatLevel(String serviceId, String property);

	/**
	 * @param score
	 *            Rating object to delete
	 */
	public abstract void deleteThreatLevel(ThreatLevel level);
		
	
}
